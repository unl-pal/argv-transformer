package ufrgs.maslab.abstractsimulator.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum MessageType {
	
	VOICE(0),
	RADIO(1),
	EMAIL(2),
	LETTER(3);
	
	private int value;
	
	private static final List<MessageType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	
	private static final int SIZE = VALUES.size();
	
	private static final Random RANDOM = new Random();
	
	
	private MessageType(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public static MessageType randomMessageType()
	{
		int r = 0;
		while(r == 0)
		{
			r = RANDOM.nextInt(SIZE);
		}
		return VALUES.get(r);
	}

}
