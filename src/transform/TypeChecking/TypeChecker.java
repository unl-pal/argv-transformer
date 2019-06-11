package transform.TypeChecking;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
/**
 * Class to track what types are in the local type system.
 * In the current implementation, we are only allowing primitive types. 
 * In subsequent versions, we will also allow types from the JDK, inner 
 * class types and the class itself. 
 * 
 * @author mariapaquin
 *
 */
public class TypeChecker {
	private Set<String> javaImportTypes;
	private Set<String> classTypes;

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
	
	private boolean inJavaLangLibrary(Type type) {
		
		if(!(type instanceof SimpleType)) return false;
		Name name = ((SimpleType) type).getName();
		
		if(!(name instanceof SimpleName)) return false;
		String typeName = name.toString();
		
		return (typeName.equals("Boolean")||
				typeName.equals("Byte")||
				typeName.equals("Character")||
				typeName.equals("Character.Subset")||
				typeName.equals("Character.UnicodeBlock")||
				typeName.equals("Class<T>")||
				typeName.equals("ClassLoader")||
				typeName.equals("ClassValue<T>")||
				typeName.equals("Compiler")||
				typeName.equals("Double")||
				typeName.equals("Enum")||
				typeName.equals("Float")||
				typeName.equals("InheritableThreadLocal<T>")||
				typeName.equals("Integer")||
				typeName.equals("Long")||
				typeName.equals("Math") ||
				typeName.equals("Number")||
				typeName.equals("Object") || 
				typeName.equals("Package") ||
				typeName.equals("Process") || 
				typeName.equals("ProcessBuilder") || 
				typeName.equals("ProcessBuilder.Redirect") || 
				typeName.equals("Runtime") || 
				typeName.equals("RuntimePermission") || 
				typeName.equals("SecurityManager") ||
				typeName.equals("Short") ||
				typeName.equals("StackTraceElement") ||
				typeName.equals("StrictMath") ||
				typeName.equals("String") ||
				typeName.equals("StringBuffer") ||
				typeName.equals("StringBuilder") ||
				typeName.equals("System") ||
				typeName.equals("Thread") ||
				typeName.equals("ThreadGroup") ||
				typeName.equals("ThreadLocal<T>") ||
				typeName.equals("Throwable") ||
				typeName.equals("Void"));
	}
	
	/**
	 * Check whether the type is allowed (according to 
	 * specifications defined in the method).
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
		
//		return (type.isPrimitiveType() 
//				|| javaImportTypes.contains(type.toString())
//				|| classTypes.contains(type.toString())
//				|| inJavaLangLibrary(type));
		return (type.isPrimitiveType());
	}
	
}
