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
        
        // str = "8 10\r\n" + //
        //                 "##########\r\n" + //
        //                 "#........#\r\n" + //
        //                 "#...#...##\r\n" + //
        //                 "#.O....B.#\r\n" + //
        //                 "#......R.#\r\n" + //
        //                 "#........#\r\n" + //
        //                 "#........#\r\n" + //
        //                 "##########";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // 3~10
        int M = Integer.parseInt(args[0].split(" ")[1]); //3~10

        int[][] board = new int[N][M];


        int oy = 0; int ox = 0;

        int rx = 0; int ry = 0;

        int bx = 0; int by = 0;

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split("");
            for(int j=0; j<M; j++){
                String cell = row[j];
                if(cell.equals("#")){
                    board[i][j] = 1;
                }else if(cell.equals(".")){
                    board[i][j] = 0;
                }else if(cell.equals("O")){
                    oy=i; ox=j;
                    board[i][j] = 2;
                }else if(cell.equals("B")){
                    by=i; bx=j;
                    board[i][j] = 0;
                }else if(cell.equals("R")){
                    ry=i; rx=j;
                    board[i][j] = 0;
                }
            }
        }
        if(rx==0 && ry == 0){
            System.out.println(-1);
            return;
        }
        if(bx==0 && by == 0){
            System.out.println(-1);
            return;
        }
        if(ox==0 && oy == 0){
            System.out.println(-1);
            return;
        }


        int[] balls = new int[]{ry,rx, by, bx, 0};
        boolean[][][][] visit = new boolean[N][M][N][M];
        
        Queue<int[]> q = new LinkedList<>();
        
        q.offer(balls);
        visit[ry][rx][by][bx] = true; 

        while(!q.isEmpty()){
            int[] BALLS = q.poll();
            int curRY = BALLS[0]; int curRX = BALLS[1];
            int curBY = BALLS[2]; int curBX = BALLS[3];
            int curMove = BALLS[4];
            // System.out.println(Arrays.toString(BALLS));

            if(curMove>=10){ //조기 종료 조건
                System.out.println(-1);
                return;
            }
            for(int i=1; i<=4; i++){
                int[] newBall = Arrays.copyOf(BALLS,5);
                tilt(i, newBall, board, oy, ox);
                
                boolean isRedFall = isFall(BALLS[0], BALLS[1], newBall[0], newBall[1], oy, ox);
                boolean isBlueFall = isFall(BALLS[2], BALLS[3], newBall[2], newBall[3], oy, ox);
                
                if(isBlueFall) continue;
                else if(isRedFall){
                    System.out.println(newBall[4]);
                    return;
                }else{
                    //방문처리는 좀 유하게 처리해야하는 거 같은데
                    if(visit[newBall[0]][newBall[1]][newBall[2]][newBall[3]])continue;
                    visit[newBall[0]][newBall[1]][newBall[2]][newBall[3]] = true;
                    q.add(newBall);
                }
            }
        }
        System.out.println(-1);
        return;
    }
    public static boolean isFall(int py, int px, int cy, int cx, int oy, int ox){ 
        // curr 위치가 목표 지점인 경우
        if (cy == oy && cx == ox) {
            return true;
        }
        
        // prev에서 curr로 이동하는 경로에 (oy, ox)가 있는지 체크
        // 수직 이동
        if (px == cx && px == ox) {
            int minY = Math.min(py, cy);
            int maxY = Math.max(py, cy);
            if (minY < oy && oy < maxY) {
                return true;
            }
        }
        
        // 수평 이동
        if (py == cy && py == oy) {
            int minX = Math.min(px, cx);
            int maxX = Math.max(px, cx);
            if (minX < ox && ox < maxX) {
                return true;
            }
        }

        return false;
    }

    public static void tilt(int degree, int[] BALLS, int[][] board, int oy, int ox){ // 1동 2남 3서 4북
        int curRY = BALLS[0];
        int curRX = BALLS[1];

        int curBY = BALLS[2];
        int curBX = BALLS[3];
        //RED가 빠지면 블루도 해당 위치로 빠질 수 있음(더 이동할 수 있음.)
        //반대로 블루가 빠진 건, 다음 isFall에서 판별되니까 여기선 red만, 우선이동 일 때 판별하면 될듯
        boolean isRedFall = false;
         
        if(degree==1 || degree==3){
            int d = degree == 1 ? 1 : -1;
            if((curRX>=curBX && d>0 )|| (curRX<=curBX && d<0)){ //RED가 우선
                while(board[curRY][curRX+d] != 1){
                    curRX+=d;
                }
                isRedFall = isFall(BALLS[0], BALLS[1], curRY, curRX, oy, ox);
                while(board[curBY][curBX+d] != 1){
                    if(curRY == curBY && curBX+d == curRX && !isRedFall) break;
                    curBX+=d;
                }
            }else{
                while(board[curBY][curBX+d] != 1){
                    curBX+=d;
                }
                while(board[curRY][curRX+d] != 1){
                    if(curRY == curBY && curRX+d == curBX) break; 
                    curRX+=d;
                }
            }
        }

        if(degree==2 || degree==4){
            int d = degree == 2 ? 1 : -1;
            if((curRY>=curBY && d>0 )|| (curRY<=curBY && d<0)){ //RED가 우선
                while(board[curRY+d][curRX] != 1){
                    curRY+=d;
                }
                isRedFall = isFall(BALLS[0], BALLS[1], curRY, curRX, oy, ox);
                while(board[curBY+d][curBX] != 1 ){
                    if(curRX == curBX && curBY+d == curRY && !isRedFall) break;
                    curBY+=d;
                }
            }else{
                while(board[curBY+d][curBX] != 1){
                    curBY+=d;
                }
                while(board[curRY+d][curRX] != 1){
                    if(curRX == curBX && curRY+d == curBY) break;
                    curRY+=d;
                }
            }
        }
        BALLS[0] = curRY;
        BALLS[1] = curRX;
        BALLS[2] = curBY;
        BALLS[3] = curBX;
        BALLS[4]++;
    }
    /**
     * .'은 빈 칸을 의미하고, '#'은 공이 이동할 수 없는 장애물 또는 벽을 의미하며, 'O'는 구멍의 위치를 의미한다. 'R'은 빨간 구슬의 위치, 'B'는 파란 구슬의 위치이다.
     * 
     * BFS로 대충 뚝딱인가?
     * 1.빨간 공 탈출
     * 2.파란공은 같이 움직이지만 방해물
     * 3.O로 탈출
     * 4.기울이기에서 겹쳐지는 순서로 이동.
     * 5.파란 구슬이 구멍에 빠지면 실패
     * 6.빨간 구슬과 파란 구슬이 동시에 구멍에 빠져도 실패
     */
}
