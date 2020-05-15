package filter.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;

import sourceAnalysis.AnalyzedFile;
import sourceAnalysis.AnalyzedMethod;
import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeChecker.CType;
import transform.TypeChecking.TypeTable;
import transform.visitors.SymbolTableVisitor;
import transform.visitors.TypeCollectVisitor;
import transform.visitors.TypeTableVisitor;

/**
 * The parameterized version of SymblicSuitableMethodFinder, which
 * can take a type of operations and expressions, and a presence
 * of conditional statements and loops into considerations, such
 * that lsh/rhs of their infix expressions are of particular type.
 *
 * @author mariapaquin
 *
 */
public class SuitableMethodFinder {
	
	private CType type;

	private AnalyzedFile af;
	//private MethodDeclaration currMethodDeclaration;
	private AnalyzedMethod currAnalyzedMethod;
	//private HashSet<String> classIntVariables;
	//private Stack<HashSet<String>> blockStack;
	//private Stack<Expression> expressionsStack;
	//private ArrayList<String> unprocessedExpressions;
	//private boolean intExpression = true;
	//private List<AnalyzedMethod> intOperationsCount;
	private int operationsInExpression;
	//private AnalyzerVisitor visitor;
	private TypeTable typeTable;
	private int minTypeExpr;
	private int minTypeCond;

	public SuitableMethodFinder(File file) throws IOException {
		defaultSetUp(file);
		minTypeExpr = 0;
		minTypeCond = 0;
	}
	
	private void defaultSetUp(File file) {
		af = new AnalyzedFile(file);
		//intOperationsCount = new ArrayList<AnalyzedMethod>();
		//classIntVariables = new HashSet<String>();
		//blockStack = new Stack<HashSet<String>>();
		//expressionsStack = new Stack<Expression>();
		operationsInExpression = 0;
		//unprocessedExpressions = new ArrayList<String>();
		//setting type to int for now
		type = CType.INT;
	}
	
	public SuitableMethodFinder(File file, int minExpr, int minCond) throws IOException {
		defaultSetUp(file);
		minTypeExpr = minExpr;
		minTypeCond = minCond;
	}

	public void analyze() throws IOException {
		File file = af.getFile();
		String source = new String(Files.readAllBytes(file.toPath()));
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		ASTNode node = parser.createAST(null);
		//infer the types of nodes
		
		//collects import types
		TypeCollectVisitor typeCollectVisitor = new TypeCollectVisitor();
		node.accept(typeCollectVisitor);
		TypeChecker typeChecker = typeCollectVisitor.getTypeChecker();
		
		
		SymbolTableVisitor symTableVisitor = new SymbolTableVisitor(typeChecker);
		node.accept(symTableVisitor);
		SymbolTable rootScope = symTableVisitor.getRoot();

		TypeTableVisitor typeTableVisitor = new TypeTableVisitor(rootScope, typeChecker);
		node.accept(typeTableVisitor);
		typeTable = typeTableVisitor.getTypeTable();
		
		AnalyzerVisitor visitor = new AnalyzerVisitor();
		node.accept(visitor);
		
		//done with visiting set af
		//how to determine whether the anlyzed file is suitable?
		//when \exists at least one suitable method
		for(AnalyzedMethod m : af.getAnalyzedMethods()) {
			if(m.getConditionalCount() > minTypeCond && m.getIntOperationCount() > minTypeExpr) {
				af.addSuitableMethod(m);
			}
		}
		
	}

	public int getTotalIntOperations() {
		int count = 0;
		for (AnalyzedMethod m : af.getAnalyzedMethods()) {
			count += m.getIntOperationCount();
		}
		return count;
	}
	
	public int getTotalConditionals() {
		int count = 0;
		for (AnalyzedMethod m : af.getAnalyzedMethods()) {
			count += m.getConditionalCount();
		}
		return count;
	}

	public AnalyzedFile getAnalyzedFile() {
		return af;
	}

	private class AnalyzerVisitor extends ASTVisitor {


//		@Override
//		public boolean visit(TypeDeclaration node) {
//			blockStack.add(new HashSet<String>());
//			return true;
//		}

//		@Override
//		public boolean visit(FieldDeclaration node) {
//
//			Type type = node.getType();
//			@SuppressWarnings("unchecked")
//			List<VariableDeclarationFragment> instanceVariables = node.fragments();
//
//			if (isIntegerTypeCode(type)) {
//				for (VariableDeclarationFragment variable : instanceVariables) {
//					classIntVariables.add(variable.getName().getIdentifier());
//				}
//			}
//			return false;
//		}

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
			//currMethodDeclaration = node;
			currAnalyzedMethod = am;

//			@SuppressWarnings("unchecked")
//			HashSet<String> liveIntVariables = (HashSet<String>) classIntVariables.clone();
			//@SuppressWarnings("unchecked")
//			List<SingleVariableDeclaration> parameters = (List<SingleVariableDeclaration>) (node.parameters());
//
//			for (SingleVariableDeclaration parameter : parameters) {
//				Type parameterType = parameter.getType();
//				if (!parameterType.isPrimitiveType())
//					continue;
//
//				if (isIntegerTypeCode(parameterType)) {
//					String parameterName = parameter.getName().getIdentifier();
//					liveIntVariables.add(parameterName);
//				}
//			}
//
//			blockStack.push(liveIntVariables);

