import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int N = n + 1;

        int[] sa = new int[N];
        int[] rank = new int[N];
        int[] temp = new int[N];
        int[] count = new int[Math.max(N, 100002)];

        for (int i = 0; i < N; i++) {
            sa[i] = i;
            count[i == n ? 0 : nums[i] + 1]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        int[] sorted = new int[N];

        for (int i = N - 1; i >= 0; i--) {
            int key = i == n ? 0 : nums[i] + 1;
            sorted[--count[key]] = i;
        }

        sa = sorted;

        int classes = 0;
        int prev = -1;

        for (int i = 0; i < N; i++) {
            int key = sa[i] == n ? 0 : nums[sa[i]] + 1;

            if (key != prev) {
                classes++;
                prev = key;
            }

            rank[sa[i]] = classes - 1;
        }

        for (int len = 1; len < N; len <<= 1) {
            for (int i = 0; i < N; i++) {
                temp[i] = sa[i] - len;
                if (temp[i] < 0) temp[i] += N;
            }

            Arrays.fill(count, 0, classes, 0);

            for (int i = 0; i < N; i++) {
                count[rank[temp[i]]]++;
            }

            for (int i = 1; i < classes; i++) {
                count[i] += count[i - 1];
            }

            for (int i = N - 1; i >= 0; i--) {
                int x = temp[i];
                sa[--count[rank[x]]] = x;
            }

            int[] newRank = new int[N];
            int newClasses = 1;

            newRank[sa[0]] = 0;

            for (int i = 1; i < N; i++) {
                int cur = sa[i];
                int p = sa[i - 1];

                if (rank[cur] != rank[p] ||
                    rank[(cur + len) % N] != rank[(p + len) % N]) {
                    newClasses++;
                }

                newRank[cur] = newClasses - 1;
            }

            rank = newRank;
            classes = newClasses;

            if (classes == N) break;
        }

        int[] suffix = new int[n];

        for (int i = 1; i < N; i++) {
            suffix[i - 1] = sa[i];
        }

        int[] pos = new int[n];

        for (int i = 0; i < n; i++) {
            pos[suffix[i]] = i;
        }

        int[] lcp = new int[n];
        int k = 0;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            if (r == 0) continue;

            int j = suffix[r - 1];

            while (i + k < n && j + k < n &&
                   nums[i + k] == nums[j + k]) {
                k++;
            }

            lcp[r] = k;

            if (k > 0) k--;
        }

        int ans = n;

        for (int i = 0; i < n; i++) {
            int index = suffix[i];

            int common = 0;

            if (i > 0) {
                common = Math.max(common, lcp[i]);
            }

            if (i < n - 1) {
                common = Math.max(common, lcp[i + 1]);
            }

            int length = common + 1;

            if (length <= n - index) {
                ans = Math.min(ans, length);
            }
        }

        return ans;
    }
}