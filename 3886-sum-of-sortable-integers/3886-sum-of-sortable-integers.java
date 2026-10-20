class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] prefixMax = new int[n];
        prefixMax[0] = nums[0];

        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
        }

        int[] suffixMin = new int[n];
        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }

        int[] prefixDrops = new int[n];

        for (int i = 1; i < n; i++) {
            prefixDrops[i] = prefixDrops[i - 1]
                    + (nums[i - 1] > nums[i] ? 1 : 0);
        }

        int answer = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            if (isSortable(nums, n, k,
                    prefixMax, suffixMin, prefixDrops)) {
                answer += k;
            }
        }

        return answer;
    }

    private boolean isSortable(
            int[] nums,
            int n,
            int k,
            int[] prefixMax,
            int[] suffixMin,
            int[] prefixDrops) {

        for (int boundary = k; boundary < n; boundary += k) {
            if (prefixMax[boundary - 1] > suffixMin[boundary]) {
                return false;
            }
        }

        for (int start = 0; start < n; start += k) {
            int end = start + k - 1;

            int internalDrops =
                    prefixDrops[end] - prefixDrops[start];

            int circularDrop =
                    nums[end] > nums[start] ? 1 : 0;

            if (internalDrops + circularDrop > 1) {
                return false;
            }
        }

        return true;
    }
}