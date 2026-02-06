package transform.visitors;

import logging.Logger;
import org.eclipse.jdt.core.dom.*;

import java.util.List;


/**
 * Class to be extended with shared helper methods
 */
public class ASTVisitorUtil extends ASTVisitor {

	protected static Logger logger = Logger.defaultLogger;

	/**
	 * Use the method name and its parameters to uniquely name
	 * it's symbol table element.
	 *
	 * @param node MethodDeclaration node
	 * @return the method's name in the symbol table
	 */
	protected String getMethodSTEName(MethodDeclaration node) {
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
			} else if (type instanceof SimpleType) {
				String typeName = ((SimpleType)type).toString(); // adequate assuming java.lang.String+
				switch (typeName) {
					case "String":
					case "java.lang.String":
						name += "S";
						break;
					case "StringBuilder":
						case "java.lang.StringBuilder":
						name += "SB";
						break;
					case "StringBuffer":
						case "java.lang.StringBuffer":
						name += "SF";
						break;
					case "CharSequence":
						case "java.lang.CharSequence":
						name += "CS";
						break;
					case "Object":
						case "java.lang.Object": // ?
						name += "O";
						break;
					default:
						logger.logln("Warning: encountered unhandled type " + typeName + " in naming method " + name, 5);
				}
			}
		}
		return name;
	}
}
