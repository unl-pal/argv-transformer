package util;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

public class TypeResolutionUtils {
    
    public static int varNum = 0;
    
    /**
     * Helper method that creates a symbolic argument for a given parameter type.
     * For primitives, it uses the corresponding symbolic replacement method.
     * For array types, it creates an array literal with one symbolic element.
     * For other types, it returns a null literal.
     */
    public static Expression createSymbolicArgument(Type type, AST ast, Boolean randUsedInMethod) {
        if (type.isPrimitiveType()) {
            PrimitiveType pt = (PrimitiveType) type;
            PrimitiveType.Code code = pt.getPrimitiveTypeCode();
            // can't do switch statement on PrimitiveType.Code
            if (code == PrimitiveType.BOOLEAN) {
                return replaceWithNodeBoolean(ast, randUsedInMethod);
            } else if (code == PrimitiveType.INT) {
                return replaceWithNodeInteger(ast, randUsedInMethod);
			} else if (code == PrimitiveType.BYTE) {
				return replaceWithNodeByte(ast, randUsedInMethod);
			} else if (code == PrimitiveType.SHORT) {
				return replaceWithNodeShort(ast, randUsedInMethod);
			} else if (code == PrimitiveType.LONG) {
				return replaceWithNodeLong(ast, randUsedInMethod);
			} else if (code == PrimitiveType.CHAR) {
				return replaceWithNodeChar(ast, randUsedInMethod);
            } else if (code == PrimitiveType.DOUBLE) {
                return replaceWithNodeDouble(ast, randUsedInMethod);
            } else if (code == PrimitiveType.FLOAT) {
                return replaceWithNodeFloat(ast, randUsedInMethod);
            }
        } else if (type.resolveBinding() != null && type.resolveBinding().getQualifiedName().equals("java.lang.String")) {
            return replaceWithNodeString(ast, randUsedInMethod);
        } else if (type.isArrayType()) {
            ArrayType arrType = (ArrayType) type;
            // Create an array creation expression with an initializer.
            ArrayCreation arrayCreation = ast.newArrayCreation();
            // Copy the array type structure.
            ArrayType newArrayType = (ArrayType) ASTNode.copySubtree(ast, arrType);
            arrayCreation.setType(newArrayType);

            // Create an array initializer with one symbolic element.
            ArrayInitializer initializer = ast.newArrayInitializer();
            Expression elementArg = createSymbolicArgument(arrType.getElementType(), ast, randUsedInMethod);
            initializer.expressions().add(elementArg);
            arrayCreation.setInitializer(initializer);
            return arrayCreation;
        } 
        // For non-primitive, non-array types, return a null literal.
        return ast.newNullLiteral();
    }
    
    /**
     * Helper method that creates a symbolic argument for a given parameter binding.
     * For primitives, it uses the corresponding symbolic replacement method.
     * For array types, it creates an array literal with one symbolic element.
     * For other types, it returns a null literal.
     */
    public static Expression createSymbolicArgument(ITypeBinding binding, AST ast, Boolean randUsedInMethod) {
        if (binding == null) {
			return ast.newNullLiteral();
		}
        if (binding.isPrimitive()) {
            String name = binding.getName();
            switch (name) {
                case "boolean":
                    return replaceWithNodeBoolean(ast, randUsedInMethod);
                case "char":
					return replaceWithNodeChar(ast, randUsedInMethod);
                case "int":
                    return replaceWithNodeInteger(ast, randUsedInMethod);
                case "long":
					return replaceWithNodeLong(ast, randUsedInMethod);
                case "short":
					return replaceWithNodeShort(ast, randUsedInMethod);
                case "byte":
					return replaceWithNodeByte(ast, randUsedInMethod);
                case "double":
                    return replaceWithNodeDouble(ast, randUsedInMethod);
                case "float":
                    return replaceWithNodeFloat(ast, randUsedInMethod);
            }
        } else if ("java.lang.String".equals(binding.getQualifiedName())) {
            return replaceWithNodeString(ast, randUsedInMethod);
        } else if (binding.isArray()) {
            ITypeBinding elementBinding = binding.getElementType();

         // Construct ArrayType from element binding
            Type elementType;
            if (elementBinding.isPrimitive()) {
                elementType = ast.newPrimitiveType(PrimitiveType.toCode(elementBinding.getName()));
            } else {
                elementType = ast.newSimpleType(ast.newName(elementBinding.getQualifiedName()));
            }
            ArrayType arrayType = ast.newArrayType(elementType, binding.getDimensions());

            // Create array creation
            ArrayCreation arrayCreation = ast.newArrayCreation();
            arrayCreation.setType(arrayType);

            // Create initializer with one symbolic element
            ArrayInitializer initializer = ast.newArrayInitializer();
            Expression elementArg = createSymbolicArgument(elementBinding, ast, randUsedInMethod);
            initializer.expressions().add(elementArg);
            arrayCreation.setInitializer(initializer);
            return arrayCreation;
        }
        // For non-primitive, non-array types, return a null literal.
        return ast.newNullLiteral();
    }
    
