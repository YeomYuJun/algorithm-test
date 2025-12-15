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

        // str = "3\r\n" + 
        //       "0 1 0\r\n" +
        //       "0 0 1\r\n" +
        //       "1 0 0\r\n";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        
        // 인접 행렬 입력
        int[][] graph = new int[n][n];
        for(int i = 0; i < n; i++){
            String[] row = args[i + 1].split(" ");
            for(int j = 0; j < n; j++){
                graph[i][j] = Integer.parseInt(row[j]);
            }
        }
        
        // 플로이드-워셜: k를 거쳐가는 경로 탐색
        for(int k = 0; k < n; k++){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    // i에서 k로 갈 수 있고, k에서 j로 갈 수 있으면
                    // i에서 j로 갈 수 있음
                    if(graph[i][k] == 1 && graph[k][j] == 1){
                        graph[i][j] = 1;
                    }
                }
            }
        }
        
        // 결과 출력
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                sb.append(graph[i][j]);
                if(j < n - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        
        System.out.print(sb.toString());
    }
}