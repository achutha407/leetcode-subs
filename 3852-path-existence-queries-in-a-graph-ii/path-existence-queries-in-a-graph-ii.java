class Solution {
    public int[] pathExistenceQueries(
        int n,
        int[] nums,
        int maxDiff,
        int[][] queries
    ) {

        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        Arrays.sort(arr, (a, b) -> a[0] - b[0]);


        // original index -> sorted index
        int[] pos = new int[n];

        for (int i = 0; i < n; i++) {
            pos[arr[i][1]] = i;
        }


        // farthest reachable in one jump
        int[] reach = new int[n];

        int r = 0;

        for (int l = 0; l < n; l++) {

            while (r + 1 < n &&
                   arr[r + 1][0] - arr[l][0] <= maxDiff) {
                r++;
            }

            reach[l] = r;
        }


        int LOG = 17;

        int[][] up = new int[LOG][n];


        for (int i = 0; i < n; i++) {
            up[0][i] = reach[i];
        }


        for (int k = 1; k < LOG; k++) {

            for (int i = 0; i < n; i++) {

                up[k][i] =
                    up[k - 1][up[k - 1][i]];
            }
        }


        int[] ans = new int[queries.length];

        int idx = 0;


        for (int[] q : queries) {

            int left = pos[q[0]];
            int right = pos[q[1]];


            if (left > right) {
                int temp = left;
                left = right;
                right = temp;
            }


            if (left == right) {
                ans[idx++] = 0;
                continue;
            }


            int cur = left;
            int jumps = 0;


            for (int k = LOG - 1; k >= 0; k--) {

                if (up[k][cur] < right) {

                    cur = up[k][cur];

                    jumps += (1 << k);
                }
            }


            if (reach[cur] < right) {
                ans[idx++] = -1;
            } else {
                ans[idx++] = jumps + 1;
            }
        }


        return ans;
    }
}