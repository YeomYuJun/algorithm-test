import java.util.*;
class Solution {
    public int[] solution(int[] sequence, int k) {
        int left = 0, right = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;
        int[] answer = new int[2];

        while (right < sequence.length) {
            // 오른쪽 포인터 확장
            sum += sequence[right];

            // sum이 k보다 클 때 왼쪽 포인터 축소
            while (sum > k && left <= right) {
                sum -= sequence[left];
                left++;
            }

            // 정확한 합을 찾았을 때
            if (sum == k) {
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    answer[0] = left;
                    answer[1] = right;
                }
            }

            right++;
        }

        return answer;
    }
}