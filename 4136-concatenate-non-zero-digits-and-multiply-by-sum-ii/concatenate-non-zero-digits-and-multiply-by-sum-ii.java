class Solution {
    static final long MOD = 1_000_000_007L;

    static class Node {
        long val;
        long sum;
        int cnt;

        Node(long val, long sum, int cnt) {
            this.val = val;
            this.sum = sum;
            this.cnt = cnt;
        }
    }

    Node[] tree;
    long[] pow;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();

        pow = new long[n + 1];
        pow[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow[i] = (pow[i - 1] * 10) % MOD;
        }

        tree = new Node[4 * n];
        build(s, 0, 0, n - 1);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            Node res = query(0, 0, n - 1, queries[i][0], queries[i][1]);
            ans[i] = (int) ((res.val * res.sum) % MOD);
        }

        return ans;
    }

    void build(String s, int idx, int l, int r) {
        if (l == r) {
            int d = s.charAt(l) - '0';
            if (d == 0) {
                tree[idx] = new Node(0, 0, 0);
            } else {
                tree[idx] = new Node(d, d, 1);
            }
            return;
        }

        int mid = (l + r) / 2;
        build(s, idx * 2 + 1, l, mid);
        build(s, idx * 2 + 2, mid + 1, r);

        tree[idx] = merge(tree[idx * 2 + 1], tree[idx * 2 + 2]);
    }

    Node query(int idx, int l, int r, int ql, int qr) {
        if (qr < l || r < ql) {
            return new Node(0, 0, 0);
        }

        if (ql <= l && r <= qr) {
            return tree[idx];
        }

        int mid = (l + r) / 2;
        Node left = query(idx * 2 + 1, l, mid, ql, qr);
        Node right = query(idx * 2 + 2, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    Node merge(Node a, Node b) {
        long val = (a.val * pow[b.cnt] + b.val) % MOD;
        long sum = (a.sum + b.sum) % MOD;
        int cnt = a.cnt + b.cnt;

        return new Node(val, sum, cnt);
    }
}