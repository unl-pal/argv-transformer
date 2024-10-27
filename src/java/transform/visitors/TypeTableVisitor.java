package transform.visitors;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.SymbolTable.VarSTE;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeTable;
/**
 * Visitor class for creating a type table. The type table maps 
 * nodes in the AST to their types. 
 * 
 * @author mariapaquin
 *
 */
public class TypeTableVisitor extends ASTVisitor {
	private TypeTable table;
	private SymbolTable root;
	private Stack<SymbolTable> symbolTableStack;
	private TypeChecker typeChecker;
	private AST ast;

	/**
	 * Create a new TypeTableVisitor.
	 * 
	 * @param root The root SymbolTable (scope) of the AST.
	 * @param typeChecker The TypeChecker defining resolvable types. 
	 */
	public TypeTableVisitor(SymbolTable root, TypeChecker typeChecker) {
		table = new TypeTable();
		symbolTableStack = new Stack<SymbolTable>();
		this.root = root;
		this.typeChecker = typeChecker;
	}

	/**
	 * Getter for type table.
	 * @return TypeTable
	 */
	public TypeTable getTypeTable() {
		return table;
	}

	@Override
	public boolean visit(ArrayAccess node) {
		Expression expr = node.getArray();
		Type type = null;
		if (expr instanceof SimpleName) {
			SymbolTable currScope = symbolTableStack.peek();
			VarSTE sym = currScope.getVarSTE(((SimpleName) expr).getIdentifier());
			if (sym != null) {
				type = sym.getVarType();
			}
		}
		table.setNodeType(node, type);
		return true;
	}

	@Override
	public void endVisit(ArrayType node) {
		Type type = node.getElementType();
		table.setNodeType(node, type);
	}

	@Override
	public boolean visit(BooleanLiteral node) {
		table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
		return true;
	}

	@Override
	public void endVisit(CastExpression node) {
		if (node.getType().isPrimitiveType()) {
			table.setNodeType(node, node.getType());
		}
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		Type type = node.getType();
		table.setNodeType(node, type);
		return true;
	}

	@Override
	public void endVisit(ConditionalExpression node) {

		Expression expr = node.getExpression();
		Expression thenExpr = node.getThenExpression();
		Expression elseExpr = node.getElseExpression();

		Type typeExpr = table.getNodeType(expr);
		Type typeThenExpr = table.getNodeType(thenExpr);
		Type typeElseExpr = table.getNodeType(elseExpr);

		if (!isBooleanTypeCode(typeExpr)) {
			table.setNodeType(node, null);
			return;
		}
		if (typeThenExpr == null || typeElseExpr == null) {
			table.setNodeType(node, null);
			return;
		}

		table.setNodeType(node, typeThenExpr);
	}

	@Override
	public boolean visit(CompilationUnit node) {
		ast = node.getAST();
		symbolTableStack.push(root);
		return true;
	}

	@Override
	public boolean visit(FieldAccess node) {
		Type type = null;
		if (node.getExpression() instanceof ThisExpression) {
			SymbolTable currScope = symbolTableStack.peek();
			String name = node.getName().getIdentifier();
			VarSTE sym = currScope.getFieldVarSTE(name);
			if (sym != null) {
				type = sym.getVarType();
				table.setNodeType(node, type);
			}
		}
		table.setNodeType(node, type);

		return true;
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		Type type = node.getType();
		table.setNodeType(node, type);
		return true;
	}

	/*
	 * @Override public boolean visit(ForStatement node) {
	 * 
	 * return true; }
	 * 
	 * 
	 * @Override public void endVisit(ForStatement node) {
	 * 
	 * }
	 */
	
	@Override
	public boolean visit(IfStatement node) {
		Expression e = node.getExpression();
		//it will always be of a boolean type
		table.setNodeType(e, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
		//System.out.println("Setting " + e + " to boolean");
		//System.out.println(table.getNodeType(e));
		return true;
	}

	@Override
	public void endVisit(InfixExpression node) {
		ITypeBinding typeBinding = node.resolveTypeBinding();
		if (typeBinding != null && typeBinding.isPrimitive()) {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.toCode(typeBinding.getName())));
		} else {
			table.setNodeType(node, null);
		}
		
		Expression lhs = node.getLeftOperand();
		Expression rhs = node.getRightOperand();

		Type lhsType = table.getNodeType(lhs);
		Type rhsType = table.getNodeType(rhs);

		//only set to null when no "upper" inference happened
		if (table.getNodeType(node) == null && (lhsType == null || rhsType == null)) {
			table.setNodeType(node, null);
			return;
		}

		Operator op = node.getOperator();
		//System.out.println("Types " + lhsType + " " + rhsType + "\t" + node);

