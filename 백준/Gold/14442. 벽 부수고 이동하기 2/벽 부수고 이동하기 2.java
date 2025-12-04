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

        // str = "4 4 3\r\n" + //
        //                 "0111\r\n" + //
        //                 "1111\r\n" + //
        //                 "1111\r\n" + //
        //                 "1110";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int H = Integer.parseInt(args[0].split(" ")[0]);
        int W = Integer.parseInt(args[0].split(" ")[1]);
        int K = Integer.parseInt(args[0].split(" ")[2]);//1; //부술 수 있는 횟수
        int[][] board = new int[H][W]; //

        //map 추가
        for(int i=0; i<H; i++){
            String line = args[1+i];  // 한 번만 가져오기
            for(int j=0; j<W; j++){
                board[i][j] = line.charAt(j) - '0';  // charAt() 사용
            }
        }
        int answer = -1;
        

        int[] player = {0,0,1,K}; // y, x, move, remain K , 시작점도 1로 카운트

        Queue<int[]> q = new LinkedList<>();

        int[] dx = {1,0,-1,0}; 
        int[] dy = {0,1,0,-1}; // 우 하 좌 상 (목적지 = 우하)

        boolean[][][] visit = new boolean[K+1][H][W];
        q.offer(player);

        for(int k=0; k<=K; k++){
            visit[k][0][0]  = true;
        }
        
        while(!q.isEmpty()){
            int[] mk = q.poll();
            int y = mk[0];
            int x = mk[1];
            int cnt = mk[2];
            int crash = mk[3];
            if(y == (H-1) && x == (W-1)){
                answer = cnt;
                break;
            }
           
            for(int drct=0; drct<4; drct++){
                int nextY = y + dy[drct];
                int nextX = x + dx[drct];
                int moveCnt = cnt+1;
                int remainCrash = crash;
                
                if(nextX < 0 || nextY < 0 || nextX >= W || nextY >= H) continue; //장외 탈락
                if(visit[remainCrash][nextY][nextX]) continue; //같은 조건 방문해봄
                if(remainCrash==0 && visit[1][nextY][nextX]) continue; // 부수지 않고 방문해봄(최단거리 방문 Queue니까 부수지않고 방문이 무조건 유리한 case)
        
                if(board[nextY][nextX] == 1){
                    if(remainCrash>0){
                        visit[remainCrash][nextY][nextX] = true;
                        remainCrash--;
                        visit[remainCrash][nextY][nextX] = true;
                    }else{
                        continue;
                    }
                }else{
                    visit[remainCrash][nextY][nextX] = true;
                }
                
                int[] newPos = {nextY, nextX, moveCnt, remainCrash};
                q.offer(newPos);
                visit[remainCrash][nextY][nextX] = true;
            }
        }
        System.out.println(answer);
    } 
}