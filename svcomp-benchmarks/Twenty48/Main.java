import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by PAClab */
public class Main
{

    public static int[] merge(int[] line) {

        assert(line != null);

        int lineLength = line.length;
        assert (lineLength > 0);

        int[] result = new int[lineLength];
        boolean[] merged = new boolean[lineLength];

        assert(result.length  == lineLength || merged.length == lineLength );


        // Initialize result and merged arrays
        for (int x = 0; x < result.length; x++)
        {
            result[x] = 0;
            merged[x] = false;
        }

       // Shift non-zero elements to the left
        for (int i = 0; i < line.length; i++) {
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
        int nonZeroCount = 0;
        for (int value : line) {
            if (value != 0) {
                nonZeroCount++;
            }
        }

        int resultNonZeroCount = 0;
        for (int value : result) {
            if (value != 0) {
                resultNonZeroCount++;
            }
        }

        assert(resultNonZeroCount == nonZeroCount);


        // Assert that zeros are pushed to the end
        for (int i = 0; i < result.length - 1; i++)
        {
            if (result[i] == result[i + 1] && !merged[i]) {
                result[i] *= 2;
                merged[i] = true;
                for (int j = i + 1; j < result.length - 1; j++)
                {
                    result[j] = result[j + 1];
                }
                result[result.length - 1] = 0;
            }
        }

        assert(result.length == lineLength);
        return result;
    }

    public static void newTile(int rowLength, int colLength, int[][] board) {
        assert (rowLength > 0 );
        assert(colLength >  0);
        // Keep generating until we find an empty cell
        int randomRow;
        int randomCol;

        do {
            // Generate random indices within bounds
            randomRow = Verifier.nondetInt() % rowLength; // Ensures 0 <= randomRow < rowLength
            randomCol = Verifier.nondetInt() % colLength; // Ensures 0 <= randomCol < colLength
        } while (board[randomRow][randomCol] != 0); // Check if the cell is empty

        int chance = 1 + Verifier.nondetInt() * 10;
        if (chance == 1)
        {
            board[randomRow][randomCol] = 4;
        }
        else
        {
            board[randomRow][randomCol] = 2;
        }
        assert (board[randomRow][randomCol] == 2 || board[randomRow][randomCol] == 4);
    }

    public static void main(String[] args) {
        int length = Verifier.nondetInt();
        if(length > 0) {
            int[] line = new int[length];
            for (int i = 0; i < line.length; i++) {
                line[i] = Verifier.nondetInt();
            }
            merge(line);
        }
            int rowLength = Verifier.nondetInt();
            int colLength = Verifier.nondetInt();

            // Added ---Oct14
            if(rowLength > 0 && colLength > 0 ){
                int[][] board = new int[rowLength][colLength];
                newTile(rowLength, colLength, board);
            }

    }
}
