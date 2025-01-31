package cse143exercises.ArraysAndArrayLists;

import java.util.Arrays;

public class Test {

    public static int[] getMult(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }

        int product = 1;
        int zeroCounter = 0;
        int[] res = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                product *= nums[i];
            } else {
                zeroCounter++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];
            if (cur != 0) {
                if (zeroCounter > 0) {
                    res[i] = 0;
                } else {
                    res[i] = product/cur;
                }
            } else {
                res[i] = product;
            }
            if (zeroCounter > 1) {
                res[i] = 0;
            }
        }

        return res;
    }
    
    public static void main(String[] args) {
        int[] data = {-1, 0, 0, -3, 3};
        System.out.println(Arrays.toString(Test.getMult(data)));
    }
}
