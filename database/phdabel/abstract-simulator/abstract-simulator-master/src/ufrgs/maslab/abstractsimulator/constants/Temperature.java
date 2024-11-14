package ufrgs.maslab.abstractsimulator.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Temperature {
	
	NORMAL(0),
	WARM(1),
	HOT(2),
	ON_FIRE(3),
	INFERNO(4);
	
	private int value;
	
	private static final List<Temperature> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	
	private static final int SIZE = VALUES.size();
	
	private static final Random RANDOM = new Random();
	
	
	private Temperature(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public static Temperature randomTemperature()
	{
		int r = 0;
		while(r == 0)
		{
			r = RANDOM.nextInt(SIZE);
		}
		return VALUES.get(r);
	}

}
