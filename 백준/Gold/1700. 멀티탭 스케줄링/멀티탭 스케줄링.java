import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        /////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "3 11 \r\n" + //
        //                 "11 8 11 7 2 8 2 7 5 10 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // 구멍 수 
        int K = Integer.parseInt(args[0].split(" ")[1]); // 전기용품 수
        String[] elecStr = args[1].split(" ");

        List<Integer> elecList = new ArrayList<>();
        Set<Integer> plugInList = new HashSet<>();
        
        Map<Integer,Queue<Integer>> indexMap = new HashMap<>();
        
        int dupCnt = 0;
        for(int i=0; i<K; i++){
            Integer id = Integer.parseInt(elecStr[i]);
            if(plugInList.size() < N){ //아직 들어갈 수 있음
                plugInList.add(id); 
                dupCnt++;
            }else{
                //이제 꼽지 못한 가전 사용 순서.
                elecList.add(id);
                
                Queue<Integer> q = indexMap.getOrDefault(id, new LinkedList<>());
                q.add(i-dupCnt);
                indexMap.put(id, q);
            } 
        }
        
        int answer = 0;
        for(int i=0; i<elecList.size(); i++){ //남은 수만큼 수행.
            int elecId = elecList.get(i);
            if(plugInList.contains(elecId)) continue; //포함되면 패스

            //pList중 다음이 제일 먼 id
            int maxId = maxRemain(plugInList, indexMap, i);
            Queue<Integer> selectedQ = indexMap.get(maxId);
            if(selectedQ != null) {
                while(!selectedQ.isEmpty() && selectedQ.peek() <= i) {
                    selectedQ.poll();
                }
            }
            plugInList.remove(maxId);
            plugInList.add(elecId);
            answer++;
        }
        System.out.println(answer);

    }
    
    public static int maxRemain(Set<Integer> pList, Map<Integer,Queue<Integer>> indexMap, int i){
        
        int maxId = -1;
        int maxIdx = 0;
        Iterator<Integer> it = pList.iterator();
        while(it.hasNext()){
            if(i==4){
                int a = 1;
            }
            int id = it.next();
            Queue<Integer> q1 = indexMap.getOrDefault(id, new LinkedList<>());
            
            Integer futureIdx = null;
            for(Integer idx : q1) {
                if(idx > i) {
                    futureIdx = idx;
                    break;
                }
            }
            
            if(futureIdx == null) {
                return id; // 다시 쓸 일 없음
            }
            
            if(maxIdx == -1 || futureIdx > maxIdx) {
                maxIdx = futureIdx;
                maxId = id;
            }

        }

        return maxId;
    }
    /**
     * 사용 횟수를 맵으로 담고, 현재 List에서 다음 사용횟수가 가장 낮은 가전제품을 빼면 될듯?
     * 꼽아져있는 List 는 Set? 
     * 근데 목록 중 사용횟수가 가장 적은 게 최적인가?
     * 가장 빠른 인덱스를 남겨놔야함.=> list중 가장 멀리 있는 친구 뽑기
     */
}