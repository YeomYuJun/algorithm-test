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

        // str = "9";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        if(n==1){
            System.out.println(1);
            return;
        }
        int[] dp = new int[n+1];
        
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2; //1일 때 걸리네 아
        for(int i=3; i<=n; i++){
            dp[i] = (dp[i-1]+dp[i-2])%10007;
        }
        System.out.println(dp[n]);
    }
}