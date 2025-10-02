class Solution {
    public int solution(int[] money) {
        int answer = 0;
        int len = money.length-1;
        //int[] dp = new int[len+1];
        if (len == 2) {
            return Math.max(money[0], Math.max(money[1], money[2]));
        }
        //base
        /*
        dp[1] = money[1] > dp[0] ? money[1] : dp[0]; //크면 바꾸고 아니면 말고
        dp[2] = money[2] + dp[0] > dp[1] ?  money[2] + dp[0] : dp[1] ; // 0<1<0+2 혹은 0<0+2<1
        dp[3] = money[3] + dp[1] > dp[2] ?  money[3] + dp[1] : dp[2] ; // 
         */
        // Case 1: 첫 번째 집 포함 (마지막 집 제외)
        int[] dp1 = new int[len+1];
        dp1[0] = money[0];
        dp1[1] = Math.max(money[0], money[1]);
        
        // Case 2: 첫 번째 집 제외 (마지막 집 포함 가능)
        int[] dp2 = new int[len+1];
        dp2[0] = 0;
        dp2[1] = money[1];


        for(int i=2; i<=len; i++){
            if (i < len) {
                dp1[i] = Math.max(dp1[i-1], dp1[i-2] + money[i]);
            }
            // dp2는 마지막 집 포함
            dp2[i] = Math.max(dp2[i-1], dp2[i-2] + money[i]);
            //dp[i] = money[i] + dp[i-2] > dp[i-1] ? money[i] + dp[i-2] : dp[i-1];
        }
        answer = Math.max(dp1[len-1], dp2[len]);

        return answer;
    }
    /**
     *  3<= house <=100만
     * 
     *  0<= money[n] < 1000
     * 
     *  dp[n]을 털면 dp[n-1]은 털지 않았다는 뜻. 
     *  dp[n]을 털면 dp[n-1]은 털지않고 dp[n-2]는 털어야 이득임
     *  dp[n-1]을 털었는데(now) dp[n]을 털려고(after) 보니 dp[n-1]을 털지않고 dp[n-2]를 털기?
     *  
     *  아니; 원형이라서 0,n같이 못 텀. 
     *  그러면 첫집 포함 or 아닌 케이스 2개로 구하면?
     *  뭔 문제를 서술트릭처럼 내놓지..자꾸
     */
}