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

        // str = "11 10\r\n" + //
        //                 "7 4 0\r\n" + //
        //                 "1 1 1 1 1 1 1 1 1 1\r\n" + //
        //                 "1 0 0 0 0 0 0 0 0 1\r\n" + //
        //                 "1 0 0 0 1 1 1 1 0 1\r\n" + //
        //                 "1 0 0 1 1 0 0 0 0 1\r\n" + //
        //                 "1 0 1 1 0 0 0 0 0 1\r\n" + //
        //                 "1 0 0 0 0 0 0 0 0 1\r\n" + //
        //                 "1 0 0 0 0 0 0 1 0 1\r\n" + //
        //                 "1 0 0 0 0 0 1 1 0 1\r\n" + //
        //                 "1 0 0 0 0 0 1 1 0 1\r\n" + //
        //                 "1 0 0 0 0 0 0 0 0 1\r\n" + //
        //                 "1 1 1 1 1 1 1 1 1 1"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);
        int r = Integer.parseInt(args[1].split(" ")[0]);
        int c = Integer.parseInt(args[1].split(" ")[1]);
        int d = Integer.parseInt(args[1].split(" ")[2]);

        //로봇
        RobotCleaner rob = new RobotCleaner(r,c,d);
        
        //map
        int[][] map = new int[N][M];
        for(int i=0; i<N; i++){
            String[] row = args[i+2].split(" ");
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(row[j]);
            }
        }
        while(rob.step(map)){
            //System.out.println(rob.r + ", " + rob.c);
        }
        System.out.println(rob.clean);

    }

    static class RobotCleaner {
        private int r;
        private int c;
        private int d; //방위
        private int clean;

        private final int[] dr = {-1,0,1,0};
        private final int[] dc = {0,1,0,-1};
        //초기화
        public RobotCleaner(int r, int c, int d){
            this.r = r;
            this.c = c;
            this.d = d;
        }

        public boolean step(int[][] map){
            //1.
            if(map[this.r][this.c] == 0){
                //doClean. 
                clean++;
                map[this.r][this.c] = 2; //갱신
                return true;
            }
            // 2
            else{  
                //주변 4칸 중 청소 덜 된 곳 찾기 
                boolean needClean = false;
                for(int i=0; i<4; i++){
                    if(scan(map, i) == 0){
                        needClean = true;
                    }
                }
                if(needClean){ //3번 
                    turn(); //일단 턴
                    int nr = this.r + dr[this.d];
                    int nc = this.c + dc[this.d];
                    if(map[nr][nc] == 0){ //바라보는 방향 비어있으면 이동
                        move(nr, nc);
                    }
                }else{ // 2번 //없음 
                    //뒤 탐색
                    int nd = (this.d+2)%4; //180도 회전
                    if(scan(map, nd) == 1){//후진 불가능
                        return false;
                    }else{
                        int br = this.r+dr[nd];
                        int bc = this.c+dc[nd];
                        move(br,bc);
                    }
                }
                return true;
            }
        }

        public void move(int nr, int nc){
            this.r = nr;
            this.c = nc;
        }
        public void turn(){
            this.d = (this.d+3)%4;
        }

        public int scan(int[][] map, int d){ //d방향 스캔 (가진 않음)
            int nr = this.r + dr[d];
            int nc = this.c + dc[d];
            //근데 1로 벽있어서 굳이 탐지안해도 될듯
            if(nr < 0 || nc < 0 || nr >= map.length || nc >= map[0].length) return 1;
            else return map[nr][nc];
        }

    }
    
    /**
     * 
     * 
     * 0북 1동 2남 3서
     * r-1 c+1 r+1 c-1
     * 가장자리는 전부 블럭
     *  1. 현재 칸이 아직 청소되지 않은 경우, 현재 칸을 청소한다.
        2. 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 없는 경우,
            2-1: 바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 한 칸 후진하고 1번으로 돌아간다.
            2-2: 바라보는 방향의 뒤쪽 칸이 벽이라 후진할 수 없다면 작동을 멈춘다.
        3. 현재 칸의 주변  4칸 중 청소되지 않은 빈 칸이 있는 경우,
            3-1: 반시계 방향으로 90도 회전한다.
            3-2: 바라보는 방향을 기준으로 앞쪽 칸이 청소되지 않은 빈 칸인 경우 한 칸 전진한다.
            3-3: 1번으로 돌아간다.
     */
}