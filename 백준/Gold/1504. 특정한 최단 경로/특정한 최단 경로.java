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
            
        ///////////////////////////////////////////
        
        // str = "4 6\r\n" + //
        //                 "1 2 3\r\n" + //
        //                 "2 3 3\r\n" + //
        //                 "3 4 1\r\n" + //
        //                 "1 3 5\r\n" + //
        //                 "2 4 5\r\n" + //
        //                 "1 4 4\r\n" + //
        //                 "1 4"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int E = Integer.parseInt(args[0].split(" ")[1]);

        int v1 = Integer.parseInt(args[1+E].split(" ")[0]);
        int v2 = Integer.parseInt(args[1+E].split(" ")[1]);


        int INF = 100_000_000; // 987_654_321은 overflow 발생...

        Map<Integer,List<int[]>> map = new HashMap<>();

        for(int i=0; i<E; i++){
            String[] row = args[i+1].split(" ");
            int a = Integer.parseInt(row[0]); //
            int b = Integer.parseInt(row[1]);
            int c = Integer.parseInt(row[2]); // cost 

            List<int[]> aList = map.getOrDefault(a, new ArrayList<>());
            aList.add(new int[]{b,c});
            List<int[]> bList = map.getOrDefault(b, new ArrayList<>());
            bList.add(new int[]{a,c});
            map.put(a, aList);
            map.put(b, bList);
        }


        // 1 다익스트라
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            return o1[1] - o2[1];
        });

        int[] sejun = new int[]{1,0};
        pq.add(sejun);
        int[] visitCost = new int[N+1];
        Arrays.fill(visitCost, INF);
        visitCost[1] = 0;

        while(!pq.isEmpty()){
            int[] sj = pq.poll();
            int e = sj[0];
            int cost = sj[1];
            if(visitCost[e] < cost) continue;
            
            List<int[]> lines = map.getOrDefault(e, new ArrayList<>());
            if(lines.size()>0){
                for(int i=0; i<lines.size(); i++){
                    int[] next = lines.get(i);
                    int nc = next[1];
                    nc += cost;
                    if(visitCost[next[0]] > nc){
                        visitCost[next[0]] = nc;
                        int[] newSJ = new int[]{next[0], nc};
                        pq.add(newSJ);
                    }
                }
            }
        }


        //N 다익스트라 
        PriorityQueue<int[]> npq = new PriorityQueue<>((o1, o2) -> {
            return o1[1] - o2[1];
        });
        int[] nVisitCost = new int[N+1];
        Arrays.fill(nVisitCost, INF);
        nVisitCost[N] = 0;
        int[] sejunN = new int[]{N,0};
        npq.add(sejunN);
        while(!npq.isEmpty()){
            int[] sj = npq.poll();
            int e = sj[0];
            int cost = sj[1];
            if(nVisitCost[e] < cost) continue;
            
            List<int[]> lines = map.getOrDefault(e, new ArrayList<>());
            if(lines.size()>0){
                for(int i=0; i<lines.size(); i++){
                    int[] next = lines.get(i);

                    int nc = next[1];
                    nc += cost;
                    if(nVisitCost[next[0]] > nc){
                        nVisitCost[next[0]] = nc;
                        int[] newSJ = new int[]{next[0], nc};
                        npq.add(newSJ);
                    }
                }
            }
        }


        //v1 다익스트라
        PriorityQueue<int[]> vpq = new PriorityQueue<>((o1, o2) -> {
            return o1[1] - o2[1];
        });
        int[] vVisitCost = new int[N+1];
        Arrays.fill(vVisitCost, INF);
        vVisitCost[v1] = 0;
        int[] sejunV = new int[]{v1,0};
        vpq.add(sejunV);
        while(!vpq.isEmpty()){
            int[] sj = vpq.poll();
            int e = sj[0];
            int cost = sj[1];
            if(vVisitCost[e] < cost) continue;

            List<int[]> lines = map.getOrDefault(e, new ArrayList<>());
            if(lines.size()>0){
                for(int i=0; i<lines.size(); i++){
                    int[] next = lines.get(i);

                    int nc = next[1];
                    nc += cost;
                    if(vVisitCost[next[0]] > nc){
                        vVisitCost[next[0]] = nc;
                        int[] newSJ = new int[]{next[0], nc};
                        vpq.add(newSJ);
                    }
                }
            }
        }

        // System.out.println(Arrays.toString(visitCost));
        // System.out.println(Arrays.toString(nVisitCost));
        // System.out.println(Arrays.toString(vVisitCost));

        int route1 = visitCost[v1] + nVisitCost[v2];
        int route2 = visitCost[v2] + nVisitCost[v1];
        // if(visitCost[v1] == INF || nVisitCost[v2] )

        int result = Math.min(route1, route2) + vVisitCost[v2];
        if(result >= INF){
            System.out.println(-1);
        }else{
            System.out.println(result);
        }
        
        //min =  v1~v2이 연결되어 있을 때 , 1~v1 + v2~n 또는 1~v2 + v1~n이고
    }
    /**
     *  1 ~ v1
     *  1 ~ v2
     *  v1 ~ v2 
     *  v1 ~ N
     *  v2 ~ N
     * 보고 비교
     * 플로이드로 [][] 갱신만 다 하면 바로 나오긴 하는데 시간초과될 수도 있고 흠
     * 다익스트라 갈기고 찾으면..? N에 가깝게 다 찾아야하면 플로이드 쓰면 되긴할텐데
     */
}