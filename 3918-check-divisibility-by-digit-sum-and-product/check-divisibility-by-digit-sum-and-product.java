class Solution {
    /**
     * Checks if a number is divisible by the sum of (digit sum + digit product)
     * @param n the number to check
     * @return true if n is divisible by (sum of digits + product of digits), false otherwise
     */
    public boolean checkDivisibility(int n) {
        int digitSum = 0;      
        int digitProduct = 1;  int number = n;        // Copy of n for digit extraction
      
        while (number != 0) {
            int currentDigit = number % 10;  // Get the last digit
            number /= 10;                     // Remove the last digit
          
            digitSum += currentDigit;        // Add digit to sum
            digitProduct *= currentDigit;    // Multiply digit to product
        }
      
        return n % (digitSum + digitProduct) == 0;
    }
}
