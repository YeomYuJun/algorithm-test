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

        // str = "5\r\n" + //
        //                 "7\r\n" + //
        //                 "3 8\r\n" + //
        //                 "8 1 0\r\n" + //
        //                 "2 7 4 4\r\n" + //
        //                 "4 5 2 6 5";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        int[][] dp = new int[501][501];
        dp[1][0] = Integer.parseInt(args[1]);
        if(n == 1){
            System.out.println(dp[1][0]);
            return;
        }
        int max = Integer.MIN_VALUE;
        for(int i=2; i<=n; i++){
            String[] cols = args[i].split(" ");
            for(int j=0; j<cols.length; j++){
                int col = Integer.parseInt(cols[j]);
                
                int left = j == 0 ?  0 : j-1 ;
                int right = j == 0 ? 0 : j;
                dp[i][j] = Math.max(col +  dp[i-1][left], col + dp[i-1][right]);
                if(i==n){
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        
        System.out.println(max);
    }
}

