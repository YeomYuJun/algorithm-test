import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        /////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "1\r\n" + //
        //                 "4 4\r\n" + //
        //                 "0 0 0 0\r\n" + //
        //                 "0 0 0 0\r\n" + //
        //                 "0 0 1 1\r\n" + //
        //                 "0 0 1 0";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int K = Integer.parseInt(args[0]);
        int W = Integer.parseInt(args[1].split(" ")[0]);
        int H = Integer.parseInt(args[1].split(" ")[1]);

        int[][] board = new int[H][W]; //

        //map 추가
        for(int i=0; i<H; i++){
            for(int j=0; j<W; j++){
                board[i][j] = Integer.parseInt(args[2+i].split(" ")[j]);
            }
        }
        int answer = -1;

        int[] monkey = {0,0,0,K}; // y, x, move, remain K 

        Queue<int[]> q = new LinkedList<>();

        int[] dx = {1,0,-1,0};
        int[] dy = {0,1,0,-1}; // 우 하 좌 상 (목적지 = 우하)

        int[] hdx = {1,2,1,2,-1,-2,-1,-2}; //8방향 말 이동 
        int[] hdy = {2,1,-2,-1,2,1,-2,-1}; 

        boolean[][][] visit = new boolean[K+1][H][W];
        q.offer(monkey);

        visit[K][0][0]  = true;
        
        while(!q.isEmpty()){
            int[] mk = q.poll();
            int y = mk[0];
            int x = mk[1];
            int cnt = mk[2];
            int horse = mk[3];
            if(y == (H-1) && x == (W-1)){
                answer = cnt;
                break;
            }

            
            for(int drct=0; drct<4; drct++){
                int nextY = mk[0] + dy[drct];
                int nextX = mk[1] + dx[drct];
                int moveCnt = mk[2]+1;
                int remainHorse = mk[3];
                
                if(nextX < 0 || nextY < 0 || nextX >= W || nextY >= H) continue;
                if(board[nextY][nextX] == 1) continue;
                if(visit[remainHorse][nextY][nextX]) continue;
                
                int[] newPos = {nextY, nextX, moveCnt, remainHorse};
                q.offer(newPos);
                visit[remainHorse][nextY][nextX] = true;
            }

            if(horse>0){
                for(int drct=0; drct<8; drct++){
                    int nextY = mk[0] + hdy[drct];
                    int nextX = mk[1] + hdx[drct];
                    int moveCnt = mk[2]+1;
                    int remainHorse = mk[3]-1;

                    if(nextX < 0 || nextY < 0 || nextX >= W || nextY >= H) continue;
                    if(board[nextY][nextX] == 1) continue;
                    if(visit[remainHorse][nextY][nextX]) continue;
                    int[] newPos = {nextY, nextX, moveCnt, remainHorse};
                    q.offer(newPos);
                    visit[remainHorse][nextY][nextX] = true;
                }
            }
        }
        System.out.println(answer);

    }
  
    /**
     * 막혀있을 경우 horse 이동으로 이동 가능. 
     * [y][x]에 k를 몇 개 들고가냐..k를 최대로 하고 가는 게 이득인가?
     * 최소로 하고 가는 게 이득인가? 다 쓰면 더 빨리 갔다는 거니까 
     */
}