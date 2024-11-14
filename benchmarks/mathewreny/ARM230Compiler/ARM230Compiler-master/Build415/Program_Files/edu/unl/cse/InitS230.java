/** filtered and transformed by PAClab */
package edu.unl.cse;

import org.sosy_lab.sv_benchmarks.Verifier;

public class InitS230 {

	//Get the scanner object. Chose to make this not static 
	//because it is a global thing that needs to be reset often.
	/** PACLab: suitable */
	 public Object getScanner(){
	  if(Verifier.nondetBoolean()){ //IF THE OPPERATING SYSTEM IS WINDOWS
		for(int i = 0; i<Verifier.nondetInt(); i++ ){
			if(Verifier.nondetBoolean()){
				boolean useProvidedFilename = false;
				if(useProvidedFilename){
				} else {
				}
				
				
			}
		}
try {
			return new Object();
		} catch (FileNotFoundException e) {
		}  
	  } else {  //IF THE OPPERATING SYSTEM IS LINUX OR UNIX
		for(int i = 0; i<Verifier.nondetInt(); i++ ){
			if(Verifier.nondetBoolean()){
			}
		}
try {
			return new Object();
		} catch (FileNotFoundException e) {
		}
	  }
	  return new Object();
	}	
}
