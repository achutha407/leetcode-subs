class Solution {
    private int rows;
    private int cols;
    private int[][] grid;
  
    private final int[] directions = {-1, 0, 1, 0, -1};

    /**
     * Counts the number of islands whose sum of values is divisible by k
     * @param grid 2D array where positive values represent land
     * @param k divisor to check island sums against
     * @return number of islands with sum divisible by k
     */
    public int countIslands(int[][] grid, int k) {
        // Initialize grid dimensions
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = grid;
      
        int islandCount = 0;
      
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] > 0) {
                    long islandSum = dfs(row, col);
                    if (islandSum % k == 0) {
                        islandCount++;
                    }
                }
            }
        }
      
        return islandCount;
    }

    /**
     * Performs depth-first search to calculate sum of all connected land cells
     * Marks visited cells by setting them to 0
     * @param row current row position
     * @param col current column position
     * @return sum of all connected land cells forming an island
     */
    private long dfs(int row, int col) {
        long sum = grid[row][col];
      
        grid[row][col] = 0;
      
        for (int dir = 0; dir < 4; dir++) {
            int nextRow = row + directions[dir];
            int nextCol = col + directions[dir + 1];
          
            if (nextRow >= 0 && nextRow < rows && 
                nextCol >= 0 && nextCol < cols && 
                grid[nextRow][nextCol] > 0) {
                sum += dfs(nextRow, nextCol);
            }
        }
      
        return sum;
    }
}
