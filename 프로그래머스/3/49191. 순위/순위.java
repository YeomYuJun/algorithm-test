import java.util.*;
import java.util.stream.IntStream;
class Solution {
    public int solution(int n, int[][] results) {
        // 승패 관계를 모두 추적
        Map<Integer, Set<Integer>> wins = new HashMap<>();  // key가 이긴 선수들
        Map<Integer, Set<Integer>> loses = new HashMap<>(); // key가 진 선수들
        
        // 초기화
        for (int i = 1; i <= n; i++) {
            wins.put(i, new HashSet<>());
            loses.put(i, new HashSet<>());
        }
        
        //승패 관계
        for (int[] result : results) {
            wins.get(result[0]).add(result[1]);
            loses.get(result[1]).add(result[0]);
        }
        
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                // i가 k를 이기고, k가 j를 이기면 > i는 j도 이김
                for (int j : wins.get(k)) {
                    if (wins.get(i).contains(k)) {
                        // i가 k를 이김 && k가 j를 이김 > i는 j도 이김
                        wins.get(i).add(j);
                        loses.get(j).add(i);
                    }
                }
            }
        }
        
        //(이긴 선수 수 + 진 선수 수) == n-1 = 확정
        int answer = 0;
        for (int i = 1; i <= n; i++) {
            if (wins.get(i).size() + loses.get(i).size() == n-1) {
                answer++;
            }
        }
        
        return answer;
    }

    
    /**
     * answer = 순위의 갯수를 소거법으로 찾아가며
     * 최종 순위가 1개로 좁힌 선수의 수
     * 
     * 순위 기준
     *  [a,b]에서 a < b (a가 b를 이김. 순위는 낮음.)
     * 
     * 1~n까지의 선수중  hashmap으로 key=key가 이긴 선수 : value=[3,4] <Integer,List<Integer>> ? 
     *
     * 1이 진 선수 0;
     * 2가 진 선수 1,3,4 max=4, min =5
     * 2가 이긴 선수 5 = max=4, min=4
     * 5가 진 선수 = 4 ( 4의 min=4니까 max = 5 min=5 default)
     * => min,max가 일치하는 순간 = 확정
     * => 첫 순회에 이게 순서가 확정되지 않았다면 min,max가 맞지 않을텐데,
     * 그럼 진 선수가 이긴 선수가 있다면 초기 이긴 선수가 걔도 이길테고.. a<b<c ?
     * 플루이드-워셜 알고리즘 for 3중첩?
     * 
     */
}