import java.util.*;

class Solution {
    public Map<String,Integer> minePowerMap = new HashMap<>(){
        {

            put("diamond", 25);
            put("iron", 5);
            put("stone", 1);
        }
    };
    
    public int solution(int[] picks, String[] minerals) {
        int answer = 0;

        int diaPick = picks[0];
        int ironPick = picks[1];
        int stonePick = picks[2];
        int pickSum = diaPick + ironPick + stonePick;
        
        List<String[]> mineralList = new ArrayList<>();
        int max = Math.min((minerals.length + 4) / 5, pickSum); // 곡괭이 수만큼만 구간 생성

        for(int i = 0; i < max; i++){
            mineralList.add(Arrays.copyOfRange(minerals, (5*i), Math.min(5*(i+1), minerals.length)));
        }
        
        Map<Integer, List<Integer>> pirodoMap = new HashMap<>();
        for(int i = 0; i < mineralList.size(); i++){
            String[] mineralPiece = mineralList.get(i);
            //int tempPirodoDia = 5;
            int tempPirodoDia = mineralPiece.length;
            int tempPirodoIron = fivePickPirodo(1, mineralPiece);
            int tempPirodoStone = fivePickPirodo(2, mineralPiece);
            pirodoMap.put(i, new ArrayList<>(Arrays.asList(tempPirodoDia, tempPirodoIron, tempPirodoStone)));
        }

        int stoneSize = Math.min(mineralList.size() - ironPick - diaPick, stonePick);
        int ironSize = Math.min(mineralList.size() - diaPick, ironPick);
        int diaSize = diaPick;
        
        // 사용된 구간 추적용
        boolean[] used = new boolean[mineralList.size()];
        
        // 1. 스톤 곡괭이: get(2) 기준 오름차순으로 stoneSize만큼 선택
        List<Integer> indices = new ArrayList<>();
        for(int i = 0; i < mineralList.size(); i++) {
            indices.add(i);
        }
        
        indices.sort((a, b) -> pirodoMap.get(a).get(2) - pirodoMap.get(b).get(2));
        
        for(int i = 0; i < stoneSize && i < indices.size(); i++) {
            int idx = indices.get(i);
            answer += pirodoMap.get(idx).get(2);
            used[idx] = true;
        }
        
        // 2. 철 곡괭이: 스톤이 사용 안 된 구간 중 get(1) 기준 오름차순으로 ironSize만큼 선택
        indices.clear();
        for(int i = 0; i < mineralList.size(); i++) {
            if(!used[i]) indices.add(i);
        }
        
        indices.sort((a, b) -> pirodoMap.get(a).get(1) - pirodoMap.get(b).get(1));
        
        for(int i = 0; i < ironSize && i < indices.size(); i++) {
            int idx = indices.get(i);
            answer += pirodoMap.get(idx).get(1);
            used[idx] = true;
        }
        
        // 3. 다이아 곡괭이: 나머지 구간 (get(0)은 항상 5)
        for(int i = 0; i < mineralList.size(); i++) {
            if(!used[i]) {
                answer += pirodoMap.get(i).get(0);
            }
        }
        return answer;
    }

    public int fivePickPirodo(int type, String[] mine){
        int temp = 0;
        if(type == 0){ //다이아
            return mine.length;
        } else if(type == 1){
            for(int j = 0; j<mine.length; j++){
                temp += Math.max(minePowerMap.get(mine[j]) / 5,1) ;
            }
        } else if(type == 2){
            for(int j = 0; j<mine.length; j++){
                temp += minePowerMap.get(mine[j]);
            }
        }
        return temp;
    }
}