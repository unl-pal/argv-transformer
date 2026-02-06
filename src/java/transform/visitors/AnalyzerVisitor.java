//package transform.visitors;
//
//import org.eclipse.jdt.core.dom.*;
//import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
//import sourceAnalysis.AnalyzedFile;
//import sourceAnalysis.AnalyzedMethod;
//import transform.TypeChecking.TypeChecker;
//import transform.TypeChecking.TypeTable;
//
//import java.util.HashMap;
//import java.util.List;
//
//public class AnalyzerVisitor extends ASTVisitor{
//	private final TypeTable typeTable;
//	private final TypeChecker.CType type;
//	private final AnalyzedFile af;
//	private final ASTRewrite rewriter;
//	private final int minTypeExpr;
//	private final int minTypeCond;
//	private final int minTypeParams;
//	private AnalyzedMethod currAnalyzedMethod;
//	private HashMap<String, Integer> currOpCounts = new HashMap<>();
//
//	// @Override
//	// public boolean visit(TypeDeclaration node) {
//	// blockStack.add(new HashSet<String>());
//	// return true;
//	// }
//
//	public AnalyzerVisitor(TypeTable typeTable, TypeChecker.CType type, AnalyzedFile af,
//						   ASTRewrite rewriter, int minTypeExpr, int minTypeCond, int minTypeParams) {
//		this.typeTable = typeTable;
//		this.type = type;
//		this.af = af;
//		this.rewriter = rewriter;
//		this.minTypeExpr = minTypeExpr;
//		this.minTypeCond = minTypeCond;
//		this.minTypeParams = minTypeParams;
//	}
//
//	@Override
//	public boolean visit(FieldDeclaration node) {
//
//		return false;
//	}
//
//	@Override
//	public boolean visit(Initializer node) {
//		return false;
//	}
//
//	@Override
//	public boolean visit(NormalAnnotation node) {
//		return false;
//	}
//
//	@Override
//	public boolean visit(MarkerAnnotation node) {
//		return false;
//	}
//
//	@Override
//	public boolean visit(SingleMemberAnnotation node) {
//		return false;
//	}
//
//	@Override
//	public boolean visit(AnnotationTypeDeclaration node) {
//		return false;
//	}
//
//	@Override
//	public boolean visit(EnumDeclaration node) {
//		return false;
//	}
//
//	@Override
//	public boolean visit(MethodDeclaration node) {
//		AnalyzedMethod am = new AnalyzedMethod(node);
//		af.addMethod(am);
//
//		checkParameterTypes(am, node);
//		// currMethodDeclaration = node;
//		currAnalyzedMethod = am;
//		// System.out.println("Method\t" + am.getName());
//
//		// @SuppressWarnings("unchecked")
//		// HashSet<String> liveIntVariables = (HashSet<String>)
//		// classIntVariables.clone();
//		// @SuppressWarnings("unchecked")
//		// List<SingleVariableDeclaration> parameters =
//		// (List<SingleVariableDeclaration>) (node.parameters());
//		//
//		// for (SingleVariableDeclaration parameter : parameters) {
//		// Type parameterType = parameter.getType();
//		// if (!parameterType.isPrimitiveType())
//		// continue;
//		//
//		// if (isIntegerTypeCode(parameterType)) {
//		// String parameterName = parameter.getName().getIdentifier();
//		// liveIntVariables.add(parameterName);
//		// }
//		// }
//		//
//		// blockStack.push(liveIntVariables);
//
//		return true;
//	}
//
//	@Override
//	public void endVisit(MethodDeclaration node) {
//		// System.out.println("Visiting " + node.getName());
//		AnalyzedMethod m = currAnalyzedMethod;
//		int intOpCount = m.getTypeOperationCount();
//		// if (intOpCount > 0) {
//		//   m.setHasTypeOperations(true);
//		// }
//
//		// if (m.getTypeConditionalCount() > 0) {
//		//   m.setHasTypeConditional(true);
//		// }
//
//		if (m.getTypeParameterCount() > 0) {
//			m.setHasOnlyTypeParameters(true);
//		}
//
//		if (m.getTypeConditionalCount() < minTypeCond ||
//				m.getTypeOperationCount() < minTypeExpr ||
//				m.getTypeParameterCount() < minTypeParams) {
//			// System.out.println("Removing " + node.getName());
//			rewriter.remove(node, null);
//		}
//
//		// record operation information about method (derive class level etc. later)
//		m.setOpCounts(currOpCounts);
//		currOpCounts = new HashMap<>();
//	}
//
//	public ASTRewrite getRewriter() {
//		return rewriter;
//	}
//
//	@Override
//	public boolean visit(Block node) {
//		// if (!blockStack.empty()) {
//		// HashSet<String> liveIntVariables = blockStack.peek();
//		// @SuppressWarnings("unchecked")
//		// HashSet<String> localVarsClone = (HashSet<String>) liveIntVariables.clone();
//		// blockStack.push(localVarsClone);
//		// } else {
//		// blockStack.push(new HashSet<>());
//		// }
//		return true;
//	}
//
//	// @Override
//	// public void endVisit(Block node) {
//	// blockStack.pop();
//	// }
//
//	@Override
//	public boolean visit(IfStatement node) {
//		// eas we need to check whether the expression is of int type
//		// do we do it here or somewhere else?
//		Expression e = node.getExpression();
//		boolean hasType = hasType(e);
//		// System.out.println(e.getClass() + " " + hasType + " " +
//		// typeTable.getNodeType(e));
//		// try to visit it and find out whether it has integer exprssions?
//		// it can be 1) Boolean expression, 2) Infix expression, 3) Conditional
//		// expression
//		// other expressions that can return boolean value
//		// let's focus on infix expressions
//		// let's not -- just check whether the expression has required type
//		// that is operands there have type for which analysis is built
//		// -- if(e instanceof InfixExpression) {
//		// InfixExpression infE = (InfixExpression) e;
//		// Expression lE = infE.getLeftOperand();
//		// Type t = typeTable.getNodeType(lE);
//		// if(TypeChecker.checkType(t) == type) {
//		// currAnalyzedMethod.setHasConditional(true);
//		// }
//		// remember the count before
//		if (hasType) {
//			currAnalyzedMethod.incrementTypeConditionalCount();
//		}
//
//		// --}
//
//		// then the visitor will go into expression and count
//		// what it needs to count
//		return true;
//	}
//
//	// does any part of the expression have the specified type?
//	private boolean hasType(Expression e) {
//		if (e instanceof InfixExpression) {
//			InfixExpression infE = (InfixExpression) e;
//			return hasType(infE.getLeftOperand()) || hasType(infE.getRightOperand());
//		} else if (e instanceof ConditionalExpression) {
//			ConditionalExpression condE = (ConditionalExpression) e;
//			return hasType(condE.getThenExpression()) || hasType(condE.getElseExpression());
//		} else if (e instanceof MethodInvocation) {
//			MethodInvocation mi = (MethodInvocation) e;
//			Type recType = typeTable.getNodeType(mi.getExpression());
//			if (type == TypeChecker.CType.STRING && TypeChecker.isStringType(recType)) {
//				return TypeChecker.isStringOp(mi.getName().getIdentifier());
//			} else {
//				return type.equals(TypeChecker.checkType(recType));
//			}
//		} else if (e instanceof ParenthesizedExpression) {
//			return hasType(((ParenthesizedExpression) e).getExpression());
//		} else if (e instanceof PrefixExpression) {
//			return hasType(((PrefixExpression) e).getOperand());
//		} else if (e instanceof PostfixExpression) {
//			return hasType(((PostfixExpression) e).getOperand());
//		} else if (e instanceof FieldAccess) {
//			return type.equals(TypeChecker.checkType(typeTable.getNodeType(e)));
//		} else if (e instanceof QualifiedName) {
//			return type.equals(TypeChecker.checkType(typeTable.getNodeType(e)));
//		} else if (e instanceof SimpleName) {
//			return type.equals(TypeChecker.checkType(typeTable.getNodeType(e)));
//		} else {
//			// fallback: check the type of the expression
//			return type.equals(TypeChecker.checkType(typeTable.getNodeType(e)));
//		}
//	}
//
//	// for determining whether the expression for an if statement has relevant type
//	// private boolean hasType(Expression e) {
//	// boolean ret = false;
//	// if (e instanceof InfixExpression) {
//	// InfixExpression infE = (InfixExpression) e;
//	// Expression lE = infE.getLeftOperand();
//	// Expression rE = infE.getRightOperand();
//	// return hasType(lE) || hasType(rE);
//	// // Type lT = typeTable.getNodeType(lE);
//	// // Type rT = typeTable.getNodeType(rE);
//	// //
//	// // if (TypeChecker.checkType(lT) == type || TypeChecker.checkType(rT) ==
//	// type) {
//	// // ret = true;
//	// // } else if (TypeChecker.isBooleanType(rT) || TypeChecker.isBooleanType(lT))
//	// {
//	// // // call again since it might be just a complex expression
//	// // ret = hasType(lE) || hasType(rE);
//	// // }
//	// //
//	// // // Concatenation special case
//	// // if (type == CType.STRING && infE.getOperator() ==
//	// // InfixExpression.Operator.PLUS) {
//	// // // either has to be (specifically) String for result to be String
//	// // if (TypeChecker.isStringTypeSpecifically(lT) ||
//	// // TypeChecker.isStringTypeSpecifically(rT)) {
//	// // ret = true;
//	// // }
//	// // }
//	// } else if (e instanceof MethodInvocation) {
//	// MethodInvocation mi = (MethodInvocation) e;
//	// String methodName = mi.getName().getIdentifier();
//	// Type recType = typeTable.getNodeType(mi.getExpression());
//	// if (type == CType.STRING && TypeChecker.isStringType(recType)) {
//	// return TypeChecker.isStringOp(methodName);
//	// } else {
//	// return type.equals(TypeChecker.checkType(recType));
//	// }
//	// } else {
//	// // if it is not an infix expression then it should
//	// // be some single var of a boolean type
//	// Type vT = typeTable.getNodeType(e);
//	// // if (TypeChecker.isBooleanType(vT)) {
//	// // System.out.println("Just a var");
//	// // ret = true;
//	// // }
//	// return type.equals(TypeChecker.checkType(vT));
//	// }
//	// }
//
//	@Override
//	public void endVisit(IfStatement node) {
//		// System.out.println("done visiting");
//	}
//
//	/*
//	 * What about while statement?
//	 */
//
//	@Override
//	public boolean visit(ForStatement node) {
//		currAnalyzedMethod.setHasLoop(true);
//		// To handle scope of local variables
//		// if (!blockStack.empty()) {
//		// HashSet<String> liveIntVariables = blockStack.peek();
//		// @SuppressWarnings("unchecked")
//		// HashSet<String> localVarsClone = (HashSet<String>) liveIntVariables.clone();
//		// blockStack.push(localVarsClone);
//		// } else {
//		// blockStack.push(new HashSet<>());
//		// }
//		//
//		// @SuppressWarnings("unchecked")
//		// List<Expression> initializers = node.initializers();
//		//
//		// for (Expression variable : initializers) {
//		// if (variable.getNodeType() != ASTNode.VARIABLE_DECLARATION_EXPRESSION)
//		// continue;
//		//
//		// Type variableType = ((VariableDeclarationExpression) variable).getType();
//		//
//		// if (!variableType.isPrimitiveType())
//		// continue;
//		//
//		// if (isIntegerTypeCode(variableType)) {
//		// @SuppressWarnings("unchecked")
//		// List<VariableDeclarationFragment> fragments =
//		// ((VariableDeclarationExpression) variable)
//		// .fragments();
//		// HashSet<String> liveIntVariables = blockStack.pop();
//		//
//		// for (VariableDeclarationFragment fragment : fragments) {
//		// String loopVariable = fragment.getName().getIdentifier();
//		// liveIntVariables.add(loopVariable);
//		// }
//		//
//		// blockStack.push(liveIntVariables);
//		// }
//		// }
//		return true;
//	}
//
//	@Override
//	public void endVisit(ForStatement node) {
//		// blockStack.pop();
//	}
//
//	@Override
//	public boolean visit(VariableDeclarationStatement node) {
//
//		// Type variableType = node.getType();
//		// if (!variableType.isPrimitiveType()) {
//		// // right now we are just ignoring non-primitive declarations
//		// return true;
//		// }
//		//
//		// @SuppressWarnings("unchecked")
//		// List<VariableDeclarationFragment> fragments = node.fragments();
//		// HashSet<String> liveIntVariables = blockStack.pop();
//		//
//		// if (isIntegerTypeCode(variableType)) {
//		// for (VariableDeclarationFragment fragment : fragments) {
//		// String variableName = fragment.getName().getIdentifier();
//		// liveIntVariables.add(variableName);
//		// }
//		//
//		// } else {
//		// // Check if we are redefining an instance variable to be non integer
//		// for (VariableDeclarationFragment fragment : fragments) {
//		// String variableName = fragment.getName().getIdentifier();
//		//
//		// if (isLiveIntVariable(variableName)) {
//		// liveIntVariables.remove(variableName);
//		// }
//		// }
//		// }
//		//
//		// blockStack.push(liveIntVariables);
//
//		return true;
//	}
//
//	// @Override
//	// public boolean visit(Assignment node) {
//	// HashSet<String> liveIntVariables = blockStack.peek();
//	// Expression lhs = node.getLeftHandSide();
//	// if (!isVariable(lhs)) {
//	// return true;
//	// }
//	// String variableName = lhs.toString();
//	// if (liveIntVariables.contains(variableName)) {
//	// if (node.getOperator() != Assignment.Operator.ASSIGN) {
//	// currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
//	// }
//	// }
//	// return true;
//	// }
//
//	@Override
//	public boolean visit(CastExpression node) {
//		// expressionsStack.push(node);
//		return true;
//	}
//
//	// @Override
//	// public void endVisit(CastExpression node) {
//	// Type type = node.getType();
//	// intExpression = isIntegerTypeCode(type) ? true : false;
//	// //expressionsStack.pop();
//	// //not sure why are we counting casting as an operation
//	// //if (parentExpression()) {
//	// if (intExpression) {
//	// currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
//	// }
//	// operationsInExpression = 0;
//	// intExpression = true;
//	// //}
//	// }
//
//	@Override
//	public boolean visit(InfixExpression node) {
//		// expressionsStack.push(node);
//
//		// System.out.println(" n " + node + "\t" + typeTable.getNodeType(node));
//		Expression lE = node.getLeftOperand();
//		Type lT = typeTable.getNodeType(lE);
//		Expression rE = node.getRightOperand();
//		Type rT = typeTable.getNodeType(rE);
//		InfixExpression.Operator op = node.getOperator();
//
//		// if the parent node is not a boolean type (no logical connections)
//		// and if lhs and rhs of the required type than its op should some
//		// kind of numerical operator
//
//		if (type == TypeChecker.CType.STRING && op == InfixExpression.Operator.PLUS) {
//			// special case of string concatenation
//			if (TypeChecker.isStringTypeSpecifically(lT) || TypeChecker.isStringTypeSpecifically(rT)) {
//				currOpCounts.merge("concat", 1, Integer::sum);
//				currAnalyzedMethod.incrementTypeOperationCount();
//			}
//			//check for additional concats
//			List<Expression> extendedOperands = node.extendedOperands();
//			if (extendedOperands != null && !extendedOperands.isEmpty()) {
//				// we can just assume
//				int numOps = extendedOperands.size();
//				currOpCounts.merge("concat", numOps, Integer::sum);
//				for (int i = 0; i < numOps; i++){
//					currAnalyzedMethod.incrementTypeOperationCount();
//				}
//			}
//			// causes String == null to incrementTypeOperationCount
//		} else if ((TypeChecker.checkType(lT) == type || TypeChecker.checkType(rT) == type)) {
//
//			// does it matter what type of operand is it?
//			// if (op == Operator.PLUS ||
//			// op == Operator.MINUS ||
//			// op == Operator.DIVIDE ||
//			// op == Operator.TIMES ||
//			// op == Operator.REMAINDER) {
//			// so we count that operations
//			// if the type is int
//			// if (!TypeChecker.isBooleanType(typeTable.getNodeType(node))) {
//			// System.out.println("op " + op);
//			currAnalyzedMethod.incrementTypeOperationCount();
//			// }
//			// }
//		}
//		// no need to go further if lhs is not of int type and not boolean
//		// since a condition might use logical constructs to build complex expressions
//		return !TypeChecker.isBooleanType(lT) && !TypeChecker.isBooleanType(rT);
//	}
//
//	@Override
//	public boolean visit(MethodInvocation node) {
//		if (type == TypeChecker.CType.STRING) {
//			String methodName = node.getName().getIdentifier();
//			if (TypeChecker.isStringOp(methodName)) {
//				currOpCounts.merge(methodName, 1, Integer::sum);
//				currAnalyzedMethod.incrementTypeOperationCount();
//				// operationsInExpression++;
//			}
//		}
//		return true;
//	}
//
//	@Override
//	public void endVisit(InfixExpression node) {
//		// expressionsStack.pop();
//		// if (operationsInExpression > 0) {
//		// currAnalyzedMethod.setTypeOperationCount(currAnalyzedMethod.getTypeOperationCount()
//		// + 1);
//		// operationsInExpression = 0;
//		// }
//	}
//
//	@Override
//	public boolean visit(PrefixExpression node) {
//		// expressionsStack.push(node);
//		// System.out.println("Prefix " + node);
//		Expression operand = node.getOperand();
//		TypeChecker.CType tOp = TypeChecker.checkType(typeTable.getNodeType(operand));
//		if (tOp == type) {
//			// so it is integer
//			// operationsInExpression++;
//			currAnalyzedMethod.incrementTypeOperationCount();
//			// System.out.println("preixCount");
//		} else {
//			// no need to go if it is not an int
//			return false;
//		}
//
//		return true;
//	}
//
//	@Override
//	public void endVisit(PrefixExpression node) {
//		// expressionsStack.pop();
//		// if (operationsInExpression > 0) {
//		// currAnalyzedMethod.setTypeOperationCount(currAnalyzedMethod.getTypeOperationCount()
//		// + operationsInExpression);
//		// operationsInExpression = 0;
//		// }
//
//	}
//
//	@Override
//	public boolean visit(PostfixExpression node) {
//		// expressionsStack.push(node);
//		// HashSet<String> liveIntVariables = blockStack.peek();
//		Expression operand = node.getOperand();
//		// System.out.println("postfix " + node);
//		TypeChecker.CType tOp = TypeChecker.checkType(typeTable.getNodeType(operand));
//		if (tOp == type) {
//			currAnalyzedMethod.incrementTypeOperationCount();
//			// operationsInExpression++;
//			// System.out.println("postfix");
//		} else {
//			// no need to go in if it is not an int
//			return false;
//		}
//
//		return true;
//	}
//
//	@Override
//	public void endVisit(PostfixExpression node) {
//		// expressionsStack.pop();
//		// if (operationsInExpression > 0) {
//		// currAnalyzedMethod.setTypeOperationCount(currAnalyzedMethod.getTypeOperationCount()
//		// + operationsInExpression);
//		// operationsInExpression = 0;
//		// }
//
//	}
//
//	private void checkParameterTypes(AnalyzedMethod am, MethodDeclaration node) {
//		List<SingleVariableDeclaration> parameters = node.parameters();
//		if (!parameters.isEmpty()) {
//			// am.setHasParameters(true);
//			int typeParams = 0;
//			for (SingleVariableDeclaration parameter : parameters) {
//				TypeChecker.CType parType = TypeChecker.checkType(typeTable.getNodeType(parameter));
//				if (parType == type) {
//					typeParams++;
//				}
//			}
//			// found all parameters of a particular type
//			am.setTypeParameterCount(typeParams);
//			if (parameters.size() == typeParams) {
//				am.setHasOnlyTypeParameters(true);
//			}
//
//			// if (hasOnlyIntegerParameters(parameters)) {
//			// am.setHasOnlyIntParameters(true);
//			// am.setIntParameterCount(parameters.size());
//			// } else {
//			// am.setHasOnlyIntParameters(false);
//			// }
//		} else {
//			// am.setHasParameters(false);
//		}
//	}
//
//	// public boolean hasOnlyIntegerParameters(List<SingleVariableDeclaration>
//	// parameters) {
//	// for (SingleVariableDeclaration parameter : parameters) {
//	// CType parType = TypeChecker.checkType(typeTable.getNodeType(parameter));
//	// if(parType != type && parType != CType.BOOLEAN) {
//	// //if (!isIntegerParameter(parameter)) {
//	// return false;
//	// }
//	// }
//	// return true;
//	// }
//
//	// private boolean isSingleParameter(SingleVariableDeclaration parameter) {
//	// return (parameter.getExtraDimensions() == 0 && !parameter.isVarargs());
//	// }
//
//	// private boolean isIntegerParameter(SingleVariableDeclaration parameter) {
//	// if (!isSingleParameter(parameter))
//	// return false;
//	// Type type = parameter.getType();
//	// if (!type.isPrimitiveType())
//	// return false;
//	//
//	// Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
//	// return (typeCode == PrimitiveType.CHAR || typeCode == PrimitiveType.INT ||
//	// typeCode == PrimitiveType.LONG
//	// || typeCode == PrimitiveType.SHORT || typeCode == PrimitiveType.BYTE);
//	// }
//
//	// private boolean isIntegerTypeCode(Type type) {
//	// if (!type.isPrimitiveType())
//	// return false;
//	// Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
//	//
//	// return (typeCode == PrimitiveType.CHAR || typeCode == PrimitiveType.INT ||
//	// typeCode == PrimitiveType.LONG
//	// || typeCode == PrimitiveType.SHORT || typeCode == PrimitiveType.BYTE);
//	// }
//	//
//	// private boolean isVariable(Expression exp) {
//	// return (exp instanceof SimpleName || exp instanceof QualifiedName);
//	// }
//
//	// private boolean isLiveIntVariable(String name) {
//	// HashSet<String> liveIntVariables = blockStack.peek();
//	// if (liveIntVariables.contains(name)) {
//	// return true;
//	// }
//	// return false;
//	// }
//
//	// private boolean parentExpression() {
//	// return expressionsStack.isEmpty();
//	// }
//}
