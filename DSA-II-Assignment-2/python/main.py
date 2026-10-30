def list_to_str(lst: list) -> str:
    return "[" + ", ".join(str(x) for x in lst) + "]"


def max_sum_brute(a: list, K: int) -> int:
    n = len(a)

    if K < 1 or K > n:
        print("  [ERROR] K must satisfy 1 <= K <= n")
        return None

    max_sum = float('-inf')

    for start in range(n - K + 1):
        window_sum = 0
        for j in range(start, start + K):
            window_sum += a[j]
        if window_sum > max_sum:
            max_sum = window_sum

    return max_sum


def max_sum_sliding(a: list, K: int, trace: bool = False) -> int:
    n = len(a)

    if K < 1 or K > n:
        print("  [ERROR] K must satisfy 1 <= K <= n")
        return None

    window_sum = sum(a[:K])
    max_sum    = window_sum

    if trace:
        print(f"\n    Fixed-Window Trace (K={K}):")
        print(f"    {'-' * 64}")
        print(f"    {'Left':<6}{'Right':<7}{'Window':<24}{'CurSum':<8}MaxSum")
        print(f"    {'-' * 64}")
        win = "[" + ",".join(str(a[i]) for i in range(K)) + "]"
        print(f"    {'0':<6}{K-1:<7}{win:<24}{window_sum:<8}{max_sum}  (initial)")

    for right in range(K, n):
        left        = right - K
        window_sum += a[right]
        window_sum -= a[left]
        if window_sum > max_sum:
            max_sum = window_sum

        if trace:
            win = "[" + ",".join(str(a[i]) for i in range(left + 1, right + 1)) + "]"
            print(f"    {left+1:<6}{right:<7}{win:<24}{window_sum:<8}{max_sum}")

    if trace:
        print(f"    {'-' * 64}")

    return max_sum


def longest_at_most_k_brute(a: list, K: int) -> tuple:
    n = len(a)
    best_len, best_lo, best_hi = 0, -1, -1

    for i in range(n):
        run_sum = 0
        for j in range(i, n):
            run_sum += a[j]
            if run_sum > K:
                break
            length = j - i + 1
            if length > best_len:
                best_len, best_lo, best_hi = length, i, j

    return best_len, best_lo, best_hi


def longest_at_most_k_sliding(a: list, K: int, trace: bool = False) -> tuple:
    n = len(a)
    best_len, best_lo, best_hi = 0, -1, -1

    left = 0
    cur_sum = 0

    if trace:
        print(f"\n    Variable-Window Trace:")
        print(f"    {'-' * 68}")
        print(f"    {'Right':<7}{'Left':<6}{'Sum':<6}{'Action':<26}{'CurLen':<8}BestLen")
        print(f"    {'-' * 68}")

    for right in range(n):
        cur_sum += a[right]

        if trace:
            print(f"    {right:<7}{left:<6}{cur_sum:<6}"
                  f"Expand +a[{right}]={a[right]}")

        while cur_sum > K:
            if trace:
                print(f"    {'':7}{left:<6}{cur_sum:<6}"
                      f"Shrink -a[{left}]={a[left]}")
            cur_sum -= a[left]
            left += 1

        cur_len = right - left + 1
        if cur_len > best_len:
            best_len, best_lo, best_hi = cur_len, left, right

        if trace:
            window_label = f"Valid [{left}..{right}]"
            print(f"    {'':7}{left:<6}{cur_sum:<6}"
                  f"{window_label:<26}{cur_len:<8}{best_len}")

    if trace:
        print(f"    {'-' * 68}")

    return best_len, best_lo, best_hi


def print_part_a(label: str, result: int, K: int):
    padding = " " * (22 - len(label))
    print(f"    [{label}]{padding}"
          f"Maximum sum of {K} consecutive elements = {result}")


def print_part_b(label: str, result: tuple):
    length, lo, hi = result
    padding = " " * (22 - len(label))
    if length == 0:
        print(f"    [{label}]{padding}"
              f"Length = 0  |  Indices = (-1, -1)  (no valid subarray)")
    else:
        print(f"    [{label}]{padding}"
              f"Length = {length}  |  Indices = ({lo}, {hi})")


def main():
    print("=" * 56)
    print(" DSA-II Assignment 2 | Python 3")
    print(" Sliding Window Technique")
    print("=" * 56)
    print()

    print("─" * 56)
    print(" PART A – Maximum Sum of K Consecutive Elements")
    print(" Complexity:")
    print("   Brute Force   : Time O(n*K)  |  Auxiliary Space O(1)")
    print("   Sliding Window: Time O(n)    |  Auxiliary Space O(1)")
    print("─" * 56)
    print()

    part_a_tests = [
        ([2, 1, 5, 1, 3, 2], 3),
        ([-4, -2, -7],       2),
        ([6],                1),
        ([1, 2, 3],          3),
    ]

    for idx, (arr, K) in enumerate(part_a_tests, start=1):
        print(f"  Test {idx}: Array = {list_to_str(arr)}  |  K = {K}")

        brute_ans   = max_sum_brute(arr, K)
        sliding_ans = max_sum_sliding(arr, K, trace=True)

        print_part_a("Brute Force",    brute_ans,   K)
        print_part_a("Sliding Window", sliding_ans, K)
        print()

    print("─" * 56)
    print(" PART B – Longest Subarray with Sum at Most K")
    print(" ASSUMPTION: All array elements >= 0, K >= 0.")
    print(" Complexity:")
    print("   Brute Force   : Time O(n^2)  |  Auxiliary Space O(1)")
    print("   Sliding Window: Time O(n)    |  Auxiliary Space O(1)")
    print("─" * 56)
    print()

    part_b_tests = [
        ([1, 2, 1, 1, 3], 4),
        ([5, 1, 1],        2),
        ([4, 2, 1],        0),
        ([0, 0, 1, 0],     1),
    ]

    for idx, (arr, K) in enumerate(part_b_tests, start=1):
        print(f"  Test {idx}: Array = {list_to_str(arr)}  |  K = {K}")

        brute_res   = longest_at_most_k_brute(arr, K)
        sliding_res = longest_at_most_k_sliding(arr, K, trace=True)

        print_part_b("Brute Force",    brute_res)
        print_part_b("Sliding Window", sliding_res)
        print()

    print("=" * 56)
    print(" All test cases completed.")
    print("=" * 56)


if __name__ == "__main__":
    main()
