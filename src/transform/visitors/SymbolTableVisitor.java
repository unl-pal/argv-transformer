package transform.visitors;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
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
/**
 * Visitor class used to build the symbol table.
 * 
 * In the current implementation, we are only creating a new SymbolTable, 
 * i.e., a new scope, for type declarations (classes) and methods. Need to 
 * create a new scope for all blocks/loop bodies.
 * 
 * @author mariapaquin
 *
 */
public class SymbolTableVisitor extends ASTVisitor {
	private SymbolTable root;
	private Stack<SymbolTable> symbolTableStack;
	private TypeChecker typeChecker;

	/**
	 * Create a new SymbolTableVisitor.
	 * 
	 * @param typeChecker TypeChecker defining allowed types.
	 */
	public SymbolTableVisitor(TypeChecker typeChecker) {
		root = new SymbolTable(null);
		symbolTableStack = new Stack<SymbolTable>();
		this.typeChecker = typeChecker;
	}

	/**
	 * 
	 * @return root, i.e., parent symbol table
	 */
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
		@SuppressWarnings("unchecked")
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
			/*
			 * If the method has types that are not allowed, we don't need 
			 * to create a symbol table for it, since we are going to 
			 * remove it anyway. This simplifies the process of identifying a 
			 * method symbol table element based on its generated name (i.e., see 
			 * the method getMethodSTEName(), where we only consider primitive type 
			 * parameters).
			 */
			if (!TypeChecker.allowedType(type)) {
				return false;
			}
		}

		SymbolTable currScope = symbolTableStack.peek();

		String name = getMethodSTEName(node);
		MethodSTE sym = new MethodSTE(name);
		Type returnType = node.getReturnType2();
		sym.setReturnType(returnType);
		
		currScope.put(name, sym);
		
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
			/*
			 * If the method has a parameter type that is not allowed, 
			 * we did not create a symbol table element for it and thus 
			 * there is nothing to pop.
			 */
			if (!TypeChecker.allowedType(type)) {
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

		@SuppressWarnings("unchecked")
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

		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> fragments = node.fragments();

		for (VariableDeclarationFragment fragment : fragments) {
			VarSTE sym = new VarSTE(fragment.getName().getIdentifier(), type);
			String name = fragment.getName().getIdentifier();
			currScope.put(name, sym);
		}
		return true;
	}

	/**
	 * Use the method name and its parameters to uniquely name 
	 * it's symbol table element.
	 * 
	 * @param node MethodDeclaration node
	 * @return the method's name in the symbol table
	 */
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

}
