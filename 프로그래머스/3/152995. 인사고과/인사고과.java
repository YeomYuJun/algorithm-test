import java.util.*;

class Solution {
    public int solution(int[][] scores) {
        int answer = 0;
        int[] inhoScore = new int[]{scores[0][0], scores[0][1]};
        
        
        Arrays.sort(scores, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o2[0] - o1[0];  // 근무태도 내림차순
            } else {
                return o1[1] - o2[1];  // 동료평가 오름차순
            }
        });
    
        int maxDP = Integer.MIN_VALUE;  // 지금까지 본 동료평가 최댓값
        int rank = 1;
        
        for (int[] score : scores) {
            // 앞에 근무태도 높으면서 동료평가도 높은 사람 있으면 탈락
            if (score[1] < maxDP) {
                if (Arrays.equals(score, inhoScore)) {
                    return -1;
                }
                // 탈락자는 rank 계산 제외
            } else {
                // 생존자
                maxDP = Math.max(maxDP, score[1]);
                
                if (score[0] + score[1] > (inhoScore[0] + inhoScore[1])) {
                    rank++;
                }
            }
        }
        answer = rank;
        return answer;

    }
    /**
     * pq로 담아서 정렬한다음 하나씩 뽑으면?
     * 안되네 max찾으려면 또 돌아야하니까
     * TreeSet으로 last? 도 중복일 수도 있으니까 안되고
     * 
     * sorting만을 한 다음에
     * for 반복하면서 2개가 동시에 큰 max값(마지막)을 구해놓고, 인호가 낮으면 떨구고
     * 아니면 생존자들 중에서 인호위치 구하면 될듯
     */
}