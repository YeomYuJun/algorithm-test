import java.util.*;
class Solution {
    public int solution(int m, int n, int[][] puddles) {
        int answer = 0;
        //m = x
        //n = y

        //base
        int[][] dp = new int[n+1][m+1];
        
        dp[1][0] = 1; //dp[1][1] = 1이 되게끔.
        for(int[] p : puddles){
            dp[p[1]][p[0]] = -1;
        }
        for(int y=1; y<=n; y++){
            for(int x=1; x<=m; x++){
                if(dp[y][x] == -1){
                    dp[y][x] = 0; // 웅덩이의 경우 도달 불가능 
                }else{
                    dp[y][x] = (dp[y-1][x] + dp[y][x-1] ) % 1000000007;
                }
            }
        }

        answer = dp[n][m] % 1000000007;
        return answer;
    }
    /**
     * [n][m] 사이즈의 map;
     * 1,1 에서 m,n으로 우하향만 진행하면 됨.
     * 
     * puddles[i]의 {x,y}값의 좌표 {x-1,y-1}은 갈 수 없는 곳. 0<= puddles.length <=10
     * 
     * 1<= m,n <=100 / 2 < m+n (둘 중 하나는 1일 수 있어도 다른 하나는 아님), 불가능 case 없음.
     * 
     * dp[a][b]가 해당 좌표에 도달할 수 있는 경우의 수. 라고 한다면 
     * dp[a][b] = dp[a-1][b] + dp[a][b-1]
     * dp[0][b] = 전부 0 (1이 좌측으로 더해지게)
     * dp[a][0] = 전부 0 (1이 위에서 더해지게)
     *
     * 경로 결과  % 1,000,000,007
     */
}