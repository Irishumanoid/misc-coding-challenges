package cse143exercises.ArraysAndArrayLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayIntList {
    private List<Integer> currentList;
    private int[] elementData;
    private int size;

    public ArrayIntList(List<Integer> currentList, int[] elementData, int size) {
        this.currentList = currentList;
        this.elementData = elementData;
        this.size = elementData.length;
    }

    public int returnIndexOf(int value, List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
           if (value == list.get(i)) {
             return i;
           }
        }

        return -1;
    }

    public List<Integer> stutter() {
        List<Integer> doubledList = new ArrayList<>();
        for (int i = 0; i < currentList.size(); i++) {
            doubledList.add(2*i, currentList.get(i));
            doubledList.add(2*i+1, currentList.get(i));
        }

        return doubledList;
    }

    public List<Integer> removeIndex(int index) {
        List<Integer> truncate = new ArrayList<>();

        try {
            for (int i = 0; i < currentList.size(); i++) {
                if (i == index) {
                    continue;
                } else {
                    truncate.add(currentList.get(i));
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index chosen" + e);
        }

        return truncate;
    }

    public List<Integer> add(int index, int value) {
        List<Integer> grown = new ArrayList<>();
        for (int i = 0; i < currentList.size(); i++) {
            if (i == index) {
                grown.add(value);
                grown.add(currentList.get(i));
            } else {
                grown.add(currentList.get(i));
            }
        }

        return grown;
    }

    public boolean isPairwiseSorted() {
        if (size <= 1) {
            return true;
        } else if (size % 2 == 1) {
            for (int i = 0; i < size-1; i++) {
                if (i % 2 == 0) {
                    if (elementData[i] <= elementData[i+1]) {
                        continue;
                    } else {
                        return false;
                    }
                } 
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (i % 2 == 0) {
                    if (elementData[i] <= elementData[i+1]) {
                        continue;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* Balanced Split
    Given an array of integers (which may include repeated integers), determine if there's a way to split the array into two
    subarrays A and B such that the sum of the integers in both arrays is the same, and all of the integers in A are strictly
    smaller than all of the integers in B.
    Note: Strictly smaller denotes that every integer in A must be less than, and not equal to, every integer in B.
    Signature:
    bool balancedSplitExists(int[] arr)
    Input: All integers in array are in the range [0, 1,000,000,000].
    Output: Return true if such a split is possible, and false otherwise.
    Example 1
    arr = [1, 5, 7, 1]
    output = true
    We can split the array into A = [1, 1, 5] and B = [7].
    Example 2
    arr = [12, 7, 6, 7, 6]
    output = false
    We can't split the array into A = [6, 6, 7] and B = [7, 12] since this doesn't satisfy the requirement that all integers in A are smaller than all integers in B.
     */
    public boolean balancedSplitExists(int[] arr) {
        //sort, sum first n elements until reach halfway point, if exactly at halfway and last element in first list < first element in second list return true
        quickSort(arr, 0, arr.length-1);
        int sum = 0;
        for (int val : arr) {
            sum += val;
        }

        if (sum % 2 != 0) {
            return false;
        }

        int half_sum = 0;
        int prev_element = 0;

        for (int i = 0; i < arr.length; i++) {
            if (half_sum < sum/2) {
                half_sum += arr[i];
                prev_element = arr[i];
            } else if (half_sum == sum/2) {
                if (prev_element < arr[i]) {
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }

        return false;
    }

    public static void quicksort(int[] arr, int begin, int end) {
        if (begin < end) {
            int partition = partition(arr, begin, end);

            quicksort(arr, begin, partition-1);
            quicksort(arr, partition+1, end);
        }
    }
    
    private static int partition(int[] arr, int begin, int end) {
        int pivot_index = (int) Math.floor((end - begin)/2);
        int pivot  = arr[pivot_index];
        System.out.println("pivot: " + pivot);

        for (int i = begin; i < end; i++) {
            if (arr[i] < pivot && i > pivot_index) {
                int temp = arr[begin];
                arr[begin] = arr[i];
                arr[i] = temp;

                pivot_index++;
            } else if (arr[i] > pivot && i < pivot_index) {
                int temp = arr[end];
                arr[end] = arr[i];
                arr[i] = temp;

                pivot_index--;
            }
        }

        return pivot_index;
    }

    public void quickSort(int arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition2(arr, begin, end);
    
            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private int partition2(int arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);
    
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
    
                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
    
        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;
    
        return i+1;
    }

 

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int[] data = {3, 8, 2, 5, 19, 24, -3, 0, 4, 4, 8, 205, 42};
        ArrayIntList listInst = new ArrayIntList(list, data, 13);
        //listInst.currentList.add(1);
       
        int[] arr = {42, 7, -9, 14, 8, 39, 42, 8, 19, 0};
        for (int entry : arr) {
            list.add(entry);
        }

        //System.out.print(listInst.returnIndexOf(8, list));
        //System.out.println(listInst.stutter());
        //System.out.println(listInst.removeIndex(3));
        //System.out.println(listInst.add(2, 17));
        //System.out.println(listInst.isPairwiseSorted());
        System.out.println(listInst.balancedSplitExists(arr));
    }


    }

