import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;

        //
        int[][] dp = new int[triangle.length+1][triangle.length+1];

        //base 
        dp[0][0] = triangle[0][0];
        dp[1][0] = triangle[0][0] + triangle[1][0];
        dp[1][1] = triangle[0][0] + triangle[1][1];
        
        
        for(int i=1; i<=triangle.length-1; i++){
            for(int j=0; j<=i;  j++){
                //현재 추가할 값
                int now = triangle[i][j];
                // i=1; j=1;
                if(j==i){ //n번째 열의 n번째 숫자 ( n-1번째의 n번째 숫자는 없음. )
                    dp[i][j] = dp[i-1][j-1] + now;
                }else if(j==0){ //n번째 열의 0번째 숫자 ( n-1번째의 0-1번째 숫자는 없음. )
                    dp[i][j] = dp[i-1][j] + now;
                }else{
                    dp[i][j] = Math.max((dp[i-1][j-1] + now), (dp[i-1][j] + now)); //Math.max( a, b ) 
                }
            }
        }
        
        answer = Arrays.stream(dp[triangle.length-1]).max().getAsInt();
        return answer;
    }
    /**
     * 할 수 있는 것 = 해당 다음 i에서 index+0, index+1과 sum.
     * 그렇다면 i번째 j번째에서 = [i][j] = [i-1][j] + [i][j] , [i-1][j-1]
     * 
     * 최대 500번 반복, 최대 9999  = 500만
     */
}