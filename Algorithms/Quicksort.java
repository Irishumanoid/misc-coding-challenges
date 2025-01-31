import java.util.ArrayList;
import java.util.List;

public class Quicksort implements Sort {
    private enum PivotType {
        FIRST,
        RANDOM,
        MEDIAN_OF_THREE
    }

    public static void main(String[] args) {
        Quicksort qsort = new Quicksort();
        int[] input = new int[]{1, 5, 9, 2};
        qsort.sort(input);
    }
    
    @Override
    public void sort(int[] input) {
        List<Long> runtimes = new ArrayList<>();

        for (PivotType type : PivotType.values()) {
            System.out.printf("Beginning quicksort with pivot type %s ", type.toString());
            long startTimeMicrosecs = System.nanoTime() / 1000;
            int[] output = quicksort(input, 0, input.length - 1, type);

            System.out.printf("Finished quicksort with pivot type %s ", type.toString());
            long endTimeMicrosecs = System.nanoTime() / 1000;
            long runtime = endTimeMicrosecs - startTimeMicrosecs;
            System.out.printf("Runtime of quicksort was %d (microseconds) \n", runtime);
            runtimes.add(runtime);
        }
        // write outputs with runtimes to file (csv w/ inputSize, pivotType, and runtime in microseconds
        
    }

    private int[] quicksort(int[] input, int lower, int upper, PivotType pivotType) {
        if (lower >= upper) {
            return input;
        }
        int pivot = inPlaceParition(input, lower, upper, pivotType);
        quicksort(input, lower, pivot - 1, pivotType);
        quicksort(input, pivot, upper, pivotType);
        return input;
    }

    private int inPlaceParition(int[] input, int lower, int upper, PivotType pivotType) {
        int pivot = -1;
        switch (pivotType) {
            case FIRST:
                pivot = lower;
                break;
            case MEDIAN_OF_THREE:
                int middle = (int) Math.floor((upper + lower) / 2);
                int[] choices = new int[]{input[lower], input[upper], input[middle]};
                int median = Math.max(Math.min(choices[0], choices[1]), Math.min(choices[1], choices[2]));
                if (median == choices[0]) {
                    pivot = lower;
                } else if (median == choices[1]) {
                    pivot = middle;
                } else {
                    pivot = upper;
                }
                break;
            case RANDOM:
                pivot = (int) Math.floor(Math.random() * (upper - lower)) + lower;
                break;
            default:
                pivot = lower;
                break;
        }
        // swap the pivot and the last index
        int pVal = input[pivot];
        input[pivot] = input[upper];
        input[upper] = pVal;

        int left = lower;
        int right = upper;
        // keep swapping leftmost larger and rightmost smaller
        while (left <= right) {
            while (left <= right && input[left] < pVal) {
                left += 1;
            }
            while (right >= left && input[right] > pVal) {
                right -= 1;
            }
            // reached condition where leftVal >= pVal or rightVal <= pVal
            if (left <= right) {
                int value = input[left];
                input[left] = input[right];
                input[right] = value;
                left += 1;
                right -= 1;
            }
        }
        // move the pivot to its correct position
        input[upper] = input[left];
        input[left] = pVal;
        
        return left;
    }
}

