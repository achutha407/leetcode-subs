import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int log = 1;
        while ((1 << log) <= n) {
            log++;
        }

        int[] parent = new int[n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] iter = new int[n];
        int[] stack = new int[n];

        Arrays.fill(parent, -1);

        int timer = 0;
        int top = 0;
        stack[top++] = 0;

        while (top > 0) {
            int u = stack[top - 1];

            if (iter[u] == 0) {
                tin[u] = timer++;
            }

            if (iter[u] < adj[u].size()) {
                int v = adj[u].get(iter[u]++);
                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            } else {
                tout[u] = timer;
                top--;
            }
        }

        int[][] up = new int[log][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i] == -1 ? 0 : parent[i];
        }

        for (int k = 1; k < log; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        Fenwick fenwick = new Fenwick(n + 1);
        int[] masks = new int[n];

        for (int i = 0; i < n; i++) {
            masks[i] = 1 << (s.charAt(i) - 'a');
            fenwick.rangeXor(tin[i], tout[i], masks[i]);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            int u = Integer.parseInt(parts[1]);

            if (parts[0].equals("update")) {
                int newMask = 1 << (parts[2].charAt(0) - 'a');
                int delta = masks[u] ^ newMask;

                fenwick.rangeXor(tin[u], tout[u], delta);
                masks[u] = newMask;
            } else {
                int v = Integer.parseInt(parts[2]);
                int lca = getLca(u, v, depth, up);

                int pathMask = fenwick.query(tin[u])
                             ^ fenwick.query(tin[v])
                             ^ masks[lca];

                answer.add(Integer.bitCount(pathMask) <= 1);
            }
        }

        return answer;
    }

    private int getLca(int u, int v, int[] depth, int[][] up) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < up.length; k++) {
            if (((diff >> k) & 1) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int k = up.length - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }

    static class Fenwick {
        int[] bit;
        int n;

        Fenwick(int n) {
            this.n = n;
            bit = new int[n + 1];
        }

        void update(int index, int value) {
            for (int i = index + 1; i <= n; i += i & -i) {
                bit[i] ^= value;
            }
        }

        void rangeXor(int left, int right, int value) {
            update(left, value);
            update(right, value);
        }

        int query(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= bit[i];
            }

            return result;
        }
    }
}