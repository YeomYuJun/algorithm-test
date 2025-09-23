class Solution {
    private Integer[][][] memo;
    
    public int solution(int[][] info, int n, int m) {
        memo = new Integer[info.length][n + 1][m + 1];
        int result = dfs(info, 0, n, m);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int dfs(int[][] info, int depth, int n, int m) {
        if (depth == info.length) {
            return 0;
        }
        
        if (memo[depth][n][m] != null) {
            return memo[depth][n][m];
        }
        
        int result = Integer.MAX_VALUE;
        
        // A가 선택하는 경우 - 사용 후 흔적이 n 미만이어야 함
        if (info[depth][0] < n) { // n - info[depth][0] >= 1과 동일
            int nextResult = dfs(info, depth + 1, n - info[depth][0], m);
            if (nextResult != Integer.MAX_VALUE) {
                result = Math.min(result, nextResult + info[depth][0]);
            }
        }
        
        // B가 선택하는 경우 - 사용 후 흔적이 m 미만이어야 함  
        if (info[depth][1] < m) { // m - info[depth][1] >= 1과 동일
            int nextResult = dfs(info, depth + 1, n, m - info[depth][1]);
            if (nextResult != Integer.MAX_VALUE) {
                result = Math.min(result, nextResult);
            }
        }
        
        return memo[depth][n][m] = result;
    }
    /*     
     * 당장 생각나는건
     * 0. [i][0] , [i][1]중 [i][1]을 최우선으로 사용한다. 
     * 1. b의 목숨이 간당간당해서 고를 수 없을 때 [i][0]을 선택한다
     * 2. [i][1]이 진즉에 소모되고 [i][0]도 선택할 수 없을 때, -1 리턴
     * + 여기서 [i][1] 이 비정상적으로 커서 b의 목숨을 단번에 앗아간다면..?
     * + 즉, 다음과같이 되어야 할듯? 
     * 
     * [i][0],[i][1]을 선택하는 경우가 존재.
     * 반복하며 0, 1 / 0-0, 0-1, 1-0, 1-1 / 0-0-0, 0-0-1, 0-1-0,... 이런 식으로 DFS? 최적화가 조금 필요할 것 같은데
     */


}