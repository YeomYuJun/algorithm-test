import java.util.*;
class Solution {
    public long solution(int[] weights) {
        long answer = 0;
        
        Map<Integer, Integer> wMap = new HashMap<>();
    
        for (int w : weights) {
            wMap.put(w, wMap.getOrDefault(w, 0) + 1);
        }
        List<Integer> wList = new ArrayList<>(wMap.keySet());
        //전부 달라도 901개 밖에 안되긴해 최악 901*900
        for (int i=0; i<wList.size(); i++) {
            int w1 = wList.get(i);
            int c1 = wMap.get(w1);
            answer += (long) c1 * (c1 - 1) / 2;

            for (int j = i + 1; j < wList.size(); j++) { 
                int w2 = wList.get(j);
                int c2 = wMap.get(w2);
                
                // w1*d1 == w2*d2 체크
                if (w1*2 == w2*2 || w1*2 == w2*3 || w1*2 == w2*4 ||
                    w1*3 == w2*2 || w1*3 == w2*3 || w1*3 == w2*4 ||
                    w1*4 == w2*2 || w1*4 == w2*3 || w1*4 == w2*4) {
                    //조합이 되는데, 그 w1,w2개수만큼 사람의 조합이 가능
                    answer += (long) c1 * c2;
                }
            }
        }

        return answer;
    }
    
}