			return true;
		}

		@Override
		public void endVisit(MethodDeclaration node) {
			int intOpCount = currAnalyzedMethod.getIntOperationCount();
			if(intOpCount > 0) {
				currAnalyzedMethod.setHasIntOperations(true);
			}
			
			if(currAnalyzedMethod.getConditionalCount() > 0) {
				currAnalyzedMethod.setHasConditional(true);
			}
		}

		@Override
		public boolean visit(Block node) {
//			if (!blockStack.empty()) {
//				HashSet<String> liveIntVariables = blockStack.peek();
//				@SuppressWarnings("unchecked")
//				HashSet<String> localVarsClone = (HashSet<String>) liveIntVariables.clone();
//				blockStack.push(localVarsClone);
//			} else {
//				blockStack.push(new HashSet<>());
//			}
			return true;
		}

//		@Override
//		public void endVisit(Block node) {
//			blockStack.pop();
//		}
		
		
		@Override
		public boolean visit(IfStatement node) {
			//eas we need to check whether the expression is of int type
			//do we do it here or somewhere else?
			Expression e = node.getExpression();
			boolean hasType = hasType(e);
			System.out.println(e.getClass() + " " + hasType + " " + typeTable.getNodeType(e));
			//try to visit it and find out whether it has integer exprssions?
			//it can be 1) Boolean expression, 2) Infix expression, 3) Conditional expression
			// other expressions that can return boolean value
			//let's focus on infix expressions
			// let's not -- just check whether the expression has required type
			// that is operands there have type for which analysis is built
		//--	if(e instanceof InfixExpression) {
//				InfixExpression infE = (InfixExpression) e;
//				Expression lE = infE.getLeftOperand();
//				Type t = typeTable.getNodeType(lE);
//				if(TypeChecker.checkType(t) == type) {
//					currAnalyzedMethod.setHasConditional(true);
//				}
				//remember the count before
				if(hasType) {
					currAnalyzedMethod.setConditionalCount(currAnalyzedMethod.getConditionalCount()+1);
					
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
					System.out.println("Just a var");
					ret = true;
				}
			}
			return ret;
		}
		
		@Override
		public void endVisit(IfStatement node) {
			System.out.println("done visiting");
		}

		/*
		 * What about while statement?
		 */
		
		@Override
		public boolean visit(ForStatement node) {
			currAnalyzedMethod.setHasLoop(true);
			// To handle scope of local variables
//			if (!blockStack.empty()) {
//				HashSet<String> liveIntVariables = blockStack.peek();
//				@SuppressWarnings("unchecked")
//				HashSet<String> localVarsClone = (HashSet<String>) liveIntVariables.clone();
//				blockStack.push(localVarsClone);
//			} else {
//				blockStack.push(new HashSet<>());
//			}
//
//			@SuppressWarnings("unchecked")
//			List<Expression> initializers = node.initializers();
//
//			for (Expression variable : initializers) {
//				if (variable.getNodeType() != ASTNode.VARIABLE_DECLARATION_EXPRESSION)
//					continue;
//
//				Type variableType = ((VariableDeclarationExpression) variable).getType();
//
//				if (!variableType.isPrimitiveType())
//					continue;
//
//				if (isIntegerTypeCode(variableType)) {
//					@SuppressWarnings("unchecked")
//					List<VariableDeclarationFragment> fragments = ((VariableDeclarationExpression) variable)
//							.fragments();
//					HashSet<String> liveIntVariables = blockStack.pop();
//
//					for (VariableDeclarationFragment fragment : fragments) {
//						String loopVariable = fragment.getName().getIdentifier();
//						liveIntVariables.add(loopVariable);
//					}
//
//					blockStack.push(liveIntVariables);
//				}
//			}
			return true;
		}

		@Override
		public void endVisit(ForStatement node) {
			//blockStack.pop();
		}

		@Override
		public boolean visit(VariableDeclarationStatement node) {

//			Type variableType = node.getType();
//			if (!variableType.isPrimitiveType()) {
//				// right now we are just ignoring non-primitive declarations
//				return true;
//			}
//
//			@SuppressWarnings("unchecked")
//			List<VariableDeclarationFragment> fragments = node.fragments();
//			HashSet<String> liveIntVariables = blockStack.pop();
//
//			if (isIntegerTypeCode(variableType)) {
//				for (VariableDeclarationFragment fragment : fragments) {
//					String variableName = fragment.getName().getIdentifier();
//					liveIntVariables.add(variableName);
//				}
//
//			} else {
//				// Check if we are redefining an instance variable to be non integer
//				for (VariableDeclarationFragment fragment : fragments) {
//					String variableName = fragment.getName().getIdentifier();
//
//					if (isLiveIntVariable(variableName)) {
//						liveIntVariables.remove(variableName);
//					}
//				}
//			}
//
//			blockStack.push(liveIntVariables);

			return true;
		}

