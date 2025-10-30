class Solution {
    public int solution(int[][] beginning, int[][] target) {
        int row = beginning.length;
        int col = beginning[0].length;
        int minFlips = Integer.MAX_VALUE;
        
        // 행 뒤집기 완전탐색 (2^row)
        for (int rowMask = 0; rowMask < (1 << row); rowMask++) {
            
            //rowMask로 행 뒤집기 상태 결정
            boolean[] rowFlip = new boolean[row];
            for (int i = 0; i < row; i++) {
                rowFlip[i] = ((rowMask >> i) & 1) == 1;
            }
            
            //첫 행으로 열 뒤집기 자동 결정
            boolean[] colFlip = new boolean[col];
            for (int j = 0; j < col; j++) {
                int current = beginning[0][j] ^ (rowFlip[0] ? 1 : 0);
                colFlip[j] = (current != target[0][j]);
            }
            
            //모든 셀 검증
            boolean valid = true;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    // 행 XOR 열 = 1 (정확히 하나만 뒤집음)
                    // 행 XOR 열 = 0 (둘 다 뒤집거나 안 뒤집음)
                    int result = beginning[i][j] 
                               ^ (rowFlip[i] ? 1 : 0) 
                               ^ (colFlip[j] ? 1 : 0);
                    if (result != target[i][j]) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) break;
            }
            
            //valid하면 뒤집은 횟수 카운트
            if (valid) {
                int count = Integer.bitCount(rowMask);
                for (int j = 0; j < col; j++) {
                    if (colFlip[j]) count++;
                }
                minFlips = Math.min(minFlips, count);
            }
        }
        
        return minFlips == Integer.MAX_VALUE ? -1 : minFlips;
    }
    /**
     * 이게 begin -> target이 되는게
     *
     * 다름 = 행 or 열에서의 1번 뒤집음
     * 같음= 행과 열에서 1번씩 뒤집음 or 안뒤집음
     */

}