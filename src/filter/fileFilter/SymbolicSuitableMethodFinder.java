package filter.fileFilter;

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
import org.eclipse.jdt.core.dom.InfixExpression;
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

/**
 * This class is used to find methods with integer-only parameters and integer
 * operations, and count the number of integer operations in each method.
 *
 * @author mariapaquin
 *
 */
public class SymbolicSuitableMethodFinder {

	private AnalyzedFile af;
	private MethodDeclaration currMethodDeclaration;
	private AnalyzedMethod currAnalyzedMethod;
	private HashSet<String> classIntVariables;
	private Stack<HashSet<String>> blockStack;
	private Stack<Expression> expressionsStack;
	private ArrayList<String> unprocessedExpressions;
	private boolean intExpression = true;
	private HashMap<MethodDeclaration, Integer> intOperationsCount;
	private int operationsInExpression;

	public SymbolicSuitableMethodFinder(File file) throws IOException {
		af = new AnalyzedFile(file);
		intOperationsCount = new HashMap<MethodDeclaration, Integer>();
		classIntVariables = new HashSet<String>();
		blockStack = new Stack<HashSet<String>>();
		expressionsStack = new Stack<Expression>();
		operationsInExpression = 0;
		unprocessedExpressions = new ArrayList<String>();
	}

	public void analyze() throws IOException {
		File file = af.getFile();
		String source = new String(Files.readAllBytes(file.toPath()));
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		ASTNode node = parser.createAST(null);
		AnalyzerVisitor visitor = new AnalyzerVisitor();
		node.accept(visitor);
	}

	public int getTotalIntOperations() {
		int count = 0;
		for (Map.Entry<MethodDeclaration, Integer> entry : intOperationsCount.entrySet()) {
			count += (int) entry.getValue();
		}
		return count;
	}

	public AnalyzedFile getAnalyzedFile() {
		return af;
	}

	private class AnalyzerVisitor extends ASTVisitor {

		@Override
		public boolean visit(TypeDeclaration node) {
			blockStack.add(new HashSet<String>());
			return true;
		}

		@Override
		public boolean visit(FieldDeclaration node) {

			Type type = node.getType();
			@SuppressWarnings("unchecked")
			List<VariableDeclarationFragment> instanceVariables = node.fragments();

			if (isIntegerTypeCode(type)) {
				for (VariableDeclarationFragment variable : instanceVariables) {
					classIntVariables.add(variable.getName().getIdentifier());
				}
			}
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
			currMethodDeclaration = node;
			currAnalyzedMethod = am;

			@SuppressWarnings("unchecked")
			HashSet<String> liveIntVariables = (HashSet<String>) classIntVariables.clone();
			@SuppressWarnings("unchecked")
			List<SingleVariableDeclaration> parameters = (List<SingleVariableDeclaration>) (node.parameters());

			for (SingleVariableDeclaration parameter : parameters) {
				Type parameterType = parameter.getType();
				if (!parameterType.isPrimitiveType())
					continue;

				if (isIntegerTypeCode(parameterType)) {
					String parameterName = parameter.getName().getIdentifier();
					liveIntVariables.add(parameterName);
				}
			}

			blockStack.push(liveIntVariables);
			intOperationsCount.put(node, 0);

			return true;
		}

		@Override
		public void endVisit(MethodDeclaration node) {
			int intOpCount = intOperationsCount.get(currMethodDeclaration);
			boolean hasIntOps = intOpCount > 0 ? true : false;
			currAnalyzedMethod.setHasIntOperations(hasIntOps);
			currAnalyzedMethod.setIntOperationCount(intOpCount);
		}

		@Override
		public boolean visit(Block node) {
			if (!blockStack.empty()) {
				HashSet<String> liveIntVariables = blockStack.peek();
				@SuppressWarnings("unchecked")
				HashSet<String> localVarsClone = (HashSet<String>) liveIntVariables.clone();
				blockStack.push(localVarsClone);
			} else {
				blockStack.push(new HashSet<>());
			}
			return true;
		}

		@Override
		public void endVisit(Block node) {
			blockStack.pop();
		}

