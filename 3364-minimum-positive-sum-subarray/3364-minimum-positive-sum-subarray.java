class Solution {
    
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        final int INF = Integer.MAX_VALUE;
        int minSum = INF;
      
        for (int start = 0; start < n; start++) {
            int currentSum = 0;
                      for (int end = start; end < n; end++) {
                currentSum += nums.get(end);
              
                int subarrayLength = end - start + 1;
        
                if (subarrayLength >= l && subarrayLength <= r && currentSum > 0) {
                    minSum = Math.min(minSum, currentSum);
                }
            }
        }
      

        return minSum == INF ? -1 : minSum;
    }
}
