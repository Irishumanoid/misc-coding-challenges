
import java.util.ArrayList;
import java.util.List;

public class RB {
    public static void main(String[] args) {
        System.out.println(doubleDigit(-2001, 0));
    }

    public static int doubleDigit(int n, int d) {
        if (d < 0 || d > 9) throw new IllegalArgumentException();
        if (n == 0) return n;
        if (n < 0) return -doubleDigit(-n, d);
        int digit = n % 10;
        if (digit == d) {
            return 100 * doubleDigit(n / 10, d) + 11 * d;
        } else {
            return 10 * doubleDigit(n / 10, d) + digit;
        }
    }

    public static void printSquares(int num) {
        printSquares(num, num, new ArrayList<>());
    }

    private static void printSquares(int num, int cur, List<Integer> nums) {
        if (cur == 0) {
            printHelper(nums);
        } else if (cur > 0) {
            int start = 1;
            if (nums.size() > 0) {
                start = nums.get(nums.size() - 1) + 1;
            }
            for (int i = start; i < (int) Math.sqrt(num) + 1; i++) {
                cur -= Math.pow(i, 2);
                nums.add(i);
                printSquares(num, cur, nums);
                cur += Math.pow(i, 2);
                nums.remove(nums.size() - 1);
            }
        }
    }

    public static int climbingCats(int levels, int maxJumpHeight) {
        return climbingCats(levels, maxJumpHeight, new ArrayList<>());
    }

    private static int climbingCats(int l, int max, List<Integer> jumps) {
        if (l == 0) {
            System.out.println(jumps);
            return 1;
        } else if (l > 0) {
            int sum = 0;
            for (int i = 1; i <= Math.min(l, max); i++) {
                jumps.add(i);
                sum += climbingCats(l - i, max, jumps);
                jumps.remove(jumps.size() - 1);
            }
            return sum;
        }
        return 0;
    }


    public static void printHelper(List<Integer> nums) {
        for (int i = 0; i < nums.size(); i++) {
            String out = nums.get(i) + "^2";
            if (i < nums.size() - 1) {
                System.out.print(out + " + ");
            } else {
                System.out.print(out);
            }
        }
        System.out.println();
    }
}
