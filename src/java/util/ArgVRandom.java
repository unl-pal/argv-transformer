package util;

public class ArgVRandom {
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
