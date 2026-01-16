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
        
        // str = "20 2"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N  = Integer.parseInt(args[0].split(" ")[0]);
        int K = Integer.parseInt(args[0].split(" ")[1]);
        

        //0~N까지 K개 더해서 합이 N이 되는 경우? 
        int[][] dp = new int[201][201];

        // int INF = 1_000_000_001;
        dp[0][0] = 1; 

        
        for(int i=1; i<=200; i++){
            // dp[i][0] = INF;  //0개로 N을 만드는 방법 따윈 존재하지않음
            dp[0][i] = 1; //0을 K개로 만드는 방법
        }
        int MOD = 1_000_000_000;
        for(int k = 1; k <= K; k++){
            for(int n = 0; n <= N; n++){
                dp[n][k] = 0;
                for(int i = 0; i <= n; i++){
                    dp[n][k] = (dp[n][k] + dp[n-i][k-1]) % MOD;
                }
            }
        }

        System.out.println(dp[N][K]);

    }
    /**
     * 0부터 3까지 만드는 경우  
     * 3을 만드는 조합
     * 2개
     * K에 따른 조합 변화 
     * ?
     * dp[k] = K개를 더해서 만드는 경우 ?
     * 
     * dp[p] = 100일 때 
     * dp[p+1] = 조합 100개에 
     * 
     * dp[p] :
     * 1 2 3 ~ n
     * ...
     * ..
     * n ~ 3 2 1 
     * dp[N][K] : 
     * N을 K개 순열로 만드는 방법들?
     * dp[N-1][K-1]에서 1을 붙이고, 
     * dp[N-2][K-1]에서 2를 붙이고...
     * dp[1][K-1] ... 
     * 의 합
     * 
     * 즉
     * dp[N][K] = N을 K개 순열 만드는 방법?
     * dp[2][2] = dp[2][1] + dp[1][1] + dp[0][1] = ?
     */
}