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
        
        // str = "";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // 2~1000
        int M = Integer.parseInt(args[0].split(" ")[1]); // 1~1000

        //최대거리 = 10000, 최대 경유 = 999
        int INF = 987_654_321;
        int[][] dist = new int[N+1][N+1];
        
        for(int i=0; i<=N; i++){
            Arrays.fill(dist[i], INF);
        }

        for(int i=1; i<N;i++){
            String[] row = args[i].split(" ");

            int a = Integer.parseInt(row[0]);
            int b = Integer.parseInt(row[1]);
            int c = Integer.parseInt(row[2]);
            
            dist[a][b] = Math.min(c, dist[a][b]);
            dist[b][a] = Math.min(c, dist[b][a]);
        }

        //플로이드
        for(int k=1; k<=N; k++){
            for(int i=1; i<=N; i++){
                for(int j=1; j<=N; j++){
                    dist[i][j] = Math.min(dist[i][k] + dist[k][j], dist[i][j]);
                }
            }
        }

        for(int i=N; i<N+M; i++){
            int from = Integer.parseInt(args[i].split(" ")[0]); 
            int to = Integer.parseInt(args[i].split(" ")[1]);
            if(from == to){
                System.out.println(0);
            }else{
                System.out.println(dist[from][to]);
            }
            
        }
    }
}