    public static void safeRemoveOrReplace(MethodInvocation node, ASTRewrite rewriter, AST ast, Boolean randUsedInMethod) {
        StructuralPropertyDescriptor location = node.getLocationInParent();
        ASTNode parent = node.getParent();

        // Case 1: if/while/do/for condition
        if (location == IfStatement.EXPRESSION_PROPERTY
                || location == WhileStatement.EXPRESSION_PROPERTY
                || location == DoStatement.EXPRESSION_PROPERTY
                || location == ForStatement.EXPRESSION_PROPERTY) {
            rewriter.replace(node, replaceWithNodeBoolean(ast, randUsedInMethod), null);
            return;
        }

        // Case 2: variable initializer
        if (location == VariableDeclarationFragment.INITIALIZER_PROPERTY) {
            IVariableBinding binding = ((VariableDeclarationFragment) parent).resolveBinding();
            if (binding != null) {
                ITypeBinding typeBinding = binding.getType();
                Expression symbolicArg = createSymbolicArgument(typeBinding, ast, randUsedInMethod);
                rewriter.replace(node, symbolicArg, null);
                return;
            }
        }

        // Case 3: assignment RHS
        if (location == Assignment.RIGHT_HAND_SIDE_PROPERTY) {
            Expression lhs = ((Assignment) parent).getLeftHandSide();
            ITypeBinding lhsTypeBinding = lhs.resolveTypeBinding();
            if (lhsTypeBinding != null) {
                Expression symbolicArg = createSymbolicArgument(lhsTypeBinding, ast, randUsedInMethod);
                rewriter.replace(node, symbolicArg, null);
                return;
            }
        }

        // Case 4: return statement
        if (location == ReturnStatement.EXPRESSION_PROPERTY) {
            ITypeBinding returnType = node.resolveTypeBinding();
            Expression symbolicArg = createSymbolicArgument(returnType, ast, randUsedInMethod);
            rewriter.replace(node, symbolicArg, null);
            return;
        }
        
        if (location == MethodInvocation.ARGUMENTS_PROPERTY) {
            MethodInvocation parentInvocation = (MethodInvocation) node.getParent();
            List<Expression> args = parentInvocation.arguments();
            if (args != null) {
                int index = args.indexOf(node);
                IMethodBinding parentBinding = parentInvocation.resolveMethodBinding();
                if (parentBinding != null) {
                    rewriter.replace(node, createSymbolicArgument(parentBinding.getParameterTypes()[index], ast, randUsedInMethod), null);
                }
                return;
            }
			safeRemoveOrReplace(parentInvocation, rewriter, ast, randUsedInMethod);
			return;
		}

        // Case 5: argument in a method/constructor call
        if (location.isChildListProperty()) {
            rewriter.remove(node, null);
            return;
        }

        // Case 6: standalone statement
        if (parent instanceof ExpressionStatement) {
            rewriter.remove(parent, null);
            return;
        }
        
        // Case 7: cast expression
        if (parent instanceof CastExpression) {
	        rewriter.replace(node, createSymbolicArgument(((CastExpression) parent).getType(), ast, randUsedInMethod), null);
	        return;
        }

        // Default: fallback, delete node
        ASTNode ancestor = node.getParent();
        while (ancestor != null && !(ancestor instanceof Block) && !(ancestor instanceof MethodDeclaration)) {
            if (ancestor.getParent() instanceof ReturnStatement) {
                ITypeBinding returnType = getTypeBindingOfReturnStatement((ReturnStatement) ancestor.getParent());
                Expression symbolicArg = createSymbolicArgument(returnType, ast, randUsedInMethod);
                rewriter.replace(ancestor, symbolicArg, null);
                return;
            }
	        if (ancestor == null || ancestor.getParent() instanceof Block || ancestor.getParent() instanceof MethodDeclaration) {
		        rewriter.remove(ancestor, null);
		        return;
	        }
            ancestor = ancestor.getParent();
        };
        
        rewriter.remove(ancestor, null);
    }
    
