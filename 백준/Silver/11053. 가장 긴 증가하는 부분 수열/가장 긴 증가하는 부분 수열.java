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

        // str = "6\r\n" + //
        //                 "10 20 10 30 20 50";

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
        String[] arr = args[1].split(" ");

        int[] dp = new int[n+1];

        dp[0] = 0;
        dp[1] = 1;
        // 1 2 1 3 2 4 
        int ttm = Integer.MIN_VALUE;
        for(int i=2; i<=n; i++){
            int cur = Integer.parseInt(arr[i-1]);

            int cnt = 1;
            int curMax = cur;
            for(int j=i-1; j>0; j--){
                if(curMax > Integer.parseInt(arr[j-1])){
                    cnt = Math.max(1+dp[j], cnt);
                }
            }
            dp[i] = cnt;
            ttm = Math.max(cnt,ttm);
        }
        System.out.println(ttm);
    }
}