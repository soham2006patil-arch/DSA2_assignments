# DSA-II Programming Assignment 1

## Two-Pointer Technique
### Removing Duplicates from a Sorted Array · Pair Sum in a Sorted Array

---

## Table of Contents
1. [Project Structure](#project-structure)
2. [Problem Descriptions](#problem-descriptions)
3. [Algorithms Implemented](#algorithms-implemented)
4. [Compilation & Execution](#compilation--execution)
5. [Test Cases & Expected Output](#test-cases--expected-output)
6. [Complexity Analysis](#complexity-analysis)

---

## Project Structure

```
DSA-II-Assignment-1/
├── cpp/
│   └── main.cpp        ← C++17  (all 4 implementations)
├── java/
│   └── Main.java       ← Java   (all 4 implementations)
├── python/
│   └── main.py         ← Python 3 (all 4 implementations)
└── README.md
```

---

## Problem Descriptions

### Part A – Remove Duplicates from a Sorted Array
Given a **sorted** integer array, remove all duplicate elements so that each unique value appears **exactly once**.  
Return `k` — the number of unique elements — and ensure the first `k` positions of the array hold the unique values.

### Part B – Pair Sum in a Sorted Array
Given a **sorted** integer array and an integer `target`, find **two different indices** `i` and `j` such that `arr[i] + arr[j] == target`.  
Return the pair of indices, or `(-1, -1)` if no such pair exists.

---

## Algorithms Implemented

### Part A – Remove Duplicates

| # | Name | Core Idea | Time | Space |
|---|------|-----------|------|-------|
| A1 | **Reference Approach** | Build a separate result list; check every element before appending | O(n²) | O(n) |
| A2 | **Optimized (Two-Pointer)** | `slow` = write head, `fast` = read head; write only when a new unique value is found | O(n) | O(1) |

#### A2 – Two-Pointer Logic (in-place)
```
slow = 0
for fast in range(1, n):
    if arr[fast] != arr[slow]:   ← new unique element found
        slow += 1                ← advance write head
        arr[slow] = arr[fast]    ← place new unique value
return slow + 1                  ← k = number of unique elements
```

### Part B – Pair Sum

| # | Name | Core Idea | Time | Space |
|---|------|-----------|------|-------|
| B1 | **Brute Force** | Nested loops; check every pair (i, j) with j > i | O(n²) | O(1) |
| B2 | **Optimized (Two-Pointer)** | `left` starts at 0, `right` at n-1; converge based on sum comparison | O(n) | O(1) |

#### B2 – Two-Pointer Logic
```
left = 0,  right = n - 1
while left < right:
    sum = arr[left] + arr[right]
    if   sum == target  →  return (left, right)   ← found!
    elif sum  < target  →  left  += 1             ← sum too small; increase it
    else                →  right -= 1             ← sum too large; decrease it
return (-1, -1)
```

---

## Compilation & Execution

### C++ (requires g++ with C++17 support)

```bash
cd cpp
g++ -std=c++17 -Wall -o main main.cpp
./main
```

### Java (requires JDK 11 or later)

```bash
cd java
javac Main.java
java Main
```

### Python (requires Python 3.6+)

```bash
cd python
python3 main.py
```

---

## Test Cases & Expected Output

### Part A – Remove Duplicates

| # | Input Array | Expected k | Expected Unique Prefix |
|---|-------------|------------|------------------------|
| 1 | `[1, 1, 2, 2, 2, 3, 4, 4]` | **4** | `[1, 2, 3, 4]` |
| 2 | `[5]` | **1** | `[5]` |
| 3 | `[]` | **0** | `[]` |
| 4 | `[-3, -3, -1, 0, 0]` | **3** | `[-3, -1, 0]` |

### Part B – Pair Sum

| # | Array | Target | Expected Indices | Notes |
|---|-------|--------|-----------------|-------|
| 1 | `[1, 2, 4, 7, 11, 15]` | 15 | **(2, 4)** | 4 + 11 = 15 |
| 2 | `[1, 3, 5, 8]` | 7 | **(-1, -1)** | No valid pair exists |
| 3 | `[2, 4, 6]` | 20 | **(-1, -1)** | No valid pair exists |
| 4 | `[1, 2, 2, 3]` | 4 | **(0, 3)** | 1 + 3 = 4 (valid pair) |

> **Note on Test 2:** For array `[1, 3, 5, 8]` with target `7`, checking all pairs:
> 1+3=4, 1+5=6, 1+8=9, 3+5=8, 3+8=11, 5+8=13 — **none equals 7**.  
> The correct output is `(-1, -1)`, which all three implementations produce.

---

## Complexity Analysis

### Part A – Remove Duplicates

| Approach | Time Complexity | Space Complexity | Modifies Input? |
|----------|----------------|-----------------|-----------------|
| Reference (A1) | O(n²) | O(n) | No – builds new array |
| Two-Pointer (A2) | **O(n)** | **O(1)** | Yes – in-place |

### Part B – Pair Sum

| Approach | Time Complexity | Space Complexity |
|----------|----------------|-----------------|
| Brute Force (B1) | O(n²) | O(1) |
| Two-Pointer (B2) | **O(n)** | **O(1)** |

<img width="396" height="610" alt="image" src="https://github.com/user-attachments/assets/3ec6c3e5-2083-4676-8414-2c95b309d822" />


---

## Key Concepts

- **Two-Pointer Pattern**: Two index variables that move toward each other (or in tandem) to reduce O(n²) brute-force problems to O(n).
- **In-Place Modification**: The optimized duplicate-removal writes results back into the original array, saving the extra O(n) space needed for a result array.
- **Sorted Array Property**: Both optimized solutions exploit the sorted order — duplicates are adjacent (Part A), and the relationship between `arr[left]+arr[right]` and the target is predictable (Part B).