    public static boolean methodIsFromSameClass(MethodInvocation node) {
		IMethodBinding binding = node.resolveMethodBinding();
		if (binding != null) {
			ITypeBinding declaringClassBinding = binding.getDeclaringClass();
			if (declaringClassBinding != null) {
			    ITypeBinding currentClassBinding = null;
			    ASTNode parent = node.getParent();
			    while (parent != null) {
			        if (parent instanceof TypeDeclaration) {
			            currentClassBinding = ((TypeDeclaration) parent).resolveBinding();
			            break;
			        } else if (parent instanceof AnonymousClassDeclaration) {
			            currentClassBinding = ((AnonymousClassDeclaration) parent).resolveBinding();
			            break;
			        }
			        parent = parent.getParent();
			    }
			    if (declaringClassBinding.isEqualTo(currentClassBinding)) {
				    return true;
			    }
			}
		}
	    return false;
    }
    
    public static ITypeBinding getTypeBindingOfReturnStatement(ReturnStatement node) {
        ASTNode parent = node.getParent();
        while (parent != null && !(parent instanceof MethodDeclaration)) {
            parent = parent.getParent();
        }
        if (parent instanceof MethodDeclaration) {
            MethodDeclaration method = (MethodDeclaration) parent;
            ITypeBinding methodType = method.getReturnType2().resolveBinding();
			return methodType;
        }
        return null;
    }
    
    
    /**================================================BOOLEAN==========================================================================*/  

    public static void replaceBoolean(Expression exp, String target, AST ast, ASTRewrite rewriter, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = null;
        switch(target) {
        case "SPF" : randMethodInvocation = replaceWithSymbolicBoolean(ast);
            break;
        case "SVCOMP" : randMethodInvocation = replaceWithNodeBoolean(ast, randUsedInMethod);
            break;
        default: randMethodInvocation = replaceWithRandomBoolean(ast, randUsedInMethod);
        }
        rewriter.replace(exp, randMethodInvocation, null);
    }
    
    public static MethodInvocation replaceWithRandomBoolean(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("rand"));
        randMethodInvocation.setName(ast.newSimpleName("nextBoolean"));
        
        randUsedInMethod = true;
        //randUsedInProgram = true;
        return randMethodInvocation;
        
    }
    
    public static MethodInvocation replaceWithNodeBoolean(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
        randMethodInvocation.setName(ast.newSimpleName("nondetBoolean"));
        
        randUsedInMethod = false;
        return randMethodInvocation;
        
    }
    
    
    

    public static MethodInvocation replaceWithSymbolicBoolean(AST ast) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Debug"));
        randMethodInvocation.setName(ast.newSimpleName("makeSymbolicBoolean"));
        StringLiteral str = ast.newStringLiteral();
        str.setLiteralValue("x" + varNum);
        randMethodInvocation.arguments().add(str);
        varNum++;
        return randMethodInvocation;
    }
