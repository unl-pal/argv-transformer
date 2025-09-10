package transform.visitors;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;



public class DisallowedMethodVisitor extends ASTVisitor {
    
    private final ASTRewrite rewriter;
    private final Set<ASTNode> disallowedMethods;
    
	public DisallowedMethodVisitor(ASTRewrite rewriter, Set<ASTNode> disallowedMethods) {
		this.rewriter = rewriter;
		this.disallowedMethods = disallowedMethods;
	}
}
