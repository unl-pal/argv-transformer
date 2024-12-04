package ufrgs.maslab.abstractsimulator.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WriteFile {
	
	private Map<File, BufferedWriter> bw = new HashMap<File, BufferedWriter>();
	private static WriteFile instance = null;

}
