import java.util.Arrays;

public class Main {

    static String arrToStr(int[] a) {
        if (a.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
            if (i < a.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    static String pad(String s, int width) {
        while (s.length() < width) s += " ";
        return s;
    }

    static int maxSumBrute(int[] a, int K) {
        int n = a.length;

        if (K < 1 || K > n) {
            System.out.println("  [ERROR] K must satisfy 1 <= K <= n");
            return Integer.MIN_VALUE;
        }

        int maxSum = Integer.MIN_VALUE;

        for (int start = 0; start <= n - K; start++) {
            int windowSum = 0;
            for (int j = start; j < start + K; j++) {
                windowSum += a[j];
            }
            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }
        return maxSum;
    }

    static int maxSumSliding(int[] a, int K, boolean trace) {
        int n = a.length;

        if (K < 1 || K > n) {
            System.out.println("  [ERROR] K must satisfy 1 <= K <= n");
            return Integer.MIN_VALUE;
        }

        int windowSum = 0;
        for (int i = 0; i < K; i++) windowSum += a[i];
        int maxSum = windowSum;

        if (trace) {
            System.out.println("\n    Fixed-Window Trace (K=" + K + "):");
            System.out.println("    " + "-".repeat(64));
            System.out.println("    Left  Right  Window                  CurSum  MaxSum");
            System.out.println("    " + "-".repeat(64));

            StringBuilder win = new StringBuilder("[");
            for (int i = 0; i < K; i++) {
                win.append(a[i]);
                if (i < K - 1) win.append(",");
            }
            win.append("]");
            System.out.println("    " + pad("0", 6) + pad(String.valueOf(K - 1), 7)
                    + pad(win.toString(), 24) + pad(String.valueOf(windowSum), 8)
                    + maxSum + "  (initial)");
        }

        for (int right = K; right < n; right++) {
            int left = right - K;
            windowSum += a[right];
            windowSum -= a[left];

            if (windowSum > maxSum) maxSum = windowSum;

            if (trace) {
                StringBuilder win = new StringBuilder("[");
                for (int i = left + 1; i <= right; i++) {
                    win.append(a[i]);
                    if (i < right) win.append(",");
                }
                win.append("]");
                System.out.println("    " + pad(String.valueOf(left + 1), 6)
                        + pad(String.valueOf(right), 7)
                        + pad(win.toString(), 24)
                        + pad(String.valueOf(windowSum), 8)
                        + maxSum);
            }
        }

        if (trace) System.out.println("    " + "-".repeat(64));
        return maxSum;
    }

    static int[] makeResult(int length, int lo, int hi) {
        return new int[]{length, lo, hi};
    }

    static int[] longestAtMostKBrute(int[] a, int K) {
        int n = a.length;
        int[] best = makeResult(0, -1, -1);

        for (int i = 0; i < n; i++) {
            int runSum = 0;
            for (int j = i; j < n; j++) {
                runSum += a[j];
                if (runSum > K) break;
                int len = j - i + 1;
                if (len > best[0]) {
                    best = makeResult(len, i, j);
                }
            }
        }
        return best;
    }

    static int[] longestAtMostKSliding(int[] a, int K, boolean trace) {
        int n = a.length;
        int[] best = makeResult(0, -1, -1);

        int left = 0, sum = 0;

        if (trace) {
            System.out.println("\n    Variable-Window Trace:");
            System.out.println("    " + "-".repeat(68));
            System.out.println("    Right  Left  Sum   Action                    CurLen  BestLen");
            System.out.println("    " + "-".repeat(68));
        }

        for (int right = 0; right < n; right++) {
            sum += a[right];

            if (trace) {
                System.out.println("    " + pad(String.valueOf(right), 7)
                        + pad(String.valueOf(left), 6)
                        + pad(String.valueOf(sum), 6)
                        + "Expand +a[" + right + "]=" + a[right]);
            }

            while (sum > K) {
                if (trace) {
                    System.out.println("    " + "       "
                            + pad(String.valueOf(left), 6)
                            + pad(String.valueOf(sum), 6)
                            + "Shrink -a[" + left + "]=" + a[left]);
                }
                sum -= a[left];
                left++;
            }

            int curLen = right - left + 1;
            if (curLen > best[0]) {
                best = makeResult(curLen, left, right);
            }

            if (trace) {
                System.out.println("    " + "       "
                        + pad(String.valueOf(left), 6)
                        + pad(String.valueOf(sum), 6)
                        + pad("Valid [" + left + ".." + right + "]", 26)
                        + pad(String.valueOf(curLen), 8)
                        + best[0]);
            }
        }

        if (trace) System.out.println("    " + "-".repeat(68));
        return best;
    }

    static void printPartA(String label, int result, int K) {
        System.out.print("    [" + label + "]");
        int pad = 22 - label.length();
        while (pad-- > 0) System.out.print(" ");
        System.out.println("Maximum sum of " + K
                + " consecutive elements = " + result);
    }

    static void printPartB(String label, int[] r) {
        System.out.print("    [" + label + "]");
        int pad = 22 - label.length();
        while (pad-- > 0) System.out.print(" ");
        if (r[0] == 0) {
            System.out.println("Length = 0  |  Indices = (-1, -1)  (no valid subarray)");
        } else {
            System.out.println("Length = " + r[0]
                    + "  |  Indices = (" + r[1] + ", " + r[2] + ")");
        }
    }

    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println(" DSA-II Assignment 2 | Java");
        System.out.println(" Sliding Window Technique");
        System.out.println("========================================================\n");

        System.out.println("────────────────────────────────────────────────────────");
        System.out.println(" PART A – Maximum Sum of K Consecutive Elements");
        System.out.println(" Complexity:");
        System.out.println("   Brute Force   : Time O(n*K)  |  Auxiliary Space O(1)");
        System.out.println("   Sliding Window: Time O(n)    |  Auxiliary Space O(1)");
        System.out.println("────────────────────────────────────────────────────────\n");

        int[][] partAArrays = {
            {2, 1, 5, 1, 3, 2},
            {-4, -2, -7},
            {6},
            {1, 2, 3},
        };
        int[] partAKs = {3, 2, 1, 3};

        for (int t = 0; t < partAArrays.length; t++) {
            int[] arr = partAArrays[t];
            int   K   = partAKs[t];
            System.out.println("  Test " + (t + 1)
                    + ": Array = " + arrToStr(arr)
                    + "  |  K = " + K);

            int bruteAns   = maxSumBrute(arr, K);
            int slidingAns = maxSumSliding(arr, K, true);

            printPartA("Brute Force",    bruteAns,   K);
            printPartA("Sliding Window", slidingAns, K);
            System.out.println();
        }

        System.out.println("────────────────────────────────────────────────────────");
        System.out.println(" PART B – Longest Subarray with Sum at Most K");
        System.out.println(" ASSUMPTION: All array elements >= 0, K >= 0.");
        System.out.println(" Complexity:");
        System.out.println("   Brute Force   : Time O(n^2)  |  Auxiliary Space O(1)");
        System.out.println("   Sliding Window: Time O(n)    |  Auxiliary Space O(1)");
        System.out.println("────────────────────────────────────────────────────────\n");

        int[][] partBArrays = {
            {1, 2, 1, 1, 3},
            {5, 1, 1},
            {4, 2, 1},
            {0, 0, 1, 0},
        };
        int[] partBKs = {4, 2, 0, 1};

        for (int t = 0; t < partBArrays.length; t++) {
            int[] arr = partBArrays[t];
            int   K   = partBKs[t];
            System.out.println("  Test " + (t + 1)
                    + ": Array = " + arrToStr(arr)
                    + "  |  K = " + K);

            int[] bruteRes   = longestAtMostKBrute(arr, K);
            int[] slidingRes = longestAtMostKSliding(arr, K, true);

            printPartB("Brute Force",    bruteRes);
            printPartB("Sliding Window", slidingRes);
            System.out.println();
        }

        System.out.println("========================================================");
        System.out.println(" All test cases completed.");
        System.out.println("========================================================");
    }
}
