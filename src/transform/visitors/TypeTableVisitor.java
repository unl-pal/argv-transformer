package transform.visitors;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
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
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.SymbolTable.VarSTE;
import transform.TypeChecking.TypeTable;

public class TypeTableVisitor extends ASTVisitor {
	private TypeTable table;
	private SymbolTable root;
	private Stack<SymbolTable> symbolTableStack;
	private AST ast;
	private String currClass;
	private String currMethod;

	public TypeTableVisitor(SymbolTable root) {
		table = new TypeTable();
		symbolTableStack = new Stack<SymbolTable>();
		this.root = root;
	}

	public TypeTable getTypeTable() {
		return table;
	}
	
	@Override
	public boolean visit(CompilationUnit node) {
		ast = node.getAST();
		symbolTableStack.push(root);
		return true;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		if(node.isInterface()) {
			return false;
		}
		SymbolTable currScope = symbolTableStack.peek();
		ClassSTE sym = currScope.getClassSTE(node.getName().getIdentifier());

		currClass = node.getName().getIdentifier();
		
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
	public boolean visit(FieldDeclaration node) {
		Type type = node.getType();
		table.setNodeType(node, type);
		return true;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
//		System.out.println(node.getName());
		SymbolTable currScope = symbolTableStack.peek();
//		System.out.println(currScope.getId() + " - " + currScope.getTable());

		MethodSTE sym = currScope.getMethodSTE(node.getName().getIdentifier());
		SymbolTable newScope = sym.getSymbolTable();
		symbolTableStack.push(newScope);

		currMethod = node.getName().getIdentifier();
		
		Type type = node.getReturnType2();
		table.setNodeType(node, type);
		return true;
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {
		symbolTableStack.pop();
	}
	
//	@Override
//	public boolean visit(ForStatement node) {		
//
//		return true;
//	}
//	
//	
//	@Override
//	public void endVisit(ForStatement node) {
//
//	}
	
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		Type type = node.getType();
		table.setNodeType(node, type);
		return true;
	}
	
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		Type type = node.getType();
		table.setNodeType(node, type);
		
		List<VariableDeclarationFragment> fragments = node.fragments();
		for(VariableDeclarationFragment fragment: fragments) {
			table.setNodeType(fragment, type);
		}
		return true;
	}
	
	@Override
	public boolean visit(NumberLiteral node) {
		String number = node.getToken();
		if(number.contains(".")) {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
		}else {
			table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
		}
		return true;
	}
	
	@Override
	public boolean visit(BooleanLiteral node) {
		table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
		return true;
	}
	
	@Override
	public boolean visit(NullLiteral node) {

		return true;
	}
	
	@Override
	public boolean visit(SimpleName node) {
		Type type = null;
		String name = node.getIdentifier();
		SymbolTable currScope = symbolTableStack.peek();

		VarSTE sym = currScope.getVarSTE(name);
		if(sym != null) {
			type = sym.getVarType();
		}
		table.setNodeType(node, type);
		return true;
	}
	
	@Override
	public boolean visit(QualifiedName node) {
		// TODO: For this we will have to change the scope
		// to node.getQualifier() and set the type to 
		// the type of node.getName();
		SymbolTable currScope = symbolTableStack.peek();
		Type type = null;
		VarSTE sym = currScope.getVarSTE(node.getQualifier().toString());
		if(sym != null) {
			type = sym.getVarType();
		}

		return true;
	}
	
	
	@Override
	public boolean visit(ClassInstanceCreation node) {
		Type type = node.getType();
		table.setNodeType(node, type);
		return true;
	}
	
	@Override
	public void endVisit(InstanceofExpression node) {
		Type type = node.getRightOperand();
		table.setNodeType(node, type);
	}
	
	@Override
	public void endVisit(CastExpression node) {
		Expression expr = node.getExpression();
		if(table.getNodeType(expr) != null) {
			table.setNodeType(node, node.getType());
		}
	}
	
	@Override
	public void endVisit(ParenthesizedExpression node) {
		Expression expr = node.getExpression();
		Type type = table.getNodeType(expr);
		table.setNodeType(node, type);
	}
	
	@Override
	public void endVisit(ParameterizedType node) {
		Type type = node.getType();
		table.setNodeType(node, type);
	}
	
	@Override
	public void endVisit(SimpleType node) {
		table.setNodeType(node, node);
	}
	
	@Override
	public void endVisit(ArrayType node) {
		Type type = node.getElementType();
		table.setNodeType(node, type);
	}
	
