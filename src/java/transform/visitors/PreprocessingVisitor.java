package transform.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import transform.TypeChecking.TypeChecker;

/**
 * Performs simple reformatting and blanket removals of disallowed constructs for simpler transformation.
 */
public class PreprocessingVisitor extends ASTVisitor {
    
    private ASTRewrite rewriter;
    private AST ast;
    private TypeChecker typeChecker = new TypeChecker();
    
	public PreprocessingVisitor(ASTRewrite rewriter, AST ast) {
		this.rewriter = rewriter;
		this.ast = ast;
	}
	
	/**
	 * Removes anonymous classes without exception.
	 */
    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        rewriter.remove(node, null);
        return true;
    }
	
    /**
     * Reformats one-line if statements to block statements.
     * 
     * i.e. if (x > 0)
     *       System.out.println(x);
     * 
     * becomes:
     *  
	 * if (x > 0) {
	 *    System.out.println(x);
	 * }
     */
	@Override
	public boolean visit(IfStatement node) {
	    Statement thenStmt = node.getThenStatement();

	    if (thenStmt instanceof ExpressionStatement) {

	        Block block = ast.newBlock();

	        Statement copiedStmt = (Statement) rewriter.createCopyTarget(thenStmt);
	        block.statements().add(copiedStmt);

	        rewriter.replace(thenStmt, block, null);
	    }

	    return true;
	}
	
	/**
	 * Removes nested classes without exception.
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
	    if (!(node.getParent() instanceof CompilationUnit)) {
	        rewriter.remove(node, null);
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Removes methods with a return type or parameters that are not allowed.
	 * Disallowed types are determined by the type checker.
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
	    IMethodBinding binding = node.resolveBinding();
        if (!typeChecker.allowedType(binding != null ? binding.getReturnType() : null) && !node.isConstructor()) {
            rewriter.remove(node, null);
            return false;
        }
	    @SuppressWarnings("unchecked")
        List<SingleVariableDeclaration> params = node.parameters();
        for (SingleVariableDeclaration param : params) {
            IVariableBinding paramBinding = param.resolveBinding();
            if (!typeChecker.allowedType(paramBinding != null ? paramBinding.getType() : null)) {
                rewriter.remove(node, null);
                return false;
            }
        }
	    return true;
	}

}
