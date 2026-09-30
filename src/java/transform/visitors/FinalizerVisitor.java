package transform.visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;

import util.AnalyzedFile;
import util.AnalyzedMethod;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeTable;
import transform.TypeChecking.TypeChecker.CType;
import util.ASTUtils;
import util.TypeResolutionUtils;

/**
 * The last visitor to be called. Two major tasks:
 * 1. Generates the main method that calls all of the methods that were found in the AST.
 * 2. Obtains statistics about the number of expressions, operations in expressions, conditions, and parameters. These are used for filtering and analysis post-transformation.
 */
public class FinalizerVisitor extends ASTVisitor {
	
	private AnalyzedMethod currAnalyzedMethod;
	private final AnalyzedFile af;
	
	private final int minTypeExpr;
	private final int minTypeCond;
	private final int minTypeParams;
	private final TypeTable typeTable;
	private final CType type;
	private int operationsInExpression;
	private final ASTRewrite rewriter;
	private final AST ast;
	private final TypeChecker typeChecker;

	
	public FinalizerVisitor(AST ast, ASTRewrite rewriter, TypeChecker typeChecker, AnalyzedFile af, TypeTable typeTable, int minTypeExpr, int minTypeCond, int minTypeParams, CType type) {
		this.af = af;
		this.typeTable = typeTable;
		this.minTypeExpr = minTypeExpr;
		this.minTypeCond = minTypeCond;
		this.minTypeParams = minTypeParams;
		this.type = type;
		this.ast = ast;
		this.rewriter = rewriter;
		this.typeChecker = typeChecker;
		operationsInExpression = 0;
		//System.out.println("Done setting Finilizer Visitor");
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		//System.out.println("Type " + node.getName());
		return true;
	}
	
