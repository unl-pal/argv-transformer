package transform.benchmark_sv;

import java.util.LinkedHashMap;
import org.yaml.snakeyaml.Yaml;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import org.yaml.snakeyaml.DumperOptions;



public class CreateYmlFile {
	
	public static void buildFile(String filePath, String fileName, String programPath, boolean isAssertionTrue) {
		Map<String, Object> yamlContent = new LinkedHashMap<>();
        yamlContent.put("format_version", "2.0");
        

        List<String> inputFiles = new ArrayList<>();
        inputFiles.add("../common/");
        inputFiles.add(programPath + "/");
        yamlContent.put("input_files", inputFiles);

        List<Map<String, Object>> properties = new ArrayList<>();
        Map<String, Object> property = new LinkedHashMap<>();
        property.put("property_file", "../properties/assert_java.prp");
        
        
        if (isAssertionTrue)
        	property.put("expected_verdict", true);
        
        else
        	property.put("expected_verdict", false);
        
        properties.add(property);
        yamlContent.put("properties", properties);
        

        Map<String, Object> options = new LinkedHashMap<>();
        options.put("language", "Java");
        yamlContent.put("options", options);
        
        try {
        	fileName = fileName + ".yml";
        	FileWriter writer = new FileWriter(filePath + "/" + fileName);
            DumperOptions dumperOptions = new DumperOptions();
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(dumperOptions);
            yaml.dump(yamlContent, writer);
            writer.close();
            System.out.println("YAML file created successfully.");
        } catch (IOException e) {
        	System.out.println("Error writing YAML file: " + e.getMessage());
        }
		
	}
	
	
	
	

}
