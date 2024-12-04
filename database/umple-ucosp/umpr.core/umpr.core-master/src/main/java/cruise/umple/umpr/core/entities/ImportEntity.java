/**
 * 
 */
package cruise.umple.umpr.core.entities;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.Repository;

/**
 * Entity to store information about importing. This includes the {@link Path} to store the output file, the 
 * {@link Repository} the Entity belongs to, and a method for getting an {@link InputStream} of the content. 
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * @since Mar 2, 2015
 */
public interface ImportEntity extends Supplier<String> {
}
