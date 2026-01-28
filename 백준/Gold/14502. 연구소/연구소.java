import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        ////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        } 
        str = mainSb.toString();
            
        ///////////////////////////////////////////
        
        // str = "4 6\r\n" + //
        //                 "0 0 0 0 0 0\r\n" + //
        //                 "1 0 0 0 0 2\r\n" + //
        //                 "1 1 1 0 0 2\r\n" + //
        //                 "0 0 0 0 0 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    static List<int[]> virus = new ArrayList<>();
    static int wallCnt = 0;
    static int MAX = 0;
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);  //3~8
        int M = Integer.parseInt(args[0].split(" ")[1]);  //3~8

        int[][] map = new int[N][M];
        
        
        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<M; j++){
                int val = Integer.parseInt(row[j]);
                if(val == 2){
                    virus.add(new int[]{i,j});
                }
                if(val == 1){
                    wallCnt++;
                }
                map[i][j] = val;
            }
        }
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(map[i][j]!=0) continue;
                List<int[]> arr = new ArrayList<>();
                int[] curr = new int[]{i,j};
                arr.add(curr);
                backtrack(curr, arr, map, 1); //depth는 필요없을듯
            }
        }
        
        System.out.println(MAX);
    }
    public static void backtrack(int[] current, List<int[]> result, int[][] map, int DEPTH){
        int N = map.length;
        int M = map[0].length;
        if(result.size() == 3){
            BFS(result, map);
            return;
        }
        for(int i=current[0]; i<N; i++){
            int tempJ = i==current[0] ? current[1]+1 : 0;
            for(int j=tempJ; j<M; j++){  //같은 행에서는 다음부터 체크 아닌 경우 0부터
                if(map[i][j] != 0) continue;
                int[] newCur = new int[]{i,j};
                result.add(newCur);
                backtrack(newCur, result, map, DEPTH+1);
                result.remove(result.size()-1);
            }
        }
    }

    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    public static void BFS(List<int[]> walls, int[][] map){
        int N = map.length;
        int M = map[0].length;
        Queue<int[]> q = new LinkedList<>();
        q.addAll(virus);
        boolean[][] visit = new boolean[N][M];
        int visitCnt = 0;
        visitCnt+=virus.size();

        int[] wall1 = walls.get(0);
        int[] wall2 = walls.get(1);
        int[] wall3 = walls.get(2);

        while(!q.isEmpty()){
            int[] v = q.poll();
            visit[v[0]][v[1]] = true;
            
            for(int d=0; d<4; d++){
                int ny = v[0] + dy[d];
                int nx = v[1] + dx[d];
                if(nx < 0 || ny < 0 || nx >= M || ny >= N ) continue;
                if(visit[ny][nx]) continue;
                if(wall1[0] == ny && wall1[1] == nx) continue; //못감
                if(wall2[0] == ny && wall2[1] == nx) continue; //못감
                if(wall3[0] == ny && wall3[1] == nx) continue; //못감

                if(map[ny][nx]==0){
                    visit[ny][nx] = true;
                    q.add(new int[]{ny,nx});
                    visitCnt++;
                }
            }
        }
        MAX = Math.max((N*M)-wallCnt-visitCnt-walls.size(), MAX);
        return;
    }

    /**
     * 꼭 3개의 벽을 전부 사용하여
     * 바이러스 퍼짐 = 상하좌우 이동.
     * 벽이 3개가 놓여야 완성되는 케이스가 있음 
     * 즉 3개를 놓고 탐색해야함. 1개씩 불가능. 그럼
     * 벽배치 여부 = [y1][x1],[y2][x2],[y3][x3] = true.
     * 아니다 backtrack으로 1,2,3, 1,2,4 .. 1,2,n => 1,3,4, 1,3,5 .. 1,3,n => 
     * 중복없이 모든 벽 배치 테스트 
     * 벽 배치 후 backtrack return할 때 bfs 로 너비탐색. 아마 visit을 공유하는 바이러스 이동이
     * 0을 전부 탐색하는 것보다 빠를 듯
     */
}