	/**
     * Generates a new main method that invokes all of the suitable methods found in the AST.
     * Escapes from our current symbolTable scope. Does not pop on interfaces because we do not enter the scope of interfaces.
     */
    @Override
    public void endVisit(TypeDeclaration node) {
        // Detect our constructor
        MethodDeclaration constructor = null;
        for (Object memberObj : node.bodyDeclarations()) {
            if (memberObj instanceof MethodDeclaration) {
                MethodDeclaration methodDecl = (MethodDeclaration) memberObj;
                if (methodDecl.isConstructor()) {
                    constructor = methodDecl;
                }
            }
        }
        
         // Create the main method declaration.
        if (node.getParent() instanceof CompilationUnit) {
            MethodDeclaration mainMethod = ast.newMethodDeclaration();
            mainMethod.setName(ast.newSimpleName("main"));
            mainMethod.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
            mainMethod.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));
            mainMethod.thrownExceptionTypes().add(ast.newSimpleType(ast.newSimpleName("Exception")));
            mainMethod.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));

            // Create the String[] args parameter.
            SingleVariableDeclaration param = ast.newSingleVariableDeclaration();
            ArrayType arrayType = ast.newArrayType(ast.newSimpleType(ast.newSimpleName("String")));
            param.setType(arrayType);
            param.setName(ast.newSimpleName("args"));
            mainMethod.parameters().add(param);

            Block mainBlock = ast.newBlock();
            mainMethod.setBody(mainBlock);
            
            // add comment above main method
            Statement commentPlaceholder = (Statement) rewriter.createStringPlaceholder("/** This main was generated by ARG-V */\n", ASTNode.EMPTY_STATEMENT);
			rewriter.getListRewrite(mainMethod, MethodDeclaration.MODIFIERS2_PROPERTY).insertFirst(commentPlaceholder, null);

            // Collect all methods to invoke (skip constructors and any existing main).
            boolean needsInstance = false;
            List<MethodDeclaration> methodDeclarations = new ArrayList<>();
            for (Object memberObj : node.bodyDeclarations()) {
                if (memberObj instanceof MethodDeclaration) {
                    MethodDeclaration methodDecl = (MethodDeclaration) memberObj;
                    if (!methodDecl.isConstructor() && !methodDecl.getName().getIdentifier().equals("main")) {
                        methodDeclarations.add(methodDecl);
                        // If any method is non-static, we will need an instance.
                        if (!Modifier.isStatic(methodDecl.getModifiers())) {
                            needsInstance = true;
                        }
                    }
                }
            }

            // If at least one non-static method exists, create an instance using the no-arg constructor.
            if (needsInstance) {
                // Creates: ClassName instance = new ClassName();
                VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
                fragment.setName(ast.newSimpleName("instance"));
                ClassInstanceCreation cic = ast.newClassInstanceCreation();
                if (constructor != null) {
                    for (SingleVariableDeclaration paramObj : (List<SingleVariableDeclaration>) constructor.parameters()) {
                        ITypeBinding normalizedType = ASTUtils.getNormalizedBinding(paramObj);
                        Expression expr = TypeResolutionUtils.createSymbolicArgument(normalizedType, ast, false);
                        if (expr instanceof NullLiteral && typeChecker.allowedType(normalizedType)) {
                            Type newType = ASTUtils.newTypeFromBinding(ast, normalizedType);
                            CastExpression cast = ast.newCastExpression();
                            cast.setExpression((Expression) ASTNode.copySubtree(ast, expr));
                            cast.setType(newType);
                            cic.arguments().add(cast);
                        } else {
                            cic.arguments().add(expr);
                        }
                    }
                }
                cic.setType(ast.newSimpleType(ast.newSimpleName(node.getName().getIdentifier())));
                fragment.setInitializer(cic);

                VariableDeclarationStatement instanceDecl = ast.newVariableDeclarationStatement(fragment);
                instanceDecl.setType(ast.newSimpleType(ast.newSimpleName(node.getName().getIdentifier())));
                mainBlock.statements().add(instanceDecl);
            }

            // For each method, create a method invocation statement with symbolic arguments.
            for (MethodDeclaration methodDecl : methodDeclarations) {
                MethodInvocation invocation = ast.newMethodInvocation();
                invocation.setName(ast.newSimpleName(methodDecl.getName().getIdentifier()));

                // If the method is non-static, invoke it on the instance.
                if (!Modifier.isStatic(methodDecl.getModifiers())) {
                    invocation.setExpression(ast.newSimpleName("instance"));
                }

                // Process each parameter of the method.
                for (Object paramObj : methodDecl.parameters()) {
                    if (paramObj instanceof SingleVariableDeclaration) {
                        SingleVariableDeclaration svd = (SingleVariableDeclaration) paramObj;
                        ITypeBinding normalizedType = ASTUtils.getNormalizedBinding(svd);
                        Expression arg = TypeResolutionUtils.createSymbolicArgument(normalizedType, ast, false);
                        if (arg instanceof NullLiteral && typeChecker.allowedType(normalizedType)) {
                            Type newType = ASTUtils.newTypeFromBinding(ast, normalizedType);
                            CastExpression cast = ast.newCastExpression();
                            cast.setExpression((Expression) ASTNode.copySubtree(ast, arg));
                            cast.setType(newType);
                            invocation.arguments().add(cast);
                        } else {
                            invocation.arguments().add(arg);
                        }
                    }
                }

                ExpressionStatement invocationStmt = ast.newExpressionStatement(invocation);
                mainBlock.statements().add(invocationStmt);
            }

            // Insert the newly created main method into the class.
            rewriter.getListRewrite(node, TypeDeclaration.BODY_DECLARATIONS_PROPERTY)
                    .insertLast(mainMethod, null);
        }
    }
    
    @Override
    public boolean visit(FieldDeclaration node) {
	    return false; // do not analyze characteristics in field declarations
    }

	@Override
	public boolean visit(MethodDeclaration node) {
		AnalyzedMethod am = new AnalyzedMethod(node);
		af.addMethod(am);

		checkParameterTypes(am, node);
		currAnalyzedMethod = am;

		return true;
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		//System.out.println("Visiting " + node.getName());
		AnalyzedMethod m = currAnalyzedMethod;
		System.out.println("Oper " + m.getTypeOperationCount());
		System.out.println("Cond " + m.getTypeConditionalCount());
		System.out.println("Par " + m.getTypeParameterCount());
		
		if(m.getTypeConditionalCount() < minTypeCond || 
				m.getTypeOperationCount() < minTypeExpr || 
				m.getTypeParameterCount() < minTypeParams) {
			System.out.println("Commenting out " + node.getName());
			
		} else {
			af.addSuitableMethod(m);
			System.out.println("Suitable method " + node.getName());
		}
		
	}

	@Override
	public boolean visit(Block node) {
//		if (!blockStack.empty()) {
//			HashSet<String> liveIntVariables = blockStack.peek();
//			@SuppressWarnings("unchecked")
//			HashSet<String> localVarsClone = (HashSet<String>) liveIntVariables.clone();
//			blockStack.push(localVarsClone);
//		} else {
//			blockStack.push(new HashSet<>());
//		}
		return true;
	}

