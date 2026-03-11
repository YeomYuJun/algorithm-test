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
        
        // str = "2\r\n" + //
        //                 "Az\r\n" + //
        //                 "aZ";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) {
        int N = Integer.parseInt(args[0]);

        int[][] graph = new int[N][N];
        int totalSum = 0;

        for (int i = 0; i < N; i++) {
            String row = args[i + 1];
            for (int j = 0; j < N; j++) {
                char c = row.charAt(j);
                if (c == '0') continue;
                int weight = (c >= 'a') ? c - 'a' + 1 : c - 'A' + 27;
                graph[i][j] = weight;
                totalSum += weight; // 대각선 포함, (i,j)/(j,i) 모두 합산
            }
        }

        int[] minEdge = new int[N];
        boolean[] inMST = new boolean[N];
        Arrays.fill(minEdge, Integer.MAX_VALUE);
        minEdge[0] = 0;

        int mstSum = 0;
        int connectedCount = 0;

        for (int iter = 0; iter < N; iter++) {
            int u = -1;
            for (int v = 0; v < N; v++) {
                if (!inMST[v] && minEdge[v] != Integer.MAX_VALUE)
                    if (u == -1 || minEdge[v] < minEdge[u]) u = v;
            }
            if (u == -1) break;

            inMST[u] = true;
            mstSum += minEdge[u];
            connectedCount++;

            for (int v = 0; v < N; v++) {
                if (inMST[v]) continue;
                int edgeWeight = 0;
                if (graph[u][v] != 0 && graph[v][u] != 0)
                    edgeWeight = Math.min(graph[u][v], graph[v][u]);
                else if (graph[u][v] != 0) edgeWeight = graph[u][v];
                else if (graph[v][u] != 0) edgeWeight = graph[v][u];

                if (edgeWeight != 0)
                    minEdge[v] = Math.min(minEdge[v], edgeWeight);
            }
        }

        System.out.println(connectedCount < N ? -1 : totalSum - mstSum);
    }
}

