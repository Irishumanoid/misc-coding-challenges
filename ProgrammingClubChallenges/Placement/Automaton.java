import java.util.Arrays;

public class Automaton {

    /** 50-50 chance to generate 0 or 1 */
    private static int getRandom() {
        return Math.random() < 0.5 ? 0 : 1;
    }

    /** Apply update rule to current value for given cell */
    public static int calculate(int value, int[] updateRule) {
        return updateRule[value];
    }

    public static void main(String[] args) {
        int numCells;
        int numGenerations;
        int[] updateRule;
        try {
            numCells = Integer.parseInt(args[0]);
            numGenerations = Integer.parseInt(args[1]);
            updateRule = new int[]{Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])};
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Incorrect number of arguments (should be 6)");
            return;
        } catch (java.lang.NumberFormatException nfe) {
            System.out.println("Only numbers can be passed in as arguments");
            return;
        }
        
        if (numCells < 1 || numGenerations < 0  || Arrays.stream(updateRule).anyMatch(e -> !(e == 0 || e == 1))) {
            System.out.println("Initial conditions for values of inputs are not met. Do not enter negative numbers, or numbers besides 0 or 1 for the update rule.");
            return;
        }

        int[] prevState = new int[numCells];
        // populate first generation
        for (int i = 0; i < numCells; i++) {
            int cellState = Automaton.getRandom();
            prevState[i] = cellState;
            System.out.print(cellState);
        }

        for (int i = 0; i < numGenerations; i++) {
            System.out.println();

            int[] curState = new int[numCells];
            for (int j = 0; j < numCells; j++) {
                int value;
                // take the sum of the cells in the previous generation, handling boundary conditions
                if (j == 0) {
                    value = prevState[j] + prevState[j+1];
                } else if (j == numCells - 1) {
                    value = prevState[j-1] + prevState[j];
                } else {
                    value = prevState[j-1] + prevState[j] + prevState[j+1];
                }

                int output = Automaton.calculate(value, updateRule);
                curState[j] = output;
                System.out.print(output);
            }

            prevState = curState;
        }
    }
}
