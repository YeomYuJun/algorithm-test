import java.util.PriorityQueue;
class Solution {
    public int solution(int n, int k, int[] enemy) {
        int answer = 0;
        int maxRound = enemy.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>(); //낮은 순부터 빼면 될 듯
        
        int pqMin = Integer.MAX_VALUE;
        if(k >= maxRound) {
            answer = maxRound;
            return answer;
        }
        for(int i=0; i<maxRound; i++){
            if(k>0){ //일단 queue 넣기
                pq.add(enemy[i]);
                pqMin = Math.min(pqMin, enemy[i]);
                k--;
                continue;
            }
            int cnt = enemy[i];
            int required = cnt;
            if(pqMin < cnt){
                pq.add(cnt);
                required = pq.poll();
            }
            
            if(n >= required){
                n -= required;
                if(i==maxRound-1){
                    answer = maxRound;
                }
            }else {
                answer = i;
                return answer;
            }
        }
        return answer;
    }
    
    /**
     * n이 목숨이라고 치면 
     * k번 목숨소모 없이 라운드 패스 가능 => 패스를 언제 쓰는게 최적인지가 관건
     * 패스는 도달할 수 있는 최대라운드중 가장 높은 순서대로 쓰는 게 최적
     * 그렇다면 최대라운드란? k를 길이로한 패스를 쓸 인덱스 배열을 두고 바꾸면서
     * 더 높은 enemy를 만나면 갭만큼 목숨 채워주면 될듯. > 그렇다면 우선순위큐?
     */
}