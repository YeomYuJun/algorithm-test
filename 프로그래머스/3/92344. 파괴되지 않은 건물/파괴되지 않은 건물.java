class Solution {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        //skill의 각 행은 [type, r1, c1, r2, c2, degree]형태를 가지고 있습니다
        int rows = board.length;
        int cols = board[0].length;

        long[][] diff = new long[rows+1][cols+1];

        for(int turn = 0; turn < skill.length; turn ++){
            int type = skill[turn][0]; // 1: 공격 , 2: 회복
            int r1 = skill[turn][1];
            int c1 = skill[turn][2];
            int r2 = skill[turn][3];
            int c2 = skill[turn][4];
            int degree = skill[turn][5];
            degree = type == 1 ? degree * -1 : degree; // 공격이면 - , 회복이면 +
            //25만 * 100 = 2500만? long 해야하나
            
            diff[r1][c1] += degree;        // 시작점에 +degree
            diff[r1][c2+1] -= degree;      // 오른쪽 경계 다음에 -degree
            diff[r2+1][c1] -= degree;      // 아래쪽 경계 다음에 -degree
            diff[r2+1][c2+1] += degree;    // 대각선 반대편에 +degree (중복 제거)
        }

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(i > 0) diff[i][j] += diff[i-1][j];     // 위쪽에서 누적
                if(j > 0) diff[i][j] += diff[i][j-1];     // 왼쪽에서 누적  
                if(i > 0 && j > 0) diff[i][j] -= diff[i-1][j-1]; // 중복 제거
                
                board[i][j] += diff[i][j]; // 원본 배열에 적용
                if(board[i][j] >= 1){
                    answer++;
                }
            }
            
        }


        return answer;
    }

    /**
     * 
     * 1 ≤ skill의 행의 길이 ≤ 250,000인데, for 반복해도 되는가?
     * 일반적으로 (r2-r1+1) * (c2-c1+1) 만큼 또 순회해야하는데,  n과m이 1~1000이니 1000^2,
     * 250,000 * 1,000,000  = 2,500,000,000,000 =  2500억..
     * 차분배열인 거 같은데 +1 사이즈 하나를 만들고 거기에 좌표에 +- 를 r1,r2,c1,c2에 대해 누적해놓고 마지막에 board만 순회하면 될듯?
     */
}