import java.io.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Main {
    public static int PRE_MAX = Integer.MIN_VALUE;
    public static int PRE_MAX_IDX = Integer.MIN_VALUE;
    public static int MAX = Integer.MIN_VALUE;
    public static int MAX_IDX = Integer.MIN_VALUE;

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
        //                 "14\r\n" + //
        //                 "1 2 2\r\n" + //
        //                 "1 3 3\r\n" + //
        //                 "1 4 1\r\n" + //
        //                 "1 5 10\r\n" + //
        //                 "2 4 2\r\n" + //
        //                 "3 4 1\r\n" + //
        //                 "3 5 1\r\n" + //
        //                 "4 5 3\r\n" + //
        //                 "3 5 10\r\n" + //
        //                 "3 1 8\r\n" + //
        //                 "1 4 2\r\n" + //
        //                 "5 1 7\r\n" + //
        //                 "3 4 2\r\n" + //
        //                 "5 2 4";//

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]); //도시의 개수
        int m = Integer.parseInt(args[1]); //버스의 개수

        int[][] dist = new int[n+1][n+1];
        for(int i = 1; i <= n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }

        for(int i=2; i<m+2; i++){
            String[] row = args[i].split(" ");

            int cityA = Integer.parseInt(row[0]);
            int cityB = Integer.parseInt(row[1]);
            int cost = Integer.parseInt(row[2]);

            dist[cityA][cityB] = Math.min(dist[cityA][cityB], cost);
            //A to B의 cost가 여럿일 수 있음.   > != B to A 
        }

        for(int k = 1; k <= n; k++) {           // 경유지
            for(int i = 1; i <= n; i++) {       // 출발
                for(int j = 1; j <= n; j++) {   // 도착
                    if(dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        // 출력
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                if(dist[i][j] == Integer.MAX_VALUE) {
                    sb.append(0);
                } else {
                    sb.append(dist[i][j]);
                }
                if(j < n) sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
    /**
     * 플로이드 2차원 행렬에서
     * a ~ b  a-k-b 와 a-b사이의 최소값 구하면 
     */
}


