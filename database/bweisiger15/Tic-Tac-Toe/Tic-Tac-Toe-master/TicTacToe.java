import java.util.Scanner;

public class TicTacToe
{
  
  private static char[][] board = new char[3][3];
  
  public static void checkTie() //check for a full board
  {
    int temp;
    int temp2;
    int count = 0;
    for (temp = 0; temp <= 2; temp++)
    {
      for (temp2 = 0; temp2 <= 2; temp2++)
      {
        if (board[temp][temp2] == 'X' || board[temp][temp2] == 'O'){
          count++;
        }
      }
    }
    if (count == 9)
    {
      System.out.println("IT'S A TIE! New game: ");
      initialBoard();
      
    }
    else getInfo();
  }
  
  
  
} 






