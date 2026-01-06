import java.io.*;
import java.util.*;

public class Main {
    
    final static long MOD = 1_000_000_007L;
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
        
        // str = "UPWJCIRUCAXIIRGL\r\n" + //
        //                 "SBQNYBSBZDFNEV"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        String S = args[0]; // 첫번째 문자열
        String R = args[1]; // 두번째 문자열

        int sLen = S.length();
        int rLen = R.length();
        
        int[][] dp = new int[sLen][rLen];

        char[] sChar = S.toCharArray();
        char[] rChar = R.toCharArray();
        int max = 0;
        for(int i=0; i<sLen; i++){
            char s = sChar[i];
            for(int j=0; j<rLen; j++){
                char r = rChar[j];
                if(i>0 && j>0){
                    dp[i][j] += dp[i-1][j-1];
                }
                if(r == s){
                    dp[i][j] += 1;
                }else{
                    dp[i][j] = 0;
                }
                max = Math.max(max,dp[i][j]);
            }
        }
        System.out.println(max);
        // System.out.println(Arrays.toString(dp[sLen-1]));
        // System.out.println(Arrays.toString(dp[sLen-2]));
    }
}
 