/**==============================================INTEGER==========================================================================*/
    
    public static MethodInvocation replaceWithSymbolicInteger(AST ast) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Debug"));
        randMethodInvocation.setName(ast.newSimpleName("makeSymbolicInteger"));
        StringLiteral str = ast.newStringLiteral();
        str.setLiteralValue("x" + varNum);
        randMethodInvocation.arguments().add(str);
        varNum++;
        
        return randMethodInvocation;

    }
    
    public static MethodInvocation replaceWithRandomInteger(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("rand"));
        randMethodInvocation.setName(ast.newSimpleName("nextInt"));
        
        randUsedInMethod = true;
        return randMethodInvocation;
        
    }
    
    public static MethodInvocation replaceWithNodeInteger(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
        randMethodInvocation.setName(ast.newSimpleName("nondetInt"));
        
        randUsedInMethod = false;
        return randMethodInvocation;
        
    }
    

    
    public static void replaceInteger(Expression exp, String target, AST ast, ASTRewrite rewriter, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = null;
        switch(target) {
        case "SPF" : randMethodInvocation = replaceWithSymbolicInteger(ast);
        break;
        case "SVCOMP" : randMethodInvocation = replaceWithNodeInteger(ast, randUsedInMethod);
        break;
        default: randMethodInvocation = replaceWithRandomInteger(ast, randUsedInMethod);
        }
        rewriter.replace(exp, randMethodInvocation, null);
    }



    /* Actually it is Double */
    public static void replaceDouble(Expression exp, String target, AST ast, ASTRewrite rewriter, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = null;
        switch(target){
        case "SPF" : randMethodInvocation = replaceWithSymbolicDouble(ast);
        break;
        case "SVCOMP" : randMethodInvocation = replaceWithNodeDouble(ast, randUsedInMethod);
        break;
        default: randMethodInvocation = replaceWithRandomDouble(ast, randUsedInMethod);
        }
        
        rewriter.replace(exp, randMethodInvocation , null);
    }
    
    public static MethodInvocation replaceWithRandomDouble(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("rand"));
        randMethodInvocation.setName(ast.newSimpleName("nextDouble"));
        randUsedInMethod = true;
        return randMethodInvocation;
    }

    public static MethodInvocation replaceWithSymbolicDouble(AST ast) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Debug"));
        randMethodInvocation.setName(ast.newSimpleName("makeSymbolicReal"));
        StringLiteral str = ast.newStringLiteral();
        str.setLiteralValue("x" + varNum);
        randMethodInvocation.arguments().add(str);
        varNum++;
        return randMethodInvocation;
    }
    
    
    public static MethodInvocation replaceWithNodeDouble(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
        randMethodInvocation.setName(ast.newSimpleName("nondetDouble"));
        
        randUsedInMethod = false;
        return randMethodInvocation;
        
    }
    
