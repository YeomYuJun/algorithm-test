import java.util.LinkedList;
import java.util.Queue;
class Solution {
    public int solution(String[] board) {
        int answer = -1;

        int[][] map = new int[board.length][board[0].length()];
        
        int[] reco = new int[3];
        int[] goal = new int[2];


        for(int i=0; i<board.length; i++){
            String row = board[i];
            String[] cell = row.split("");
            for(int j=0; j<cell.length; j++){
                if("D".equals(cell[j])){
                    // ??
                    map[i][j] = 1;
                }else if(".".equals(cell[j])){
                    // 체크 필요? 
                    map[i][j] = 0;
                }else if("R".equals(cell[j])){
                    reco[0] = i;
                    reco[1] = j;
                    map[i][j] = 0;
                }else if("G".equals(cell[j])){
                    goal[0] = i;
                    goal[1] = j;
                    map[i][j] = 0;
                }
            }
        }

        reco[2] = 0;
        boolean[][] visited = new boolean[board.length][board[0].length()];
        answer = reco(map, visited, reco, goal);

        return answer;
    } 

    public int reco(int[][] maps, boolean[][] visited, int[] pos, int[] goal){
        int[] dx = {1, -1 , 0 ,0};
        int[] dy = {0, 0, 1, -1};
        Queue<int[]> q = new LinkedList<>();

        visited[pos[0]][pos[1]] = true;
        q.offer(pos);

        while(!q.isEmpty()){
            int[] movedPos = q.poll();
            int row = movedPos[0];
            int col = movedPos[1];
            int move = movedPos[2];
            if(row == goal[0] && col == goal[1]){
                return move;
            }

            for(int direction = 0; direction<4; direction++){
                int moveRow = row;
                int moveCol = col;
                if(dy[direction] != 0){
                    boolean isBlock = false;
                    int gap = 0;
                    while(!isBlock){
                        gap = gap  + dy[direction];
                        if( row+gap < 0 || row+gap >= maps.length){
                            isBlock = true;
                        } else if(maps[row+gap][col] == 1){
                            isBlock = true;
                        }
                    }
                    moveRow = row+gap - (1*dy[direction]);
                }
                else if(dx[direction] != 0){
                    boolean isBlock = false;
                    int gap = 0;
                    while(!isBlock){
                        gap = gap  + dx[direction];
                        if( col+gap < 0 || col+gap >= maps[0].length){
                            isBlock = true;
                        } else if(maps[row][col+gap] == 1){
                            isBlock = true;
                        }
                    }
                    moveCol = col+gap - (1*dx[direction]);
                }
                if(!visited[moveRow][moveCol]){
                    visited[moveRow][moveCol] = true;
                    q.offer(new int[]{moveRow, moveCol, move+1});
                }
            }
        }
        return -1;
    }
    /**
     *  최소 몇 번 이동 이니까
     *  BFS인데 이동 방향에서 while로 D or 장외만 찾으면 될듯
     */
}