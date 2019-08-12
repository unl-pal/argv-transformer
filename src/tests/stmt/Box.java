package tests.stmt;

import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.Symbolic;

public class Box {
	
    @Symbolic("true")
	private int length;
    
    @Symbolic("true")
   	private int width;
    
    @Symbolic("true")
   	private boolean isEmpty;
	
	
	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return (int) Debug.makeSymbolicInteger("length");
	}

	public int getWidth() {
		return (int) Debug.makeSymbolicInteger("width");
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isEmpty() {
		return isEmpty;
	}
	

}
