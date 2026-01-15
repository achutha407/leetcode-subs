import java.util.*;

class Solution {
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int gap = Math.min(maxContinuousGap(hBars), maxContinuousGap(vBars));
        return gap * gap;
    }

    private int maxContinuousGap(int[] bars) {
        Arrays.sort(bars);
        int res = 2;
        int runningGap = 2;

        for (int i = 1; i < bars.length; i++) {
            if (bars[i] == bars[i - 1] + 1) {
                runningGap++;
            } else {
                runningGap = 2;
            }
            res = Math.max(res, runningGap);
        }
        return res;
    }
}
