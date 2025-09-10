package transform.visitors;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import transform.TypeChecking.TypeChecker;



public class DisallowedMethodAndFieldVisitor extends ASTVisitor {
    
    private final ASTRewrite rewriter;
    private final Set<ASTNode> disallowed;
    private final TypeChecker typeChecker;
    
	public DisallowedMethodAndFieldVisitor(ASTRewrite rewriter, Set<ASTNode> disallowed, TypeChecker typeChecker) {
		this.rewriter = rewriter;
		this.disallowed = disallowed;
		this.typeChecker = typeChecker;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		if (disallowed.contains(node) || !typeChecker.allowedType(node.getReturnType2())) {
			rewriter.remove(node, null);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
        return true;
	}
}