/**==============================================FLOAT==========================================================================*/
    
    public static void replaceFloat(Expression exp, String target, AST ast, ASTRewrite rewriter, Boolean randUsedInMethod) {
        
        ASTNode expression = null;
        switch(target) {
        case "SPF":  expression = replaceWithSymbolicFloat(ast);
        break;
        case "SVCOMP" : expression = replaceWithNodeFloat(ast, randUsedInMethod);
        break;
        default: expression = replaceWithRandomFloat(ast, randUsedInMethod);
        }
        rewriter.replace(exp, expression , null);
    }
    public static MethodInvocation replaceWithNodeFloat(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
        randMethodInvocation.setName(ast.newSimpleName("nondetFloat"));
        
        randUsedInMethod = false;
        return randMethodInvocation;
        
    }
    
    public static MethodInvocation replaceWithRandomFloat(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("rand"));
        randMethodInvocation.setName(ast.newSimpleName("nextFloat"));
        randUsedInMethod = true;
        return randMethodInvocation;
    }

    public static CastExpression replaceWithSymbolicFloat(AST ast) {
        CastExpression castExpression = ast.newCastExpression();
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Debug"));
        randMethodInvocation.setName(ast.newSimpleName("makeSymbolicReal"));
        StringLiteral str = ast.newStringLiteral();
        str.setLiteralValue("x" + varNum);
        randMethodInvocation.arguments().add(str);
        varNum++;
        
        castExpression.setExpression(randMethodInvocation);
        castExpression.setType(ast.newPrimitiveType(PrimitiveType.FLOAT));
        return castExpression;
    }
    
    /**==============================================BYTE==========================================================================*/

	public static MethodInvocation replaceWithNodeByte(AST ast, Boolean randUsedInMethod) {
		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
		randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
		randMethodInvocation.setName(ast.newSimpleName("nondetByte"));
		
		randUsedInMethod = false;
		return randMethodInvocation;
		
	}
	
    /**==============================================SHORT==========================================================================*/

	public static MethodInvocation replaceWithNodeShort(AST ast, Boolean randUsedInMethod) {
		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
		randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
		randMethodInvocation.setName(ast.newSimpleName("nondetShort"));
		
		randUsedInMethod = false;
		return randMethodInvocation;
		
	}
	
    /**==============================================CHAR==========================================================================*/

	public static MethodInvocation replaceWithNodeChar(AST ast, Boolean randUsedInMethod) {
		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
		randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
		randMethodInvocation.setName(ast.newSimpleName("nondetChar"));
		
		randUsedInMethod = false;
		return randMethodInvocation;
		
	}
	
	/**==============================================LONG==========================================================================*/
	
	public static MethodInvocation replaceWithNodeLong(AST ast, Boolean randUsedInMethod) {
		MethodInvocation randMethodInvocation = ast.newMethodInvocation();
		randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
		randMethodInvocation.setName(ast.newSimpleName("nondetLong"));
		
		randUsedInMethod = false;
		return randMethodInvocation;
	}
    
    /**==============================================String==========================================================================*/

    public static MethodInvocation replaceWithNodeString(AST ast, Boolean randUsedInMethod) {
        MethodInvocation randMethodInvocation = ast.newMethodInvocation();
        randMethodInvocation.setExpression(ast.newSimpleName("Verifier"));
        randMethodInvocation.setName(ast.newSimpleName("nondetString"));
        
        randUsedInMethod = false;
        return randMethodInvocation;
        
    }

    public static boolean isStringType(Type type) {
        if(type == null) return false;
        if (!type.isSimpleType())
            return false;
        Name name = ((SimpleType) type).getName();
        if (!name.isSimpleName())
            return false;
        return (((SimpleName) name).getIdentifier().equals("String"));
    }
    
    public static boolean isNumericTypeCode(Type type) {
        return isFloatingPointTypeCode(type) || 
                isDoubleTypeCode(type) ||
                isIntegerTypeCode(type);
    }
    
    public static boolean isNumericTypeCode(ITypeBinding type) {
		return isFloatingPointTypeCode(type) || 
				isDoubleTypeCode(type) ||
				isIntegerTypeCode(type);
	}

    public static boolean isFloatingPointTypeCode(Type type) {
        if(type == null) return false;
        if (!type.isPrimitiveType())
            return false;
        Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
        return typeCode == PrimitiveType.FLOAT;
    }
    
	public static boolean isFloatingPointTypeCode(ITypeBinding type) {
	    if (type != null && type.isPrimitive()) {
		    String typeName = type.getName();
		    return typeName.equals("float");
	    }
	    return false;
	}
    
    public static boolean isDoubleTypeCode(Type type) {
        if(type == null) return false;
        if (!type.isPrimitiveType())
            return false;
        Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
        return typeCode == PrimitiveType.DOUBLE;
    }
    
    public static boolean isDoubleTypeCode(ITypeBinding type) {
	    if (type != null && type.isPrimitive()) {
		    String typeName = type.getName();
		    return typeName.equals("double");
	    }
	    return false;
    }

    public static boolean isIntegerTypeCode(Type type) {
        if(type == null) return false;
        if (!type.isPrimitiveType())
            return false;
        Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
        return (typeCode == PrimitiveType.CHAR ||
                typeCode == PrimitiveType.INT || 
                typeCode == PrimitiveType.LONG || 
                typeCode == PrimitiveType.SHORT || 
                typeCode == PrimitiveType.BYTE);
    }
    
    public static boolean isIntegerTypeCode(ITypeBinding type) {
		if (type != null && type.isPrimitive()) {
			String typeName = type.getName();
			return typeName.equals("char") || typeName.equals("int") || typeName.equals("long")
					|| typeName.equals("short") || typeName.equals("byte");
		}
		return false;
    }

    public static boolean isBooleanTypeCode(Type type) {
        if(type == null) return false;
        if (!type.isPrimitiveType())
            return false;
        Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
        return (typeCode == PrimitiveType.BOOLEAN);
    }
    
	public static boolean isBooleanTypeCode(ITypeBinding type) {
		if (type != null && type.isPrimitive()) {
			String typeName = type.getName();
			return typeName.equals("boolean");
		}
		return false;
	}

    public static boolean isVoidTypeCode(Type type) {
        if (!type.isPrimitiveType())
            return false;
        Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
        return (typeCode == PrimitiveType.VOID);
    }
    
    public static boolean isIntegerOrIntegerArrayTypeCode(Type type) {
        if(type.isArrayType()) {
            return isIntegerOrIntegerArrayTypeCode(((ArrayType) type).getElementType());
        }
        return isIntegerTypeCode(type);
    }
    
    public static boolean isBooleanOrBooleanArrayTypeCode(Type type) {
        if(type.isArrayType()) {
            return isBooleanOrBooleanArrayTypeCode(((ArrayType) type).getElementType());
        }
        return isBooleanTypeCode(type);
    }

}
