import java.util.*;
class Solution {
    private int min = Integer.MAX_VALUE;
    
    public int solution(int[][] maps) {
        int answer = 0;
        int[] goal = {maps.length-1, maps[0].length-1};
        boolean[][] visited = new boolean[maps.length][maps[0].length];
        
        Queue<int[]> queue = new LinkedList<>();
        
        queue.offer(new int[]{0, 0, 1});
        visited[0][0] = true;
        
        int[] dx = {1, 0, -1, 0}; //우하향하게 순서 우,하,좌,상으로 
        int[] dy = {0, 1, 0, -1};
        
        // 큐 비우기
        while(!queue.isEmpty()){
            
            int[] current = queue.poll();
            int px = current[0];
            int py = current[1];
            int move = current[2];
            
            
            if(px == goal[0] && py == goal[1]){ //도착
                answer = move;
                return answer;
            }
            for(int direction = 0; direction < 4; direction++){
                int move_px = px + dx[direction];
                int move_py = py + dy[direction];
                
                if(move_px < 0 || move_py < 0  || 
                    move_px >= maps.length || move_py >= maps[0].length){
                    continue;
                } else if(maps[move_px][move_py] == 0){
                    continue;
                } else if(visited[move_px][move_py]){
                    continue;
                }

                if(maps[move_px][move_py] == 1){
                    visited[move_px][move_py] = true; // 큐에 넣으면서 방문처리
                    queue.offer(new int[]{move_px, move_py, move + 1});
                }
            }
        }
        
        answer = -1; // 도달 불가능
        return answer;
    }

}