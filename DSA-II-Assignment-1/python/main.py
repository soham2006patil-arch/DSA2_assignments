def remove_duplicates_reference(arr: list) -> tuple:
    result = []

    for element in arr:
        already_added = False
        for existing in result:
            if existing == element:
                already_added = True
                break

        if not already_added:
            result.append(element)

    return len(result), result


def remove_duplicates_optimized(arr: list) -> int:
    if not arr:
        return 0
    if len(arr) == 1:
        return 1

    slow = 0

    for fast in range(1, len(arr)):
        if arr[fast] != arr[slow]:
            slow += 1
            arr[slow] = arr[fast]

    return slow + 1


def pair_sum_brute(arr: list, target: int) -> tuple:
    n = len(arr)

    for i in range(n - 1):
        for j in range(i + 1, n):
            if arr[i] + arr[j] == target:
                return (i, j)

    return (-1, -1)


def pair_sum_optimized(arr: list, target: int) -> tuple:
    left  = 0
    right = len(arr) - 1

    while left < right:
        current_sum = arr[left] + arr[right]

        if current_sum == target:
            return (left, right)
        elif current_sum < target:
            left  += 1
        else:
            right -= 1

    return (-1, -1)


def list_to_str(lst: list, limit: int = None) -> str:
    if limit is not None:
        lst = lst[:limit]
    return "[" + ", ".join(str(x) for x in lst) + "]"


def main():
    print("=" * 56)
    print(" DSA-II Assignment 1 | Python 3")
    print(" Two-Pointer Technique")
    print("=" * 56)
    print()

    print("─" * 56)
    print(" PART A – Remove Duplicates from a Sorted Array")
    print("─" * 56)
    print()

    dup_tests = [
        [1, 1, 2, 2, 2, 3, 4, 4],
        [5],
        [],
        [-3, -3, -1, 0, 0],
    ]

    for idx, test in enumerate(dup_tests, start=1):
        print(f"  Test {idx}: Input = {list_to_str(test)}")

        ref_k, ref_result = remove_duplicates_reference(test)
        print(f"    [Reference Approach]  k = {ref_k}"
              f"  |  Unique elements: {list_to_str(ref_result)}")

        arr_copy = test[:]
        opt_k    = remove_duplicates_optimized(arr_copy)
        print(f"    [Optimized Approach]  k = {opt_k}"
              f"  |  First k elements:  {list_to_str(arr_copy, opt_k)}")
        print()

    print("─" * 56)
    print(" PART B – Pair Sum in a Sorted Array")
    print("─" * 56)
    print()

    pair_tests = [
        ([1, 2, 4, 7, 11, 15], 15),
        ([1, 3, 5, 8],          7),
        ([2, 4, 6],            20),
        ([1, 2, 2, 3],          4),
    ]

    for idx, (arr, target) in enumerate(pair_tests, start=1):
        print(f"  Test {idx}: Array = {list_to_str(arr)}  |  Target = {target}")

        bi, bj = pair_sum_brute(arr, target)
        brute_str = f"  →  arr[{bi}]={arr[bi]} + arr[{bj}]={arr[bj]} = {target}" \
                    if bi != -1 else ""
        print(f"    [Brute Force]   Indices = ({bi}, {bj}){brute_str}")

        oi, oj = pair_sum_optimized(arr, target)
        opt_str = f"  →  arr[{oi}]={arr[oi]} + arr[{oj}]={arr[oj]} = {target}" \
                  if oi != -1 else ""
        print(f"    [Two-Pointer]   Indices = ({oi}, {oj}){opt_str}")
        print()

    print("=" * 56)
    print(" All test cases completed.")
    print("=" * 56)


if __name__ == "__main__":
    main()
