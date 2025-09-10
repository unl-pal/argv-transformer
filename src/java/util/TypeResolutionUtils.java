package util;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
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
            if (code == PrimitiveType.BOOLEAN) {
                return replaceWithNodeBoolean(ast, randUsedInMethod);
            } else if (code == PrimitiveType.CHAR || code == PrimitiveType.INT ||
                       code == PrimitiveType.LONG || code == PrimitiveType.SHORT ||
                       code == PrimitiveType.BYTE) {
                return replaceWithNodeInteger(ast, randUsedInMethod);
            } else if (code == PrimitiveType.DOUBLE) {
                return replaceWithNodeDouble(ast, randUsedInMethod);
            } else if (code == PrimitiveType.FLOAT) {
                return replaceWithNodeFloat(ast, randUsedInMethod);
            }
        } else if (type.equals(ast.newSimpleType(ast.newSimpleName("String")))) {
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

    public static boolean isFloatingPointTypeCode(Type type) {
        if(type == null) return false;
        if (!type.isPrimitiveType())
            return false;
        Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
        return typeCode == PrimitiveType.FLOAT;
    }
    
    public static boolean isDoubleTypeCode(Type type) {
        if(type == null) return false;
        if (!type.isPrimitiveType())
            return false;
        Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
        return typeCode == PrimitiveType.DOUBLE;
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

    public static boolean isBooleanTypeCode(Type type) {
        if(type == null) return false;
        if (!type.isPrimitiveType())
            return false;
        Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
        return (typeCode == PrimitiveType.BOOLEAN);
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
