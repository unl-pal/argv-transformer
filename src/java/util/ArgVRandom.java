package util;

public class ArgVRandom {
    
    private int number;
	
	public ArgVRandom(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
    
	public static double randomDouble() {
		return Math.random();
	}
	
	public static int randomInt() {
		return (int) (Math.random() * 10);
	}
	
	public static float randomFloat() {
		return (float) (Math.random() * 10.0f);
	}
}
