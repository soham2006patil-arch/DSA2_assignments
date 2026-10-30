#include <iostream>
#include <vector>
#include <string>
#include <climits>

static std::string vecToStr(const std::vector<int>& v) {
    std::string s = "[";
    for (int i = 0; i < (int)v.size(); ++i) {
        s += std::to_string(v[i]);
        if (i < (int)v.size() - 1) s += ", ";
    }
    return s + "]";
}

int maxSumBrute(const std::vector<int>& a, int K) {
    int n = (int)a.size();

    if (K < 1 || K > n) {
        std::cerr << "  [ERROR] K must satisfy 1 <= K <= n\n";
        return INT_MIN;
    }

    int maxSum = INT_MIN;

    for (int start = 0; start <= n - K; ++start) {
        int windowSum = 0;
        for (int j = start; j < start + K; ++j) {
            windowSum += a[j];
        }
        if (windowSum > maxSum) {
            maxSum = windowSum;
        }
    }
    return maxSum;
}

int maxSumSliding(const std::vector<int>& a, int K, bool trace = false) {
    int n = (int)a.size();

    if (K < 1 || K > n) {
        std::cerr << "  [ERROR] K must satisfy 1 <= K <= n\n";
        return INT_MIN;
    }

    int windowSum = 0;
    for (int i = 0; i < K; ++i) windowSum += a[i];

    int maxSum = windowSum;

    if (trace) {
        std::cout << "\n    Fixed-Window Trace (K=" << K << "):\n";
        std::cout << "    " << std::string(64, '-') << "\n";
        std::cout << "    Left  Right  Window                  CurSum  MaxSum\n";
        std::cout << "    " << std::string(64, '-') << "\n";

        std::string win = "[";
        for (int i = 0; i < K; ++i) {
            win += std::to_string(a[i]);
            if (i < K - 1) win += ",";
        }
        win += "]";
        while ((int)win.size() < 22) win += " ";
        std::cout << "    " << "0      " << (K - 1) << "      "
                  << win
                  << windowSum << "       " << maxSum << "  (initial)\n";
    }

    for (int right = K; right < n; ++right) {
        int left = right - K;
        windowSum += a[right];
        windowSum -= a[left];

        if (windowSum > maxSum) maxSum = windowSum;

        if (trace) {
            std::string win = "[";
            for (int i = left + 1; i <= right; ++i) {
                win += std::to_string(a[i]);
                if (i < right) win += ",";
            }
            win += "]";
            while ((int)win.size() < 22) win += " ";
            std::cout << "    " << (left + 1) << "      " << right << "      "
                      << win
                      << windowSum << "       " << maxSum << "\n";
        }
    }

    if (trace) std::cout << "    " << std::string(64, '-') << "\n";
    return maxSum;
}

struct SubarrayResult {
    int length;
    int lo;
    int hi;
};

SubarrayResult longestAtMostKBrute(const std::vector<int>& a, int K) {
    int n = (int)a.size();
    SubarrayResult best{0, -1, -1};

    for (int i = 0; i < n; ++i) {
        int runSum = 0;
        for (int j = i; j < n; ++j) {
            runSum += a[j];
            if (runSum > K) break;
            int len = j - i + 1;
            if (len > best.length) {
                best = {len, i, j};
            }
        }
    }
    return best;
}

SubarrayResult longestAtMostKSliding(const std::vector<int>& a, int K,
                                     bool trace = false) {
    int n = (int)a.size();
    SubarrayResult best{0, -1, -1};

    int left = 0;
    int sum  = 0;

    if (trace) {
        std::cout << "\n    Variable-Window Trace:\n";
        std::cout << "    " << std::string(68, '-') << "\n";
        std::cout << "    Right  Left  Sum   Action                    CurLen  BestLen\n";
        std::cout << "    " << std::string(68, '-') << "\n";
    }

    for (int right = 0; right < n; ++right) {
        sum += a[right];

        if (trace) {
            std::cout << "    " << right << "      " << left
                      << "     " << sum
                      << "    Expand +a[" << right << "]=" << a[right] << "\n";
        }

        while (sum > K) {
            if (trace) {
                std::cout << "    " << "       " << left
                          << "     " << sum
                          << "    Shrink -a[" << left << "]=" << a[left] << "\n";
            }
            sum -= a[left];
            ++left;
        }

        int curLen = right - left + 1;
        if (curLen > best.length) {
            best = {curLen, left, right};
        }

        if (trace) {
            std::cout << "    " << "       " << left
                      << "     " << sum
                      << "    Valid [" << left << ".." << right << "]"
                      << "              " << curLen << "       " << best.length << "\n";
        }
    }

    if (trace) std::cout << "    " << std::string(68, '-') << "\n";
    return best;
}

