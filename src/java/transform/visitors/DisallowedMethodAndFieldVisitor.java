package transform.visitors;

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


/**
 * Propagate removals of disallowed methods and fields to invocations and references.
 * This is used in conjunction with RemoveEmptyBlockVisitor to clean up the document after transformation.
 */
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
	
	/**
	 * Removes methods that have been determined to be disallowed during transformation.
	 * New removals are added to the set of disallowed bindings
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
	    IBinding binding = node.resolveBinding();
		// binding can be null when it fails to resolve (e.g. incomplete
		// environment); disallowedBindings must never contain null, or every
		// subsequent method with an unresolved binding would spuriously match.
		if ((binding != null && disallowedBindings.contains(binding))
				|| (!node.isConstructor() && !typeChecker.allowedType(node.getReturnType2()))) {
			rewriter.remove(node, null);
			if (binding != null) {
				disallowedBindings.add(binding);
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Safely removes invocations of disallowed methods
	 */
	@Override
	public boolean visit(MethodInvocation node) {
	    IMethodBinding binding = node.resolveMethodBinding();
        if (binding == null || disallowedBindings.contains(binding.getMethodDeclaration())) {
            TypeResolutionUtils.safeRemoveOrReplace(node, rewriter, node.getAST(), false);
            return false;
        }
        return true;
	}
	
	/**
	 * Helper method to get the binding (with type handling) for a node in a stream.
	 * @param node a node that has been determined to be disallowed. This can be a 
	 * MethodDeclaration, VariableDeclarationFragment, or TypeDeclaration
	 * @return the binding for the node
	 */
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