		// boolean operators
		if (op == Operator.CONDITIONAL_AND || op == Operator.CONDITIONAL_OR || op == Operator.XOR
				|| op == Operator.EQUALS || op == Operator.NOT_EQUALS) {
			//why do we even check that? only boolean nodes can be there
			//if (isBooleanTypeCode(lhsType) && isBooleanTypeCode(rhsType)) {
				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				//so are lhsType and rhsType
				//but not for the case of equals and not-equals
				if (!isNumericTypeCode(lhsType) && !isNumericTypeCode(rhsType)) {
					table.setNodeType(lhs, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
					table.setNodeType(rhs, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				}
				return;
			//}
		}

		// relational operators
		if (op == Operator.GREATER || op == Operator.GREATER_EQUALS || op == Operator.LESS || op == Operator.LESS_EQUALS
				|| op == Operator.EQUALS || op == Operator.NOT_EQUALS) {
			if ((isNumericTypeCode(lhsType) && isNumericTypeCode(rhsType))) {
				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			}
		}


		// TODO: need to differentiate between precisions
		// arithmetic operators, results in integer
		if (op == Operator.PLUS || op == Operator.MINUS || op == Operator.TIMES || op == Operator.DIVIDE) {
			if (isIntegerTypeCode(lhsType) && isIntegerTypeCode(rhsType)) {
				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
			}
		}
		
		// arithmetic operators, result in double
		if (op == Operator.PLUS || op == Operator.MINUS || op == Operator.TIMES || op == Operator.DIVIDE) {
			if ((isIntegerTypeCode(lhsType) && isFloatingPointTypeCode(rhsType)) ||
					(isFloatingPointTypeCode(lhsType) && isIntegerTypeCode(rhsType))) {
				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
			}
		}
		
		// arithmetic operators, result in double
		if (op == Operator.PLUS || op == Operator.MINUS || op == Operator.TIMES || op == Operator.DIVIDE) {
			if ((isIntegerTypeCode(lhsType) && isDoubleTypeCode(rhsType)) ||
					(isDoubleTypeCode(lhsType) && isIntegerTypeCode(rhsType))) {
				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.DOUBLE));
			}
		}
		
		// string concatenation
				/*
				 * if (op == Operator.PLUS) { if (isStringType(lhsType) ||
				 * isStringType(rhsType)) { table.setNodeType(node,
				 * ast.newSimpleType(ast.newSimpleName("String"))); } }
				 */
		
		
	}

		


	@Override
	public void endVisit(InstanceofExpression node) {
		Type type = node.getRightOperand();
		table.setNodeType(node, type);
	}

	/*
	 * The type of a method declaration will be its return type. 
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> params = node.parameters();
		for (SingleVariableDeclaration param : params) {
			Type type = param.getType();
			if (!typeChecker.allowedType(type)) {
				return false;
			}
		}

		SymbolTable currScope = symbolTableStack.peek();
		String name = getMethodSTEName(node);
		MethodSTE sym = currScope.getMethodSTE(name);
		//System.out.println("MD " + node.getName());
		SymbolTable newScope = sym.getSymbolTable();
		symbolTableStack.push(newScope);

		Type type = node.getReturnType2();
		table.setNodeType(node, type);
		return true;
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		boolean pushedMethod = true;
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> params = node.parameters();
		for (SingleVariableDeclaration param : params) {
			Type type = param.getType();
			if (!typeChecker.allowedType(type)) {
				pushedMethod = false;
			}
		}
		if (pushedMethod) {
			symbolTableStack.pop();
		}
	}

	@Override
	public void endVisit(MethodInvocation node) {
		
		/*
		 * Sets to the symbolic type if already assigned, otherwise sets it to the return
		 * type of the MethodInvocation if it can be found
		 * 
		 * e.g., for the method invocation table.setNodeType(node, null), we'd 
		 * need to look for the scope 'table' and find its method symbol table 
		 * element 'setNodeType.' Then we set the type of table.setNodeType(node, null) 
		 * to whatever the return type of 'setNodeType' is. 
		 */
		
//		Expression expr = node.getExpression();
//		if (expr == null) {
//			table.setNodeType(node, null);
//			return;
//		}
		String identifier = node.getName().getIdentifier();
		if(identifier.contains("makeSymbolicInteger")) {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
		} else if (identifier.equals("makeSymbolicBoolean")) {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
		} else if (identifier.equals("makeSymbolicReal")) {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.DOUBLE));
		} else if (identifier.equals("makeSymbolicFloat")) {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
		} else {
			IMethodBinding methodBinding = node.resolveMethodBinding();
			if (methodBinding != null) {
				ITypeBinding typeBinding = methodBinding.getReturnType();
				if (typeBinding != null && typeBinding.isPrimitive()) {
					table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.toCode(typeBinding.getName())));
				}
			} else {
				table.setNodeType(node, null);
			}
		}
		
		