//	@Override
//	public void endVisit(Block node) {
//		blockStack.pop();
//	}
	
	
	@Override
	public boolean visit(IfStatement node) {
		//eas we need to check whether the expression is of int type
		//do we do it here or somewhere else?
		Expression e = node.getExpression();
		boolean hasType = hasType(e);
		//System.out.println(e.getClass() + " " + hasType + " " + typeTable.getNodeType(e));
		//try to visit it and find out whether it has integer exprssions?
		//it can be 1) Boolean expression, 2) Infix expression, 3) Conditional expression
		// other expressions that can return boolean value
		//let's focus on infix expressions
		// let's not -- just check whether the expression has required type
		// that is operands there have type for which analysis is built
	//--	if(e instanceof InfixExpression) {
//			InfixExpression infE = (InfixExpression) e;
//			Expression lE = infE.getLeftOperand();
//			Type t = typeTable.getNodeType(lE);
//			if(TypeChecker.checkType(t) == type) {
//				currAnalyzedMethod.setHasConditional(true);
//			}
			//remember the count before
			if(hasType) {
				currAnalyzedMethod.setConditionalCount(currAnalyzedMethod.getTypeConditionalCount()+1);
				
			}
			
		//--}
	
		//then the visitor will go into expression and count
		//what it needs to count
		return true;
	}
	
	private boolean hasType(Expression e) {
		boolean ret = false;
		if(e instanceof InfixExpression) {
			InfixExpression infE = (InfixExpression) e;
			Expression lE = infE.getLeftOperand();
			Expression rE = infE.getRightOperand();
			Type lT = typeTable.getNodeType(lE);
			Type rT = typeTable.getNodeType(rE);
			if(TypeChecker.checkType(lT) == type || TypeChecker.checkType(rT) == type) {
				ret = true;
			} else if (TypeChecker.isBooleanType(rT) || TypeChecker.isBooleanType(lT)) {
				//call again since it might be just a complex expression
				ret = hasType(lE) || hasType(rE);
			}
		}  else {
			//if it is not an infix expression then it should
			//be some single var of a boolean type
			Type vT = typeTable.getNodeType(e);
			if(TypeChecker.isBooleanType(vT)) {
				//System.out.println("Just a var");
				ret = true;
			}
		}
		return ret;
	}
	
	@Override
	public boolean visit(ForStatement node) {
		// To handle scope of local variables
		return true;
	}





	@Override
	public boolean visit(InfixExpression node) {
		//expressionsStack.push(node);

		//System.out.println(" n " + node + "\t" + typeTable.getNodeType(node));
		Expression lE = node.getLeftOperand();
		Type lT = typeTable.getNodeType(lE);
		Expression rE = node.getRightOperand();
		Type rT = typeTable.getNodeType(rE);
		//if the parent node is not a boolean type (no logical connections)
		//and if lhs and rhs of the required type than its op should some
		//kind of numerical operator
		if((TypeChecker.checkType(lT) == type || TypeChecker.checkType(rT) == type)) {
			
			//does it matter what type of operand is it?
		Operator op = node.getOperator();
//		if (op == Operator.PLUS ||
//				op == Operator.MINUS ||
//				op == Operator.DIVIDE ||
//				op == Operator.TIMES ||
//				op == Operator.REMAINDER) {
			// so we count that operations
			//if the type is int
		//System.out.println(typeTable.getNodeType(node) +"\t" + lT + "\t" + rT);
		Type nodeType = typeTable.getNodeType(node);
		if(!TypeChecker.isBooleanType(nodeType)) {
			//System.out.println("op " + op);
				operationsInExpression++;  
		}
			//}
		} else if( !TypeChecker.isBooleanType(lT) && !TypeChecker.isBooleanType(rT)){
			// no need to go further if lhs is not of int type and not boolean
			//since a condition might use logical constructs to build complex expressions
			return false;
		}
		return true;
	}

	@Override
	public void endVisit(InfixExpression node) {
		//expressionsStack.pop();
		if(operationsInExpression > 0 && currAnalyzedMethod != null) {
			//why do we just add one? need to add all of them
			//currAnalyzedMethod.setTypeOperationCount(currAnalyzedMethod.getTypeOperationCount()+1);
			currAnalyzedMethod.setTypeOperationCount(currAnalyzedMethod.getTypeOperationCount()+
					operationsInExpression);
				operationsInExpression = 0;
		}
	}

	@Override
	public boolean visit(PrefixExpression node) {
		//expressionsStack.push(node);
		//System.out.println("Prefix " + node);
		Expression operand = node.getOperand();
		CType tOp = TypeChecker.checkType(typeTable.getNodeType(operand));
		if( tOp == type) {
			//so it is integer
			operationsInExpression++;
			//System.out.println("preixCount");
		} else {
			//no need to go if it is not an int
			return false;
		}

		return true;
	}

	@Override
	public void endVisit(PrefixExpression node) {
		//expressionsStack.pop();
		if(operationsInExpression > 0) {
			currAnalyzedMethod.setTypeOperationCount(currAnalyzedMethod.getTypeOperationCount()+operationsInExpression);
				operationsInExpression = 0;
			}
			
	}

	@Override
	public boolean visit(PostfixExpression node) {
		//expressionsStack.push(node);
		//HashSet<String> liveIntVariables = blockStack.peek();
		Expression operand = node.getOperand();
		//System.out.println("postfix " + node);
		CType tOp = TypeChecker.checkType(typeTable.getNodeType(operand));
		if(tOp == type) {
			operationsInExpression++;
			//System.out.println("postfix");
		} else {
			//no need to go in if it is not an int
			return false;
		}

		return true;
	}

	@Override
	public void endVisit(PostfixExpression node) {
		//expressionsStack.pop();
		if(operationsInExpression > 0) {
			currAnalyzedMethod.setTypeOperationCount(currAnalyzedMethod.getTypeOperationCount()+operationsInExpression);
		operationsInExpression = 0;
			}
			


}

private void checkParameterTypes(AnalyzedMethod am, MethodDeclaration node) {
	List<SingleVariableDeclaration> parameters = node.parameters();
	int typeParams = 0;
	for (SingleVariableDeclaration parameter : parameters) {
		CType parType = TypeChecker.checkType(typeTable.getNodeType(parameter));
		if(parType == type) {
		  typeParams++;
		}
	}
	//found all parameters of a particular type
	am.setTypeParameterCount(typeParams);
}

}
