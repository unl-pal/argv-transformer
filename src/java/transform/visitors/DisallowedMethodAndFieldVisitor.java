package transform.visitors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import transform.TypeChecking.TypeChecker;
import util.TypeResolutionUtils;



public class DisallowedMethodAndFieldVisitor extends ASTVisitor {
    
    private final ASTRewrite rewriter;
    private final Set<IBinding> disallowedBindings;
    private final TypeChecker typeChecker;
    
	public DisallowedMethodAndFieldVisitor(ASTRewrite rewriter, Set<ASTNode> disallowed, TypeChecker typeChecker) {
		this.rewriter = rewriter;
		this.typeChecker = typeChecker;
		this.disallowedBindings = disallowed.stream()
	            .map(this::getBindingForNode)
	            .filter(Objects::nonNull)
	            .collect(Collectors.toSet());
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
	    IBinding binding = node.resolveBinding();
		if (disallowedBindings.contains(binding) || !typeChecker.allowedType(node.getReturnType2())) {
			rewriter.remove(node, null);
			disallowedBindings.add(binding);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
	    IMethodBinding binding = node.resolveMethodBinding();
        if (binding != null && disallowedBindings.contains(binding.getMethodDeclaration())) {
            TypeResolutionUtils.safeRemoveOrReplace(node, rewriter, node.getAST(), false);
            return false;
        }
        return true;
	    // TODO: detect if disallowed contains invoked methods from removed methoddeclarations and if so run saferemoveorreplace
	    // TODO: move where we put main method
	}
	
	 private IBinding getBindingForNode(ASTNode node) {
	        if (node instanceof MethodDeclaration) {
	            return ((MethodDeclaration) node).resolveBinding();
	        } else if (node instanceof VariableDeclarationFragment) {
	            return ((VariableDeclarationFragment) node).resolveBinding();
	        } else if (node instanceof TypeDeclaration) {
	            return ((TypeDeclaration) node).resolveBinding();
	        }
	        return null;
	    }
}
