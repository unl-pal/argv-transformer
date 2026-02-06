package transform.visitors;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

public class CommentAddingVisitor extends ASTVisitor {
	
	private ASTRewrite rewriter;
	private List<String> preImportComments;
	private List<String> postImportComments;

	public CommentAddingVisitor(ASTRewrite rewriter, List<String> preImportComments, List<String> postImportComments) {
		this.rewriter = rewriter;
		this.preImportComments = preImportComments;
		this.postImportComments = postImportComments;
	}
	
	@Override
	public boolean visit(CompilationUnit node) {
		ListRewrite aboveImportsRewrite = rewriter.getListRewrite(node, CompilationUnit.IMPORTS_PROPERTY);
        ListRewrite belowImportsRewrite = rewriter.getListRewrite(node, CompilationUnit.TYPES_PROPERTY);
        Collections.reverse(postImportComments);
        Collections.reverse(preImportComments);
        for (String comment : preImportComments) {
        	 // Extract the text from the original source.
            Statement commentPlaceholder = (Statement) rewriter.createStringPlaceholder(comment, ASTNode.EMPTY_STATEMENT);                 
            aboveImportsRewrite.insertFirst(commentPlaceholder, null);
        }
		Statement disclaimer = (Statement) rewriter.createStringPlaceholder(
				"/** [ARG-V](https://arg-v.dev) was used to collect, filter, and transform these benchmarks automatically */",
				ASTNode.EMPTY_STATEMENT);

		aboveImportsRewrite.insertFirst(disclaimer, null);
        for (String comment : postImportComments) {
       	 // Extract the text from the original source.
           Statement commentPlaceholder = (Statement) rewriter.createStringPlaceholder(comment, ASTNode.EMPTY_STATEMENT);                 
           belowImportsRewrite.insertFirst(commentPlaceholder, null);
       }
		return true;
	}
}
