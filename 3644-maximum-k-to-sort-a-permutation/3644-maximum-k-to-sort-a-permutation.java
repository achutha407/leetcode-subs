class Solution {
    public int sortPermutation(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        int pow2 = 1;
        while (pow2 < n) pow2 <<= 1;
        int max_k = pow2 - 1;
        int k = max_k;

        for (int i = 0; i < n; ++i) {
            if (nums[i] != i) k &= nums[i];
        }

        return (k == max_k) ? 0 : k;
    }
}