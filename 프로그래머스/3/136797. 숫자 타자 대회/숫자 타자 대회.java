import java.util.Arrays;
class Solution {
    public int solution(String numbers) {
        int n = numbers.length();

        int[][] prev = new int[10][10];
        int[][] curr = new int[10][10];

        for (int i = 0; i < 10; i++) {
            Arrays.fill(prev[i], Integer.MAX_VALUE / 2);
            Arrays.fill(curr[i], Integer.MAX_VALUE / 2);
        }

        prev[4][6] = 0;

        for (int i = 0; i < n; i++) {
            int target = numbers.charAt(i) - '0';

            for (int j = 0; j < 10; j++) {
                Arrays.fill(curr[j], Integer.MAX_VALUE / 2);
            }

            // 도달 가능한 상태 순회
            for (int left = 0; left < 10; left++) {
                for (int right = 0; right < 10; right++) {
                    if (prev[left][right] >= Integer.MAX_VALUE / 2) continue;

                    int currentCost = prev[left][right];

                    // Case 1: 왼손으로 누름
                    if (right != target) {
                        int cost = currentCost + calcDistance(left, target);
                        curr[target][right] = Math.min(curr[target][right], cost);
                    }

                    // Case 2: 오른손으로 누름
                    if (left != target) {
                        int cost = currentCost + calcDistance(right, target);
                        curr[left][target] = Math.min(curr[left][target], cost);
                    }

                    // Case 3: 강제 선택
                    if (left == target) {
                        int cost = currentCost + 1;
                        curr[target][right] = Math.min(curr[target][right], cost);
                    }
                    if (right == target) {
                        int cost = currentCost + 1;
                        curr[left][target] = Math.min(curr[left][target], cost);
                    }
                }
            }

            int[][] temp = prev;
            prev = curr;
            curr = temp;
        }

        int result = Integer.MAX_VALUE;
        for (int left = 0; left < 10; left++) {
            for (int right = 0; right < 10; right++) {
                result = Math.min(result, prev[left][right]);
            }
        }

        return result;
    }
    private int calcDistance(int from, int to) {
        int[] fromCoor = numToCoor(from);
        int[] toCoor = numToCoor(to);

        int xGap = Math.abs(fromCoor[1] - toCoor[1]);
        int yGap = Math.abs(fromCoor[0] - toCoor[0]);

        if (xGap == 0 && yGap == 0) return 1;

        int diagonal = Math.min(xGap, yGap);
        int straight = xGap + yGap - 2 * diagonal;

        return diagonal * 3 + straight * 2;
    }

    private int[] numToCoor(int num) {
        if (num == 0) return new int[]{3, 1};
        return new int[]{(num - 1) / 3, (num - 1) % 3};
    }
}