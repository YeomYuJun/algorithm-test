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
        
        // str = "abcd\r\n" + //
        //                 "acabcddbabcd"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        String S = args[0]; // 원본 문자열
        String R = args[1]; // 목표 문자열

        int sLen = S.length();
        int rLen = R.length();
        
        final int INF = 1000;
        
        // dp[i][j][0] = S의 i번째까지, R의 j번째까지 매칭시킨 최소 삽입 횟수
        // dp[i][j][1] = S의 i번째가 R의 j번째와 연속으로 매칭되는지 여부 (INF=끊김, 작은값=연속)
        int[][][] dp = new int[sLen + 1][rLen + 1][2];
        
        // 초기화
        dp[0][0][0] = 0;
        dp[0][0][1] = INF;
        
        for (int i = 1; i <= rLen; i++) {
            dp[0][i][0] = INF;
            dp[0][i][1] = 1; // 시작 부분은 연속으로 간주
        }
        
        for (int i = 1; i <= sLen; i++) {
            dp[i][0][0] = INF;
            dp[i][0][1] = INF;
        }
        
        // DP 계산
        for (int i = 0; i < sLen; i++) {
            for (int j = 0; j <= i; j++) { //닫지 않는 영역
                dp[i + 1][j][0] = dp[i + 1][j][1] = INF;
            }
            for (int j = i; j < rLen; j++) {
                if (S.charAt(i) == R.charAt(j)) {
                    // 매칭되는 경우
                    dp[i + 1][j + 1][0] = Math.min(dp[i][j][0], dp[i][j][1]);
                } else {
                    dp[i + 1][j + 1][0] = INF;
                }
                
                dp[i + 1][j + 1][1] = Math.min(dp[i + 1][j][0] + 1, dp[i + 1][j][1]);
            }
        }
        
        int result = Math.min(dp[sLen][rLen][0], dp[sLen][rLen][1]);
        System.out.println((result >= INF) ? -1 : result);
    }
    /**
     * 삽입 최소값 => dp가 끊어지는 타이밍
     * 증감 분석 => idx, 최대값 배열 확인  => 실패???????
     * lcs가 순서 무시하고 찾네.  단조 증가 확인해야함
     * 순서보장해도, 최장길이가 여럿일 경우 고려해야함 ?????????
     * 3차원dp로 해야할듯 
     */
}
