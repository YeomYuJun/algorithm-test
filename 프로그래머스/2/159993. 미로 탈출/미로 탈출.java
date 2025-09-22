import java.util.*;
class Solution {
    public int solution(String[] maps) {
        int answer = 0;

        int[][] map = new int[maps.length][maps[0].length()];

        int[] s = new int[2];
        int[] e = new int[2];
        int[] l = new int[2];

        //굳이 안꺼내도 indexOf로 상관없을 거 같긴한데...
        for(int i =0; i<maps.length; i++){
            String[] c = maps[i].split("");
            for(int j=0; j<c.length; j++){
                String m = c[j];
                if("S".equals(m)){
                    s[0] = i; s[1] = j;
                } else if("L".equals(m)){
                    l[0] = i; l[1] = j;
                } else if("E".equals(m)){
                    e[0] = i; e[1] = j;
                }
                if("X".equals(m)){
                    map[i][j] = 0;
                }else{
                    map[i][j] = 1;
                }
            }
        }

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{s[0],s[1],0});
        //first 도착지 = l 다음 e.
        int[] dx = {1, 0,-1, 0}; 
        int[] dy = {0, 1, 0,-1}; //우 하 좌 상

        boolean[][] leverVisited = new boolean[maps.length][maps[0].length()];
        boolean[][] exitVisited = new boolean[maps.length][maps[0].length()];
        int lx = l[0];
        int ly = l[1];
        int ex = e[0];
        int ey = e[1];

        int moveCnt = -1;
        while(!q.isEmpty()){
            int[] pxy = q.poll();
            int px = pxy[0];
            int py = pxy[1];
            int move = pxy[2];

            if(px == lx && py == ly){
                moveCnt = move;
                break;
            }
            for(int d=0; d<4; d++){
                int moveX = px + dx[d];
                int moveY = py + dy[d];

                if(moveX < 0 || moveY < 0) continue;
                if(moveX >= map.length || moveY >= map[0].length) continue;
                if(map[moveX][moveY] == 0) continue;
                if(leverVisited[moveX][moveY]) continue;
                
                leverVisited[moveX][moveY] = true;
                q.offer(new int[]{moveX,moveY, move+1});
            }
        }
        if(moveCnt == -1) return moveCnt;
        q.clear();
        q.offer(new int[]{l[0],l[1],0});
        int leverToExit = -1;
        while(!q.isEmpty()){
            int[] pxy = q.poll();
            int px = pxy[0];
            int py = pxy[1];
            int move = pxy[2];

            if(px == ex && py == ey){
                leverToExit = move;
                break;
            }
            for(int d=0; d<4; d++){
                int moveX = px + dx[d];
                int moveY = py + dy[d];

                if(moveX < 0 || moveY < 0) continue; 
                if(moveX >= map.length || moveY >= map[0].length) continue;
                if(map[moveX][moveY] == 0) continue;
                if(exitVisited[moveX][moveY]) continue;

                exitVisited[moveX][moveY] = true;
                q.offer(new int[]{moveX,moveY,move+1});
            }
        }
        if(leverToExit == -1){
            return -1;
        }else{
            answer = leverToExit + moveCnt;
        }
        return answer;
    }
    /**
     *  1. bfs로 레버찾기
     *  2. 해당 위치에서 bfs로 출구찾기
     *  (방문 가능이니까 visited 2개 or 초기화하고)
     *  while 2번 돌면 될듯
     * 
     * 
     *  S : 시작 지점
        E : 출구
        L : 레버
        O : 통로
        X : 벽
     */
}