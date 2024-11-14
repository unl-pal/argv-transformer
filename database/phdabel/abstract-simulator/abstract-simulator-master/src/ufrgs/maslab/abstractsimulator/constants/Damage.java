package ufrgs.maslab.abstractsimulator.constants;


public enum Damage {
	
	CONTUSION(0),
	LETHAL(1),
	SEVERE(2);
		
	
	private int value;
	
	private Damage(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	

}