//
//		Type type = table.getNodeType(expr);
//		if (type == null) {
//			table.setNodeType(node, null);
//			return;
//		}
		//System.out.println("Method invocation " + node.getExpression() + " " + node.getName().getIdentifier());
		
	}

	@Override
	public boolean visit(NullLiteral node) {
		// TODO: Need to differentiate between null literal and unresolvable type
		return true;
	}

	@Override
	public boolean visit(NumberLiteral node) {
		String number = node.getToken();
		// TODO: How to differentiate between precisions?
		if (number.contains(".")) {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
		} else {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
		}
		return true;
	}

	@Override
	public void endVisit(ParameterizedType node) {
		Type type = node.getType();
		table.setNodeType(node, type);
	}

	@Override
	public void endVisit(ParenthesizedExpression node) {
		Expression expr = node.getExpression();
		Type type = table.getNodeType(expr);
		table.setNodeType(node, type);
	}

	@Override
	public void endVisit(PostfixExpression node) {
		Expression expr = node.getOperand();
		Type type = table.getNodeType(expr);
		table.setNodeType(node, type);
	}

	@Override
	public void endVisit(PrefixExpression node) {
		Expression expr = node.getOperand();
		Type type = table.getNodeType(expr);
		table.setNodeType(node, type);
	}

	@Override
	public void endVisit(PrimitiveType node) {
		table.setNodeType(node, node);
	}

	@Override
	public boolean visit(QualifiedName node) {
		// TODO: For this we will have to change the scope
		// to node.getQualifier() and set the type to
		// the type of node.getName();
		/*
		 * SymbolTable currScope = symbolTableStack.peek(); Type type = null; VarSTE sym
		 * = currScope.getVarSTE(node.getQualifier().toString()); if (sym != null) {
		 * type = sym.getVarType(); }
		 */

		return true;
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		Type type = node.getType();
		table.setNodeType(node, type);
		return true;
	}

	@Override
	public boolean visit(SimpleName node) {
		Type type = null;
		String name = node.getIdentifier();
		SymbolTable currScope = symbolTableStack.peek();

		VarSTE sym = currScope.getVarSTE(name);
		if (sym != null) {
			type = sym.getVarType();
		}
//		if(name.contains("currentSize")) {
//			System.out.println("SimpleName " + node + " " + node.getParent() + "\t" + type);
//		}
		table.setNodeType(node, type);
		return true;
	}

	@Override
	public void endVisit(SimpleType node) {
		table.setNodeType(node, node);
	}

	@Override
	public boolean visit(StringLiteral node) {
		table.setNodeType(node, ast.newSimpleType(ast.newSimpleName("String")));
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		node.resolveBinding();
		if (node.isInterface()) {
			return false;
		}
		SymbolTable currScope = symbolTableStack.peek();
		ClassSTE sym = currScope.getClassSTE(node.getName().getIdentifier());
		//System.out.println("Sym " + sym + "\t" + node.getName().getIdentifier() + "\t" + node.isLocalTypeDeclaration() + "\t" + node.isMemberTypeDeclaration());
		//hack
		SymbolTable newScope = sym.getSymbolTable();
		symbolTableStack.push(newScope);
		return true;
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		if (!node.isInterface()) {
			symbolTableStack.pop();
		}
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		Type type = node.getType();
		table.setNodeType(node, type);

		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> fragments = node.fragments();
		for (VariableDeclarationFragment fragment : fragments) {
			table.setNodeType(fragment, type);
		}
		return true;
	}

	private String getMethodSTEName(MethodDeclaration node) {
		String name = node.getName().getIdentifier();

		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> parameters = node.parameters();
		for (SingleVariableDeclaration param : parameters) {
			Type type = param.getType();

			if (type instanceof PrimitiveType) {

				switch (((PrimitiveType) type).toString()) {
				case ("int"):
					name += "i";
					break;
				case ("double"):
					name += "d";
					break;
				case ("byte"):
					name += "b";
					break;
				case ("short"):
					name += "s";
					break;
				case ("char"):
					name += "c";
					break;
				case ("long"):
					name += "l";
					break;
				case ("float"):
					name += "f";
					break;
				case ("boolean"):
					name += "a";
					break;
				case ("void"):
					name += "v";
					break;
				}
			}
		}
		return name;
	}

	private boolean isStringType(Type type) {
		if (!type.isSimpleType())
			return false;
		Name name = ((SimpleType) type).getName();
		if (!name.isSimpleName())
			return false;
		return (((SimpleName) name).getIdentifier().equals("String"));
	}

	private boolean isDoubleTypeCode(Type type) {
		if(type == null) return false;
		if (!type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
		return typeCode == PrimitiveType.DOUBLE;
	}
	
	private boolean isFloatingPointTypeCode(Type type) {
		if (type == null || !type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.FLOAT);
	}
	
	private boolean isNumericTypeCode(Type type) {
		return isFloatingPointTypeCode(type) || 
				isDoubleTypeCode(type) ||
				isIntegerTypeCode(type);
	}
	private boolean isIntegerTypeCode(Type type) {
		if (type == null) {
			return false;
		}
		if (!type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();

		return (typeCode == PrimitiveType.CHAR || typeCode == PrimitiveType.INT || typeCode == PrimitiveType.LONG
				|| typeCode == PrimitiveType.SHORT || typeCode == PrimitiveType.BYTE);
	}

	private boolean isBooleanTypeCode(Type type) {
		if (type == null) {
			return false;
		}
		if (!type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.BOOLEAN);
	}
}