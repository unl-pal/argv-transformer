package transform.visitors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.SymbolTable.VarSTE;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeTable;
/**
 * Visitor class used to find and perform necessary code transformations.
 * 
 * @author mariapaquin
 *
 */
public class TypeCheckingVisitor extends ASTVisitor {

	private ASTRewrite rewriter;
	private AST ast;
	private File file;
	private TypeChecker typeChecker;
	private SymbolTable root;
	private TypeTable typeTable;
	private Stack<SymbolTable> symbolTableStack;
	private boolean randomImported;
	private boolean randUsedInMethod;
	private boolean randUsedInProgram;
	private String currMethod;
	private ArrayList<VarSTE> initializedVars;

	/**
	 * 
	 * @param root
	 * @param rewriter
	 * @param typeTable
	 * @param typeChecker
	 * @throws IOException
	 */
	public TypeCheckingVisitor(SymbolTable root, ASTRewrite rewriter, TypeTable typeTable, TypeChecker typeChecker)
			throws IOException {
		this.root = root;
		this.rewriter = rewriter;
		this.typeTable = typeTable;
		this.typeChecker = typeChecker;
		randomImported = false;
		randUsedInMethod = false;
		randUsedInProgram = false;
	}
	
	@Override
	public void endVisit(ArrayAccess node) {
		Type type = typeTable.getNodeType(node);
		if (type != null && typeChecker.allowedType(type)) {
			return;
		}

		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
		} else if (node.getLocationInParent() == Assignment.LEFT_HAND_SIDE_PROPERTY) {
			// TODO
		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			// TODO
		}
	}
	
	@Override
	public boolean visit(Assignment node) {
		Expression lhs = node.getLeftHandSide();
		Expression rhs = node.getRightHandSide();

		Type lhsType = typeTable.getNodeType(lhs);
	
		if ((lhsType == null || !typeChecker.allowedType(lhsType)) || lhs instanceof FieldAccess) {
			ASTNode parent = node.getParent(); // ExpressionStatement
			if (parent.getParent() instanceof Block) {
				rewriter.remove(parent, null);
			} else {
				rewriter.replace(parent, ast.newBlock(), null);
			}

			typeTable.setNodeType(lhs, null);
			typeTable.setNodeType(rhs, null);

			return false;
		}
		return true;
	}
	
	@Override
	public void endVisit(CastExpression node) {
		Type castType = typeTable.getNodeType(node);
		if(typeChecker.allowedType(castType)) {
			return;
		}
		
		if(node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			VariableDeclarationFragment parent = (VariableDeclarationFragment) node.getParent();
			Type type = typeTable.getNodeType(parent);

			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
				return;
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
				return;
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
				return;
			} else {
				rewriter.remove(node, null);
				typeTable.setNodeType(parent, null);
			}
		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			// TODO
		}
	}
	
	@Override
	public void endVisit(ClassInstanceCreation node) {
		Type type = typeTable.getNodeType(node);
		if (!typeChecker.allowedType(type)) {
			if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
				Assignment parent = (Assignment) node.getParent();

				typeTable.setNodeType(parent.getLeftHandSide(), null);
				rewriter.remove(node.getParent().getParent(), null);

			} else if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
				VariableDeclarationFragment parent = (VariableDeclarationFragment) node.getParent();

				typeTable.setNodeType(parent.getName(), null);
				rewriter.remove(node.getParent().getParent(), null);

			} else if (node.getLocationInParent() == ReturnStatement.EXPRESSION_PROPERTY) {
				ReturnStatement parent = (ReturnStatement) node.getParent();
				ClassInstanceCreation ci = ast.newClassInstanceCreation();
				ci.setType(ast.newSimpleType(ast.newSimpleName("Object")));
				rewriter.replace(parent.getExpression(), ci, null);
			}
		}
	}
	
	@Override
	public boolean visit(CompilationUnit node) {
		ast = node.getAST();

		// Remove imports not in java standard library
		@SuppressWarnings("unchecked")
		List<ImportDeclaration> imports = node.imports();

		for (ImportDeclaration importDec : imports) {
			String importName = importDec.getName().getFullyQualifiedName();
			String[] importSplit = importName.split("\\.");
			String className = importSplit[importSplit.length - 1];
		//	if (!importName.startsWith("java.") && !importName.startsWith("javax.")) {
			if (!importName.startsWith("java.")){
				rewriter.remove(importDec, null);
			} else {
				if (className.equals("Random")) {
					randomImported = true;
				}
			}
		}

		symbolTableStack = new Stack<SymbolTable>();
		symbolTableStack.push(root);

		return true;
	}

	@Override
	public void endVisit(CompilationUnit node) {
		ImportDeclaration id = ast.newImportDeclaration();
		id.setName(ast.newName("gov.nasa.jpf.symbc.Debug".split("\\.")));
		ListRewrite listRewrite = rewriter.getListRewrite(node, CompilationUnit.IMPORTS_PROPERTY);
		listRewrite.insertFirst(id, null);
		
		if (!randomImported && randUsedInProgram) {
			id = ast.newImportDeclaration();
			id.setName(ast.newName("java.util.Random".split("\\.")));
			listRewrite = rewriter.getListRewrite(node, CompilationUnit.IMPORTS_PROPERTY);
			listRewrite.insertFirst(id, null);
		}
	}
	
	
	@Override
	public void endVisit(ConditionalExpression node) {
		Expression expr = node.getExpression();
		Expression thenExpr = node.getThenExpression();
		Expression elseExpr = node.getElseExpression();

		Type typeExpr = typeTable.getNodeType(expr);
		Type typeThenExpr = typeTable.getNodeType(thenExpr);
		Type typeElseExpr = typeTable.getNodeType(elseExpr);

		if (!isBooleanTypeCode(typeExpr)) {
			replaceWithRandomBoolean(expr);
			typeTable.setNodeType(expr, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
		}

		if (typeChecker.allowedType(typeThenExpr) && typeChecker.allowedType(typeElseExpr)) {
			return;
		}

		if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			Type type = typeTable.getNodeType(node.getParent());

			if (isIntegerTypeCode(type)) {
				if (!isIntegerTypeCode(typeThenExpr)) {
					replaceWithRandomInteger(thenExpr);
					typeTable.setNodeType(thenExpr, ast.newPrimitiveType(PrimitiveType.INT));
				}
				if (!isIntegerTypeCode(typeElseExpr)) {
					replaceWithRandomInteger(elseExpr);
					typeTable.setNodeType(elseExpr, ast.newPrimitiveType(PrimitiveType.INT));
				}

			} else if (isBooleanTypeCode(type)) {
				if (!isBooleanTypeCode(typeThenExpr)) {
					replaceWithRandomBoolean(thenExpr);
					typeTable.setNodeType(thenExpr, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				}
				if (!isBooleanTypeCode(typeElseExpr)) {
					replaceWithRandomBoolean(elseExpr);
					typeTable.setNodeType(elseExpr, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				}
			} else if (isFloatingPointTypeCode(type)) {

				if (!isFloatingPointTypeCode(typeThenExpr)) {
					replaceWithRandomFloat(thenExpr);
					typeTable.setNodeType(thenExpr, ast.newPrimitiveType(PrimitiveType.FLOAT));
				}
				if (!isFloatingPointTypeCode(typeElseExpr)) {
					replaceWithRandomFloat(elseExpr);
					typeTable.setNodeType(elseExpr, ast.newPrimitiveType(PrimitiveType.FLOAT));
				}
			}
		}
	}
	
	public boolean visit(EnhancedForStatement node) {
		if (node.getParent() instanceof Block) {
			rewriter.remove(node, null);
		} else {
			rewriter.replace(node, ast.newBlock(), null);
		}
		return false;
	}
	
	@Override
	public boolean visit(FieldAccess node) {
		String name = node.getName().getIdentifier();

		if (node.getExpression() instanceof ThisExpression) {
			rewriter.replace(node, ast.newSimpleName(name), null);
		}
		
		if(node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			Type type = typeTable.getNodeType(node.getParent());
			if (type != null) {
				if (isIntegerTypeCode(type)) {
					replaceWithRandomInteger(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
				} else if (isBooleanTypeCode(type)) {
					replaceWithRandomBoolean(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				} else if (isFloatingPointTypeCode(type)) {
					replaceWithRandomFloat(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
				}
			}
		}

		return true;
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
			rewriter.remove(node, null);
		return false;
	}
	
	@Override
	public void endVisit(InfixExpression node) {
		Expression lhs = node.getLeftOperand();
		Expression rhs = node.getRightOperand();

		Type lhsType = typeTable.getNodeType(lhs);
		Type rhsType = typeTable.getNodeType(rhs);

		// nothing to be done
		if ((lhsType != null && typeChecker.allowedType(lhsType))
				&& (rhsType != null && typeChecker.allowedType(rhsType))) {
			return;
		}

		// if we can infer the type of lhs form rhs
		if ((lhsType == null || !typeChecker.allowedType(lhsType))
				&& (rhsType != null && typeChecker.allowedType(rhsType))) {
			if (isIntegerTypeCode(rhsType)) {
				replaceWithRandomInteger(lhs);
				typeTable.setNodeType(lhs, ast.newPrimitiveType(PrimitiveType.INT));
			} else if (isBooleanTypeCode(rhsType)) {
				replaceWithRandomBoolean(lhs);
				typeTable.setNodeType(lhs, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			} else if (isFloatingPointTypeCode(rhsType)) {
				replaceWithRandomFloat(lhs);
				typeTable.setNodeType(lhs, ast.newPrimitiveType(PrimitiveType.FLOAT));
			}

			// if we can infer the type of rhs from lhs
		} else if ((rhsType == null || !typeChecker.allowedType(rhsType))
				&& (lhsType != null && typeChecker.allowedType(lhsType))) {
			if (isIntegerTypeCode(lhsType)) {
				replaceWithRandomInteger(rhs);
				typeTable.setNodeType(rhs, ast.newPrimitiveType(PrimitiveType.INT));
			} else if (isBooleanTypeCode(lhsType)) {
				replaceWithRandomBoolean(rhs);
				typeTable.setNodeType(rhs, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			} else if (isFloatingPointTypeCode(lhsType)) {
				replaceWithRandomFloat(rhs);
				typeTable.setNodeType(rhs, ast.newPrimitiveType(PrimitiveType.FLOAT));
			}
			
			// else replace according to the location in parent
		} else if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			Type type = typeTable.getNodeType(node.getParent());
			if (type != null) {
				if (isIntegerTypeCode(type)) {
					replaceWithRandomInteger(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
				} else if (isBooleanTypeCode(type)) {
					replaceWithRandomBoolean(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				} else if (isFloatingPointTypeCode(type)) {
					replaceWithRandomFloat(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
				}
			}

		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			Expression lhsAssign = ((Assignment) node.getParent()).getLeftHandSide();
			Type type = typeTable.getNodeType(lhsAssign);
			if (type != null) {
				if (isIntegerTypeCode(type)) {
					replaceWithRandomInteger(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
				} else if (isBooleanTypeCode(type)) {
					replaceWithRandomBoolean(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				} else if (isFloatingPointTypeCode(type)) {
					replaceWithRandomFloat(node);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
				}
			}
			
		} else if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
			typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
			typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
		}

		resetInfixNodeType(node);
	}

	@Override
	public boolean visit(Initializer node) {
		rewriter.remove(node, null);
		return false;
	}
	

	@Override
	public void endVisit(InstanceofExpression node) {
		Type type = typeTable.getNodeType(node);
		if (type == null) {
			return;
		}
		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			if (!typeChecker.allowedType(type)) {
				replaceWithRandomBoolean(node);
			}
		}
	}
	
	@Override
	public boolean visit(MarkerAnnotation node) {
		rewriter.remove(node, null);
		return false;
	}
	
	/**
	 * Currently, we are removing all methods that have parameters that are not
	 * integer types.
	 * 
	 * This may leave some field variables uninitialized. 
	 * 
	 * Eventually (to better preserve the original program structure), we will
	 * remove non-integer parameters/unresolvable typed parameters and update method
	 * calls with another pass through the AST.
	 */
	@Override
	public boolean visit(MethodDeclaration node) {

		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> params = node.parameters();
		for (SingleVariableDeclaration param : params) {
			Type type = param.getType();
			if (!typeChecker.allowedType(type)) {
				rewriter.remove(node, null);
				return false;
			}
		}
		
		initializedVars = new ArrayList<VarSTE>();
		
		String name = getMethodSTEName(node);
		currMethod = name;
		SymbolTable currScope = symbolTableStack.peek();
		MethodSTE sym = currScope.getMethodSTE(name);

		SymbolTable newScope = sym.getSymbolTable();
		symbolTableStack.push(newScope);

		checkThrownExceptions(node);
		if (!node.isConstructor()) {
			checkReturnType(node);
		}

		return true;
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		boolean pushedMethod = true;
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> params = node.parameters();
		for (SingleVariableDeclaration param : params) {
			Type type = param.getType();
			if (!typeChecker.allowedType(type)) {
				pushedMethod = false;
			}
		}
		if (pushedMethod) {
			if (randUsedInMethod) {
				addRandomVariableDeclaration(node);
			}
			randUsedInMethod = false;
			symbolTableStack.pop();
		}
	}
	
	// stmt rule
	@Override
	public boolean visit(MethodInvocation node) {
		// TODO: Check that the method contains unresolvable types before we remove it.
		if (node.getLocationInParent() == ExpressionStatement.EXPRESSION_PROPERTY) {
			ASTNode parent = node.getParent(); // ExpressionStatement
			if (parent.getParent() instanceof Block) {
				rewriter.remove(parent, null);
			} else {
				rewriter.replace(parent, ast.newBlock(), null);
			}
		}
		return false;
	}
	
	/**
	 * Currently, we are just removing every method invocation.
	 * 
	 * Eventually, to better preserve the original program structure, we will check
	 * if the method invocation is resolvable and only remove those that are not.
	 */
	@Override
	public void endVisit(MethodInvocation node) {
		
		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
			return;
			
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
			return;
			
		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			Expression lhs = ((Assignment) node.getParent()).getLeftHandSide();
			Type type = typeTable.getNodeType(lhs);
			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
			} else {
				if (node.getParent().getParent() instanceof Block) {
					rewriter.remove(node.getParent().getParent(), null);
				} else {
					rewriter.replace(node.getParent().getParent(), ast.newBlock(), null);
				}
				typeTable.setNodeType(lhs, null);
			}

		} else if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			VariableDeclarationFragment parent = (VariableDeclarationFragment) node.getParent();
			Type type = typeTable.getNodeType(parent);

			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
				return;
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
				return;
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
				return;
			} else {
				rewriter.remove(node, null);
				typeTable.setNodeType(parent, null);
			}
			
		} else if (node.getLocationInParent() == CastExpression.EXPRESSION_PROPERTY) {
			CastExpression parent = (CastExpression) node.getParent();
			Type type = parent.getType();

			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
				typeTable.setNodeType(parent, ast.newPrimitiveType(PrimitiveType.INT));
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
				typeTable.setNodeType(parent, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
				typeTable.setNodeType(parent, ast.newPrimitiveType(PrimitiveType.FLOAT));
			}
		} 
	}
	

	@Override
	public boolean visit(NormalAnnotation node) {
		rewriter.remove(node, null);
		return false;
	}
	
	@Override
	public boolean visit(PostfixExpression node) {
		if (node.getLocationInParent() == ExpressionStatement.EXPRESSION_PROPERTY) {
			Type type = typeTable.getNodeType(node);
			if ((type == null) || !typeChecker.allowedType(type)) {
				ASTNode parent = node.getParent(); // ExpressionStatement
				if (parent.getParent() instanceof Block) {
					rewriter.remove(parent, null);
				} else {
					rewriter.replace(parent, ast.newBlock(), null);
				}
				return false;
			}
		}
		return true;
	}

	
	@Override
	public void endVisit(PrefixExpression node) {
		Type type = typeTable.getNodeType(node);

		if (typeChecker.allowedType(type)) {
			return;
		}
		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
			return;
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
			return;
		}
	}
	
	
	@Override
	public void endVisit(QualifiedName node) {
		if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {

			Type type = typeTable.getNodeType(node.getParent());
			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
			}
		} else if(node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
		} else if(node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			Type type = typeTable.getNodeType(((Assignment) node.getParent()).getLeftHandSide());
			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
			}
		}
	}
	

	@Override
	public void endVisit(ReturnStatement node) {
		if (node.getExpression() == null) {
			return;
		}
		SymbolTable currScope = symbolTableStack.peek();
		MethodSTE sym = currScope.getMethodSTE(currMethod);

		if (sym != null && !typeChecker.allowedType(sym.getReturnType())) {
			ClassInstanceCreation ci = ast.newClassInstanceCreation();
			ci.setType(ast.newSimpleType(ast.newSimpleName("Object")));
			rewriter.replace(node.getExpression(), ci, null);
			return;
		}

		// else the return type is resolvable.
		// check that the type of the expression node matches,
		// or else try to replace it.
		Expression expr = node.getExpression();

		Type type = typeTable.getNodeType(expr);
		Type returnType = sym.getReturnType();

		if (type == null && returnType != null) {
			if (isIntegerTypeCode(returnType)) {
				replaceWithRandomInteger(node.getExpression());
			} else if (isBooleanTypeCode(returnType)) {
				replaceWithRandomBoolean(node.getExpression());
			} else if (isFloatingPointTypeCode(returnType)) {
				replaceWithRandomFloat(node.getExpression());
			}
			
			/*
			 * else if (isStringType(returnType)) { rewriter.replace(node.getExpression(),
			 * ast.newStringLiteral(), null); }
			 */
			
			/*
			 * else { ClassInstanceCreation ci = ast.newClassInstanceCreation();
			 * ci.setType(ast.newSimpleType(ast.newSimpleName("Object")));
			 * rewriter.replace(node.getExpression(), ci, null); }
			 */
		}
		return;
	}
	
	@Override
	public boolean visit(SimpleName node) {
		
		if(node.getLocationInParent() == TypeDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == MethodDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == SingleVariableDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == QualifiedName.NAME_PROPERTY ||
				node.getLocationInParent() == QualifiedName.QUALIFIER_PROPERTY ||
				node.getLocationInParent() == PackageDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == SimpleType.NAME_PROPERTY ||
				node.getLocationInParent() == ImportDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == org.eclipse.jdt.core.dom.TypeParameter.NAME_PROPERTY){
			return true;
		}
		
		String name = node.getIdentifier();
		SymbolTable currScope = symbolTableStack.peek();
		VarSTE sym = null;
		
		if(node.getLocationInParent() == FieldAccess.NAME_PROPERTY) {
			sym = currScope.getFieldVarSTE(name);
		} else {
			sym = currScope.getVarSTE(name);
		}

		Type type = typeTable.getNodeType(node);
	//	MethodSTE methodSym = currScope.getMethodSTE(currMethod);
		ASTNode parent = node.getParent();
		while (!(parent instanceof MethodDeclaration)) {
			parent = parent.getParent();
		}

		if(sym != null && sym.isFieldVar() && !initializedVars.contains(sym)) {
			
			if(type.isPrimitiveType()) {
				if(isIntegerTypeCode(type)) {
					
					MethodInvocation randMethodInvocation = ast.newMethodInvocation();
					randMethodInvocation.setExpression(ast.newSimpleName("rand"));
					randMethodInvocation.setName(ast.newSimpleName("nextInt"));
					
					VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
					fragment.setName(ast.newSimpleName(name));
					fragment.setInitializer(randMethodInvocation);
					
					VariableDeclarationStatement varDeclaration = ast.newVariableDeclarationStatement(fragment);
					varDeclaration.setType(ast.newPrimitiveType(PrimitiveType.INT));
					
					Block block = ((MethodDeclaration) parent).getBody();
					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					listRewrite.insertFirst(varDeclaration, null);
					
					randUsedInMethod = true;
					randUsedInProgram = true;
					initializedVars.add(sym);
					
				} else if(isFloatingPointTypeCode(type)) {
					
					MethodInvocation randMethodInvocation = ast.newMethodInvocation();
					randMethodInvocation.setExpression(ast.newSimpleName("rand"));
					randMethodInvocation.setName(ast.newSimpleName("nextFloat"));
					
					VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
					fragment.setName(ast.newSimpleName(name));
					fragment.setInitializer(randMethodInvocation);
					
					VariableDeclarationStatement varDeclaration = ast.newVariableDeclarationStatement(fragment);
					varDeclaration.setType(ast.newPrimitiveType(PrimitiveType.FLOAT));
					
					Block block = ((MethodDeclaration) parent).getBody();
					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					listRewrite.insertFirst(varDeclaration, null);
					
					randUsedInMethod = true;
					randUsedInProgram = true;
					initializedVars.add(sym);
					
				} else if(isBooleanTypeCode(type)) {
					
					MethodInvocation randMethodInvocation = ast.newMethodInvocation();
					randMethodInvocation.setExpression(ast.newSimpleName("rand"));
					randMethodInvocation.setName(ast.newSimpleName("nextBoolean"));
					
					VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
					fragment.setName(ast.newSimpleName(name));
					fragment.setInitializer(randMethodInvocation);
					
					VariableDeclarationStatement varDeclaration = ast.newVariableDeclarationStatement(fragment);
					varDeclaration.setType(ast.newPrimitiveType(PrimitiveType.BOOLEAN));
					
					Block block = ((MethodDeclaration) parent).getBody();
					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					listRewrite.insertFirst(varDeclaration, null);
					
					randUsedInMethod = true;
					randUsedInProgram = true;
					initializedVars.add(sym);
					
				}
				
//				if (type.isSimpleType()) {
//
//					SimpleName variable = ast.newSimpleName(name);
//					rewriter.replace(node, variable, null);
//
//					VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
//					VariableDeclarationStatement varDeclaration = ast.newVariableDeclarationStatement(fragment);
//
//					fragment.setName(ast.newSimpleName(name));
//
//					Name typeName = ((SimpleType) type).getName();
//					String stringTypeName = typeName.getFullyQualifiedName();
//					SimpleType newType = ast.newSimpleType(ast.newName(stringTypeName));
//
//					varDeclaration.setType(newType);
//					ASTNode parent = node.getParent();
//					while (!(parent instanceof MethodDeclaration)) {
//						parent = parent.getParent();
//					}
//
//					Block block = ((MethodDeclaration) parent).getBody();
//					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
//					listRewrite.insertFirst(varDeclaration, null);
//				}
				
			}
			
		}
		return true;
	}

	@Override
	public void endVisit(SimpleName node) {
		Type type = typeTable.getNodeType(node);

		if (typeChecker.allowedType(type)) {
			return;
		}

		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
		}
	}
	
	
	
	@Override
	public boolean visit(SingleMemberAnnotation node) {
		rewriter.remove(node, null);
		return false;
	}
	
	
	@Override
	public boolean visit(SuperConstructorInvocation node) {
		rewriter.remove(node, null);
		return false;
	}
	
	@Override
	public boolean visit(SuperMethodInvocation node) {
		// TODO: Check that the method contains unresolvable types before we remove it.
		if (node.getLocationInParent() == ExpressionStatement.EXPRESSION_PROPERTY) {
			ASTNode parent = node.getParent(); // ExpressionStatement
			if (parent.getParent() instanceof Block) {
				rewriter.remove(parent, null);
			} else {
				rewriter.replace(parent, ast.newBlock(), null);
			}
		}
		return false;
	}
	
	@Override
	public void endVisit(SuperMethodInvocation node) {
		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
			return;
			
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
			replaceWithRandomBoolean(node);
			return;
			
		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			Expression lhs = ((Assignment) node.getParent()).getLeftHandSide();
			Type type = typeTable.getNodeType(lhs);
			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
			} else {
				if (node.getParent().getParent() instanceof Block) {
					rewriter.remove(node.getParent().getParent(), null);
				} else {
					rewriter.replace(node.getParent().getParent(), ast.newBlock(), null);
				}
				typeTable.setNodeType(lhs, null);
			}

		} else if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {

			VariableDeclarationFragment parent = (VariableDeclarationFragment) node.getParent();
			Type type = typeTable.getNodeType(parent);

			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
				return;
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
				return;
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
				return;
			}

			rewriter.remove(node, null);
			typeTable.setNodeType(parent, null);

		} else if (node.getLocationInParent() == CastExpression.EXPRESSION_PROPERTY) {
			CastExpression parent = (CastExpression) node.getParent();
			Type type = parent.getType();

			if (isIntegerTypeCode(type)) {
				replaceWithRandomInteger(node);
				typeTable.setNodeType(parent, ast.newPrimitiveType(PrimitiveType.INT));
			} else if (isBooleanTypeCode(type)) {
				replaceWithRandomBoolean(node);
				typeTable.setNodeType(parent, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			} else if (isFloatingPointTypeCode(type)) {
				replaceWithRandomFloat(node);
				typeTable.setNodeType(parent, ast.newPrimitiveType(PrimitiveType.FLOAT));
			}

		} else if (node.getLocationInParent() == ReturnStatement.EXPRESSION_PROPERTY) {
			ASTNode parent = node.getParent();
			while (!(parent instanceof MethodDeclaration)) {
				parent = parent.getParent();
			}

			Type returnType = ((MethodDeclaration) parent).getReturnType2();

			if (returnType != null) {
				if (isIntegerTypeCode(returnType)) {
					replaceWithRandomInteger(node);
				} else if (isBooleanTypeCode(returnType)) {
					replaceWithRandomBoolean(node);
				} else if (isFloatingPointTypeCode(returnType)) {
					replaceWithRandomFloat(node);
				}
			}
		}
	}
	
	
	public boolean visit(SwitchStatement node) {
		// TODO
		if (node.getParent() instanceof Block) {
			rewriter.remove(node, null);
		} else {
			rewriter.replace(node, ast.newBlock(), null);
		}
		return false;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		if (node.isInterface()) {
			rewriter.remove(node, null);
			return false;
		}
		// Removing superclass and interface types
		if (node.getSuperclassType() != null) {
			rewriter.remove(node.getSuperclassType(), null);
		}
		@SuppressWarnings("unchecked")
		List<Type> interfaceTypes = node.superInterfaceTypes();
		for (Type interfaceType : interfaceTypes) {
			rewriter.remove(interfaceType, null);
		}

		SymbolTable currScope = symbolTableStack.peek();
		ClassSTE sym = currScope.getClassSTE(node.getName().getIdentifier());

		SymbolTable newScope = sym.getSymbolTable();
		symbolTableStack.push(newScope);

		return true;
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		if (!node.isInterface()) {
			symbolTableStack.pop();
		}
	}
	

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		if (!typeChecker.allowedType(node.getType())) {
			if (node.getParent() instanceof Block) {
				rewriter.remove(node, null);
			} else {
				rewriter.replace(node, ast.newBlock(), null);
			}
			return false;
		}
		return true;
	}

	private void replaceWithRandomBoolean(Expression exp) {
		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
		randMethodInvocation.setExpression(ast.newSimpleName("Debug"));
		randMethodInvocation.setName(ast.newSimpleName("makeSymbolicBoolean"));
		randMethodInvocation.arguments().add(ast.newStringLiteral());

//		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
//		randMethodInvocation.setExpression(ast.newSimpleName("rand"));
//		randMethodInvocation.setName(ast.newSimpleName("nextBoolean"));
		rewriter.replace(exp, randMethodInvocation, null);
//		randUsedInMethod = true;
//		randUsedInProgram = true;
	}

	private void replaceWithRandomInteger(Expression exp) {
		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
		randMethodInvocation.setExpression(ast.newSimpleName("Debug"));
		randMethodInvocation.setName(ast.newSimpleName("makeSymbolicInteger"));
		randMethodInvocation.arguments().add(ast.newStringLiteral());
		
//		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
//		randMethodInvocation.setExpression(ast.newSimpleName("rand"));
//		randMethodInvocation.setName(ast.newSimpleName("nextInt"));
		rewriter.replace(exp, randMethodInvocation, null);
//		randUsedInMethod = true;
//		randUsedInProgram = true;

	}

	private void replaceWithRandomFloat(Expression exp) {
		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
		randMethodInvocation.setExpression(ast.newSimpleName("rand"));
		randMethodInvocation.setName(ast.newSimpleName("nextFloat"));
		rewriter.replace(exp, randMethodInvocation, null);
		
		randUsedInMethod = true;
		randUsedInProgram = true;
	}

	private boolean isStringType(Type type) {
		if(type == null) return false;
		if (!type.isSimpleType())
			return false;
		Name name = ((SimpleType) type).getName();
		if (!name.isSimpleName())
			return false;
		return (((SimpleName) name).getIdentifier().equals("String"));
	}

	private boolean isFloatingPointTypeCode(Type type) {
		if(type == null) return false;
		if (!type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.FLOAT || typeCode == PrimitiveType.DOUBLE);
	}

	private boolean isIntegerTypeCode(Type type) {
		if(type == null) return false;
		if (!type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.CHAR ||
				typeCode == PrimitiveType.INT || typeCode == PrimitiveType.LONG
				|| typeCode == PrimitiveType.SHORT || typeCode == PrimitiveType.BYTE);
	}

	private boolean isBooleanTypeCode(Type type) {
		if(type == null) return false;
		if (!type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.BOOLEAN);
	}

	private boolean isVoidTypeCode(Type type) {
		if (!type.isPrimitiveType())
			return false;
		Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
		return (typeCode == PrimitiveType.VOID);
	}
	
	private boolean isIntegerOrIntegerArrayTypeCode(Type type) {
		if(type.isArrayType()) {
			return isIntegerOrIntegerArrayTypeCode(((ArrayType) type).getElementType());
		}
		return isIntegerTypeCode(type);
	}
	
	private boolean isBooleanOrBooleanArrayTypeCode(Type type) {
		if(type.isArrayType()) {
			return isBooleanOrBooleanArrayTypeCode(((ArrayType) type).getElementType());
		}
		return isBooleanTypeCode(type);
	}

	// of a qualified name
	public static SimpleName getLeftMostSimpleName(Name name) {
		if (name instanceof SimpleName) {
			return (SimpleName) name;
		} else {
			final SimpleName[] result = new SimpleName[1];
			ASTVisitor visitor = new ASTVisitor() {
				@Override
				public boolean visit(QualifiedName qualifiedName) {
					Name left = qualifiedName.getQualifier();
					if (left instanceof SimpleName) {
						result[0] = (SimpleName) left;
					} else {
						left.accept(this);
					}
					return false;
				}
			};
			name.accept(visitor);
			return result[0];
		}
	}
	
	private String getMethodSTEName(MethodDeclaration node) {
		String name = node.getName().getIdentifier();

		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> parameters = node.parameters();
		for (SingleVariableDeclaration param : parameters) {
			Type type = param.getType();

			if (type instanceof PrimitiveType) {

				switch (((PrimitiveType) type).toString()) {
				case ("int"):
					name += "i";
					break;
				case ("double"):
					name += "d";
					break;
				case ("byte"):
					name += "b";
					break;
				case ("short"):
					name += "s";
					break;
				case ("char"):
					name += "c";
					break;
				case ("long"):
					name += "l";
					break;
				case ("float"):
					name += "f";
					break;
				case ("boolean"):
					name += "a";
					break;
				case ("void"):
					name += "v";
					break;
				}
			}
		}
		return name;
	}
	
	private void addRandomVariableDeclaration(MethodDeclaration node) {
		VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
		fragment.setName(ast.newSimpleName("rand"));
		VariableDeclarationStatement randVarDeclaration = ast.newVariableDeclarationStatement(fragment);
		Type type = ast.newSimpleType(ast.newSimpleName("Random"));
		randVarDeclaration.setType(type);
		ClassInstanceCreation instanceCreation = ast.newClassInstanceCreation();
		instanceCreation.setType(ast.newSimpleType(ast.newSimpleName("Random")));
		fragment.setInitializer(instanceCreation);

		Block block = node.getBody();

		if (block != null) { // not abstract
			ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			listRewrite.insertFirst(randVarDeclaration, null);
		}
	}
	
	private void checkReturnType(MethodDeclaration node) {
		Type type = node.getReturnType2();
		if(!typeChecker.allowedType(type)) {
//		if ((type != null) && !isVoidTypeCode(type) && !isIntegerTypeCode(type) && !isBooleanTypeCode(type)
//				&& !isFloatingPointTypeCode(type) && !isStringType(type)) {
			rewriter.replace(node.getReturnType2(), ast.newSimpleType(ast.newName("Object")), null);
		}
	}

	private void checkThrownExceptions(MethodDeclaration node) {
		@SuppressWarnings("unchecked")
		List<Name> exceptions = node.thrownExceptions();
		if (!exceptions.isEmpty()) {
			ListRewrite listRewrite = rewriter.getListRewrite(node, MethodDeclaration.THROWN_EXCEPTIONS_PROPERTY);
			for (Name name : exceptions) {
				listRewrite.remove(name, null);
			}
			listRewrite.insertFirst(ast.newSimpleName("Exception"), null);
		}
	}
	
	private void resetInfixNodeType(InfixExpression node) {
		Type lhsType = typeTable.getNodeType(node.getLeftOperand());
		Type rhsType = typeTable.getNodeType(node.getRightOperand());
		
		if (lhsType == null || rhsType == null) {
			typeTable.setNodeType(node, null);
			return;
		}

		Operator op = node.getOperator();

		// boolean operators
		if (op == Operator.CONDITIONAL_AND || op == Operator.CONDITIONAL_OR || op == Operator.XOR
				|| op == Operator.EQUALS || op == Operator.NOT_EQUALS) {
			if (isBooleanTypeCode(lhsType) && isBooleanTypeCode(rhsType)) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				return;
			}
		}

		// relational operators
		if (op == Operator.GREATER || op == Operator.GREATER_EQUALS || op == Operator.LESS || op == Operator.LESS_EQUALS
				|| op == Operator.EQUALS || op == Operator.NOT_EQUALS) {
			if ((isIntegerTypeCode(lhsType) || isFloatingPointTypeCode(lhsType))
					&& (isIntegerTypeCode(rhsType) || isFloatingPointTypeCode(rhsType))) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			}
		}

		// arithmetic operators, result in int
		if (op == Operator.PLUS || op == Operator.MINUS || op == Operator.TIMES || op == Operator.DIVIDE) {
			if (isIntegerTypeCode(lhsType) && isIntegerTypeCode(rhsType)) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
			}
		}
		
		// arithmetic operators, result in double
		if (op == Operator.PLUS || op == Operator.MINUS || op == Operator.TIMES || op == Operator.DIVIDE) {
			if ((isIntegerTypeCode(lhsType) && isFloatingPointTypeCode(rhsType)) ||
					(isFloatingPointTypeCode(lhsType) && isIntegerTypeCode(rhsType))) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
			}
		}

		// string concatenation
		/*
		 * if (op == Operator.PLUS) { if (isStringType(lhsType) ||
		 * isStringType(rhsType)) { typeTable.setNodeType(node,
		 * ast.newSimpleType(ast.newSimpleName("String"))); } }
		 */
		
	}

	public ASTRewrite getRewriter() {
		return rewriter;
	}

	public void printChanges() {
		try {
			String source = new String(Files.readAllBytes(file.toPath()));
			Document document = new Document(source);
			TextEdit edits = rewriter.rewriteAST(document, null);
			edits.apply(document);
			System.out.println(document.get());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void applyChangesToFile() {
		try {
			String source = new String(Files.readAllBytes(file.toPath()));
			Document document = new Document(source);

			TextEdit edits = rewriter.rewriteAST(document, null);
			edits.apply(document);

			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(document.get());
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedTreeException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
