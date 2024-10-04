
package nonlinearExprSub.reachingDef.visitor;

import org.eclipse.jdt.core.dom.*;
import nonlinearExprSub.reachingDef.Constraint.Constraint;
import nonlinearExprSub.reachingDef.Constraint.SubsetOperator;
import nonlinearExprSub.reachingDef.Constraint.Term.ConstraintTerm;
import nonlinearExprSub.reachingDef.Constraint.Term.DefinitionLiteral;
import nonlinearExprSub.reachingDef.Constraint.Term.SetDifference;
import nonlinearExprSub.reachingDef.ConstraintCreator.ConstraintTermFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This visitor creates a map from statement nodes to entry labels
 * (contain the information of what expressions are available
 * upon entry to the statement).
 */
public class ConstraintVisitor extends ASTVisitor {
    private ArrayList<Constraint> constraints;
    private ConstraintTermFactory variableFactory;

    public ConstraintTermFactory getVariableFactory() {
        return variableFactory;
    }

    public ConstraintVisitor() {
        variableFactory = new ConstraintTermFactory();
        constraints  = new ArrayList<>();
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        List<ASTNode> exitStmts = new ArrayList<>();
        BlockVisitor visitor = new BlockVisitor(exitStmts);
        node.accept(visitor);
        return false;
    }


    public class BlockVisitor extends ASTVisitor {
        private List<ASTNode> exitStmts;

        public BlockVisitor(List<ASTNode> blockPrev) {
            exitStmts = new ArrayList<>();
            for (ASTNode p : blockPrev) {
                exitStmts.add(p);
            }
        }

        @Override
        public boolean visit(Assignment node) {
            ASTNode parent = node.getParent();

            variableFactory.createEntryLabel(parent);
            variableFactory.createExitLabel(parent);
            return true;
        }

