import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        ////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        } 
        str = mainSb.toString();
            
        // ////////////////////////////////////// /////
        
        // str = "";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); 
        int M = Integer.parseInt(args[0].split(" ")[1]); 
        int K = Integer.parseInt(args[0].split(" ")[2]); 
        //n개의 노드, m개의 간선, 출력은 각 노드별 k번째 최단거리
        
        Map<Integer, List<int[]>> map = new HashMap<>();
        for(int i=0; i<M; i++){
            String[] row = args[i+1].split(" ");

            int from = Integer.parseInt(row[0]);
            int to = Integer.parseInt(row[1]);
            int cost = Integer.parseInt(row[2]);

            List<int[]> nodeList = map.getOrDefault(from, new ArrayList<>());
            nodeList.add(new int[]{to, cost});//도착지, 비용
            map.put(from, nodeList);
            // arr[from][to] = cost;

        }
        
        int[] count = new int[N+1];
        int[] kCost = new int[N+1];
        Arrays.fill(kCost, -1);
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            return o1[1] - o2[1];
        });

        int[] pos = {1,0};
        pq.add(pos);
        
        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int curNode = cur[0];
            if(kCost[curNode] != -1) continue; 
            count[curNode]++; //방문.
            int curCost = cur[1];
            if(count[curNode] == K){ //k번째 방문 완료
                kCost[curNode] = curCost;
            }
            List<int[]> arrival = map.getOrDefault(curNode, new ArrayList<>());
            if(arrival.size() == 0) continue;
            
            for(int[] to : arrival){
                if(kCost[to[0]] != -1) continue; //-1이 아니라면, k번째에 방문해봤으니 또 갈 필요 없음.
                int[] next = new int[]{to[0], curCost+to[1]};
                pq.add(next);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i=1; i<=N; i++){
            int val = kCost[i];
            sb.append(val).append("\n");
        }
        System.out.println(sb.toString().trim());
    }
    /**
     * n, 노드 최대 1000개
     * m, 간선 0~25만개
     * k, 1~100
     * 1 to j 로 가는데에 cost List가 필요함. 정렬이 되면 좋을듯
     * 
     * 다익스트라 = 최단거리만으로 움직이니까 안될 거 같고
     * 아니지. 각 노드의 방문 횟수를 카운트하면, 해당 카운트가 사실상 최단 k번째니까 
     * 다익스트라지
     * 
     *  
     */
}

