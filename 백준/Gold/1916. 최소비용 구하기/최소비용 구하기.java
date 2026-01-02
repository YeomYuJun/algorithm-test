import java.io.*;
import java.util.*;

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
        
        ///////////////////////////////////////////
        
        // str = "5\r\n" + //
        //                 "8\r\n" + //
        //                 "1 2 2\r\n" + //
        //                 "1 3 3\r\n" + //
        //                 "1 4 1\r\n" + //
        //                 "1 5 10\r\n" + //
        //                 "2 4 2\r\n" + //
        //                 "3 4 1\r\n" + //
        //                 "3 5 1\r\n" + //
        //                 "4 5 3\r\n" + //
        //                 "1 5";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 도시의 수
        int M = Integer.parseInt(args[1]); // 버스의 수


        Map<Integer, List<int[]>> map = new HashMap<>();
        // int[][] costMap = new int[N+1][N+1];
        long[] cost = new long[N+1];

        for(int i=2; i<2+M; i++){
            String[] row = args[i].split(" ");

            int s = Integer.parseInt(row[0]);
            int e = Integer.parseInt(row[1]);
            int c = Integer.parseInt(row[2]);

            // costMap[s][e] = costMap[s][e] == 0 ? c : Math.min(costMap[s][e], c);
            // 행렬은 필요없을듯
            List<int[]> list = map.getOrDefault(s, new ArrayList<>());
            list.add(new int[]{e,c});
            map.put(s, list);
        }

        int ns = Integer.parseInt(args[M+2].split(" ")[0]);
        int ne = Integer.parseInt(args[M+2].split(" ")[1]);
        //ns ~ ne = N의 비용으로  

        PriorityQueue<long[]> q = new PriorityQueue<>((o1, o2) -> {
            return Long.compare(o1[1],o2[1]);
        });
        Arrays.fill(cost, Long.MAX_VALUE);
        
        q.offer(new long[]{ns,0});
        while(!q.isEmpty()){
            long[] now = q.poll();
            int pos = (int)now[0];
            long w = now[1];

            if(cost[pos] < w ) continue;

            List<int[]> list = map.get(pos);
            if(list == null || list.size() == 0 )  continue;

            for(int[] end : list){
                if(cost[end[0]] <= w+end[1]){ // 비용 더 크면 굳이 가지 않기.
                    continue;
                }else{
                    cost[end[0]] = Math.min(cost[end[0]], w+end[1]);
                    q.add(new long[]{end[0], w+end[1]}); //도착지까지, 가중치를 더하며 이동
                }
            }
        }
        // System.out.println(Arrays.toString(cost));
        System.out.println(cost[ne]);
    }

}
 