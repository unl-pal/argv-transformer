/**
 * 
 */
package cruise.umple.umpr.core.consistent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.ConsoleMain;
import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.ImportFSM;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.consistent.ConsistentsModule.ConsistentsJacksonConfig;
import cruise.umple.umpr.core.util.Networks;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;
import com.google.inject.Provider;


/**
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 *
 * @since 11 Mar 2015
 */
public abstract class Consistents {
  
  @Inject
  private static ConsistentsFactory CONSISTENTS_FACTORY;
  
  @SuppressWarnings("unused")
  @Inject
  private static Logger logger;
  
  @Inject @ConsistentsJacksonConfig // get the json config from the module
  private static ObjectMapper mapper;

  /**
   * Converts an {@link ImportRepositorySet} to JSON
   * 
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   * @since 17 Mar 2015
   */
  static class ImportRepositorySetSerializer extends JsonSerializer<ImportRepositorySet> {
  }
    
  /**
   * Converts an {@link ImportRepository} to JSON
   * 
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   * @since 17 Mar 2015
   */
  static class ImportRepositorySerializer extends JsonSerializer<ImportRepository> {
  }
  
  /**
   * Converts an {@link ImportFile} to JSON
   * 
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   * @since 17 Mar 2015
   */
  static class ImportFileSerializer extends JsonSerializer<ImportFile> {
  }
  
  static class AttribSerializer extends JsonSerializer<ImportAttrib> {
    
  }
  
  static class ImportRepositorySetDeserializer extends JsonDeserializer<ImportRepositorySet> {
    
    private final Provider<ConsistentsFactory> factory;
    
    private final static Map<String, DiagramType> diagramMapping = getFakeEnumMapping(DiagramType.class);
    private final static Map<String, UmpleImportType> importMapping = getFakeEnumMapping(UmpleImportType.class);
    
  }
}
