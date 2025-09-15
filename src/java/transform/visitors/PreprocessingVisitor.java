package transform.visitors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

public class PreprocessingVisitor extends ASTVisitor {
    
    private ASTRewrite rewriter;
    private AST ast;
    
	public PreprocessingVisitor(ASTRewrite rewriter, AST ast) {
		this.rewriter = rewriter;
		this.ast = ast;
	}
	
	@Override
	public boolean visit(IfStatement node) {
	    Statement thenStmt = node.getThenStatement();

	    if (thenStmt instanceof ExpressionStatement) {

	        // Create a new block
	        Block block = ast.newBlock();

	        // Copy the original expression statement into the block
	        Statement copiedStmt = (Statement) rewriter.createCopyTarget(thenStmt);
	        block.statements().add(copiedStmt);

	        // Replace the old thenStatement with the new block
	        rewriter.replace(thenStmt, block, null);

	        // Apply rewriter later when you collect edits
	    }

	    return true;
	}

}
