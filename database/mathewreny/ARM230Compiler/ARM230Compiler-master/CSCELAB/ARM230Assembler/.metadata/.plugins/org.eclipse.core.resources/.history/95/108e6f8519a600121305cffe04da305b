package edu.unl.cse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InitMIF {

	private ArrayList<String> argList = new ArrayList<String>();
	
	public InitMIF(String[] args){
		for(int i = 0; i<args.length; i++){
			String arg = args[i];
			this.argList.add(arg);
			//System.out.println("added "+arg);
		}
	}
	
	public PrintWriter getPrintWriter(){
		String filename = "";
		//CHECKS FOR THE FOLLOWING FLAGS
		//-f filename specified. Should always be present
		//-o* output specified.		
		for(int i = 0; i<argList.size(); i++ ){
			if(argList.get(i).equals("-f")){
				boolean outputSpecified = false;
				String outputType = null;
				String outputFilename = null;
				boolean saveFilename = false;
				for(String s: argList){
					if(saveFilename){
						outputFilename = s.trim();
						saveFilename = false;
					} else if(s.substring(0,2).equals("-o")){
						outputSpecified = true;
						outputType = s.substring(2);
						saveFilename = true;
					}
				}
				if(outputSpecified){
					if (outputType.equals("")){
						filename = outputFilename;
					} else if (outputType.equals("MI")){
						filename = "MemoryInitialization.mif";
					} else if (outputType.equals("MIf")){
						filename = "MemoryInitialization"+argList.get(i+1)
								.replace(".s230",".mif").replace(".S230", ".mif");
					} else {
						System.out.println("Error with -o syntax");
					}
				} else {
					filename = argList.get(i+1);
					filename = filename.trim().replace(".s230", ".mif");
					filename = filename.trim().replace(".S230", ".mif");
				}
				

			}
		}
//		System.out.println(System.getProperty("user.dir") + " ... " + filename);
		String dirName = System.getProperty("user.dir");
		if(Compiler.isWindows()){
			dirName = dirName.replace("\\Program_Files\\source","\\").trim();
			dirName = dirName.replace("\\Program_Files\\classes","\\").trim();
			dirName = dirName.concat("\\InputOutputFolder\\");
		} else {
			dirName = dirName.replace("/Program_Files/source","/").trim();
			dirName = dirName.replace("/Program_Files/classes","/").trim();
			dirName = dirName.concat("/InputOutputFolder/");			
		}
			//		System.out.println(dirName+filename);
		File file = new File(dirName, filename);
		try{
			return new PrintWriter(file);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}


	  return null;
	}
}