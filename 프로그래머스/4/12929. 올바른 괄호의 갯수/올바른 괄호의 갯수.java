class Solution {
    public int solution(int n) {
        return DFS(0, 0, n);
    }
    public static int DFS(int open, int close, int n) {
        if (open == n) {
            return 1;
        } else if (open - close < 0) { //닫이 더 많으면 불가능
            return 0;
        }
        int num = 0;
        num += DFS(open + 1, close, n);
        num += DFS(open, close + 1, n);
        return num;
    }
    /**
     * 열닫
     * 열닫열닫 
     * 열열닫닫
     * 열닫닫? X
     * 닫..? X
     * dp0이 열닫일 때 
     * dp0 조합 1개, 신규 1개 = dp1 = 2 개
     * dp1 열열닫닫의 조합 3개, dp1 열닫열닫의 조합 2개 (dp0(열닫)조합 포함) = dp2 = 5개
     * dp3은 ??  
     * 
     * ( 혹은 ) 한개씩 추가하는 DFS?
     */
}