		@Override
		public boolean visit(ForStatement node) {

			// To handle scope of local variables
			if (!blockStack.empty()) {
				HashSet<String> liveIntVariables = blockStack.peek();
				@SuppressWarnings("unchecked")
				HashSet<String> localVarsClone = (HashSet<String>) liveIntVariables.clone();
				blockStack.push(localVarsClone);
			} else {
				blockStack.push(new HashSet<>());
			}

			@SuppressWarnings("unchecked")
			List<Expression> initializers = node.initializers();

			for (Expression variable : initializers) {
				if (variable.getNodeType() != ASTNode.VARIABLE_DECLARATION_EXPRESSION)
					continue;

				Type variableType = ((VariableDeclarationExpression) variable).getType();

				if (!variableType.isPrimitiveType())
					continue;

				if (isIntegerTypeCode(variableType)) {
					@SuppressWarnings("unchecked")
					List<VariableDeclarationFragment> fragments = ((VariableDeclarationExpression) variable)
							.fragments();
					HashSet<String> liveIntVariables = blockStack.pop();

					for (VariableDeclarationFragment fragment : fragments) {
						String loopVariable = fragment.getName().getIdentifier();
						liveIntVariables.add(loopVariable);
					}

					blockStack.push(liveIntVariables);
				}
			}
			return true;
		}

		@Override
		public void endVisit(ForStatement node) {
			blockStack.pop();
		}

		@Override
		public boolean visit(VariableDeclarationStatement node) {

			Type variableType = node.getType();
			if (!variableType.isPrimitiveType()) {
				// right now we are just ignoring non-primitive declarations
				return true;
			}

			@SuppressWarnings("unchecked")
			List<VariableDeclarationFragment> fragments = node.fragments();
			HashSet<String> liveIntVariables = blockStack.pop();

			if (isIntegerTypeCode(variableType)) {
				for (VariableDeclarationFragment fragment : fragments) {
					String variableName = fragment.getName().getIdentifier();
					liveIntVariables.add(variableName);
				}

			} else {
				// Check if we are redefining an instance variable to be non integer
				for (VariableDeclarationFragment fragment : fragments) {
					String variableName = fragment.getName().getIdentifier();

					if (isLiveIntVariable(variableName)) {
						liveIntVariables.remove(variableName);
					}
				}
			}

			blockStack.push(liveIntVariables);

			return true;
		}

		@Override
		public boolean visit(Assignment node) {
			HashSet<String> liveIntVariables = blockStack.peek();
			Expression lhs = node.getLeftHandSide();
			if (!isVariable(lhs)) {
				return true;
			}
			String variableName = lhs.toString();
			if (liveIntVariables.contains(variableName)) {
				if (node.getOperator() != Assignment.Operator.ASSIGN) {
					intOperationsCount.put(currMethodDeclaration, intOperationsCount.get(currMethodDeclaration) + 1);
				}
			}
			return true;
		}

		@Override
		public boolean visit(CastExpression node) {
			expressionsStack.push(node);
			return true;
		}

		@Override
		public void endVisit(CastExpression node) {
			Type type = node.getType();
			intExpression = isIntegerTypeCode(type) ? true : false;
			expressionsStack.pop();

			if (parentExpression()) {
				if (intExpression) {
					intOperationsCount.put(currMethodDeclaration,
							intOperationsCount.get(currMethodDeclaration) + operationsInExpression);
				}
				operationsInExpression = 0;
				intExpression = true;
			}
		}

		@Override
		public boolean visit(InfixExpression node) {
			expressionsStack.push(node);

			@SuppressWarnings("unchecked")
			List<Expression> extendedOps = node.extendedOperands();
			for (Expression exp : extendedOps) {
				operationsInExpression++;
				if (isLiteral(exp) || isVariable(exp)) {
					if (isNonIntLiteral(exp) || isNonIntVariable(exp)) {
						intExpression = false;
					}
				} else if (!isExpression(exp)) {
					// TODO: these are cases that are not being handled
					unprocessedExpressions.add(exp.toString());
				}
			}

			Expression rhs = node.getRightOperand();
			Expression lhs = node.getLeftOperand();

			if (isNonIntLiteral(lhs) || isNonIntVariable(lhs)) {
				intExpression = false;
			}
			if (isNonIntLiteral(rhs) || isNonIntVariable(rhs)) {
				intExpression = false;
			}

			// TODO: These are cases not handles. e.g. Math.abs(-1), obj.value, etc.
			if (!(isLiteral(rhs) || isVariable(rhs) || isExpression(rhs))
					|| !(isLiteral(lhs) || isVariable(lhs) || isExpression(lhs))) {
				unprocessedExpressions.add(node.toString());
				intExpression = false;
			}

			operationsInExpression++;
			return true;
		}

