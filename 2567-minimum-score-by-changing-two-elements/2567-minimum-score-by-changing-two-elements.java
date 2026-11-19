class Solution {
  public int minimizeSum(int[] nums) {
    Arrays.sort(nums);
    final int n = nums.length;
    final int highOfChangingTwoMins = nums[n - 1] - nums[2];
    final int highOfChangingTwoMaxs = nums[n - 3] - nums[0];
    final int highOfChangingMinAndMax = nums[n - 2] - nums[1];
    return Math.min(Math.min(highOfChangingTwoMins, highOfChangingTwoMaxs),
                    highOfChangingMinAndMax);
  }
}