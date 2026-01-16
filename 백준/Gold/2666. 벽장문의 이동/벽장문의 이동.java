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
        
        // str = "7\r\n" + //
        //                 "2 5\r\n" + //
        //                 "4\r\n" + //
        //                 "3\r\n" + //
        //                 "1\r\n" + //
        //                 "6\r\n" + //
        //                 "5"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        int d1 = Integer.parseInt(args[1].split(" ")[0]);
        int d2 = Integer.parseInt(args[1].split(" ")[1]);

        int M = Integer.parseInt(args[2]);
        int[] targets = new int[M];

        for(int i=0; i<M; i++){
            int d = Integer.parseInt(args[i+3]);
            targets[i] = d;
        }
        int INF = 1_000_000;
        // dp[i][left][right] = i번째까지 처리, 열린 벽장 left, right일 때 최소 이동
        // left < right 보장
        int[][][] dp = new int[M + 1][N + 1][N + 1];
        
        for(int i = 0; i <= M; i++){
            for(int j = 0; j <= N; j++){
                Arrays.fill(dp[i][j], INF);
            }
        }
        
        // 초기 상태
        if(d1 < d2){
            dp[0][d1][d2] = 0;
        } else {
            dp[0][d2][d1] = 0;
        }
        
        // DP 진행
        for(int i = 0; i < M; i++){
            int target = targets[i];
            
            for(int left = 1; left <= N; left++){
                for(int right = left + 1; right <= N; right++){
                    if(dp[i][left][right] == INF) continue;
                    
                    int current = dp[i][left][right];
                    
                    //왼쪽 문을 target으로 이동
                    int cost1 = Math.abs(left - target);
                    int newLeft1 = target;
                    int newRight1 = right;
                    if(newLeft1 > newRight1){
                        int tmp = newLeft1;
                        newLeft1 = newRight1;
                        newRight1 = tmp;
                    }
                    dp[i + 1][newLeft1][newRight1] = Math.min(
                        dp[i + 1][newLeft1][newRight1], 
                        current + cost1
                    );
                    
                    //오른쪽 문을 target으로 이동
                    int cost2 = Math.abs(right - target);
                    int newLeft2 = left;
                    int newRight2 = target;
                    if(newLeft2 > newRight2){
                        int tmp = newLeft2;
                        newLeft2 = newRight2;
                        newRight2 = tmp;
                    }
                    dp[i + 1][newLeft2][newRight2] = Math.min(
                        dp[i + 1][newLeft2][newRight2], 
                        current + cost2
                    );
                }
            }
        }
        
        // 최소값 찾기
        int answer = INF;
        for(int left = 1; left <= N; left++){
            for(int right = left + 1; right <= N; right++){
                answer = Math.min(answer, dp[M][left][right]);
            }
        }
        
        System.out.println(answer);
        
    }
    /**
     * dp로, 좌 이동, 우 이동을 담고? 
     * 
     * 2 4
     * 3    3 4 = 1 , 2 3 = 1
     * 4    3 4 = 0 , 2 4 = 1
     * 3    3 4 = 0 , 3 4 = 1
     * 1    1 4 = 2 , 1 4 = 2
     * 5    1 5 = 1 , 1 5 = 1
     * 
     * 
     */
}