#include <iostream>
#include <vector>
#include <string>

std::pair<int, std::vector<int>> removeDuplicatesReference(const std::vector<int>& arr) {
    std::vector<int> result;

    for (int i = 0; i < (int)arr.size(); ++i) {
        bool alreadyAdded = false;
        for (int j = 0; j < (int)result.size(); ++j) {
            if (result[j] == arr[i]) {
                alreadyAdded = true;
                break;
            }
        }
        if (!alreadyAdded) {
            result.push_back(arr[i]);
        }
    }

    return { (int)result.size(), result };
}

int removeDuplicatesOptimized(std::vector<int>& arr) {
    if (arr.empty()) return 0;
    if (arr.size() == 1) return 1;

    int slow = 0;

    for (int fast = 1; fast < (int)arr.size(); ++fast) {
        if (arr[fast] != arr[slow]) {
            ++slow;
            arr[slow] = arr[fast];
        }
    }

    return slow + 1;
}

std::pair<int, int> pairSumBrute(const std::vector<int>& arr, int target) {
    int n = (int)arr.size();

    for (int i = 0; i < n - 1; ++i) {
        for (int j = i + 1; j < n; ++j) {
            if (arr[i] + arr[j] == target) {
                return { i, j };
            }
        }
    }

    return { -1, -1 };
}

std::pair<int, int> pairSumOptimized(const std::vector<int>& arr, int target) {
    int left  = 0;
    int right = (int)arr.size() - 1;

    while (left < right) {
        int currentSum = arr[left] + arr[right];

        if (currentSum == target) {
            return { left, right };
        } else if (currentSum < target) {
            ++left;
        } else {
            --right;
        }
    }

    return { -1, -1 };
}

void printVector(const std::vector<int>& v, int limit = -1) {
    int n = (limit == -1) ? (int)v.size() : limit;
    std::cout << "[";
    for (int i = 0; i < n; ++i) {
        std::cout << v[i];
        if (i < n - 1) std::cout << ", ";
    }
    std::cout << "]";
}

int main() {
    std::cout << "========================================================\n";
    std::cout << " DSA-II Assignment 1 | C++17\n";
    std::cout << " Two-Pointer Technique\n";
    std::cout << "========================================================\n\n";

    std::cout << "────────────────────────────────────────────────────────\n";
    std::cout << " PART A – Remove Duplicates from a Sorted Array\n";
    std::cout << "────────────────────────────────────────────────────────\n\n";

    std::vector<std::vector<int>> dupTests = {
        {1, 1, 2, 2, 2, 3, 4, 4},
        {5},
        {},
        {-3, -3, -1, 0, 0}
    };

    for (int t = 0; t < (int)dupTests.size(); ++t) {
        std::cout << "  Test " << (t + 1) << ": Input = ";
        printVector(dupTests[t]);
        std::cout << "\n";

        auto [refK, refResult] = removeDuplicatesReference(dupTests[t]);
        std::cout << "    [Reference Approach]  k = " << refK
                  << "  |  Unique elements: ";
        printVector(refResult);
        std::cout << "\n";

        std::vector<int> arrCopy = dupTests[t];
        int optK = removeDuplicatesOptimized(arrCopy);
        std::cout << "    [Optimized Approach]  k = " << optK
                  << "  |  First k elements:  ";
        printVector(arrCopy, optK);
        std::cout << "\n\n";
    }

    std::cout << "────────────────────────────────────────────────────────\n";
    std::cout << " PART B – Pair Sum in a Sorted Array\n";
    std::cout << "────────────────────────────────────────────────────────\n\n";

    std::vector<std::pair<std::vector<int>, int>> pairTests = {
        { {1, 2, 4, 7, 11, 15}, 15 },
        { {1, 3, 5, 8},         7  },
        { {2, 4, 6},            20 },
        { {1, 2, 2, 3},         4  }
    };

    for (int t = 0; t < (int)pairTests.size(); ++t) {
        auto& [arr, target] = pairTests[t];
        std::cout << "  Test " << (t + 1) << ": Array = ";
        printVector(arr);
        std::cout << "  |  Target = " << target << "\n";

        auto [bi, bj] = pairSumBrute(arr, target);
        std::cout << "    [Brute Force]   Indices = (" << bi << ", " << bj << ")";
        if (bi != -1)
            std::cout << "  →  arr[" << bi << "]=" << arr[bi]
                      << " + arr[" << bj << "]=" << arr[bj] << " = " << target;
        std::cout << "\n";

        auto [oi, oj] = pairSumOptimized(arr, target);
        std::cout << "    [Two-Pointer]   Indices = (" << oi << ", " << oj << ")";
        if (oi != -1)
            std::cout << "  →  arr[" << oi << "]=" << arr[oi]
                      << " + arr[" << oj << "]=" << arr[oj] << " = " << target;
        std::cout << "\n\n";
    }

    std::cout << "========================================================\n";
    std::cout << " All test cases completed.\n";
    std::cout << "========================================================\n";

    return 0;
}
