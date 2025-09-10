package util;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

public class ASTUtils {
    
    /**
     * Normalize a variable declaration's type so that array dimensions
     * are always represented as an ArrayType, regardless of whether
     * the source declared them on the type or after the variable name.
     */
    public static Type getNormalizedType(VariableDeclarationFragment frag, Type baseType) {
        Type result = baseType;

        // Handle extraDimensions from the variable fragment
        int extraDims = frag.getExtraDimensions();
        for (int i = 0; i < extraDims; i++) {
            result = frag.getAST().newArrayType(result);
        }

        return result;
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
}
