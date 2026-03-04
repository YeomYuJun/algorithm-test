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
        
        // str = "10\r\n" + //
        //                 "9\r\n" + //
        //                 "1 2 0\r\n" + //
        //                 "2 3 0\r\n" + //
        //                 "3 4 0\r\n" + //
        //                 "4 5 0\r\n" + //
        //                 "5 6 0\r\n" + //
        //                 "6 7 0\r\n" + //
        //                 "7 8 0\r\n" + //
        //                 "8 9 0\r\n" + //
        //                 "9 10 0\r\n" + //
        //                 "1 10";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);
        int start = Integer.parseInt(args[M+2].split(" ")[0]);
        int goal = Integer.parseInt(args[M+2].split(" ")[1]);

        Map<Integer, List<int[]>> busInfos = new HashMap<>();
        
        for(int i=2; i<M+2; i++){
            String[] row = args[i].split(" ");

            int from = Integer.parseInt(row[0]);
            int to = Integer.parseInt(row[1]);
            int cost = Integer.parseInt(row[2]);

            List<int[]> info = busInfos.getOrDefault(from, new ArrayList<>());
            info.add(new int[]{to, cost});
            busInfos.put(from, info);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            return o1[1] - o2[1];
        });        
        int[] startNode = new int[]{start, 0, start};//현 위치, 현재 코스트, 이전 from(시작은 ==)

        int[] visitCost = new int[N+1];
        int INF = 1_000_000_000;
        Arrays.fill(visitCost, INF);
        visitCost[start] = 0;

        Map<Integer, Integer> fromMap = new HashMap<>();
        pq.add(startNode);
        //딱 이전 from만 가지고, pq poll 할 때 to from을 담으면?  map.get(goal).

        int[] result = null;
        while(!pq.isEmpty()){
            int[] curNode = pq.poll();
            int curPos = curNode[0];
            int curCost = curNode[1];
            int prevPos = curNode[2];

            
            if(curPos == goal){
                result = curNode.clone();
                break;
            }
            if(visitCost[curPos]<curCost) continue;

            List<int[]> ableDestination = busInfos.get(curNode[0]);
            if(ableDestination == null || ableDestination.size() == 0) continue;
            
            for(int i=0; i<ableDestination.size(); i++){
                int[] toInfo = ableDestination.get(i);
                int to = toInfo[0];
                int cost = toInfo[1];
                if(visitCost[to] <= curCost+cost) continue; //해당 비용으로 방문할 이유가 없는 경우
                visitCost[to] = curCost+cost; //해당 비용으로 방문처리
                fromMap.put(to, curPos);//k v(k) v(k) v(k) ... v(k) == v 가 되는 순간 = 경로. 
                //방문 이력을 pq에 담아다닌다.
                pq.add(new int[]{to, curCost+cost, curPos});
            }
        }
        if(result == null){ //못찾음
            System.err.println("error");
        }else{
            int minCost = result[1];
            
            int key = goal;
            StringBuilder sb = new StringBuilder();
            int size = 1;
            Stack<Integer> stack = new Stack<>();
            while(key != start){//
                stack.add(key);
                key = fromMap.get(key);
                size++;
            }
            stack.add(key);
            while(!stack.isEmpty()){
                sb.append(stack.pop()).append(" ");
            }

            System.out.println(minCost);
            System.out.println(size);
            System.out.println(sb.toString().trim());
            //아 reverse 10 -> 01..

        }
    }

    /**
     * from > to , to > from 의 순환 경로가 있을 경우.
     */
}

