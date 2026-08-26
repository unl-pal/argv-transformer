package transform.TypeChecking;

import java.util.List;

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
/**
 * Class to track what types are in the local type system.
 * 
 * Presently allows for primitive types and classes from the Java Standard Library
 * 
 */
public class TypeChecker {
	//those are collective types we area dealing with
	//any means that the type is unknown
	public enum CType {INT, REAL, STRING, BOOLEAN, ANY}

	/**
	 * Check whether the type is allowed (according to specifications defined 
	 * in this method).
	 * 
	 * @param type
	 * @return true if the type is allowed, false otherwise.
	 */
	public boolean allowedType(Type type) {
		if(type == null) return false;
		
		if(type.isArrayType()) {
			return allowedType(((ArrayType) type).getElementType());
		}
		
		if(type.isParameterizedType()) {
			boolean allowedArgTypes = true;
			@SuppressWarnings("unchecked")
			List<Type> typeArgs = ((ParameterizedType) type).typeArguments();
			for(Type typeArg : typeArgs) {
				if(!allowedType(typeArg)) {
					allowedArgTypes = false;
				}
			}
			return (allowedType(((ParameterizedType) type).getType()) && allowedArgTypes);
		}
		
		if (type.isSimpleType()) {
			Name name = ((SimpleType) type).getName();
			if(name.isSimpleName() && ((SimpleName) name).getIdentifier().contentEquals("String")) {
				return true;
			}
		}
		ITypeBinding typeBinding = type.resolveBinding();
		String qualifiedName = typeBinding != null ? typeBinding.getQualifiedName() : "unknown";
		return (type.isPrimitiveType() || qualifiedName.startsWith("java.") || qualifiedName.startsWith("javax."));
	}
	
	/**
	 * Check whether the type binding is allowed using type bindings
	 *
	 * @param binding ITypeBinding to check
	 * @return true if the type is allowed, false otherwise
	 */
	public boolean allowedType(ITypeBinding binding) {
	    if (binding == null) {
	        return false;
	    }
	    
	    // Handle arrays recursively
	    if (binding.isArray()) {
	        return allowedType(binding.getElementType());
	    }

	    // Handle parameterized types: check both the raw type and argument types
	    if (binding.isParameterizedType()) {
	        boolean allowedArgTypes = true;
	        for (ITypeBinding arg : binding.getTypeArguments()) {
	            if (!allowedType(arg)) {
	                allowedArgTypes = false;
	            }
	        }
	        ITypeBinding erasure = binding.getErasure();
	        if (erasure == binding) {
	            return false;
	        }
	        return allowedArgTypes && allowedType(erasure);
	    }

	    // Handle wildcards (e.g., ? extends Number)
	    if (binding.isWildcardType()) {
	        ITypeBinding bound = binding.getBound();
	        if (bound != null) {
	            return allowedType(bound);
	        }
	        return true; // unbounded wildcard is fine
	    }

	    // Handle type variables (generics like T)
	    if (binding.isTypeVariable()) {
	        ITypeBinding bound = binding.getBound();
	        if (bound != null) {
	            return allowedType(bound);
	        }
	        return true; // unbounded T is fine
	    }

	    // Handle primitives or JDK types
	    String qualifiedName = binding.getQualifiedName();
	    
	    return (binding.isPrimitive()
	            || qualifiedName.startsWith("java.")
	            || qualifiedName.startsWith("javax."));
	}
//-------------------------------------------------------------------------------------	
	//eas taken from TransformVisitor and made it static
	
	public static CType checkType(Type type) {
		CType ret = CType.ANY;
		if(isIntegerType(type)) {
			ret = CType.INT;
		} else if(isBooleanType(type)) {
			ret = CType.BOOLEAN;
		} else if(isRealType(type)) {
			ret = CType.REAL;
		} else if(isStringType(type)) {
			ret = CType.STRING;
		}
		
		return ret;
	}
	
	public static boolean isStringType(Type type) {
		boolean ret = false;
		if (type != null) {
			if(type.isSimpleType()) {
				Name name = ((SimpleType) type).getName();
				if(name.isSimpleName()) {
					SimpleName sName =  (SimpleName) name;
					ret = sName.getIdentifier().contentEquals("String") || sName.getIdentifier().contentEquals("StringBuffer");
				}
			} else if (type.isPrimitiveType()) {
				//checking for chars since it should be treated as string type
			 ret = ((PrimitiveType) type).getPrimitiveTypeCode() == PrimitiveType.CHAR;
			}
		}
		return ret;
	}
	
	public static boolean isIntegerType(Type type) {
		boolean ret = false;
		if(type != null && type.isPrimitiveType()) {
			Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
			if(typeCode == PrimitiveType.INT || 
					typeCode == PrimitiveType.LONG ||
					typeCode == PrimitiveType.SHORT || 
					typeCode == PrimitiveType.BYTE) {
				ret = true;
			}
		}
		
		return ret;
	}
	
	public static boolean isRealType(Type type) {
		boolean ret = false;
		if(type != null && type.isPrimitiveType()) {
			Code typeCode = ((PrimitiveType) type).getPrimitiveTypeCode();
			if(typeCode == PrimitiveType.DOUBLE || 
					typeCode == PrimitiveType.FLOAT) {
				ret = true;
			}
		}

		return ret;
	}
	
	public static boolean isBooleanType(Type type) {
		boolean ret = false;
		if(type != null && type.isPrimitiveType()) {
			 ret = ((PrimitiveType) type).getPrimitiveTypeCode() == PrimitiveType.BOOLEAN;
		}
		return ret;
	}
	
	public static boolean isVoidType(Type type) {
		boolean ret = false;
		if(type != null & type.isPrimitiveType()) {
			 ret = ((PrimitiveType) type).getPrimitiveTypeCode() == PrimitiveType.VOID;
		}
		return ret;
	}
	
	/**
	 * Whether the array's element type is an integer type, regardless of the
	 * array's depth. There is no dimension parameter: JDT's
	 * {@link ArrayType#getElementType()} always resolves straight to the
	 * innermost non-array element type in one step (e.g. int[][] -> int), so
	 * depth can't be distinguished this way and isn't checked.
	 */
	public static boolean isIntegerArrayType(Type type) {
		return isIntegerType(arrayElementType(type));
	}

	private static Type arrayElementType(Type type) {
		return type.isArrayType() ? ((ArrayType) type).getElementType() : null;
	}

	/**
	 * Whether the array's element type is a real (floating-point) type,
	 * regardless of the array's depth. See {@link #isIntegerArrayType(Type)}
	 * for why there is no dimension parameter.
	 */
	public static boolean isRealArrayType(Type type) {
		return isRealType(arrayElementType(type));
	}

}