//		@Override
//		public boolean visit(Assignment node) {
//			HashSet<String> liveIntVariables = blockStack.peek();
//			Expression lhs = node.getLeftHandSide();
//			if (!isVariable(lhs)) {
//				return true;
//			}
//			String variableName = lhs.toString();
//			if (liveIntVariables.contains(variableName)) {
//				if (node.getOperator() != Assignment.Operator.ASSIGN) {
//					currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
//				}
//			}
//			return true;
//		}

		@Override
		public boolean visit(CastExpression node) {
			//expressionsStack.push(node);
			return true;
		}

//		@Override
//		public void endVisit(CastExpression node) {
//			Type type = node.getType();
//			intExpression = isIntegerTypeCode(type) ? true : false;
//			//expressionsStack.pop();
//			//not sure why are we counting casting as an operation
//			//if (parentExpression()) {
//				if (intExpression) {
//					currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
//				}
//				operationsInExpression = 0;
//				intExpression = true;
//			//}
//		}

		@Override
		public boolean visit(InfixExpression node) {
			//expressionsStack.push(node);

			System.out.println(" n " + node + "\t" + typeTable.getNodeType(node));
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
//			if (op == Operator.PLUS ||
//					op == Operator.MINUS ||
//					op == Operator.DIVIDE ||
//					op == Operator.TIMES ||
//					op == Operator.REMAINDER) {
				// so we count that operations
				//if the type is int
			if(!TypeChecker.isBooleanType(typeTable.getNodeType(node))) {
				System.out.println("op " + op);
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
				currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
					operationsInExpression = 0;
			}
		}

		@Override
		public boolean visit(PrefixExpression node) {
			//expressionsStack.push(node);
			System.out.println("Prefix " + node);
			Expression operand = node.getOperand();
			CType tOp = TypeChecker.checkType(typeTable.getNodeType(operand));
			if( tOp == type) {
				//so it is integer
				operationsInExpression++;
				System.out.println("preixCount");
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
				currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
					operationsInExpression = 0;
				}
				
		}

		@Override
		public boolean visit(PostfixExpression node) {
			//expressionsStack.push(node);
			//HashSet<String> liveIntVariables = blockStack.peek();
			Expression operand = node.getOperand();
			System.out.println("postfix " + node);
			CType tOp = TypeChecker.checkType(typeTable.getNodeType(operand));
			if(tOp == type) {
				operationsInExpression++;
				System.out.println("postfix");
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
				currAnalyzedMethod.setIntOperationCount(currAnalyzedMethod.getIntOperationCount()+1);
			operationsInExpression = 0;
				}
				


	}

	private void checkParameterTypes(AnalyzedMethod am, MethodDeclaration node) {
		List<SingleVariableDeclaration> parameters = node.parameters();
		if (!parameters.isEmpty()) {
			am.setHasParameters(true);
			if (hasOnlyIntegerParameters(parameters)) {
				am.setHasOnlyIntParameters(true);
				am.setIntParameterCount(parameters.size());
			} else {
				am.setHasOnlyIntParameters(false);
			}
		} else {
			am.setHasParameters(false);
		}
	}

	public boolean hasOnlyIntegerParameters(List<SingleVariableDeclaration> parameters) {
		for (SingleVariableDeclaration parameter : parameters) {
			if (!isIntegerParameter(parameter)) {
				return false;
			}
		}
		return true;
	}

	private boolean isSingleParameter(SingleVariableDeclaration parameter) {
		return (parameter.getExtraDimensions() == 0 && !parameter.isVarargs());
	}

	private boolean isIntegerParameter(SingleVariableDeclaration parameter) {
		if (!isSingleParameter(parameter))
			return false;
		Type type = parameter.getType();
		if (!type.isPrimitiveType())
			return false;

		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.CHAR || typeCode == PrimitiveType.INT || typeCode == PrimitiveType.LONG
				|| typeCode == PrimitiveType.SHORT || typeCode == PrimitiveType.BYTE);
	}

//	private boolean isIntegerTypeCode(Type type) {
//		if (!type.isPrimitiveType())
//			return false;
//		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
//
//		return (typeCode == PrimitiveType.CHAR || typeCode == PrimitiveType.INT || typeCode == PrimitiveType.LONG
//				|| typeCode == PrimitiveType.SHORT || typeCode == PrimitiveType.BYTE);
//	}
//
//	private boolean isVariable(Expression exp) {
//		return (exp instanceof SimpleName || exp instanceof QualifiedName);
//	}



//	private boolean isLiveIntVariable(String name) {
//		HashSet<String> liveIntVariables = blockStack.peek();
//		if (liveIntVariables.contains(name)) {
//			return true;
//		}
//		return false;
//	}


//	private boolean parentExpression() {
//		return expressionsStack.isEmpty();
//	}
	}
}
