package transform.visitors;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
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

public class SymbolTableVisitor extends ASTVisitor {
	private SymbolTable root;
	private Stack<SymbolTable> symbolTableStack;

	public SymbolTableVisitor() {
		root = new SymbolTable(null);
		symbolTableStack = new Stack<SymbolTable>();
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
	public boolean visit(TypeDeclaration node) {
		if(node.isInterface()) {
			return false;
		}
		
		SymbolTable currScope = symbolTableStack.peek();
//		System.out.println("adding " + node.getName() + " to scope " +  currScope.getId());

		ClassSTE sym = new ClassSTE(node.getName().getIdentifier());
		String name = node.getName().getIdentifier();
		currScope.put(name, sym);
		
		SymbolTable newScope = new SymbolTable(currScope);
		newScope.setId(node.getName().getIdentifier() + "_CLASS");
		sym.setSymbolTable(newScope);
		symbolTableStack.push(newScope);
		return true;
	}
	
	@Override
	public void endVisit(TypeDeclaration node) {
		if (!node.isInterface()) {
//			System.out.println(node.getName().getIdentifier() + " - " + symbolTableStack.peek().getTable());
			symbolTableStack.pop();
		}
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
//		System.out.println(node.getName());
		SymbolTable currScope = symbolTableStack.peek();
//		System.out.println("adding " + node.getName() + " to scope " +  currScope.getId());

		// Create a SymbolTableElement, add it to currScope
		MethodSTE sym = new MethodSTE(node.getName().getIdentifier());
		
		List<SingleVariableDeclaration> parameters = node.parameters();
		for(SingleVariableDeclaration param: parameters) {
			sym.addParam(param);
		}
		
		Type returnType = node.getReturnType2();
		sym.setReturnType(returnType);
		
		String name = node.getName().getIdentifier();
		currScope.put(name, sym);
		// Create a new scope (SymbolTable), and push it onto the stack
		SymbolTable newScope = new SymbolTable(currScope);
		newScope.setId(node.getName().getIdentifier() + "_METHOD");
		sym.setSymbolTable(newScope);

		symbolTableStack.push(newScope);
		
		return true;
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {
		symbolTableStack.pop();
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
//	
//	@Override
//	public void endVisit(ForStatement node) {
//		symbolTableStack.pop();
//	}
//	
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
	public boolean visit(SingleVariableDeclaration node) {
		Type type = node.getType();
		SymbolTable currScope = symbolTableStack.peek();

		VarSTE sym = new VarSTE(node.getName().getIdentifier(), type);
		String name = node.getName().getIdentifier();
		currScope.put(name, sym);
		return true;
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
	
}
