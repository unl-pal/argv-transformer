package transform.visitors;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeChecker;

public class MethodCallParamVisitor extends ASTVisitor {
	private SymbolTable root;
	private Stack<SymbolTable> symbolTableStack;
	private ASTRewrite rewriter;

	public MethodCallParamVisitor(SymbolTable root, ASTRewrite rewriter) {
		this.root = root;
		this.rewriter = rewriter;		
		symbolTableStack = new Stack<SymbolTable>();
	}
	
	public ASTRewrite getRewriter() {
		return rewriter;
	}
	

	@Override
	public boolean visit(CompilationUnit node) {
		symbolTableStack.push(root);
		return true;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		SymbolTable currScope = symbolTableStack.peek();
		ClassSTE sym = currScope.getClassSTE(node.getName().getIdentifier());

		SymbolTable newScope = sym.getSymbolTable();

		symbolTableStack.push(newScope);
		return true;
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		symbolTableStack.pop();
	}
	
	
	@Override
	public boolean visit(MethodDeclaration node) {
		SymbolTable currScope = symbolTableStack.peek();
		MethodSTE sym = currScope.getMethodSTE(node.getName().getIdentifier());
		SymbolTable newScope = sym.getSymbolTable();
		symbolTableStack.push(newScope);
		return true;
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		symbolTableStack.pop();
	}

}