        @Override
        public void endVisit(Assignment node) {
            List<Constraint> result = new ArrayList<Constraint>();

            ASTNode parent = node.getParent();

            ConstraintTerm entry = variableFactory.createEntryLabel(parent);
            ConstraintTerm exit = variableFactory.createExitLabel(parent);


            Expression lhs = node.getLeftHandSide();

            // if lhs isn't a simple name, it isn't a local variable
            // TODO: check here for symbolic variables that we want to track
            if (lhs.getNodeType() != ASTNode.SIMPLE_NAME) {
                return;
            }

            SimpleName name = (SimpleName) lhs;

            // parent is expression statement (wrapper for assignments)
            DefinitionLiteral defWild = variableFactory.createDefinitionWildcard(name.getIdentifier());
            SetDifference setDiff = getSetDiff(entry, defWild);
            variableFactory.setEntryLabel(parent, setDiff);

            ConstraintTerm def = variableFactory.createDefinition(name.getIdentifier(), parent); // (v,v=e)

            result.add(newSubsetConstraint(def, exit));
            result.add(newSubsetConstraint(setDiff, exit));

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, variableFactory.createEntryLabel(parent)));
                }
            }

            exitStmts.clear();
            exitStmts.add(parent);

            constraints.addAll(result);
        }

        @Override
        public boolean visit(DoStatement node){
            //*****************//
            //   do statement  //
            //*****************//
            List<Constraint> result = new ArrayList<Constraint>();

            ConstraintTerm entry = variableFactory.createEntryLabel(node);
            ConstraintTerm exit = variableFactory.createExitLabel(node);

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, entry));
                }
            }

            result.add(newSubsetConstraint(entry, exit));

            exitStmts.clear();
            exitStmts.add(node);

            //***************//
            //   condition   //
            //***************//
            Expression cond = node.getExpression();

            ConstraintTerm condEntry = variableFactory.createEntryLabel(cond);
            ConstraintTerm condExit = variableFactory.createExitLabel(cond);

            result.add(newSubsetConstraint(condEntry, condExit));

            exitStmts.add(cond);

            //************//
            //    body    //
            //************//
            Statement body = node.getBody();
            List<ASTNode> bodyExitStmts = new ArrayList<>();

            if(body instanceof Block && ((Block) body).statements().size() == 0) {
                result.add(newSubsetConstraint(condEntry, exit));

            } else {
                BlockVisitor visitor = new BlockVisitor(exitStmts);
                body.accept(visitor);
                bodyExitStmts = visitor.getExitStmts();

                if (bodyExitStmts.equals((exitStmts))) {
                    bodyExitStmts.clear();
                }
            }

            exitStmts.clear();

            for (ASTNode stmt : bodyExitStmts) {
                exitStmts.add(stmt);
            }

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, condEntry));
                }
            }

            exitStmts.clear();
            exitStmts.add(cond);

            constraints.addAll(result);

            return false;
        }

        @Override
        public boolean visit(EnhancedForStatement node) {
            //*******************//
            // foreach statement //
            //*******************//
            ConstraintTerm entry = variableFactory.createEntryLabel(node);
            ConstraintTerm exit = variableFactory.createExitLabel(node);

            List<Constraint> result = new ArrayList<Constraint>();

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, entry));
                }
            }

            result.add(newSubsetConstraint(entry, exit));

            exitStmts.clear();
            exitStmts.add(node);

            constraints.addAll(result);

            //************//
            //    body    //
            //************//
            Statement body = node.getBody();

            BlockVisitor visitor = new BlockVisitor(exitStmts);
            body.accept(visitor);

            List<ASTNode>  bodyExitStmts = visitor.getExitStmts();

            // empty while body
            if (bodyExitStmts.equals((exitStmts))) {
                bodyExitStmts.clear();
            }

            for (ASTNode stmt : bodyExitStmts) {
                exitStmts.add(stmt);
            }
            return false;
        }


        @Override
        public boolean visit(ForStatement node){
            //*******************//
            //   for statement   //
            //*******************//
            ConstraintTerm entry = variableFactory.createEntryLabel(node);
            ConstraintTerm exit = variableFactory.createExitLabel(node);

            List<Constraint> result = new ArrayList<Constraint>();

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, entry));
                }
            }

            result.add(newSubsetConstraint(entry, exit));

            //*******************//
            //   initialization  //
            //*******************//
            Expression init = (Expression) node.initializers().get(0);

            ConstraintTerm initEntry = variableFactory.createEntryLabel(init);
            ConstraintTerm initExit = variableFactory.createExitLabel(init);

            result.add(newSubsetConstraint(exit, initEntry));
            result.add(newSubsetConstraint(initEntry, initExit));


            //****************//
            //   condition    //
            //****************//
            Expression cond = node.getExpression();

            ConstraintTerm condEntry = variableFactory.createEntryLabel(cond);
            ConstraintTerm condExit = variableFactory.createExitLabel(cond);

            result.add(newSubsetConstraint(initExit, condEntry));
            result.add(newSubsetConstraint(condEntry, condExit));

            exitStmts.clear();
            exitStmts.add(cond);

            //***********//
            //   body    //
            //***********//
            Statement body = node.getBody();

            BlockVisitor visitor = new BlockVisitor(exitStmts);
            body.accept(visitor);

            List<ASTNode> bodyExitStmts = visitor.getExitStmts();
            exitStmts.clear();

            for (ASTNode stmt : bodyExitStmts) {
                exitStmts.add(stmt);
            }

            //*************//
            //   update    //
            //*************//
            Expression update = (Expression) node.updaters().get(0);

            ConstraintTerm updateEntry = variableFactory.createEntryLabel(update);
            ConstraintTerm updateExit = variableFactory.createExitLabel(update);

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, updateEntry));
                }
            }

            result.add(newSubsetConstraint(updateEntry, updateExit));
            result.add(newSubsetConstraint(updateExit, condEntry));

            // condition will flow to the statement after the for loop
            exitStmts.clear();
            exitStmts.add(cond);

            constraints.addAll(result);

            return false;
        }

        @Override
        public boolean visit(IfStatement node) {
            List<Constraint> result = new ArrayList<Constraint>();

            //**************//
            // if statement //
            //**************//
            ConstraintTerm entry = variableFactory.createEntryLabel(node);
            ConstraintTerm exit = variableFactory.createExitLabel(node);

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, entry));
                }
            }
            result.add(newSubsetConstraint(entry, exit));

            //**************//
            //  condition   //
            //**************//
            Expression cond = node.getExpression();

            ConstraintTerm condEntry = variableFactory.createEntryLabel(cond);
            ConstraintTerm condExit = variableFactory.createExitLabel(cond);

            result.add(newSubsetConstraint(condEntry, condExit));
            result.add(newSubsetConstraint(exit, condEntry));

            exitStmts.clear();
            exitStmts.add(cond);

            //**************//
            //    body      //
            //**************//
            Statement thenBlock = node.getThenStatement();
            Statement elseBlock = node.getElseStatement();

            BlockVisitor visitor = new BlockVisitor(exitStmts);
            thenBlock.accept(visitor);

            List<ASTNode> thenBlockExit = visitor.getExitStmts();
            List<ASTNode> elseBlockExit = new ArrayList<>();

            // then block was empty, nothing new added or deleted
            if (thenBlockExit.equals(exitStmts)) {
                thenBlockExit.clear();
            }

            if(elseBlock != null){
                BlockVisitor elseVisitor = new BlockVisitor(exitStmts);
                elseBlock.accept(elseVisitor);

                elseBlockExit = elseVisitor.getExitStmts();

                // control flow will go to 'then' or 'else' - we can remove the conditional
                exitStmts.clear();
            }

            for (ASTNode stmt : thenBlockExit) {
                exitStmts.add(stmt);
            }

            for (ASTNode stmt : elseBlockExit) {
                exitStmts.add(stmt);
            }

            constraints.addAll(result);

            return false;
        }


        @Override
        public boolean visit(MethodInvocation node) {
            if (!(node.getParent() instanceof ExpressionStatement)) {
                return false;
            }

            ASTNode parent = node.getParent();

            variableFactory.createEntryLabel(parent);
            variableFactory.createExitLabel(parent);
            return true;
        }

        @Override
        public void endVisit(MethodInvocation node) {
            List<Constraint> result = new ArrayList<Constraint>();

            ASTNode parent = node.getParent();

            ConstraintTerm entry = variableFactory.createEntryLabel(parent);
            ConstraintTerm exit = variableFactory.createExitLabel(parent);

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, entry));
                }
            }

            result.add(newSubsetConstraint(entry, exit));

            exitStmts.clear();
            exitStmts.add(parent);

            constraints.addAll(result);
        }

        @Override
        public boolean visit(PostfixExpression node) {
            if (!(node.getParent() instanceof ExpressionStatement)) {
                return false;
            }
            variableFactory.createEntryLabel(node);
            variableFactory.createExitLabel(node);
            return true;
        }

        @Override
        public void endVisit(PostfixExpression node) {
            List<Constraint> result = new ArrayList<Constraint>();

            ConstraintTerm entry = variableFactory.createEntryLabel(node);
            ConstraintTerm exit = variableFactory.createExitLabel(node);

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(entry, prevExit));
                }
            }

            result.add(newSubsetConstraint(exit, entry));

            exitStmts.clear();
            exitStmts.add(node);

            constraints.addAll(result);
        }

        @Override
        public boolean visit(PrefixExpression node) {
            if (!(node.getParent() instanceof ExpressionStatement)) {
                return false;
            }
            variableFactory.createEntryLabel(node);
            variableFactory.createExitLabel(node);
            return true;
        }

        @Override
        public void endVisit(PrefixExpression node) {
            List<Constraint> result = new ArrayList<Constraint>();

            ConstraintTerm entry = variableFactory.createEntryLabel(node);
            ConstraintTerm exit = variableFactory.createExitLabel(node);

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(entry, prevExit));
                }
            }

            result.add(newSubsetConstraint(exit, entry));

            exitStmts.clear();
            exitStmts.add(node);

            constraints.addAll(result);
        }

        @Override
        public boolean visit(VariableDeclarationStatement node) {
            variableFactory.createEntryLabel(node);
            variableFactory.createExitLabel(node);
            return true;
        }

        @Override
        public void endVisit(VariableDeclarationStatement node) {
            List<Constraint> result = new ArrayList<Constraint>();

            ConstraintTerm entry = variableFactory.createEntryLabel(node);
            ConstraintTerm exit = variableFactory.createExitLabel(node);

            VariableDeclarationFragment fragment = ((List<VariableDeclarationFragment>)
                    node.fragments()).get(0);

            Expression lhs = fragment.getName();
            Expression rhs = fragment.getInitializer();

            // if lhs isn't a simple name, it isn't a local variable
            // TODO: check here for symbolic variables
            if (lhs.getNodeType() != ASTNode.SIMPLE_NAME || rhs == null) {
                return;
            }

            SimpleName name = (SimpleName) lhs;

            ConstraintTerm assignEntry = variableFactory.createEntryLabel(node); // RD_entry[v=e]
            DefinitionLiteral defWild = variableFactory.createDefinitionWildcard(name.getIdentifier());
            ConstraintTerm setDiff = getSetDiff(assignEntry, defWild);
            variableFactory.setEntryLabel(node, setDiff);

            ConstraintTerm assignExit = variableFactory.createExitLabel(node); // RD_exit[v=e]
            ConstraintTerm def = variableFactory.createDefinition(name.getIdentifier(), node); // (v,v=e)

            result.add(newSubsetConstraint(def, assignExit));
            result.add(newSubsetConstraint(setDiff, assignExit));

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, variableFactory.createEntryLabel(node)));

                }
            }

            exitStmts.clear();
            exitStmts.add(node);

            constraints.addAll(result);
        }

        @Override
        public boolean visit(WhileStatement node) {
            List<Constraint> result = new ArrayList<Constraint>();

            //*******************//
            //  while statement  //
            //*******************//
            ConstraintTerm entry = variableFactory.createEntryLabel(node);
            ConstraintTerm exit = variableFactory.createExitLabel(node);

            if(!exitStmts.isEmpty()){
                for (ASTNode stmt : exitStmts) {
                    ConstraintTerm prevExit = variableFactory.createExitLabel(stmt);
                    result.add(newSubsetConstraint(prevExit, entry));
                }
            }

            result.add(newSubsetConstraint(entry, exit));

            //***************//
            //  condition    //
            //***************//
            Expression cond = node.getExpression();

            ConstraintTerm condEntry = variableFactory.createEntryLabel(cond);
            ConstraintTerm condExit = variableFactory.createExitLabel(cond);


            result.add(newSubsetConstraint(exit, condEntry));
            result.add(newSubsetConstraint(condEntry, condExit));

            exitStmts.clear();
            exitStmts.add(cond);

            //**************//
            //    body      //
            //**************//
            Statement body = node.getBody();

            BlockVisitor visitor = new BlockVisitor(exitStmts);
            body.accept(visitor);

            List<ASTNode> bodyExit = visitor.getExitStmts();

            // empty while body
            if (bodyExit.equals((exitStmts))) {
                bodyExit.clear();
            }

            for (ASTNode stmt : bodyExit) {
                ConstraintTerm exitStmt = variableFactory.createExitLabel(stmt);
                result.add(newSubsetConstraint(exitStmt, condEntry));
            }

            constraints.addAll(result);

            return false;
        }

        public Constraint newSubsetConstraint(ConstraintTerm l, ConstraintTerm r) {
            return new Constraint(l, new SubsetOperator(), r);
        }

        public SetDifference getSetDiff(ConstraintTerm t1, DefinitionLiteral t2) {
            return new SetDifference(t1, t2); // temporary
        }



        public List<ASTNode> getExitStmts() {
            return exitStmts;
        }

    }

        public ArrayList<Constraint> getConstraints() {
            return constraints;
        }

}
