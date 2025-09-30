package transform.visitors;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
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
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
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
import util.TypeResolutionUtils;
/**
 * Visitor class used to find and perform necessary code transformations.
 * 
 * @author mariapaquin
 *
 */
public class TransformVisitor extends ASTVisitor {

	private ASTRewrite rewriter;
	private AST ast;
	private File file;
	private TypeChecker typeChecker;
	private SymbolTable root;
	private TypeTable typeTable;
	private Stack<SymbolTable> symbolTableStack;
	private String currMethod;
	private ArrayList<VarSTE> initializedVars;
	private List<String> preImportComments = new ArrayList<String>();
	private List<String> postImportComments = new ArrayList<String>();
	private final Set<ASTNode> disallowed = new HashSet<>();
	public static int varNum = 0;
	private String target;
	private Boolean randUsedInMethod;
	private boolean hasRandom;
	private String rootNodePackage = null; // instantiated as needed
	private String source;

	/**
	 * 
	 * @param root
	 * @param rewriter
	 * @param typeTable
	 * @param typeChecker
	 */
	public TransformVisitor(SymbolTable root, ASTRewrite rewriter, TypeTable typeTable, TypeChecker typeChecker, String target, String source) {
		this.root = root;
		this.rewriter = rewriter;
		this.typeTable = typeTable;
		this.typeChecker = typeChecker;
		this.target = target;
		randUsedInMethod = false;
		hasRandom = false;
		this.source = source;
	}
	

	
	// expr
	@Override
	public void endVisit(ArrayAccess node) {
		Type type = typeTable.getNodeType(node);
		if (type != null && typeChecker.allowedType(type)) {
			return;
		}

		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
			TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
		} else if (node.getLocationInParent() == Assignment.LEFT_HAND_SIDE_PROPERTY) {
			// TODO
		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			// TODO
		}
	}
	
	// stmt
	@Override
	public boolean visit(Assignment node) {
		Expression lhs = node.getLeftHandSide();
		Expression rhs = node.getRightHandSide();
		Type lhsType = typeTable.getNodeType(lhs);
	
		if ((lhsType == null || !typeChecker.allowedType(lhsType))) {
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
		if (rhs != null && !typeChecker.allowedType(rhs.resolveTypeBinding())) {
		    rewriter.replace(rhs, TypeResolutionUtils.createSymbolicArgument(lhsType, ast, randUsedInMethod), null);
		    return false;
		}
		return true;
	}
	
	// expr
	@Override
	public void endVisit(CastExpression node) {
		Type castType = node.getType();
		if(typeChecker.allowedType(castType)) {
			return;
		}
		
		if(node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			VariableDeclarationFragment parent = (VariableDeclarationFragment) node.getParent();
			Type type = typeTable.getNodeType(parent);
			
			Expression expr = TypeResolutionUtils.createSymbolicArgument(type, ast, randUsedInMethod);

			if (expr instanceof NullLiteral) {
			    rewriter.remove(node, null);
                typeTable.setNodeType(parent, null);
			} else {
				rewriter.replace(node, expr, null);
			}
		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			// TODO
		}
	}
	
	@Override
	public boolean visit(CatchClause node) {
	    SingleVariableDeclaration exception = node.getException();
	    SingleVariableDeclaration basicException = ast.newSingleVariableDeclaration();
	    basicException.setName(ast.newSimpleName("e"));
	    basicException.setType(ast.newSimpleType(ast.newSimpleName("Exception")));
	    rewriter.replace(exception, basicException, null);
	    return true;
	    
	}
	
	/**
	 * On a method containing a disallowed ClassInstanceCreation (such as new DisallowedType()), removing it if it's in
	 * an initializer or right hand side, and replacing it with a blank object if in a return statement.
	 */
	@Override
	public void endVisit(ClassInstanceCreation node) {
	    
		Type type = typeTable.getNodeType(node);
		
		boolean argsAllowed = true;

		for (Expression arg : (List<Expression>) node.arguments()) {
			if (!typeChecker.allowedType(arg.resolveTypeBinding()) || disallowed.contains(arg)) {
				argsAllowed = false;
			}
		}
		if (!typeChecker.allowedType(type) || !argsAllowed) {
			if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
				Assignment parent = (Assignment) node.getParent();

				typeTable.setNodeType(parent.getLeftHandSide(), null);
				rewriter.remove(node.getParent().getParent(), null);

			} else if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
				VariableDeclarationFragment parent = (VariableDeclarationFragment) node.getParent();

				typeTable.setNodeType(parent.getName(), null);
				// remove VariableDeclarationExpression
				disallowed.add(parent.getParent());
				rewriter.remove(parent.getParent(), null);

			} else if (node.getLocationInParent() == ReturnStatement.EXPRESSION_PROPERTY) {
				ReturnStatement parent = (ReturnStatement) node.getParent();
				disallowed.add(parent);
				rewriter.replace(node, ast.newNullLiteral(), null); //TODO: propagate method invalidation
			} else if (node.getLocationInParent() == ThrowStatement.EXPRESSION_PROPERTY) {
			    disallowed.add(node);
			    boolean checkedException = TypeResolutionUtils.isCheckedException(node.resolveTypeBinding());
			    ClassInstanceCreation exceptionCreation = ast.newClassInstanceCreation();
			    if (checkedException) {
			        exceptionCreation.setType(ast.newSimpleType(ast.newSimpleName("Exception")));
			    } else {
			        exceptionCreation.setType(ast.newSimpleType(ast.newSimpleName("RuntimeException")));
			    }
			    rewriter.replace(node, exceptionCreation, null);
			} else {
				disallowed.add(node);
				rewriter.remove(node, null);
			}
		}
	}
	
	/**
	 * Initializes AST and symbolTableStack and pushes root to symbolTableStack. Additional changes handled in endVisit
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		ast = node.getAST();
		symbolTableStack = new Stack<SymbolTable>();
		symbolTableStack.push(root);
		initializedVars = new ArrayList<VarSTE>();

		return true;
	}

	/**
	 * Adds necessary imports for the symbolic testing tool we are using. Currently supports java Random and nasa's symbc.Debug
	 * Removes imports not in Java Standard Library and ensures comments are preserved.
	 */
	@Override
	public void endVisit(CompilationUnit node) {
	    // Determine the start position of the first class signature.
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
	    
        PackageDeclaration pkg = node.getPackage();
		if (pkg != null) {
			rewriter.remove(pkg, null);
		}
		ImportDeclaration id = ast.newImportDeclaration();
		String importName = "";
		switch(target){
			case "SPF": 
				importName = "gov.nasa.jpf.symbc.Debug";
				break;
			case "SVCOMP": 
				importName = "org.sosy_lab.sv_benchmarks.Verifier";
				break;
			default:
				importName = hasRandom?"":"java.util.Random";
				break;
		}

		if(!importName.isEmpty()) {
			id.setName(ast.newName(importName.split("\\.")));
			ListRewrite listRewrite = rewriter.getListRewrite(node, CompilationUnit.IMPORTS_PROPERTY);
			listRewrite.insertFirst(id, null);
		}
		
		@SuppressWarnings("unchecked")
		List<ImportDeclaration> imports = node.imports() != null ? node.imports() : new ArrayList<ImportDeclaration>();
		
		int importEnd = 0;
		for (ImportDeclaration importDec : imports) {
			importName = importDec.getName().getFullyQualifiedName();
			if(!hasRandom && importName.equals("java.util.Random")) {
				hasRandom = true;
			}
			if (!importName.startsWith("java.") && !importName.startsWith("org.sosy_lab.sv_benchmarks") && !importName.startsWith("javax.")){
				// Find the next import or first type declaration to move comments to                
				rewriter.remove(importDec, null);
			}
			importEnd = Math.max(importDec.getStartPosition() + importDec.getLength(), importEnd);
		}
		
	    // Retrieve all comments from the CompilationUnit
	    List<Comment> comments = node.getCommentList();

        for (Comment comment : comments) {
        	int commentStart = comment.getStartPosition();
        	if (commentStart < firstBodyStart && commentStart >= importEnd) {
        		postImportComments.add(source.substring(commentStart, commentStart + comment.getLength()));
        	} else if (commentStart < importEnd) {
        		preImportComments.add(source.substring(commentStart, commentStart + comment.getLength()));
        	}
        } 
	}


	/**
	 * Checks if then or else expression in a conditional is not of an allowed type, replacing it with a symbolic value of
	 * the type of the variable declaration. Also replaces the conditional statement with a symbolic boolean if it is not boolean type code.
	 */
	@Override
	public void endVisit(ConditionalExpression node) {
		Expression expr = node.getExpression();
		Expression thenExpr = node.getThenExpression();
		Expression elseExpr = node.getElseExpression();

		Type typeExpr = typeTable.getNodeType(expr);
		Type typeThenExpr = typeTable.getNodeType(thenExpr);
		Type typeElseExpr = typeTable.getNodeType(elseExpr);

		if (!TypeResolutionUtils.isBooleanTypeCode(typeExpr)) {
		    TypeResolutionUtils.replaceBoolean(expr, target, ast, rewriter, randUsedInMethod);
			typeTable.setNodeType(expr, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
		}

		if (typeChecker.allowedType(typeThenExpr) && typeChecker.allowedType(typeElseExpr)) {
			return;
		}

		if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			Type type = typeTable.getNodeType(node.getParent());

			if (TypeResolutionUtils.isIntegerTypeCode(type)) {
				if (!TypeResolutionUtils.isIntegerTypeCode(typeThenExpr)) {
				    TypeResolutionUtils.replaceInteger(thenExpr, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(thenExpr, ast.newPrimitiveType(PrimitiveType.INT));
				}
				if (!TypeResolutionUtils.isIntegerTypeCode(typeElseExpr)) {
				    TypeResolutionUtils.replaceInteger(elseExpr, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(elseExpr, ast.newPrimitiveType(PrimitiveType.INT));
				}

			} else if (TypeResolutionUtils.isBooleanTypeCode(type)) {
				if (!TypeResolutionUtils.isBooleanTypeCode(typeThenExpr)) {
				    TypeResolutionUtils.replaceBoolean(thenExpr, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(thenExpr, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				}
				if (!TypeResolutionUtils.isBooleanTypeCode(typeElseExpr)) {
				    TypeResolutionUtils.replaceBoolean(elseExpr, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(elseExpr, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				}
			} else if(TypeResolutionUtils.isFloatingPointTypeCode(type)) {

				if(!TypeResolutionUtils.isFloatingPointTypeCode(typeThenExpr)) {
				    TypeResolutionUtils.replaceFloat(thenExpr, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(thenExpr, ast.newPrimitiveType(PrimitiveType.FLOAT));
				}
				if(!TypeResolutionUtils.isFloatingPointTypeCode(typeElseExpr)) {
				    TypeResolutionUtils.replaceFloat(elseExpr, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(elseExpr, ast.newPrimitiveType(PrimitiveType.FLOAT));
				}
			} else if(TypeResolutionUtils.isDoubleTypeCode(type)){
				if(!TypeResolutionUtils.isDoubleTypeCode(typeThenExpr)) {
				    TypeResolutionUtils.replaceDouble(thenExpr, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(thenExpr, ast.newPrimitiveType(PrimitiveType.DOUBLE));
				}
				if(!TypeResolutionUtils.isDoubleTypeCode(typeElseExpr)) {
				    TypeResolutionUtils.replaceDouble(elseExpr, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(elseExpr, ast.newPrimitiveType(PrimitiveType.DOUBLE));
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
		
		if(node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			Type type = typeTable.getNodeType(node.getParent());
			if (type != null) {
			    Expression expr = TypeResolutionUtils.createSymbolicArgument(type, ast, randUsedInMethod);
			    if (!(expr instanceof NullLiteral)) {
			        rewriter.replace(node, expr, null);
	                typeTable.setNodeType(node, type);
			    }
			}
		}

		return true;
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
	    List<IExtendedModifier> mods = node.modifiers();
        for (IExtendedModifier m : mods) {
            if (m instanceof Modifier) {
                Modifier mod = (Modifier) m;
                if (mod.isFinal()) {
                    rewriter.remove(mod, null);
                }
            }
        }
	    if (!typeChecker.allowedType(node.getType())) {
	           rewriter.remove(node, null);
	    } else {
	        SymbolTable currScope = symbolTableStack.peek();
	        initializedVars.add(currScope.getFieldVarSTE(((VariableDeclarationFragment)(node.fragments().get(0))).getName().getIdentifier()));
	    }
		return true;
	}
	
	@Override
	public void endVisit(InfixExpression node) {
		Expression lhs = node.getLeftOperand();
		Expression rhs = node.getRightOperand();

		ITypeBinding lhsType = lhs.resolveTypeBinding();
		ITypeBinding rhsType = rhs.resolveTypeBinding();

		// nothing to be done
		if ((lhsType != null && typeChecker.allowedType(lhsType))
				&& (rhsType != null && typeChecker.allowedType(rhsType))) {
			return;
		}

		// if we can infer the type of lhs form rhs
		if ((lhsType == null || !typeChecker.allowedType(lhsType))
				&& (rhsType != null && typeChecker.allowedType(rhsType))) {
			if (TypeResolutionUtils.isIntegerTypeCode(rhsType)) {
			    TypeResolutionUtils.replaceInteger(lhs, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(lhs, ast.newPrimitiveType(PrimitiveType.INT));
			} else if (TypeResolutionUtils.isBooleanTypeCode(rhsType)) {
			    TypeResolutionUtils.replaceBoolean(lhs, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(lhs, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			} else if (TypeResolutionUtils.isFloatingPointTypeCode(rhsType)) {
			    TypeResolutionUtils.replaceDouble(lhs, target, ast, rewriter, randUsedInMethod); // TODO: figure out why this thinks doubles are floats
				typeTable.setNodeType(lhs, ast.newPrimitiveType(PrimitiveType.FLOAT));
			} else if(TypeResolutionUtils.isDoubleTypeCode(rhsType)) {
			    TypeResolutionUtils.replaceDouble(lhs, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(lhs, ast.newPrimitiveType(PrimitiveType.DOUBLE));
			}

			// if we can infer the type of rhs from lhs
		} else if ((rhsType == null || !typeChecker.allowedType(rhsType))
				&& (lhsType != null && typeChecker.allowedType(lhsType))) {
			if (TypeResolutionUtils.isIntegerTypeCode(lhsType)) {
			    TypeResolutionUtils.replaceInteger(rhs, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(rhs, ast.newPrimitiveType(PrimitiveType.INT));
			} else if (TypeResolutionUtils.isBooleanTypeCode(lhsType)) {
			    TypeResolutionUtils.replaceBoolean(rhs, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(rhs, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			} else if (TypeResolutionUtils.isFloatingPointTypeCode(lhsType)) {
			    TypeResolutionUtils.replaceFloat(rhs, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(rhs, ast.newPrimitiveType(PrimitiveType.FLOAT));
			} else if(TypeResolutionUtils.isDoubleTypeCode(lhsType)) {
			    TypeResolutionUtils.replaceDouble(rhs, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(rhs, ast.newPrimitiveType(PrimitiveType.DOUBLE));
			}
			
			// else replace according to the location in parent
		} else if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			Type type = typeTable.getNodeType(node.getParent());
			if (type != null) {
				if (TypeResolutionUtils.isIntegerTypeCode(type)) {
				    TypeResolutionUtils.replaceInteger(node, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
				} else if (TypeResolutionUtils.isBooleanTypeCode(type)) {
				    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				} else if (TypeResolutionUtils.isFloatingPointTypeCode(type)) {
				    TypeResolutionUtils.replaceFloat(node, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
				} else if(TypeResolutionUtils.isDoubleTypeCode(type)) {
				    TypeResolutionUtils.replaceDouble(node, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.DOUBLE));
				}
			}

		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			Expression lhsAssign = ((Assignment) node.getParent()).getLeftHandSide();
			Type type = typeTable.getNodeType(lhsAssign);
			if (type != null) {
				if (TypeResolutionUtils.isIntegerTypeCode(type)) {
				    TypeResolutionUtils.replaceInteger(node, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
				} else if (TypeResolutionUtils.isBooleanTypeCode(type)) {
				    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				} else if (TypeResolutionUtils.isFloatingPointTypeCode(type)) {
				    TypeResolutionUtils.replaceFloat(node, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
				} else if(TypeResolutionUtils.isDoubleTypeCode(type)) {
				    TypeResolutionUtils.replaceDouble(node, target, ast, rewriter, randUsedInMethod);
					typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.DOUBLE));
				}
			}
			
		} else if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
			typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
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
			    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
			}
		}
	}
	
	// stmt
	@Override
	public boolean visit(MarkerAnnotation node) {
		rewriter.remove(node, null);
		return false;
	}
	
	/**
	 * Removes MethodDeclarations that are mains or not part of typeChecker's allowed types. See typeChecker for a longer description.
	 * Configures global variable currMethod to this node's method and pushes this method's scope to the top of symbolTableStack
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
	    
	    if (node.getName().getIdentifier().equals("main")) {
	        rewriter.remove(node, null);
	    }
		
		
				
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
		
		if(randUsedInMethod) {
			addRandomVariableDeclaration(node);
			randUsedInMethod = false;
		}
		
		if (pushedMethod) {
			symbolTableStack.pop();
		}
	}
	
	/**
	 * Checks if a MethodInvocation node is part of the JDK or package environment, replacing/removing it if otherwise.
	 * Presently replaces primitive types, removing the node otherwise.
	 */
	@Override
	public void endVisit(MethodInvocation node) {
		IMethodBinding methodBinding = node.resolveMethodBinding();
        if (methodBinding != null && methodBinding.getDeclaringClass() != null) {
            String packageName = methodBinding.getDeclaringClass().getPackage().getName();
            if (rootNodePackage == null) {
            	rootNodePackage = ((CompilationUnit) node.getRoot()).getPackage().getName().getFullyQualifiedName();
            }
            // Check if it's part of the JDK
            if (packageName.startsWith("java.") || packageName.startsWith("javax.") || TypeResolutionUtils.methodIsFromSameClass(node)) {
            	// do nothing for now
            } else {
            	ITypeBinding typeBinding = methodBinding.getReturnType();
				if (typeBinding != null && typeBinding.isPrimitive()) {
					Type type = ast.newPrimitiveType(PrimitiveType.toCode(typeBinding.getName()));
					if (TypeResolutionUtils.isIntegerTypeCode(type)) {
					    TypeResolutionUtils.replaceInteger(node, target, ast, rewriter, randUsedInMethod);
        				return;
        			} else if (TypeResolutionUtils.isBooleanTypeCode(type)) {
        			    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
        				return;
        			} else if (TypeResolutionUtils.isFloatingPointTypeCode(type)) {
        			    TypeResolutionUtils.replaceFloat(node, target, ast, rewriter, randUsedInMethod);
        				return;
        			} else if (TypeResolutionUtils.isDoubleTypeCode(type)) {
        			    TypeResolutionUtils.replaceDouble(node, target, ast, rewriter, randUsedInMethod);
        				return;
        			}
				}
				TypeResolutionUtils.safeRemoveOrReplace(node, rewriter, ast, randUsedInMethod);
				typeTable.setNodeType(node.getParent(), null);
            }
        } else {
			TypeResolutionUtils.safeRemoveOrReplace(node, rewriter, ast, randUsedInMethod);
			typeTable.setNodeType(node.getParent(), null);
		}
	}
	

	/**
	 * Unconditionally removes NormalAnnotation nodes from the tree
	 */
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

	/**
	 * Checks if a PrefixExpression node is an allowed type. 
	 * If not and part of an if or while statement, replaces with a symbolic boolean
	 */
	@Override
	public void endVisit(PrefixExpression node) {
		Type type = typeTable.getNodeType(node);

		if (typeChecker.allowedType(type)) {
			return;
		}
		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
			return;
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
			return;
		}
	}
	
	
	@Override
	public void endVisit(QualifiedName node) {
		Type type = typeTable.getNodeType(node);
		if (type != null && typeChecker.allowedType(type)) {
			return;
		}
		if (node.getLocationInParent() == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
			Type parentType = typeTable.getNodeType(node.getParent());
			Expression expr = TypeResolutionUtils.createSymbolicArgument(parentType, ast, randUsedInMethod);
			if (!(expr instanceof NullLiteral)) {
				rewriter.replace(node, expr, null);
			}
		} else if(node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
		} else if(node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			Type parentType = typeTable.getNodeType(((Assignment) node.getParent()).getLeftHandSide());
			Expression expr = TypeResolutionUtils.createSymbolicArgument(parentType, ast, randUsedInMethod);
            if (!(expr instanceof NullLiteral)) {
                rewriter.replace(node, expr, null);
            }
		} else if (node.getLocationInParent() == MethodInvocation.ARGUMENTS_PROPERTY) {
		    TypeResolutionUtils.replaceArgumentWithinMethodInvocation(node, (MethodInvocation) node.getParent(), rewriter, ast, randUsedInMethod);
		} else if (node.getLocationInParent() == SwitchCase.EXPRESSION_PROPERTY) {
		    SwitchStatement switchStatement = (SwitchStatement) node.getParent().getParent();
		    rewriter.remove(switchStatement, null); // when we do not know the constant, we cannot have the switch statement
		}
	}
	

	/**
	 * If the return type is resolveable and not of an allowed type, replaces it with a new blank Object
	 * If resolvable and of an allowed type but not in typeTable, replaces it with a corresponding symbolic value.
	 * Otherwise, does nothing.
	 */
	@Override
	public void endVisit(ReturnStatement node) {
		if (node.getParent() instanceof Block) {
			String assertStmt = "assert true; //inline assert generated by ARG-V";
	        ASTNode newAssert = rewriter.createStringPlaceholder(assertStmt, ASTNode.EMPTY_STATEMENT);
	        rewriter.getListRewrite(node.getParent(), Block.STATEMENTS_PROPERTY).insertBefore(newAssert, node, null);
		}
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

		if (!typeChecker.allowedType(type)) {
		    ITypeBinding returnTypeBinding = TypeResolutionUtils.getTypeBindingOfReturnStatement(node);
		    rewriter.replace(expr, TypeResolutionUtils.createSymbolicArgument(returnTypeBinding, ast, randUsedInMethod), null);
		}
	}
	
	// Checking for field variables that need to be initialized
	@Override
	public boolean visit(SimpleName node) {
		
	    // skipping unapplicable nodes
		if(node.getLocationInParent() == TypeDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == MethodDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == SingleVariableDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == QualifiedName.NAME_PROPERTY ||
				node.getLocationInParent() == QualifiedName.QUALIFIER_PROPERTY ||
				node.getLocationInParent() == PackageDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == SimpleType.NAME_PROPERTY ||
				node.getLocationInParent() == ImportDeclaration.NAME_PROPERTY ||
				node.getLocationInParent() == TypeParameter.NAME_PROPERTY){
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
		ASTNode ancestor = node.getParent();
		while (ancestor != null && !(ancestor instanceof MethodDeclaration)) {
		    ancestor = ancestor.getParent();
		}
		
		if (ancestor == null) {
			return false;
		}

		
		
		if(sym != null && sym.isFieldVar() && !initializedVars.contains(sym)) {
			if(type.isPrimitiveType()) {
				if(TypeResolutionUtils.isIntegerTypeCode(type)) {
					
					Expression randMethodInvocation = TypeResolutionUtils.generateIntegerFromTarget(ast, randUsedInMethod, target);
							
					VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
					fragment.setName(ast.newSimpleName(name));
					fragment.setInitializer(randMethodInvocation);
					
					VariableDeclarationStatement varDeclaration = ast.newVariableDeclarationStatement(fragment);
					varDeclaration.setType(ast.newPrimitiveType(PrimitiveType.INT));
					
					Block block = ((MethodDeclaration) ancestor).getBody();
					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					listRewrite.insertFirst(varDeclaration, null);
					
					initializedVars.add(sym);
					
				} else if(TypeResolutionUtils.isFloatingPointTypeCode(type)) {
										
					Expression expression = TypeResolutionUtils.generateFloatFromTarget(ast, randUsedInMethod, target);
					
					VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
					fragment.setName(ast.newSimpleName(name));
					fragment.setInitializer(expression);
					
					VariableDeclarationStatement varDeclaration = ast.newVariableDeclarationStatement(fragment);
					varDeclaration.setType(ast.newPrimitiveType(PrimitiveType.FLOAT));
					
					Block block = ((MethodDeclaration) ancestor).getBody();
					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					listRewrite.insertFirst(varDeclaration, null);
					
					initializedVars.add(sym);
				} else if(TypeResolutionUtils.isDoubleTypeCode(type)) {
										
					Expression randMethodInvocation = TypeResolutionUtils.generateDoubleFromTarget(ast, randUsedInMethod, target);
							
					
					VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
					fragment.setName(ast.newSimpleName(name));
					fragment.setInitializer(randMethodInvocation);
					
					VariableDeclarationStatement varDeclaration = ast.newVariableDeclarationStatement(fragment);
					varDeclaration.setType(ast.newPrimitiveType(PrimitiveType.DOUBLE));
					
					Block block = ((MethodDeclaration) ancestor).getBody();
					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					listRewrite.insertFirst(varDeclaration, null);
					
					initializedVars.add(sym);
					
				} else if(TypeResolutionUtils.isBooleanTypeCode(type)) {
					
					Expression randMethodInvocation = TypeResolutionUtils.generateBooleanFromTarget(ast, randUsedInMethod, target);
					
					VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
					fragment.setName(ast.newSimpleName(name));
					fragment.setInitializer(randMethodInvocation);
					
					VariableDeclarationStatement varDeclaration = ast.newVariableDeclarationStatement(fragment);
					varDeclaration.setType(ast.newPrimitiveType(PrimitiveType.BOOLEAN));
					
					Block block = ((MethodDeclaration) ancestor).getBody();
					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					listRewrite.insertFirst(varDeclaration, null);
					
					initializedVars.add(sym);
					
				}		
			}
			
		}
		return true;
	}

	/**
	 * If a simpleName node is not of an allowed type and belongs to an if statement, replaces it with a symbolic boolean.
	 * Otherwise, do nothing.
	 */
	@Override
	public void endVisit(SimpleName node) {
		Type type = typeTable.getNodeType(node);

		if (typeChecker.allowedType(type)) {
			return;
		}

		if (node.getLocationInParent() == IfStatement.EXPRESSION_PROPERTY) {
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
		}
	}
	
	
	/**
	 * Unconditionally removes SingleMemberAnnotation nodes from the tree.
	 */
	@Override
	public boolean visit(SingleMemberAnnotation node) {
		rewriter.remove(node, null);
		return false;
	}
	
	/**
	 * Unconditionally removes SuperConstructorAnnotation nodes from the tree.
	 */
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
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
			return;
			
		} else if (node.getLocationInParent() == WhileStatement.EXPRESSION_PROPERTY) {
		    TypeResolutionUtils.replaceBoolean(node, target, ast, rewriter, randUsedInMethod);
			return;
			
		} else if (node.getLocationInParent() == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
			Expression lhs = ((Assignment) node.getParent()).getLeftHandSide();
			Type type = typeTable.getNodeType(lhs);
			Expression expr = TypeResolutionUtils.createSymbolicArgument(type, ast, randUsedInMethod);
			if (!(expr instanceof NullLiteral)) {
				rewriter.replace(node, expr, null);
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
	        Expression expr = TypeResolutionUtils.createSymbolicArgument(type, ast, randUsedInMethod);
	        if (!(expr instanceof NullLiteral)) {
		        rewriter.replace(node, expr, null);
		        return;
	        }

			rewriter.remove(node, null);
			typeTable.setNodeType(parent, null);

		} else if (node.getLocationInParent() == CastExpression.EXPRESSION_PROPERTY) {
			CastExpression parent = (CastExpression) node.getParent();
			Type type = parent.getType();
			
			// if the type directly above is a cast, we can ignore the need for it as it is implicit in the new symbolic value
			if (TypeResolutionUtils.isIntegerTypeCode(type)) {
			    TypeResolutionUtils.replaceInteger(parent, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(parent.getParent(), ast.newPrimitiveType(PrimitiveType.INT));
			} else if (TypeResolutionUtils.isBooleanTypeCode(type)) {
			    TypeResolutionUtils.replaceBoolean(parent, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(parent.getParent(), ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			} else if (TypeResolutionUtils.isFloatingPointTypeCode(type)) {
			    TypeResolutionUtils.replaceDouble(node, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(parent, ast.newPrimitiveType(PrimitiveType.FLOAT));
			} else if (TypeResolutionUtils.isDoubleTypeCode(type)) {
			    TypeResolutionUtils.replaceDouble(parent, target, ast, rewriter, randUsedInMethod);
				typeTable.setNodeType(parent.getParent(), ast.newPrimitiveType(PrimitiveType.DOUBLE));
			}

		} else if (node.getLocationInParent() == ReturnStatement.EXPRESSION_PROPERTY) {
			ASTNode parent = node.getParent();
			while (!(parent instanceof MethodDeclaration)) {
				parent = parent.getParent();
			}

			Type returnType = ((MethodDeclaration) parent).getReturnType2();

			if (returnType != null) {
			    Expression expr = TypeResolutionUtils.createSymbolicArgument(returnType, ast, randUsedInMethod);
			    if (!(expr instanceof NullLiteral)) {
			    	rewriter.replace(node, expr, null);
			    }
			}
		}
	}
	
	@Override
	public void endVisit(TryStatement node) {
	    for (VariableDeclarationExpression resource : (List<VariableDeclarationExpression>) node.resources()) {
		    if (disallowed.contains(resource)) {
			    rewriter.remove(node, null);
		    }
	    }
	}

	/**
	 * Removes Interfaces and strips and "extends" and "implements" from a class' type declaration
	 * Pushes a new scope to the symbol table for the class we are about to enter for this type declaration
	 */
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

	/**
	 * Does not pop on interfaces because we do not enter the scope of interfaces.
	 */
	@Override
	public void endVisit(TypeDeclaration node) {
	    
		if (!node.isInterface()) {
			symbolTableStack.pop();
		}
	}
	
	/**
	 * If a VariableDeclarationFragment is missing an initializer, an initalizer is added
	 */
	@Override
	public boolean visit(VariableDeclarationFragment node) {
	    if (node.getInitializer() == null || !typeChecker.allowedType(node.getInitializer().resolveTypeBinding())) {
	        ITypeBinding typeBinding = node.resolveBinding() != null ? node.resolveBinding().getType() : null;
	        Expression initializer = TypeResolutionUtils.createSymbolicArgument(typeBinding, ast, randUsedInMethod);
	        rewriter.set(node, VariableDeclarationFragment.INITIALIZER_PROPERTY, initializer, null);
	        return false;
	    }
        return true;
		
	}
	

	/**
	 * If a VariableDeclarationStatement is not an allowed type, it is removed.
	 * If its parent is not a block, instead of directly removing the VariableDeclarationStatement,
	 * it is replaced with an empty block "{}". Note: This can create issues for some statements
	 */
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		if (!typeChecker.allowedType(node.getType())) { // TODO: handle own class instantiation
			if (node.getParent() instanceof Block) {
				rewriter.remove(node, null);
			} else {
				rewriter.replace(node, ast.newBlock(), null);
			}
			return false;
		}
		return true;
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
			rewriter.replace(node.getReturnType2(), ast.newSimpleType(ast.newName("Object")), null);
		}
	}

	private void checkThrownExceptions(MethodDeclaration node) {
		@SuppressWarnings("unchecked")
		List<Type> exceptions = node.thrownExceptionTypes();
		if (!exceptions.isEmpty()) {
		    boolean checkedException = false;
			ListRewrite listRewrite = rewriter.getListRewrite(node, MethodDeclaration.THROWN_EXCEPTION_TYPES_PROPERTY);
			for (Type name : exceptions) {
				listRewrite.remove(name, null);
				if (TypeResolutionUtils.isCheckedException(name.resolveBinding())) {
	                checkedException = true;
	            }
			}
			if (checkedException) {
			    listRewrite.insertFirst(ast.newSimpleName("Exception"), null);
			} else {
		        listRewrite.insertFirst(ast.newSimpleName("RuntimeException"), null);
			}
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
			if (TypeResolutionUtils.isBooleanTypeCode(lhsType) && TypeResolutionUtils.isBooleanTypeCode(rhsType)) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
				return;
			}
		}

		// relational operators
		if (op == Operator.GREATER || op == Operator.GREATER_EQUALS || op == Operator.LESS || op == Operator.LESS_EQUALS
				|| op == Operator.EQUALS || op == Operator.NOT_EQUALS) {
			if (TypeResolutionUtils.isNumericTypeCode(lhsType) && TypeResolutionUtils.isNumericTypeCode(rhsType)) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.BOOLEAN));
			}
		}

		
		// arithmetic operators, result in int
		if (op == Operator.PLUS || op == Operator.MINUS || op == Operator.TIMES || op == Operator.DIVIDE) {
			if (TypeResolutionUtils.isIntegerTypeCode(lhsType) && TypeResolutionUtils.isIntegerTypeCode(rhsType)) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.INT));
			}
		}
		
		
		// arithmetic operators, result in float
		if (op == Operator.PLUS || op == Operator.MINUS || op == Operator.TIMES || op == Operator.DIVIDE) {
			if ((TypeResolutionUtils.isIntegerTypeCode(lhsType) && TypeResolutionUtils.isFloatingPointTypeCode(rhsType)) ||
					(TypeResolutionUtils.isFloatingPointTypeCode(lhsType) && TypeResolutionUtils.isIntegerTypeCode(rhsType))) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.FLOAT));
			}
		}
		
		// arithmetic operators, result in double
		if (op == Operator.PLUS || op == Operator.MINUS || op == Operator.TIMES || op == Operator.DIVIDE) {
			if ((TypeResolutionUtils.isIntegerTypeCode(lhsType) && TypeResolutionUtils.isDoubleTypeCode(rhsType)) ||
					(TypeResolutionUtils.isDoubleTypeCode(lhsType) && TypeResolutionUtils.isIntegerTypeCode(rhsType))) {
				typeTable.setNodeType(node, ast.newPrimitiveType(PrimitiveType.DOUBLE));
			}
		}		
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
			//System.out.println(document.get());
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
	
	/**
	 * Getter for symbolTableStack
	 * @return symbolTableStack a stack of all symbolTables (innermost scope on top)
	 */
	public Stack<SymbolTable> getSymbolTableStack(){
		return symbolTableStack;
	}



	public List<String> getPreImportComments() {
		return preImportComments;
	}


	public List<String> getPostImportComments() {
		return postImportComments;
	}
	
	public Set<ASTNode> getDisallowed() {
		return disallowed;
	}
}
