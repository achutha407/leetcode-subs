class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int MAX = 2048;
        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int v : nums) {
            for (int k = 3; k >= 1; k--) {
                for (int x = 0; x < MAX; x++) {
                    if (dp[k - 1][x]) {
                        dp[k][x ^ v] = true;
                    }
                }
            }
        }

        int ans = 0;
        for (int x = 0; x < MAX; x++) {
            if (dp[1][x] || dp[3][x]) {
                ans++;
            }
        }
        return ans;
    }
}