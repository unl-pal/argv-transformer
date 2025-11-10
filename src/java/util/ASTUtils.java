package util;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IntersectionType;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WildcardType;

public class ASTUtils {        
    
    /**
     * Returns the normalized binding for a variable declaration. This is necessary during finalization
     * because double[] x and double x[] are not equivalent Type objects. Type bindings preserve this information correctly.
     * @param vd The variable declaration to be normalized for syntax
     * @returnTthe normalized ITypeBinding. May need to be later re-converted to an AST type.
     */
    public static ITypeBinding getNormalizedBinding(VariableDeclaration vd) {
        IVariableBinding binding = vd.resolveBinding();
        if (binding == null) return null;

        ITypeBinding varType = binding.getType();
        if (varType == null) return null;

        // varType already includes base dimensions + extraDimensions
        return varType; 
    }
    
    /**
     * Takes a binding and does its best effort to turn it into an AST type. This is necessary for
     * explicit creation of a new cast expression, where the type must be set. It is recommended you use typeChecker before invoking this.
     * @param ast The ast to be used
     * @param binding The binding to derive a new type from
     * @return a new Type object pulled from an ITypeBinding.
     */
    public static Type newTypeFromBinding(AST ast, ITypeBinding binding) {
        // TODO: Write unit tests for each of these cases; this was created with the help of generative AI
        if (binding == null) return null;
        

        // Primitive
        if (binding.isPrimitive()) {
            if ("void".equals(binding.getName())) {
                return ast.newPrimitiveType(PrimitiveType.VOID);
            }
            return ast.newPrimitiveType(PrimitiveType.toCode(binding.getName()));
        }

        // Array
        if (binding.isArray()) {
            Type elementType = newTypeFromBinding(ast, binding.getElementType());
            return ast.newArrayType(elementType, binding.getDimensions());
        }

        // Type variable (T)
        if (binding.isTypeVariable()) {
            return ast.newSimpleType(ast.newSimpleName(binding.getName()));
        }

        // Wildcard: ?, ? extends X, ? super X
        if (binding.isWildcardType()) {
            WildcardType wildcard = ast.newWildcardType();
            ITypeBinding bound = binding.getBound();
            if (bound != null) {
                wildcard.setBound(newTypeFromBinding(ast, bound), binding.isUpperbound());
            }
            return wildcard;
        }

        // Parameterized generic type: List<String>
        if (binding.isParameterizedType()) {
            ParameterizedType pt = ast.newParameterizedType(
                newTypeFromBinding(ast, binding.getErasure())
            );
            for (ITypeBinding arg : binding.getTypeArguments()) {
                pt.typeArguments().add(newTypeFromBinding(ast, arg));
            }
            return pt;
        }

        // Raw generic type: List
        if (binding.isRawType()) {
            return newTypeFromBinding(ast, binding.getErasure());
        }

        // Qualified type (Outer.Inner)
        if (binding.isMember()) {
            Type outer = newTypeFromBinding(ast, binding.getDeclaringClass());
            return ast.newQualifiedType(
                outer,
                ast.newSimpleName(binding.getName())
            );
        }

        // Intersection types (A & B)
        if (binding.isIntersectionType()) {
            IntersectionType it = ast.newIntersectionType();
            for (ITypeBinding superBound : binding.getTypeBounds()) {
                it.types().add(newTypeFromBinding(ast, superBound));
            }
            return it;
        }
        
        // Fallback: simple qualified name (java.util.List)
        return ast.newSimpleType(ast.newName(binding.getQualifiedName()));
    }
}
