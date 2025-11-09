package util;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ASTUtils {        
    /**
     * Normalize a variable declaration's type so that array dimensions
     * are always represented as an ArrayType, regardless of whether
     * the source declared them on the type or after the variable name.
     */
    public static Type getNormalizedType(VariableDeclaration vd, Type baseType) {
        AST ast = vd.getAST();

        // keep the original type node (binding stays valid)
        Type root = baseType;

        int extraDims = vd.getExtraDimensions();
        if (extraDims == 0) {
            return root; // nothing to do
        }

        // Wrap ONCE: add array layers on top, but leave baseType untouched
        Type wrapped = root;
        for (int i = 0; i < extraDims; i++) {
            wrapped = ast.newArrayType(wrapped);
        }

        return wrapped;
    }
}
