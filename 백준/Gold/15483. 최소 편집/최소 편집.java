import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
            
        // ///////////////////////////////////////////
        
        // str = "abcdyyzzed\r\n" + //
        //                 "bcdezzdyyzzed";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        String A = args[0];
        String B = args[1];
        
        if(A.equals(B)) { //조기 종료 조건
            System.out.println(0);
            return;
        }
        int m = A.length();
        int n = B.length();

        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) dp[i][0] = i; 
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    // 문자가 같으면 이전 상태 그대로
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    // 세 가지 연산 중 최소값
                    dp[i][j] = 1 + Math.min(
                        dp[i-1][j],      // A[i] 삭제
                        Math.min(
                            dp[i][j-1],  // B[j] 삽입
                            dp[i-1][j-1] // A[i]를 B[j]로 교체
                        )
                    );
                }
            }
        }
        
        System.out.println(dp[m][n]);

    }
}
