# DSA-II Programming Assignment 2

## Sliding Window Technique
### Maximum Sum of K Consecutive Elements · Longest Subarray with Sum at Most K

---

## Table of Contents
1. [Project Structure](#project-structure)
2. [Problem Descriptions](#problem-descriptions)
3. [Algorithms Implemented](#algorithms-implemented)
4. [Compilation & Execution](#compilation--execution)
5. [Test Cases & Expected Output](#test-cases--expected-output)
6. [Complexity Analysis](#complexity-analysis)
7. [Key Concepts & Assumptions](#key-concepts--assumptions)

---

## Project Structure

```
DSA-II-Assignment-2/
├── cpp/
│   └── main.cpp        ← C++17  (all 4 implementations + traces)
├── java/
│   └── Main.java       ← Java   (all 4 implementations + traces)
├── python/
│   └── main.py         ← Python 3 (all 4 implementations + traces)
└── README.md
```

---

## Problem Descriptions

### Part A – Maximum Sum of K Consecutive Elements
Given an integer array (which may contain **negative** values) and a window size **K**,
find the **maximum sum** of any contiguous subarray of exactly **K** elements.

**Constraint:** `1 <= K <= n`

### Part B – Longest Subarray with Sum at Most K
Given an array of **non-negative** integers and a non-negative integer **K**, find the
**longest contiguous subarray** whose element sum is `<= K`.  
Return its **length** and **start/end indices**.  
Return `length = 0` and `indices = (-1, -1)` if no non-empty valid subarray exists.

**Constraint (IMPORTANT):** All array elements must be `>= 0` and `K >= 0`.  
The variable-size sliding window is invalid for arrays containing negative numbers
because shrinking from the left could *increase* the sum.

---

## Algorithms Implemented

### Part A – Maximum Sum of K Consecutive Elements

| # | Name | Core Idea | Time | Aux. Space |
|---|------|-----------|------|-----------|
| A1 | **Reference (Brute Force)** | For every starting index, sum the next K elements from scratch | O(n·K) | O(1) |
| A2 | **Optimized (Fixed-Size Sliding Window)** | Keep a running sum; add the entering element and subtract the leaving element each step | O(n) | O(1) |

#### A2 – Fixed-Size Sliding Window Logic
```
windowSum = sum(a[0..K-1])     ← first window
maxSum    = windowSum

for right from K to n-1:
    left       = right - K
    windowSum += a[right]      ← add entering element  (right pointer moves in)
    windowSum -= a[left]       ← subtract leaving element (left pointer moves out)
    maxSum     = max(maxSum, windowSum)

return maxSum
```

**Dry-run example** — `a = [2, 1, 5, 1, 3, 2]`, `K = 3`:

| Left | Right | Window      | CurSum | MaxSum |
|------|-------|-------------|--------|--------|
| 0    | 2     | [2, 1, 5]   | 8      | 8      |
| 1    | 3     | [1, 5, 1]   | 7      | 8      |
| 2    | 4     | [5, 1, 3]   | 9      | **9**  |
| 3    | 5     | [1, 3, 2]   | 6      | 9      |

---

### Part B – Longest Subarray with Sum at Most K

| # | Name | Core Idea | Time | Aux. Space |
|---|------|-----------|------|-----------|
| B1 | **Reference (Brute Force)** | For every starting index, extend right while sum ≤ K | O(n²) | O(1) |
| B2 | **Optimized (Variable-Size Sliding Window)** | Expand right; shrink left whenever sum > K | O(n) | O(1) |

#### B2 – Variable-Size Sliding Window Logic
```
left = 0, sum = 0, bestLength = 0

for right from 0 to n-1:
    sum += a[right]                 ← EXPAND window rightward

    while sum > K:                  ← SHRINK window from left
        sum  -= a[left]
        left += 1

    # window [left..right] is now valid (sum <= K)
    curLen = right - left + 1
    if curLen > bestLength:
        bestLength = curLen
        bestLo, bestHi = left, right

return bestLength, bestLo, bestHi
```

**Dry-run example** — `a = [1, 2, 1, 1, 3]`, `K = 4`:

| Right | Left | Sum | Action               | CurLen | BestLen |
|-------|------|-----|----------------------|--------|---------|
| 0     | 0    | 1   | Expand +a[0]=1       | 1      | 1       |
| 1     | 0    | 3   | Expand +a[1]=2       | 2      | 2       |
| 2     | 0    | 4   | Expand +a[2]=1       | 3      | **3**   |
| 3     | 0    | 5   | Expand +a[3]=1       | –      | –       |
|       | 0    | 5   | Shrink -a[0]=1       | –      | –       |
|       | 1    | 4   | Valid [1..3]         | 3      | 3       |
| 4     | 1    | 7   | Expand +a[4]=3       | –      | –       |
|       | 1    | 7   | Shrink -a[1]=2       | –      | –       |
|       | 2    | 5   | Shrink -a[2]=1       | –      | –       |
|       | 3    | 4   | Shrink -a[3]=1       | –      | –       |
|       | 4    | 3   | Valid [4..4]         | 1      | 3       |

---

## Compilation & Execution

### C++ (requires g++ with C++17 support)

```bash
cd DSA-II-Assignment-2/cpp
g++ -std=c++17 -Wall -o main main.cpp
./main
```

### Java (requires JDK 11 or later)

```bash
cd DSA-II-Assignment-2/java
javac Main.java
java Main
```

### Python (requires Python 3.6+)

```bash
cd DSA-II-Assignment-2/python
python3 main.py
```

---

## Test Cases & Expected Output

### Part A – Maximum Sum of K Consecutive Elements

| # | Array | K | Expected Max Sum | Winning Window |
|---|-------|---|-----------------|----------------|
| 1 | `[2, 1, 5, 1, 3, 2]` | 3 | **9** | `[5, 1, 3]` |
| 2 | `[-4, -2, -7]` | 2 | **-6** | `[-4, -2]` |
| 3 | `[6]` | 1 | **6** | `[6]` |
| 4 | `[1, 2, 3]` | 3 | **6** | `[1, 2, 3]` |

> **Note on Test 2:** The array has only negative integers.  
> The brute-force initialises `maxSum = INT_MIN / float('-inf')` so the
> algorithm handles all-negative arrays correctly without special casing.

### Part B – Longest Subarray with Sum at Most K

| # | Array | K | Expected Length | Valid Indices | Notes |
|---|-------|---|----------------|--------------|-------|
| 1 | `[1, 2, 1, 1, 3]` | 4 | **3** | (0,2) or (1,3) | Both `[1,2,1]` and `[2,1,1]` sum to ≤ 4 |
| 2 | `[5, 1, 1]` | 2 | **2** | (1, 2) | Subarray `[1, 1]` |
| 3 | `[4, 2, 1]` | 0 | **0** | (-1, -1) | Even a single element exceeds K=0 |
| 4 | `[0, 0, 1, 0]` | 1 | **4** | (0, 3) | Entire array sums to 1 ≤ K=1 |

> **Note on Test 4:** Zero-valued elements are handled correctly because
> adding a zero never exceeds K.  The window expands over all four elements.

---

## Complexity Analysis

### Part A – Maximum Sum of K Consecutive Elements

| Approach | Time Complexity | Auxiliary Space |
|----------|----------------|----------------|
| Reference / Brute Force (A1) | O(n · K) | O(1) |
| Optimized Sliding Window (A2) | **O(n)** | **O(1)** |

### Part B – Longest Subarray with Sum at Most K

| Approach | Time Complexity | Auxiliary Space |
|----------|----------------|----------------|
| Reference / Brute Force (B1) | O(n²) | O(1) |
| Optimized Sliding Window (B2) | **O(n)** | **O(1)** |

> **Why O(n) for B2?** Each element is added (`right` pointer moves in) and
> removed (`left` pointer moves out) at most once.  The inner `while` loop's
> total iterations across the entire outer loop is at most `n`, giving amortised
> O(1) per step and O(n) overall.

---

## Key Concepts & Assumptions

### Fixed-Size Sliding Window (Part A)
- The window size is always exactly **K**.
- Both pointers advance in lock-step; `right` moves first, then `left = right - K`.
- No contraction needed — the window never shrinks.
- **Works with negative integers** because the maximum is tracked explicitly, not assumed from window growth.

### Variable-Size Sliding Window (Part B)
- The window size changes dynamically between 0 and n.
- **Non-negative element assumption is required** — the algorithm relies on the invariant that adding an element to the right never decreases the window sum, so expanding `left` always makes progress toward satisfying `sum <= K`.
- With negative numbers, shrinking from the left could *raise* the sum, causing an infinite loop or incorrect results.
- Zero-valued elements are handled correctly: they never push the sum above K.
