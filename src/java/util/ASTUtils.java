package util;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

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
}
