import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        // ////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "ABCDFGH\r\n" + // ACDFGH
        //                 "ACDEFGHBDEFG";//

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        String[] str = args[0].split("");
        String[] ano = args[1].split("");
        //A-Z로 구성, 최대 1000글자

        int[][] dp = new int[str.length+1][ano.length+1];

        for(int i=1; i<=str.length; i++){
            //0 ~ i 까지의
            for(int j=1; j<=ano.length; j++){

                if (str[i-1].equals(ano[j-1])){
                    dp[i][j] = dp[i-1][j-1] + 1;  // 둘 다 매칭 → 이전 LCS + 1
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);  // 하나 제외한 최댓값
                }
            }
        }
        System.out.println(dp[str.length][ano.length]);
    }
    /**
     * lcs 
     * 최장공통부분수열 
     * Java 11: 0.4 초 제한 
     * str = "ABCDFGH\r\n" + // ACDFGH
            "ACDEFGHBDEFG";//
            ACDFGH가 나와야함
     */
}
