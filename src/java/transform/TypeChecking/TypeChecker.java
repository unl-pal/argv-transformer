package transform.TypeChecking;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private static Set<String> javaImportTypes;
	private static Set<String> classTypes;
	
	//those are collective types we area dealing with
	//any means that the type is unknown
	public enum CType {INT, REAL, STRING, BOOLEAN, ANY}

	/**
	 * Create a new TypeChecker.
	 */
	public TypeChecker() {
		javaImportTypes = new HashSet<String>();
		classTypes = new HashSet<String>();
	}
	
	/**
	 * Add a type to the list of Java class Libraries imported. 
	 * 
	 * @param name Name of the Java class. 
	 */
	public void addJavaImportType(String name) {
		javaImportTypes.add(name);
	}
	
	/**
	 * Add a class type to the list of resolvable class types. 
	 * 
	 * @param name Name of the class. 
	 */
	public void addClassType(String name) {
		classTypes.add(name);
	}
	
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
		// check if is character literal		
		
//		return (type.isPrimitiveType() 
//				|| javaImportTypes.contains(type.toString())
//				|| classTypes.contains(type.toString())
//				|| inJavaLangLibrary(type));
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
	 * Integer array type of desired dimension
	 * @param type
	 * @param dim - desired dimensions, >= 1
	 * @return
	 */
	public static boolean isIntegerArrayType(Type type, int dim) {
		type = arrayType(type,dim);
		return isIntegerType(type);
	}
	
	private static Type arrayType(Type type, int dim) {
		Type ret = null;
		if(type.isArrayType()) {
			dim--;
			type = ((ArrayType) type).getElementType();
			while(dim > 0 && type.isArrayType()) {
				type = ((ArrayType) type).getElementType();
				dim--;
			}
			//went through all dimensions
			if(dim == 0) {
				ret = type;
			}
		}
		return ret;
	}
	
	public static boolean isRealArrayType(Type type, int dim) {
		type = arrayType(type,dim);
		return isRealType(type);
	}
	
}
