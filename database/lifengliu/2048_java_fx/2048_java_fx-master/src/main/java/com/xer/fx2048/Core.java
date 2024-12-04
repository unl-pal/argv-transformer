/*
 * The core logic of 2048
 */
package com.xer.fx2048;

/**
 *
 * @author lifeng liu 
 */
public class Core {
	private int [][] board;
	private int size;
	private int list[][];
	private int score;
	/**
	 * Randomly add a tile in empty slot.
	 */
	public void addRandom()
	{
		int len=0;
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				if(board[i][j]==0)
				{
					list[len][0]=i;
					list[len][1]=j;
					len++;
				}
			}
		}
		if(len>0)
		{
			int r=(int)(Math.random()*len);	
			int x=list[r][0];
			int y=list[r][1];
			int n=(((int)(Math.random()*10))/9+1)*2;	
			board[x][y]=n;	
		}
		
	}
	/**
	 * slide a column 
	 */
	public boolean slideColumn(int [] line)
	{
		int stop=0;
		boolean success=false;
		for(int i=0;i<line.length;i++)
		{
			if(line[i]!=0)
			{
				int t=findTarget(line,i,stop);
				if(t!=i)
				{
					if(line[t]!=0)
					{
						score+=line[t]+line[i];
						stop=t+1;
					}
					line[t]+=line[i];
					line[i]=0;
					success=true;
				}
			}
		}
		return success;
	}
	/**
	 * Find merge target for location x
	 */
	public int findTarget(int [] line, int x,int stop)
	{
		if(x==0)
		{
			return 0;
		}
		for(int i=x-1;i>=0;i--)
		{
			if(line[i]!=0)
			{
				if(line[i]!=line[x])
				{
					return i+1;	
				}
				else
				{
					return i;
				}
			}
			else
			{
				if(i==stop)
				{
					return i;
				}
			}
		}
		return x;
	}
}
