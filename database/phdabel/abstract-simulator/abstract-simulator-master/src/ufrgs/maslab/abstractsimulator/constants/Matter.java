package ufrgs.maslab.abstractsimulator.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Matter {
	
	WOODEN_HOUSE(0),
	STEEL_FRAME_HOUSE(1),
	REINFORCED_CONCRETE(2);
	
	private static final List<Matter> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	
	private static final int SIZE = VALUES.size();
	
	private static final Random RANDOM = new Random();
	
	private int value;
	
	private Matter(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public static double getProbability(int m){
		double prob = 0.0;
		switch(m){
			case 0:
				prob = 0.4;
				break;
			case 1:
				prob = 0.2;
				break;
			case 2:
				prob = 0.1;
				break;
		}
		return prob;
	}
	
	public static Matter randomMatter()
	{
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

}
