import java.util.*;

class Solution {
    
    public static void main(String[] args){
        Solution sol = new Solution();
        int[][] maps = {{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,1},{0,0,0,0,1}};	//11
        
        System.out.println(sol.solutionDFS(maps)); // 시간초과 (효율성 생각못함 bfs로 전환함.)
        System.out.println(sol.solutionBFS(maps));
    }

    private int min = Integer.MAX_VALUE;
    
    public int solutionDFS(int[][] maps) {
        int answer = 0;
        int[] playerPos = {0,0}; // x,y
        int[] goal = {maps.length-1, maps[0].length-1};
        boolean[][] visited = new boolean[maps.length][maps[0].length]; 

        min = Integer.MAX_VALUE;
        dfsSearch(maps, playerPos[0], playerPos[1], goal, visited, 1);

        answer = min == Integer.MAX_VALUE ? -1 : min;
        return answer;
    }

    public void dfsSearch(int[][] maps, int px, int py, int[] goal, boolean[][] visited, int move){
        int[] dx = {1, 0, -1, 0}; // 하, 우, 상, 좌 (우하향 우선순위)
        int[] dy = {0, 1, 0, -1};

        // 가지치기: 이미 찾은 최단거리보다 길면 중단
        if (move >= min) {
            return;
        }

        if(px == goal[0] && py == goal[1]){ // 목표 도달
            min = Math.min(min, move);
            return;
        }

        visited[px][py] = true;
        
        for(int direction = 0; direction < 4; direction++){
            int move_px = px + dx[direction];
            int move_py = py + dy[direction];
            
            if(move_px < 0 || move_py < 0  || 
                move_px >= maps.length || move_py >= maps[0].length){ // 장외인가?
                continue;
            } else if(maps[move_px][move_py] == 0){ //벽인가?
                continue;
            } else if(visited[move_px][move_py]){ //방문한 적 있는가?
                continue;
            }

            if(maps[move_px][move_py] == 1){ // 이동 가능
                dfsSearch(maps, move_px, move_py, goal, visited, move+1);
            }
        }
        
        visited[px][py] = false; // 백트래킹
    }

    /*
     *         
     * 장외인가? => 예 => 가지마
     * 아니오
     * 벽인가? => 예 => 가지마
     * 아니오 
     * 방문한 적 있는가 ? => 예 => 가지마
     * 아니오
     * 가.
     * 
     * 반복..도달 시 private min과 비교 (최단거리)
     * 도달 불가능 시 -1 리턴
     * 
     * 효율성 문제로 시간초과가 뜸...> BFS 최단거리를 해야함
     * 
     */
    public int solutionBFS(int[][] maps) {
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
