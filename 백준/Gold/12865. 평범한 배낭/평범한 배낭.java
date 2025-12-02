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

        // str = "4 7\r\n" + //
        //                 "6 13\r\n" + //
        //                 "4 8\r\n" + //
        //                 "3 6\r\n" + //
        //                 "5 12";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException{
        int n = Integer.parseInt(args[0].split(" ")[0]); // len
        int k = Integer.parseInt(args[0].split(" ")[1]); // weight limit

        int[][] dp = new int[n+1][k+1];
        
        for(int i=1; i<=n; i++){
            int weight = Integer.parseInt(args[i].split(" ")[0]);
            int value = Integer.parseInt(args[i].split(" ")[1]);
            for(int w = 0; w<= k; w++){
                dp[i][w] = dp[i-1][w];
                if(weight <= w){
                    dp[i][w] = 
                    Math.max(
                        dp[i][w],
                        dp[i-1][(w-weight)] + value
                    );
                }
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(dp[n][k]+"\n");
        bw.flush();
        bw.close();
    }
    /**
     * 냅색이네
     */

}

