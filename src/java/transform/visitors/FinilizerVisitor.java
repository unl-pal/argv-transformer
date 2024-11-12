package transform.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import sourceAnalysis.AnalyzedFile;
import sourceAnalysis.AnalyzedMethod;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeTable;
import transform.TypeChecking.TypeChecker.CType;

public class FinilizerVisitor extends ASTVisitor {
	
	private AnalyzedMethod currAnalyzedMethod;
	private AnalyzedFile af;
	
	private int minTypeExpr;
	private int minTypeCond;
	private int minTypeParams;
	private TypeTable typeTable;
	private CType type;
	private int operationsInExpression;

	
	public FinilizerVisitor(AnalyzedFile af, TypeTable typeTable, int minTypeExpr, int minTypeCond, int minTypeParams, CType type) {
		this.af = af;
		this.typeTable = typeTable;
		this.minTypeExpr = minTypeExpr;
		this.minTypeCond = minTypeCond;
		this.minTypeParams = minTypeParams;
		this.type = type;
		operationsInExpression = 0;
		//System.out.println("Done setting Finilizer Visitor");
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		//System.out.println("Type " + node.getName());
		return true;
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		
		return false;
	}

	@Override
	public boolean visit(Initializer node) {
		return false;
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		return false;
	}

	@Override
	public boolean visit(MarkerAnnotation node) {
		return false;
	}

	@Override
	public boolean visit(SingleMemberAnnotation node) {
		return false;
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		return false;
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
		int intOpCount = m.getTypeOperationCount();
		if(intOpCount > 0) {
			m.setHasTypeOperations(true);
		}
		
		if(m.getTypeConditionalCount() > 0) {
			m.setHasTypeConditional(true);
		}
		
		if(m.getTypeParameterCount() > 0) {
			m.setHasOnlyTypeParameters(true);
		}
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
	public void endVisit(IfStatement node) {
		//System.out.println("done visiting");
	}

	/*
	 * What about while statement?
	 */
	
	@Override
	public boolean visit(ForStatement node) {
		currAnalyzedMethod.setHasLoop(true);
		// To handle scope of local variables
//		if (!blockStack.empty()) {
//			HashSet<String> liveIntVariables = blockStack.peek();
//			@SuppressWarnings("unchecked")
//			HashSet<String> localVarsClone = (HashSet<String>) liveIntVariables.clone();
//			blockStack.push(localVarsClone);
//		} else {
//			blockStack.push(new HashSet<>());
//		}
//
//		@SuppressWarnings("unchecked")
//		List<Expression> initializers = node.initializers();
//
//		for (Expression variable : initializers) {
//			if (variable.getNodeType() != ASTNode.VARIABLE_DECLARATION_EXPRESSION)
//				continue;
//
//			Type variableType = ((VariableDeclarationExpression) variable).getType();
//
//			if (!variableType.isPrimitiveType())
//				continue;
//
//			if (isIntegerTypeCode(variableType)) {
//				@SuppressWarnings("unchecked")
//				List<VariableDeclarationFragment> fragments = ((VariableDeclarationExpression) variable)
//						.fragments();
//				HashSet<String> liveIntVariables = blockStack.pop();
//
//				for (VariableDeclarationFragment fragment : fragments) {
//					String loopVariable = fragment.getName().getIdentifier();
//					liveIntVariables.add(loopVariable);
//				}
//
//				blockStack.push(liveIntVariables);
//			}
//		}
		return true;
	}

	@Override
	public void endVisit(ForStatement node) {
		//blockStack.pop();
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {

//		Type variableType = node.getType();
//		if (!variableType.isPrimitiveType()) {
//			// right now we are just ignoring non-primitive declarations
//			return true;
//		}
//
//		@SuppressWarnings("unchecked")
//		List<VariableDeclarationFragment> fragments = node.fragments();
//		HashSet<String> liveIntVariables = blockStack.pop();
//
//		if (isIntegerTypeCode(variableType)) {
//			for (VariableDeclarationFragment fragment : fragments) {
//				String variableName = fragment.getName().getIdentifier();
//				liveIntVariables.add(variableName);
//			}
//
//		} else {
//			// Check if we are redefining an instance variable to be non integer
//			for (VariableDeclarationFragment fragment : fragments) {
//				String variableName = fragment.getName().getIdentifier();
//
//				if (isLiveIntVariable(variableName)) {
//					liveIntVariables.remove(variableName);
//				}
//			}
//		}
//
//		blockStack.push(liveIntVariables);

		return true;
	}

//	@Override
//	public boolean visit(Assignment node) {
//		HashSet<String> liveIntVariables = blockStack.peek();
//		Expression lhs = node.getLeftHandSide();
//		if (!isVariable(lhs)) {
//			return true;
//		}
//		String variableName = lhs.toString();
//		if (liveIntVariables.contains(variableName)) {
//			if (node.getOperator() != Assignment.Operator.ASSIGN) {
//				currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
//			}
//		}
//		return true;
//	}

	@Override
	public boolean visit(CastExpression node) {
		//expressionsStack.push(node);
		return true;
	}

//	@Override
//	public void endVisit(CastExpression node) {
//		Type type = node.getType();
//		intExpression = isIntegerTypeCode(type) ? true : false;
//		//expressionsStack.pop();
//		//not sure why are we counting casting as an operation
//		//if (parentExpression()) {
//			if (intExpression) {
//				currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
//			}
//			operationsInExpression = 0;
//			intExpression = true;
//		//}
//	}

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
		if(operationsInExpression > 0) {
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
	if (!parameters.isEmpty()) {
		am.setHasParameters(true);
		int typeParams = 0;
		for (SingleVariableDeclaration parameter : parameters) {
			CType parType = TypeChecker.checkType(typeTable.getNodeType(parameter));
			if(parType == type) {
			  typeParams++;
			}
		}
		//found all parameters of a particular type
		am.setTypeParameterCount(typeParams);
		if(parameters.size() == typeParams) {
			am.setHasOnlyTypeParameters(true);
		}
		
//		if (hasOnlyIntegerParameters(parameters)) {
//			am.setHasOnlyIntParameters(true);
//			am.setIntParameterCount(parameters.size());
//		} else {
//			am.setHasOnlyIntParameters(false);
//		}
	} else {
		am.setHasParameters(false);
	}
}

}
