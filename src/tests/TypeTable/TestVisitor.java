package tests.TypeTable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import transform.TypeChecking.TypeTable;

public class TestVisitor extends ASTVisitor {

	TypeTable table;
	
	public TestVisitor(TypeTable table) {
		this.table = table;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {

		return true;
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
//		System.out.println("field delcaration " + node + ": " +
//				table.getNodeType(node));
		return false;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
//		System.out.println("method delcaration " + node.getName() + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(SingleVariableDeclaration node) {
//		System.out.println("parameter " + node.getName() + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(VariableDeclarationStatement node) {
//		System.out.println("variable declaration  " + node + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(NumberLiteral node) {
//		System.out.println("type of  " + node + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(ParenthesizedExpression node) {
//		System.out.println("type of  " + node + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(CastExpression node) {
//		System.out.println("type of  " + node + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(ClassInstanceCreation node) {
//		System.out.println("type of  " + node + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(PrefixExpression node) {
//		System.out.println("type of  " + node + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(PostfixExpression node) {
//		System.out.println("type of  " + node + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(SimpleName node) {
//		System.out.println("type of  " + node + ": " +
//				table.getNodeType(node));
		return true;
	}
	
	@Override
	public boolean visit(InfixExpression node) {
		System.out.println("type of  " + node + ": " +
				table.getNodeType(node));
		return true;
	}
	
}
