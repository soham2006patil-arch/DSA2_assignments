import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Integer> removeDuplicatesReference(int[] arr) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            boolean alreadyAdded = false;
            for (int j = 0; j < result.size(); j++) {
                if (result.get(j) == arr[i]) {
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded) {
                result.add(arr[i]);
            }
        }

        return result;
    }

    static int removeDuplicatesOptimized(int[] arr) {
        if (arr.length == 0) return 0;
        if (arr.length == 1) return 1;

        int slow = 0;

        for (int fast = 1; fast < arr.length; fast++) {
            if (arr[fast] != arr[slow]) {
                slow++;
                arr[slow] = arr[fast];
            }
        }

        return slow + 1;
    }

    static int[] pairSumBrute(int[] arr, int target) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[]{ i, j };
                }
            }
        }

        return new int[]{ -1, -1 };
    }

    static int[] pairSumOptimized(int[] arr, int target) {
        int left  = 0;
        int right = arr.length - 1;

        while (left < right) {
            int currentSum = arr[left] + arr[right];

            if (currentSum == target) {
                return new int[]{ left, right };
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{ -1, -1 };
    }

    static String arrayToString(int[] arr, int limit) {
        if (limit == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < limit; i++) {
            sb.append(arr[i]);
            if (i < limit - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    static String listToString(List<Integer> list) {
        if (list.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println(" DSA-II Assignment 1 | Java");
        System.out.println(" Two-Pointer Technique");
        System.out.println("========================================================\n");

        System.out.println("────────────────────────────────────────────────────────");
        System.out.println(" PART A – Remove Duplicates from a Sorted Array");
        System.out.println("────────────────────────────────────────────────────────\n");

        int[][] dupTests = {
            {1, 1, 2, 2, 2, 3, 4, 4},
            {5},
            {},
            {-3, -3, -1, 0, 0}
        };

        for (int t = 0; t < dupTests.length; t++) {
            int[] input = dupTests[t];
            System.out.println("  Test " + (t + 1) + ": Input = "
                               + arrayToString(input, input.length));

            List<Integer> refResult = removeDuplicatesReference(input);
            System.out.println("    [Reference Approach]  k = " + refResult.size()
                               + "  |  Unique elements: " + listToString(refResult));

            int[] copy = Arrays.copyOf(input, input.length);
            int optK   = removeDuplicatesOptimized(copy);
            System.out.println("    [Optimized Approach]  k = " + optK
                               + "  |  First k elements:  " + arrayToString(copy, optK));
            System.out.println();
        }

        System.out.println("────────────────────────────────────────────────────────");
        System.out.println(" PART B – Pair Sum in a Sorted Array");
        System.out.println("────────────────────────────────────────────────────────\n");

        int[][] pairArrays = {
            {1, 2, 4, 7, 11, 15},
            {1, 3, 5, 8},
            {2, 4, 6},
            {1, 2, 2, 3}
        };
        int[] targets = { 15, 7, 20, 4 };

        for (int t = 0; t < pairArrays.length; t++) {
            int[] arr    = pairArrays[t];
            int   target = targets[t];

            System.out.println("  Test " + (t + 1) + ": Array = "
                               + arrayToString(arr, arr.length)
                               + "  |  Target = " + target);

            int[] brute = pairSumBrute(arr, target);
            System.out.print("    [Brute Force]   Indices = ("
                             + brute[0] + ", " + brute[1] + ")");
            if (brute[0] != -1)
                System.out.print("  →  arr[" + brute[0] + "]=" + arr[brute[0]]
                                 + " + arr[" + brute[1] + "]=" + arr[brute[1]]
                                 + " = " + target);
            System.out.println();

            int[] opt = pairSumOptimized(arr, target);
            System.out.print("    [Two-Pointer]   Indices = ("
                             + opt[0] + ", " + opt[1] + ")");
            if (opt[0] != -1)
                System.out.print("  →  arr[" + opt[0] + "]=" + arr[opt[0]]
                                 + " + arr[" + opt[1] + "]=" + arr[opt[1]]
                                 + " = " + target);
            System.out.println("\n");
        }

        System.out.println("========================================================");
        System.out.println(" All test cases completed.");
        System.out.println("========================================================");
    }
}
