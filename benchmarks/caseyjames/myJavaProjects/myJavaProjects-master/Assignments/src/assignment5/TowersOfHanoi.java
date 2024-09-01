package assignment5;

/**
 * Represents the Towers of Hanoi puzzle. Executed from the command-line and solves for a given number of discs. Verbose
 * option allows for printing the state of the towers after each move.
 * <p/>
 * To solve the problem we need to move all N discs from Tower A to Tower B. There are three rules governing the
 * movement of discs from pole to tower.
 * <p/>
 * Rule 1. Only one disc may be moved at a time. Rule 2. Only the topmost disc on each pole is accessible. Rule 3. Only
 * a smaller disc may be moved on top a larger disc or on an empty tower.
 * <p/>
 * Stacks are used to represent the towers and recursion is used to solve and the problem.
 *
 * @author Paymon Saebi
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public class TowersOfHanoi {
    private int numOfDiscs;
    private long numOfMoves;
    private boolean verbose;
    private MyStack<Integer> towerA;
    private MyStack<Integer> towerB;
    private MyStack<Integer> towerC;

    /**
     * This method overrides the default Object toString method and formats the current state of the towers to the
     * criteria given by the assignment. It first grabs a copy of each tower and stores them on an array of objects for
     * each tower, and then displaying the towers in the required manner.
     */
    public String toString() {
        // Putting the towers in object arrays
        String towerVisual = "";
        Object[] towA = towerA.toArray();
        Object[] towB = towerB.toArray();
        Object[] towC = towerC.toArray();

        // Looping to print each line for towers
        for (int i = numOfDiscs; i > 0; i--) {
            // Checking for and putting | in empty spots in towers "A"
            if (towA.length < i)
                towerVisual = towerVisual + "\t  |  ";
            else
                // Formatting each line according to the discs number of digits
                if (Integer.parseInt(towA[i - 1].toString()) < 10)
                    towerVisual = towerVisual + "\t  " + towA[i - 1] + "  ";
                else
                    towerVisual = towerVisual + "\t  " + towA[i - 1] + " ";

            // Checking for and putting | in empty spots in towers "A"
            if (towB.length < i)
                towerVisual = towerVisual + "   |   ";
            else
                // Formatting each line according to the discs number of digits
                if (Integer.parseInt(towB[i - 1].toString()) < 10)
                    towerVisual = towerVisual + "   " + towB[i - 1] + "   ";
                else
                    towerVisual = towerVisual + "   " + towB[i - 1] + "  ";

            // Checking for and putting | in empty spots in towers "A"
            if (towC.length < i)
                towerVisual = towerVisual + "  |  ";
            else
                // Formatting each line according to the discs number of digits
                if (Integer.parseInt(towC[i - 1].toString()) < 10)
                    towerVisual = towerVisual + "  " + towC[i - 1] + "  ";
                else
                    towerVisual = towerVisual + "  " + towC[i - 1] + "  ";

            towerVisual = towerVisual + "\n";
        }

        // Creating the base of the towers
        towerVisual = towerVisual + "\t----- ----- -----" + "\n" + "\tTow A Tow B Tow C" + "\n";

        return towerVisual;
    }
}