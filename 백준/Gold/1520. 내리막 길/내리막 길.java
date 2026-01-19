import java.io.*;
import java.util.*;
import java.util.Map.Entry;

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
        
        // str = "2 18\r\n" + //
        //                 "98 91 89 80 76 74 65 58 50 49 44 32 26 23 17 15 14 8\r\n" + //
        //                 "95 91 83 82 70 67 65 55 54 50 41 27 22 20 14 10 7 3"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);


        int[][] mountain = new int[N][M];
        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<M; j++){
                int height = Integer.parseInt(row[j]);
                mountain[i][j] = height;
            }
        }

        int[] sejun = new int[]{0,0}; // (0,0).

        int[][] visit = new int[N][M];
        visit[N-1][M-1] = 1;
        dfs(mountain, sejun, visit);
        // for(int i=0; i<N; i++){
        //     System.out.println(Arrays.toString(visit[i]));
        // }
        System.out.println(visit[0][0]);
        
    }
    public static int paths = 0;
    public static boolean dfs(int[][] mountain, int[] curPos, int[][] visit){
        int N = mountain.length;
        int M = mountain[0].length;

        int[] dx = {1,-1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        int curY = curPos[0];
        int curX = curPos[1];
        if(curY == N-1 && curX == M-1){
            return true;
        }
        int curH = mountain[curY][curX];

        boolean isGoal = false;
        for(int d=0; d<4; d++){
            int ny = curY + dy[d];
            int nx = curX + dx[d];
            if(ny < 0 || nx < 0 || ny >= N || nx >= M) continue;
            int nh = mountain[ny][nx];
            if(nh >= curH) continue;
            if(visit[ny][nx] == -1){ //갈 수 없는 경로.
                continue;
            }
            if(visit[ny][nx] > 0){
                visit[curY][curX] += visit[ny][nx];
                isGoal = true;
                continue;
            }else{ // 0 = 미방문
                if(dfs(mountain, new int[]{ny,nx}, visit)){
                    visit[curY][curX] += visit[ny][nx];
                    isGoal = true;
                }else{
                    visit[ny][nx] = -1;
                }
            }
        }
        return isGoal;
    }

    /**
     * NM 500까지 500*500, 높이 10,000;
     * 결과값 또한 10억 이하
     * i,j의 상하좌우가 가능하니까 순차탐색은 안되겠고
     * BFS로 가야할듯?
     * queue로 탐색하다가, 만나면, 누적 맵에 덧연산. 이동할 때는 누적된 값 몰고 다니기.
     * 더 낮은 곳이라, 재방문 케이스는 없을듯 근데 그러면 경우가 너무 많나? 
     * 아 DFS로 방문을하고, 방문처리를 하다가, 돌다가 방문한 곳을 밟으려고할 경우 정답 += 1하면 되겠다
     * => 골인한 경로만 방문처리해야함. 흠.
     */

}