static void printPartAResult(const std::string& label, int result, int K) {
    std::cout << "    [" << label << "]";
    int pad = 22 - (int)label.size();
    while (pad-- > 0) std::cout << ' ';
    std::cout << "Maximum sum of " << K
              << " consecutive elements = " << result << "\n";
}

static void printPartBResult(const std::string& label, const SubarrayResult& r) {
    std::cout << "    [" << label << "]";
    int pad = 22 - (int)label.size();
    while (pad-- > 0) std::cout << ' ';
    if (r.length == 0) {
        std::cout << "Length = 0  |  Indices = (-1, -1)  (no valid subarray)\n";
    } else {
        std::cout << "Length = " << r.length
                  << "  |  Indices = (" << r.lo << ", " << r.hi << ")\n";
    }
}

int main() {
    std::cout << "========================================================\n";
    std::cout << " DSA-II Assignment 2 | C++17\n";
    std::cout << " Sliding Window Technique\n";
    std::cout << "========================================================\n\n";

    std::cout << "────────────────────────────────────────────────────────\n";
    std::cout << " PART A – Maximum Sum of K Consecutive Elements\n";
    std::cout << " Complexity:\n";
    std::cout << "   Brute Force   : Time O(n*K)  |  Auxiliary Space O(1)\n";
    std::cout << "   Sliding Window: Time O(n)    |  Auxiliary Space O(1)\n";
    std::cout << "────────────────────────────────────────────────────────\n\n";

    struct TestA { std::vector<int> arr; int K; };
    std::vector<TestA> partATests = {
        { {2, 1, 5, 1, 3, 2}, 3 },
        { {-4, -2, -7},       2 },
        { {6},                1 },
        { {1, 2, 3},          3 },
    };

    for (int t = 0; t < (int)partATests.size(); ++t) {
        auto& [arr, K] = partATests[t];
        std::cout << "  Test " << (t + 1)
                  << ": Array = " << vecToStr(arr)
                  << "  |  K = " << K << "\n";

        int bruteAns   = maxSumBrute(arr, K);
        int slidingAns = maxSumSliding(arr, K, true);

        printPartAResult("Brute Force",    bruteAns,   K);
        printPartAResult("Sliding Window", slidingAns, K);
        std::cout << "\n";
    }

    std::cout << "────────────────────────────────────────────────────────\n";
    std::cout << " PART B – Longest Subarray with Sum at Most K\n";
    std::cout << " ASSUMPTION: All array elements >= 0, K >= 0.\n";
    std::cout << " Complexity:\n";
    std::cout << "   Brute Force   : Time O(n^2)  |  Auxiliary Space O(1)\n";
    std::cout << "   Sliding Window: Time O(n)    |  Auxiliary Space O(1)\n";
    std::cout << "────────────────────────────────────────────────────────\n\n";

    struct TestB { std::vector<int> arr; int K; };
    std::vector<TestB> partBTests = {
        { {1, 2, 1, 1, 3}, 4 },
        { {5, 1, 1},        2 },
        { {4, 2, 1},        0 },
        { {0, 0, 1, 0},     1 },
    };

    for (int t = 0; t < (int)partBTests.size(); ++t) {
        auto& [arr, K] = partBTests[t];
        std::cout << "  Test " << (t + 1)
                  << ": Array = " << vecToStr(arr)
                  << "  |  K = " << K << "\n";

        SubarrayResult bruteRes   = longestAtMostKBrute(arr, K);
        SubarrayResult slidingRes = longestAtMostKSliding(arr, K, true);

        printPartBResult("Brute Force",    bruteRes);
        printPartBResult("Sliding Window", slidingRes);
        std::cout << "\n";
    }

    std::cout << "========================================================\n";
    std::cout << " All test cases completed.\n";
    std::cout << "========================================================\n";

    return 0;
}
