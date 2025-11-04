import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
    
        List<int[]> list = Arrays.stream(targets).sorted((o1, o2) -> o2[0] - o1[0]).collect(Collectors.toList());


        int point = 0;
        int last = list.size()-1;
        for(int i=0; i<list.size(); i++){
            int[] THAAD = list.get(i);
            
            if(i == last){
                if(point < THAAD[1]){
                    answer++;
                }
                break;
            }
            int[] nextTHAAD = list.get(i+1);

            point = Math.max(point, THAAD[0]);
            
            if(point >= nextTHAAD[1]){ //안 닿는 곳이면
                point=0;//초기화
                answer++;
            }else{
                if(THAAD[0] < point && THAAD[1] > point){ //겹침
                    continue;
                }
            }
        }
        return answer;
    }
    /**
     *  
     * 1~ 40
     *  30 ~   50
     *    41  ~  60
     *       45 ~ 70
     * 의 경우 앞에서 만나면
     * 1~29 = 1
     * 30~39 = 2
     * 40~41 = 2
     * 41~44 = 2
     * 45~50 = 3 v 
     * 50~60 = 2
     * 61~70 = 1
     * 
     * asc = 1이 생략 
     * desc = 가능45 ->  1~40만 남음. 
     * 흠..desc로 그리디가 되나?
     * 그럼 desc로 [0]을 기준으로 정렬?
     */
}