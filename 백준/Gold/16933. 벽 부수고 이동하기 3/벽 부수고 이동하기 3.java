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

        // str = "7 53 10\r\n" + //
        //                 "01000001111100100001010111111001001001100100110011010\r\n" + //
        //                 "01000010001000100000000110010000000011010000101010101\r\n" + //
        //                 "00010000000101000010010100010100101100011100100010010\r\n" + //
        //                 "10100000001000000100000001000011110000010101010000110\r\n" + //
        //                 "01000010100001010001001100000010000000001011101010110\r\n" + //
        //                 "01100000100100101010000110010000100011000001000000010\r\n" + //
        //                 "00001000101110000111001010000010001000000001000001000"; // 23

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int H = Integer.parseInt(args[0].split(" ")[0]);
        int W = Integer.parseInt(args[0].split(" ")[1]);
        int K = Integer.parseInt(args[0].split(" ")[2]);//1; //부술 수 있는 횟수
        int bit = 1; // 낮과 밤. (낮에만 부술 수 있음)
        int[][] board = new int[H][W]; //

        //map 추가
        for(int i=0; i<H; i++){
            String line = args[1+i];  // 한 번만 가져오기
            for(int j=0; j<W; j++){
                board[i][j] = line.charAt(j) - '0';  // charAt() 사용
            }
        }
        int answer = -1;

        int[] player = {0,0,1,K,bit}; // y, x, move, remain K , 시작점도 1로 카운트

        Queue<int[]> q = new LinkedList<>();
        // PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[2] - b[2]);  

        int[] dx = {1,0,-1,0}; 
        int[] dy = {0,1,0,-1}; // 우 하 좌 상 (목적지 = 우하)

        
        boolean[][][][] visit = new boolean[2][K+1][H][W];
        int[][][][] dist = new int[2][K+1][H][W]; //거리 

        // 초기화
        for(int t=0; t<2; t++){
            for(int k=0; k<=K; k++){
                for(int i=0; i<H; i++){
                    Arrays.fill(dist[t][k][i], Integer.MAX_VALUE);
                }
            }
        }

        q.offer(player);
        for(int k=0; k<=K; k++){
            visit[1][k][0][0]  = true;
        }
        
        while(!q.isEmpty()){
            int[] mk = q.poll();
            int y = mk[0];
            int x = mk[1];
            int cnt = mk[2];
            int crash = mk[3];
            int morning = mk[4];
            // 이미 더 짧은 경로로 방문했으면 스킵
            if(cnt > dist[morning][crash][y][x]) continue;

            if(y == (H-1) && x == (W-1)){
                if(answer == -1 || cnt < answer){
                    answer = cnt;
                }
                continue;  // break 대신 continue
            }
            //bit ^= 1; //반전 (1은 낮, 0은 밤)
            for(int drct=0; drct<4; drct++){
                int nextY = y + dy[drct];
                int nextX = x + dx[drct];
                int moveCnt = cnt+1;
                int remainCrash = crash;
                int dirTime = morning^1;
                
                if(nextX < 0 || nextY < 0 || nextX >= W || nextY >= H) continue; //장외 탈락
                // if(visit[dirTime][remainCrash][nextY][nextX]) continue; //같은 조건 방문해봄

                if(board[nextY][nextX] == 1){
                    if(remainCrash>0 && morning==1){//진입 가정 
                        remainCrash--;
                    }else if(remainCrash >0 && morning==0){//부술 힘은 있으나 저녁임, 낮으로 하루 대기후 진입 가정 
                        //밤새고 넣었다치고 count만 올려서 Queue 넣으면 되지않나? 그리고 부순 경우에는 무조건 저녁
                        dirTime^=1;
                        moveCnt++;
                        visit[dirTime][remainCrash][y][x] = true;//밤샜으니 낮밤이 바뀐 공간 방문처리
                        remainCrash--;
                    }else{//부술 힘 없음
                        continue;
                    }
                }
                
                // 더 짧은 거리로 도달 가능한 경우만 추가
                if(moveCnt < dist[dirTime][remainCrash][nextY][nextX]){
                    dist[dirTime][remainCrash][nextY][nextX] = moveCnt;
                    q.offer(new int[]{nextY, nextX, moveCnt, remainCrash, dirTime});
                }
                // int[] newPos = {nextY, nextX, moveCnt, remainCrash, dirTime};
                // q.offer(newPos);
                // visit[dirTime][remainCrash][nextY][nextX] = true;
            }
        }
        System.out.println(answer);
    } 
}