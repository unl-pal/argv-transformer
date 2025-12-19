package filter.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeChecker.CType;
import util.TypeResolutionUtils;

public class SimplifiedSuitableClassFinder {
    
    private File file;
    private CType type;
    private int minExpr;
    private int minCondStmt;
    private int minParams;
    public boolean isSuitable = false;
    
    public SimplifiedSuitableClassFinder(File file, CType type, int minExpr, int minCondStmt, int minParams) throws IOException {
        this.file = file;
		this.type = type;
		this.minExpr = minExpr;
		this.minCondStmt = minCondStmt;
		this.minParams = minParams;
    }
    
    public void analyze() throws IOException {
        File file = this.file;
        String source = new String(Files.readAllBytes(file.toPath()));
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        //ASTNode node = parser.createAST(null);
        CompilationUnit node = (CompilationUnit) parser.createAST(null);
        AST ast = node.getAST();
        
		AnalyzerVisitor visitor = new AnalyzerVisitor(minExpr, minCondStmt, minParams);
		node.accept(visitor);
		
		this.isSuitable = visitor.isSuitable();
	}
    
    public boolean isSuitable() {
		return isSuitable;
	}
    
    private class AnalyzerVisitor extends ASTVisitor {
        
        TypeChecker typeChecker = new TypeChecker();
        private int minExpr;
        private int minParams;
        private int minCondStmt;
        
        public AnalyzerVisitor(int minExpr, int minCondStmt, int minParams) {
			this.minParams = minParams;
			this.minExpr = minExpr;
			this.minCondStmt = minCondStmt;
		}
        
        public boolean isSuitable() {
			return minExpr <= 0 && minCondStmt <= 0 && minParams <= 0;
		}
        
        @Override
		public boolean visit(CompilationUnit node) {
			return true;
		}
        
		@Override
		public boolean visit(MethodDeclaration node) {
		    for (SingleVariableDeclaration param : (List<SingleVariableDeclaration>) node.parameters()) {
		        if (typeChecker.allowedType(param.getType())){
		            this.minParams--;
		        }
		    }
			return true;
		}
		
		@Override
		public boolean visit(IfStatement node) {
		    this.minCondStmt--;
			return true;
		}
		
		@Override
		public boolean visit(InfixExpression node) {
		    this.minExpr--;
			return false; // treat each infix expression separately
		}
		
		@Override
        public boolean visit(PrefixExpression node) {
            this.minExpr--;
            return false; // treat each prefix expression separately
        }
		
		@Override
        public boolean visit(PostfixExpression node) {
            this.minExpr--;
            return false; // treat each postfix expression separately
        }
        
    }

}
