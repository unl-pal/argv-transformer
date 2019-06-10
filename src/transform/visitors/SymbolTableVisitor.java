package transform.visitors;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.SymbolTable.VarSTE;
import transform.TypeChecking.TypeChecker;

public class SymbolTableVisitor extends ASTVisitor {
	private SymbolTable root;
	private Stack<SymbolTable> symbolTableStack;
	private TypeChecker typeChecker;

	public SymbolTableVisitor(TypeChecker typeChecker) {
		root = new SymbolTable(null);
		symbolTableStack = new Stack<SymbolTable>();
		this.typeChecker = typeChecker;
	}

	public SymbolTable getRoot() {
		return root;
	}

	@Override
	public boolean visit(CompilationUnit node) {
		symbolTableStack.push(root);
		return true;
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		SymbolTable currScope = symbolTableStack.peek();
		Type type = node.getType();
		List<VariableDeclarationFragment> fragments = node.fragments();
		for (VariableDeclarationFragment fragment : fragments) {
			VarSTE sym = new VarSTE(fragment.getName().getIdentifier(), type);
			sym.setFieldVar(true);
			sym.setSymbolTable(currScope);
			String name = fragment.getName().getIdentifier();
			currScope.put(name, sym);
		}

		return true;
	}

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
		// System.out.println("adding " + node.getName() + " to scope " +

		// Create a SymbolTableElement, add it to currScope
		String name = getMethodSTEName(node);

		MethodSTE sym = new MethodSTE(name);

		Type returnType = node.getReturnType2();
		sym.setReturnType(returnType);

		currScope.put(name, sym);
		// Create a new scope (SymbolTable), and push it onto the stack
		SymbolTable newScope = new SymbolTable(currScope);

		sym.setSymbolTable(newScope);
		symbolTableStack.push(newScope);

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

//	@Override
//	public boolean visit(ForStatement node) {
//		SymbolTable currScope = symbolTableStack.peek();
//		SymbolTable newScope = new SymbolTable(currScope);
//		newScope.setId("FOR_STATEMENT");
//		symbolTableStack.push(newScope);
//
//		return true;
//	}
//
//	@Override
//	public void endVisit(ForStatement node) {
//		symbolTableStack.pop();
//	}
	

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		Type type = node.getType();
		SymbolTable currScope = symbolTableStack.peek();

		VarSTE sym = new VarSTE(node.getName().getIdentifier(), type);
		String name = node.getName().getIdentifier();
		currScope.put(name, sym);
		return true;
	}

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		Type type = node.getType();
		SymbolTable currScope = symbolTableStack.peek();

		List<VariableDeclarationFragment> fragments = node.fragments();
		for (VariableDeclarationFragment fragment : fragments) {
			VarSTE sym = new VarSTE(fragment.getName().getIdentifier(), type);
			String name = fragment.getName().getIdentifier();
			currScope.put(name, sym);
		}
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		if (node.isInterface()) {
			return false;
		}

		SymbolTable currScope = symbolTableStack.peek();
		// System.out.println("adding " + node.getName() + " to scope " +
		// currScope.getId());

		ClassSTE sym = new ClassSTE(node.getName().getIdentifier());
		String name = node.getName().getIdentifier();
		currScope.put(name, sym);

		SymbolTable newScope = new SymbolTable(currScope);

		sym.setSymbolTable(newScope);
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
		SymbolTable currScope = symbolTableStack.peek();

		List<VariableDeclarationFragment> fragments = node.fragments();

		for (VariableDeclarationFragment fragment : fragments) {
			VarSTE sym = new VarSTE(fragment.getName().getIdentifier(), type);
			String name = fragment.getName().getIdentifier();
			currScope.put(name, sym);
		}
		return true;
	}

	private String getMethodSTEName(MethodDeclaration node) {
		String name = node.getName().getIdentifier();

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

}
