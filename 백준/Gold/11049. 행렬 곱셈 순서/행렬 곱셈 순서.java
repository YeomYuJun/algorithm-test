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
        
        // str = "2\r\n" + //
        //                 "2 1\r\n" + //
        //                 "1 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        int[][] matrix = new int[N][2];
        
        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" "); 

            int r = Integer.parseInt(row[0]);
            int c = Integer.parseInt(row[1]);
            matrix[i-1] = new int[]{r,c};
        }

        
        int[][] dp = new int[N][N];

        dp[0][0] = 0;
        if(N==1){ 
            System.out.println(0);
            return;
        }
        // dp[0][1] = matrix[0][0] * matrix[1][0] * matrix[1][1];
        // dp[0][2] = matrix[0][0] * matrix[2][0] * matrix[2][1];

        // i~j 최소비용 dp로 
        for(int len = 2; len <= N; len++) {           // 구간 길이
            for(int i = 0; i <= N - len; i++) {       // 시작점
                int j = i + len - 1;                   // 끝점
                dp[i][j] = Integer.MAX_VALUE;
                
                for(int k = i; k < j; k++) {           // 분할점
                    int cost = dp[i][k] + dp[k+1][j] 
                            + matrix[i][0] * matrix[k][1] * matrix[j][1];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        System.out.println(dp[0][N-1]);
    }

    /**
     * 현재 연산이 이전 연산 +-로 1차로 되나? 
     * AB + C 든 A + BC 든 D 입장에서는 C와의 연산이고
     * D의 입장에서는 AB + C를 한 P,  A+BC를 한 Q가 있고
     * AB + CD를하는 경우?
     * 정리하면
     * AB + C + D => (ABC)까지의 최적 연산 후 D와의 연산
     * A + BC + D => (ABC)까지의 최적 연산 후 D와의 연산
     * AB + CD => (ABC)까지의 연산이 아닌, (AB)까지의 최적 연산을 확인 후, C와 D 연산 수행 후, (AB)(CD)연산 수행
     * 아 안되네. 2차 가야겠네
     * 중간중간 연산들 전부 DP에 저장해야하겠네, 
     * 
     * A~E 경우 A + BC + DE 등 중간 곱을 언제 하느냐에 따라 다르네..
     * 그러면 2차원 DP로 두고, A(BC)(DE) (AB)C(DE) (AB)(CD)E ((((AB)C)D)E) (A(BC))(DE) 등등...
     * 이어서 곱하기, 먼저 곱하기의 우선순위 = 내 이전 [i-1][0] 이 [0][0]보다 큰가?
     * ABC 로 보면 a[0]*a[1]*b[1] + a[0]*b[1]*c[1]  
     * A_BC       a[1]*b[1]*c[1] + a[0]*a[1]*c[1]    x[0] = x-1[1] 로 전환가능(X>0)
     * 
     * d[][]로 dp[i][j]는 i번째와 j번째의 행렬곱 연산 최소비용? 
     * i~j까지의 곱 = matrix[i][0] * matrix[j][0] * matrix[j][1] 이니까 
     * i~j-1까지의 곱연산 결과 =  matrix[i][0] * matrix[j-1][0] * matrix[j][1]
     * 
     * 
     */


}