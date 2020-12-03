
/** filtered by PAClab */
 public class Twenty48 
{
	private int rowLength;
	private int colLength;
	private int[][] board;
	
	public static int[] merge(int[] line)
	{
		int[] result = new int[line.length];
		boolean[] merged = new boolean[line.length];
		
		for (int x = 0; x < result.length; x++)
		{
			result[x] = 0;
			merged[x] = false;
		}
		
		for (int i = 0; i < line.length; i++)
		{
			if (line[i] != 0)
			{
				for (int j = 0; j < result.length; j++)
				{
					if (result[j] == 0)
					{
						result[j] = line[i];
						break;
					}
				}
			}
		}
		
		for (int shit = 0; shit < result.length - 1; shit++)
		{
			if (result[shit] == result[shit + 1] && !merged[shit])
			{
				result[shit] *= 2;
				merged[shit] = true;
				for (int nipple = shit + 1; nipple < result.length - 1; nipple++)
				{
					result[nipple] = result[nipple + 1];
				}
				result[result.length - 1] = 0;
			}
		}
		
		return result;
	}
	
	public void newTile()
	{
		int randomRow = (int)(Math.random() * rowLength);
		int randomCol = (int)(Math.random() * colLength);
		while (board[randomRow][randomCol] != 0)
		{
			randomRow = (int)(Math.random() * rowLength);
			randomCol = (int)(Math.random() * colLength);
		}
		int chance = 1 + (int)(Math.random() * 10);
		if (chance == 1)
		{
			board[randomRow][randomCol] = 4;
		}
		else
		{
			board[randomRow][randomCol] = 2;
		}
	}
}
