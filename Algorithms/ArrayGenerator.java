
/** Generates 14 arrays of size 2^2 to 2^15 with elements between 0 and 40,000. 
 * For each array, it is randomly ordered, ordered, reverse ordered, 50% ordered, and 75% ordered */
public class ArrayGenerator {
    private static int MAX_LENGTH = 40000;

    private int[] generateArray(int size) {
        int[] genArray = new int[size];
        for (int i = 0; i < genArray.length; i++) {
            genArray[i] = Math.random() * MAX_LENGTH;
        }

        return genArray;
    }

    // TODO remember to close csvwriter after each sort
}