		@Override
		public void endVisit(InfixExpression node) {
			expressionsStack.pop();

			if (parentExpression()) {
				if (intExpression) {
					intOperationsCount.put(currMethodDeclaration,
							intOperationsCount.get(currMethodDeclaration) + operationsInExpression);
				}
				operationsInExpression = 0;
				intExpression = true;
			}
		}

		@Override
		public boolean visit(PrefixExpression node) {
			expressionsStack.push(node);
			HashSet<String> liveIntVariables = blockStack.peek();

			Expression operand = node.getOperand();
			if (isVariable(operand)) {
				if (node.getOperator() == PrefixExpression.Operator.INCREMENT
						|| node.getOperator() == PrefixExpression.Operator.DECREMENT) {
					operationsInExpression++;
				}
				String variableName = operand.toString();
				if (!liveIntVariables.contains(variableName)) {
					intExpression = false;
				}
			} else if (isNumberLiteral(operand)) {
				if (!isIntLiteralOrCharacterLiteral(operand)) {
					intExpression = false;
				}
			} else {
				unprocessedExpressions.add(node.toString());
				intExpression = false;
			}

			return true;
		}

		@Override
		public void endVisit(PrefixExpression node) {
			expressionsStack.pop();
			if (parentExpression()) {
				if (intExpression) {
					intOperationsCount.put(currMethodDeclaration,
							intOperationsCount.get(currMethodDeclaration) + operationsInExpression);
				}
				operationsInExpression = 0;
				intExpression = true;
			}
		}

		@Override
		public boolean visit(PostfixExpression node) {
			expressionsStack.push(node);
			HashSet<String> liveIntVariables = blockStack.peek();
			Expression operand = node.getOperand();
			if (isVariable(operand)) {
				operationsInExpression++;
				String variableName = operand.toString();
				if (!liveIntVariables.contains(variableName)) {
					intExpression = false;
				}
			} else {
				// TODO: these are cases that are not covered above
				unprocessedExpressions.add(node.toString());
				intExpression = false;
			}

			return true;
		}

		@Override
		public void endVisit(PostfixExpression node) {
			expressionsStack.pop();
			if (parentExpression()) {
				if (intExpression) {
					intOperationsCount.put(currMethodDeclaration,
							intOperationsCount.get(currMethodDeclaration) + operationsInExpression);
				}
				operationsInExpression = 0;
				intExpression = true;
			}
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

	private boolean isIntegerTypeCode(Type type) {
		if (!type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();

		return (typeCode == PrimitiveType.CHAR || typeCode == PrimitiveType.INT || typeCode == PrimitiveType.LONG
				|| typeCode == PrimitiveType.SHORT || typeCode == PrimitiveType.BYTE);
	}

	private boolean isLiteral(Expression exp) {
		return (exp instanceof NumberLiteral || exp instanceof BooleanLiteral || exp instanceof CharacterLiteral
				|| exp instanceof StringLiteral || exp instanceof NullLiteral);
	}

	private boolean isNumberLiteral(Expression exp) {
		return (exp instanceof NumberLiteral);
	}

	private boolean isIntLiteralOrCharacterLiteral(Expression exp) {
		if (exp instanceof CharacterLiteral) {
			return true;
		}
		if (exp instanceof NumberLiteral) {
			String str = exp.toString();

			try {
				Integer.parseInt(str);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	private boolean isNonIntLiteral(Expression exp) {
		return (isLiteral(exp) && !isIntLiteralOrCharacterLiteral(exp));
	}

	private boolean isVariable(Expression exp) {
		return (exp instanceof SimpleName || exp instanceof QualifiedName);
	}

	private boolean isExpression(Expression exp) {
		return (exp instanceof InfixExpression || exp instanceof PostfixExpression || exp instanceof PrefixExpression
				|| exp instanceof ParenthesizedExpression || exp instanceof CastExpression);
	}

	private boolean isLiveIntVariable(Expression exp) {
		HashSet<String> liveIntVariables = blockStack.peek();
		if (isVariable(exp)) {
			String name = exp.toString();
			if (liveIntVariables.contains(name)) {
				return true;
			}
		}
		return false;
	}

	private boolean isLiveIntVariable(String name) {
		HashSet<String> liveIntVariables = blockStack.peek();
		if (liveIntVariables.contains(name)) {
			return true;
		}
		return false;
	}

	private boolean isNonIntVariable(Expression exp) {
		return (isVariable(exp) && !isLiveIntVariable(exp));
	}

	private boolean parentExpression() {
		return expressionsStack.isEmpty();
	}
}
