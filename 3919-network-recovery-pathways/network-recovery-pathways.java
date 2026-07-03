import java.util.*;

class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        int high = 0;
        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int cost = e[2];

            graph[u].add(new int[]{v, cost});
            high = Math.max(high, cost);
        }

        int low = 0;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canReach(mid, graph, online, k)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    private boolean canReach(int score, List<int[]>[] graph, boolean[] online, long k) {
        int n = graph.length;

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a[1], b[1])
        );

        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();

            int node = (int) cur[0];
            long cost = cur[1];

            if (cost != dist[node]) continue;

            for (int[] next : graph[node]) {
                int v = next[0];
                int w = next[1];

                if (w < score) continue;

                if (v != n - 1 && !online[v]) continue;

                long newCost = cost + w;

                if (newCost < dist[v] && newCost <= k) {
                    dist[v] = newCost;
                    pq.offer(new long[]{v, newCost});
                }
            }
        }

        return dist[n - 1] <= k;
    }
}