	@Override
	public void endVisit(PrimitiveType node) {
		table.setNodeType(node, node);
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
	public void endVisit(MethodInvocation node) {
		Expression expr = node.getExpression();
		if(expr == null) {
			table.setNodeType(node, null);
			return;
		}
		
		Type type = table.getNodeType(expr);
		if(type == null) {
			table.setNodeType(node, null);
			return;
		}
		
		// TODO: Else we want to change the scope and 
		// find the return type of the method node.getName(),
		// but for now ...
		table.setNodeType(node, null);
	}
	
	@Override
	public boolean visit(StringLiteral node) {
		table.setNodeType(node, ast.newSimpleType(ast.newSimpleName("String")));
		return true;
	}
	
	@Override
	public void endVisit(InfixExpression node) {
		Expression lhs = node.getLeftOperand();
		Expression rhs = node.getRightOperand();
		
		Type lhsType = table.getNodeType(lhs);
		Type rhsType = table.getNodeType(rhs);
		
		if(lhsType == null || rhsType == null) {
			table.setNodeType(node, null);
			return;
		}
		
		Operator op  = node.getOperator();
		
		if(op == Operator.CONDITIONAL_AND ||
				op == Operator.CONDITIONAL_OR ||
				op == Operator.XOR ||
				op == Operator.EQUALS ||
				op == Operator.NOT_EQUALS) {
			if(isBooleanTypeCode(lhsType) && isBooleanTypeCode(rhsType)) {
				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				return;
			}
		}
		
		if (op == Operator.GREATER || 
				op == Operator.GREATER_EQUALS || 
				op == Operator.LESS || 
				op == Operator.LESS_EQUALS || 
				op == Operator.EQUALS || 
				op == Operator.NOT_EQUALS) {
			if ((isIntegerTypeCode(lhsType) || isFloatingPointTypeCode(lhsType))
					&& (isIntegerTypeCode(rhsType) || isFloatingPointTypeCode(rhsType))) {
				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			}
		}

		if(op == Operator.PLUS ||
				op == Operator.MINUS ||
				op == Operator.TIMES) {
			if(isIntegerTypeCode(lhsType) && isIntegerTypeCode(rhsType)) {
				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
			}
		}
		
		if(op == Operator.PLUS) {
			if(isStringType(lhsType) || isStringType(rhsType)) {
				table.setNodeType(node, ast.newSimpleType(ast.newSimpleName("String")));
			}
		}
		
//		if(op == Operator.PLUS ||
//				op == Operator.MINUS ||
//				op == Operator.TIMES ||
//				op == Operator.DIVIDE) {
//			if((isFloatingPointTypeCode(lhsType) || isFloatingPointTypeCode(rhsType))) {
//				table.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
//			}
//		}
	}
	
	@Override
	public boolean visit(ArrayAccess node) {
		Expression expr = node.getArray();
		Type type = null;
		if(expr instanceof SimpleName) {
			SymbolTable currScope = symbolTableStack.peek();
			VarSTE sym = currScope.getVarSTE(((SimpleName)expr).getIdentifier());
			if(sym != null) {
				type = sym.getVarType();
			}
		}
		table.setNodeType(node, type);
		return true;
	}
	
	@Override
	public boolean visit(FieldAccess node) {
		Type type = null;
		if(node.getExpression() instanceof ThisExpression) {
			SymbolTable currScope = symbolTableStack.peek();
			String name = node.getName().getIdentifier();
			VarSTE sym = currScope.getFieldVarSTE(name);
			if(sym != null) {
				type = sym.getVarType();
				table.setNodeType(node, type);
			}
		}
		table.setNodeType(node, type);

		return true;
	}
	
	private boolean isStringType(Type type) {
		if (!type.isSimpleType()) return false;
		Name name = ((SimpleType) type).getName();
		if(!name.isSimpleName()) return false;
		return (((SimpleName)name).getIdentifier().equals("String"));
	}
	
	private boolean isFloatingPointTypeCode(Type type) {
		if (!type.isPrimitiveType()) return false;
		Code typeCode = ((PrimitiveType)type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.FLOAT ||
				typeCode == PrimitiveType.DOUBLE);
	}
	
	private boolean isIntegerTypeCode(Type type) {
		if(type == null) {
			return false;
		}
		if (!type.isPrimitiveType()) return false;
		Code typeCode = ((PrimitiveType)type).getPrimitiveTypeCode();
	
		return (typeCode == PrimitiveType.CHAR ||
				typeCode == PrimitiveType.INT ||
				typeCode == PrimitiveType.LONG ||
				typeCode == PrimitiveType.SHORT ||
				typeCode == PrimitiveType.BYTE);
	}
	
	
	private boolean isBooleanTypeCode(Type type) {
		if (!type.isPrimitiveType()) return false;
		Code typeCode = ((PrimitiveType)type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.BOOLEAN);
	}
	
	
}