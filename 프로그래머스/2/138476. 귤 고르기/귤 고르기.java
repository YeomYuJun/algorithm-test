import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public int solution(int k, int[] tangerine) {
        int answer = 0;
        Map<Integer,Integer> gyulMap = new HashMap<>();
        
        for(int i=0; i<tangerine.length; i++){
            gyulMap.put(tangerine[i], gyulMap.getOrDefault(tangerine[i], 0) + 1);
        }

        Map<Integer, Integer> sortedGyul = gyulMap.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new));

            
        int tempK = k;
        for(Integer gyul : sortedGyul.keySet()){
            if(tempK == 0){ // 해결
                break;
            }else if( tempK < sortedGyul.get(gyul)){ //최적 다음 나머지 남기는 조건 = 해결
                answer++;
                break;
            }
            if(tempK >= sortedGyul.get(gyul)){ //지속조건. 
                tempK -= sortedGyul.get(gyul);
                answer++;
            }
        }

        return answer;
    }

    /*
     * tangerine의 크기 1~ 1000만? (효율 최적화필요할듯)
     * 귤의 크기 Map<Integer,Ieteger> key 있으면 value++하고
     * value getOrDefault로 0? 
     * 근데 그러면 Map에서 빈도가 높은 순으로 정렬이 되어야하는데 정렬 후 
     * 그리디로 k 맞춰서 하면 될듯?
     */
}