class Solution {
    public long solution(int n, int m, int x, int y, int[][] queries) {
        long minRow = x;
        long maxRow = x;
        long minCol = y;
        long maxCol = y;
        
        for (int i = queries.length - 1; i >= 0; i--) {
            int cmd = queries[i][0];
            int dx = queries[i][1];
            
            if (cmd == 0) { // 왼 > 오
                maxCol = Math.min(m - 1, maxCol + dx);
                if (minCol != 0) {
                    minCol += dx;
                }
                if (minCol >= m) return 0;
                
            } else if (cmd == 1) { //오 > 왼 
                minCol = Math.max(0, minCol - dx);
                if (maxCol != m - 1) {
                    maxCol -= dx;
                }
                if (maxCol < 0) return 0;
                
            } else if (cmd == 2) { // 위 >  아래
                maxRow = Math.min(n - 1, maxRow + dx);
                if (minRow != 0) {
                    minRow += dx;
                }
                if (minRow >= n) return 0;
                
            } else { // cmd == 3 == 아래 > 위로 계싼
                minRow = Math.max(0, minRow - dx);
                if (maxRow != n - 1) {
                    maxRow -= dx;
                }
                if (maxRow < 0) return 0;
            }
        }
        
        return (maxRow - minRow + 1) * (maxCol - minCol + 1);
    }
    /**
     *  전체 이동 수행 ? 절대 안될듯
     
        느낌 상 정답 x,y에 도달하는 지만 보면 될 거 같은데
        (x,y)에 도달하는 시작 (p,q)에 해서
        쿼리 index, 순간x, 순간y들을 저장해두면
        다음번에 (p+1,q) 혹은 (p,q+1) 같은 다른 좌표에서 출발하더라도
        쿼리 인덱스가 같은 순간이 온다면 무조건 도달이라는 점?
        
        시작 좌표 갭 ~ 이동 dx 최대치 비교하면 얼추 조기조건 탈출도 가능할듯
        
        그러면 (n,m)의 좌표값에 대한 key의 value로 쿼리 인덱스를 넣어놓으면? Map<int[], Integer>
        (0,0)부터 순회하면서.. 너무 클 수 있음 
        
        아 정답부터 역순으로 쿼리? 역순으로 쿼리하면서 갈 수 있는 범위를 정하면 
        프랙탈 퍼지듯이  0,0이 정답일 때  top으로 10 이동이면, 0~10까지 다 갈 수 있고
        거기서 left 5이면 0~4까지 가능, 외각에 닿는 순간의 길이만큼 범위가 늘어나겠네
        그리고 대각선같은 범위는 절대 안나오겠고, 어떤 사각형의 범위겠네
     */
}