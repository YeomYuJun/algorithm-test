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
        
        // str = "2\r\n" + //
        //                 "3 3 1\r\n" + //
        //                 "1 2 2\r\n" + //
        //                 "1 3 4\r\n" + //
        //                 "2 3 1\r\n" + //
        //                 "3 1 3\r\n" + //
        //                 "3 2 1\r\n" + //
        //                 "1 2 3\r\n" + //
        //                 "2 3 4\r\n" + //
        //                 "3 1 8";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
    int TC = Integer.parseInt(args[0]); 
    int curIdx = 0; 
    int INF = 10_000_000;

        TESTCASE:
        for(int i=1; i<=TC; i++){
            int index = curIdx+1;
            String[] row = args[index].split(" ");
            
            int N = Integer.parseInt(row[0]);
            int M = Integer.parseInt(row[1]);
            int W = Integer.parseInt(row[2]);

            // 간선 리스트로 저장
            List<Edge> edges = new ArrayList<>();

            int sm = index+1;
            int em = index+M+1;

            // 도로 (양방향)
            for(int mIdx = sm; mIdx < em; mIdx++){
                String[] mRow = args[mIdx].split(" ");
                int S = Integer.parseInt(mRow[0]);
                int E = Integer.parseInt(mRow[1]);
                int T = Integer.parseInt(mRow[2]);

                edges.add(new Edge(S, E, T));
                edges.add(new Edge(E, S, T)); // 양방향
            }

            int sw = index+M+1;
            int ew = index+M+1+W;
            curIdx = ew-1;

            // 웜홀 (단방향, 음수)
            for(int wIdx = sw; wIdx<ew; wIdx++){
                String[] wRow = args[wIdx].split(" ");
                int S = Integer.parseInt(wRow[0]);
                int E = Integer.parseInt(wRow[1]);
                int T = Integer.parseInt(wRow[2]);
                
                edges.add(new Edge(S, E, -T)); // 웜홀은 음수
                
                if(S == E && T > 0){
                    System.out.println("YES");
                    continue TESTCASE;
                }
            }

            // 벨만-포드로 음수 사이클 체크
            if(hasNegativeCycle(N, edges)){
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    // 음수 체크
    public static boolean hasNegativeCycle(int N, List<Edge> edges){
        int[] dist = new int[N+1];
        Arrays.fill(dist, 0);
        dist[1] = 0; // 임의의 시작점
        
        // N-1번 반복으로 최단 거리 갱신
        for(int i=1; i<N; i++){
            for(Edge edge : edges){
                if(dist[edge.from] + edge.cost < dist[edge.to]){
                    dist[edge.to] = dist[edge.from] + edge.cost;
                }
            }
        }
        
        // N번째 반복: 음수 사이클 검출
        for(Edge edge : edges){
            if(dist[edge.from] + edge.cost < dist[edge.to]){
                return true; // 음수 사이클 존재
            }
        }
        
        return false;
    }

    public static class Edge {
        int from, to, cost;
        
        Edge(int from, int to, int cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    static final int INF = 10_000_000;


    /**
     * 웜홀이 음수니까 다익스트라는 안되고
     * 벨만? 플루이드?
     * S E 가 같은 정보 존재할 수 있는가? 가능은 해보임
     * 아무 노드나 한 개라도 과거 여행 가능하면 되는건지?
     * A To A로 원점회귀 사이클을 구하고 음수면 바로 패스하면 될듯 
     * 
     */
}
