import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
class Solution {
    public int[] solution(String[] maps) {
        int[] answer = {};
        List<Integer> live = new ArrayList<>();
        int[][] island = new int[maps.length][maps[0].length()];
        boolean[][] visited = new boolean[maps.length][maps[0].length()];

        for(int i=0; i< maps.length; i++){
            String row = maps[i];
            for(int j=0; j<row.length(); j++){
                if(visited[i][j]){
                    continue;
                }
                
                if(row.charAt(j) == 'X'){
                    visited[i][j] = true;
                    continue;
                }
                int day = row.charAt(j) - 48; //"9".charAt(0) = 57 "1" = 49
                Queue<int[]> q = new LinkedList<>();

                int[] pos = new int[]{i,j};
                visited[i][j] = true;
                q.offer(pos);
                while(!q.isEmpty()){
                    int[] nowIsland = q.poll();
                    int[] dx = {1,0,-1,0};
                    int[] dy = {0,1,0,-1};
                    for(int direction = 0; direction < 4; direction++){
                        int nextX = nowIsland[1] + dx[direction];
                        int nextY = nowIsland[0] + dy[direction];
                        if(nextX < 0 || nextY < 0
                            || nextX >= row.length() || nextY >= maps.length){ //장외
                            continue;
                        }
                        if(visited[nextY][nextX]){ //이미 방문
                            continue; 
                        }
                        if(maps[nextY].charAt(nextX) == 'X'){
                            visited[nextY][nextX] = true; // 바다면 방문처리 후 탈출
                            continue;
                        }
                        int liveTerm = maps[nextY].charAt(nextX) - 48;
                        day += liveTerm;
                        int[] nextIsland = new int[]{nextY,nextX};
                        System.out.println("("+i+","+j+") 에서부터 OFFER: " + Arrays.toString(nextIsland));
                        visited[nextY][nextX] = true;
                        q.offer(nextIsland);
                        
                    }
                }

                live.add(day);
            }
        }
        if(live.size()==0){
            return new int[]{-1};
        }
        Collections.sort(live);
        answer = live.stream().mapToInt(Integer::intValue).toArray();
        return answer;
    }
    /**
     * String 1~9 = 49~57 = -48
     */
}