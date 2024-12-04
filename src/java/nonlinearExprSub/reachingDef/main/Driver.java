package nonlinearExprSub.reachingDef.main;

import org.eclipse.jdt.core.dom.*;
import nonlinearExprSub.reachingDef.Constraint.Constraint;
import nonlinearExprSub.reachingDef.Solving.ConstraintSolver;
import nonlinearExprSub.reachingDef.visitor.ConstraintVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Driver {

    public static void main(String[] args) throws IOException {

        File file = new File("./tests/StatementSequence.java");
        String source = new String(Files.readAllBytes(file.toPath()));
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);


        ConstraintVisitor visitor = new ConstraintVisitor();
        cu.accept(visitor);

        // TODO: Need to use the same cu for rewriting.


        Set<String> variables = new HashSet<String>();

        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(SimpleName node) {
                if (node.getLocationInParent() == TypeDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == MethodDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == SingleVariableDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == QualifiedName.NAME_PROPERTY ||
                        node.getLocationInParent() == QualifiedName.QUALIFIER_PROPERTY ||
                        node.getLocationInParent() == PackageDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == SimpleType.NAME_PROPERTY ||
                        node.getLocationInParent() == ImportDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == TypeParameter.NAME_PROPERTY) {
                    return true;
                }
                variables.add(node.getIdentifier());
                return true;
            }
        });

        System.out.println(" ------------- \n| Constraints |\n ------------- ");
        ArrayList<Constraint> constraints = visitor.getConstraints();

        int i = 0;
        for (Constraint constraint : constraints) {
            System.out.println(++i + ") " + constraint);
        }

        System.out.println();

        System.out.println(" ------------  \n| Constraint |\n| Solutions  |\n ------------  ");
        ConstraintSolver solver = new ConstraintSolver(constraints, variables);

        solver.buildConstraintGraph();

        solver.initializeDefinitionSet();

        solver.processWorkList();

    }
}

