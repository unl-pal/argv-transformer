package transform.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MultiTextEdit;

public class CommentPruningVisitor extends ASTVisitor{
	
	private MultiTextEdit commentsToDelete = new MultiTextEdit();
	private String source;
	
	public CommentPruningVisitor(String source) {
		this.source = source;
	}
	
	@Override
	public boolean visit(CompilationUnit node) {
		int firstBodyStart = Integer.MAX_VALUE;
	    for (Object typeObj : node.types()) {
	        if (typeObj instanceof TypeDeclaration) {
	            TypeDeclaration typeDecl = (TypeDeclaration) typeObj;
	            int startPosition = typeDecl.getJavadoc() != null ? typeDecl.getJavadoc().getStartPosition() : typeDecl.getStartPosition();
	            firstBodyStart = Math.min(firstBodyStart, startPosition);
	        }
	    }
	    // If no method is found, default to the end of the file.
	    if (firstBodyStart == Integer.MAX_VALUE) {
	    	firstBodyStart = node.getLength();
	    }
		List<Comment> comments = node.getCommentList();
	    for (Comment comment : comments) {
			int start = comment.getStartPosition();
    		int length = comment.getLength();
    		boolean whitespaceOnlyOnLine = detectIfOnlyWhitespaceOnLine(start - 1, start + length);
	    	if (start + length <= firstBodyStart) {
	    		// handling extra whitespace
	    		while (whitespaceOnlyOnLine && (start + length) < source.length() && 
	    				(source.charAt(start + length) == '\t' || source.charAt(start + length) == '\n' || source.charAt(start + length) == '\r')) {
	    			length++;
	    		}
	    		commentsToDelete.addChild(new DeleteEdit(start, length));
	    	}
	    }
		return true;
	}

	public MultiTextEdit getCommentsToDelete() {
		return commentsToDelete;
	}
	
	private boolean detectIfOnlyWhitespaceOnLine(int beforeComment, int afterComment) {
		boolean newlineFound = false;
		while (!newlineFound && beforeComment >= 0) {
			if (!Character.isWhitespace(source.charAt(beforeComment))) {
				return false;
			}
			if (source.charAt(beforeComment) == '\n'){
					newlineFound = true;
			}
			beforeComment--;
		}
		newlineFound = false;
		while (!newlineFound && afterComment < source.length()) {
			if (!Character.isWhitespace(source.charAt(afterComment))) {
				return false;
			}
			if (source.charAt(afterComment) == '\n'){
					newlineFound = true;
			}
			afterComment++;
		}
		return true;
	}
	

}
