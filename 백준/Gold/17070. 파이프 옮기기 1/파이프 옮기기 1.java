import java.io.*;
import java.util.*;

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
        
        // str = "16\r\n" + //
        //                 "0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0\r\n" + //
        //                 "0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0\r\n" + //
        //                 "0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0\r\n" + //
        //                 "0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0\r\n" + //
        //                 "0 0 0 0 1 0 0 0 0 1 0 0 1 0 0 0\r\n" + //
        //                 "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\r\n" + //
        //                 "0 0 0 1 1 0 0 1 1 0 0 0 0 0 1 0\r\n" + //
        //                 "0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0\r\n" + //
        //                 "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\r\n" + //
        //                 "0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0\r\n" + //
        //                 "0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0\r\n" + //
        //                 "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\r\n" + //
        //                 "0 0 0 1 0 0 0 1 0 0 0 1 0 1 0 0\r\n" + //
        //                 "0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0\r\n" + //
        //                 "0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 1\r\n" + //
        //                 "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); //N

        int[][] map = new int[N][N];

        for(int i=1; i<=N; i++){ //맵 초기화
            String[] row = args[i].split(" ");
            for(int j=0; j<N; j++){
                map[i-1][j] = Integer.parseInt(row[j]);
            }
        }


        int[][] routeMap = new int[N][N];

        int[][][] dp = new int[N][N][3];
        routeMap[0][1] = 1;
        dp[0][1][0] = 1;
        if(map[N-1][N-1] == 0){   
            for(int i=0; i<N; i++){ //그냥 우측 출구 기준
                for(int j=0; j<N; j++){
                    if(map[i][j]==1) continue;
                    if(j>=1){
                        dp[i][j][0] += dp[i][j-1][0];
                        dp[i][j][0] += dp[i][j-1][2];
                    }
                    if(i>=1){
                        dp[i][j][1] += dp[i-1][j][1];
                        dp[i][j][1] += dp[i-1][j][2];
                    }
                    if(i>=1 && j>=1){
                        if(map[i-1][j]==0 && map[i][j-1]==0){
                            dp[i][j][2] += dp[i-1][j-1][0];
                            dp[i][j][2] += dp[i-1][j-1][1];
                            dp[i][j][2] += dp[i-1][j-1][2];
                        }
                    }
                }
            }
            /*
            Pipe pipe = new Pipe();
            Queue<Pipe> q = new LinkedList<>();        
            q.offer(pipe);
            while(!q.isEmpty()){
                Pipe p = q.poll();
                
                List<Pipe> moveList = p.getMoveable(map);
                for(int i=0; i<moveList.size(); i++){
                    Pipe next = moveList.get(i);
                    q.add(next);
                    routeMap[next.rightY][next.rightX]++;
                }
            }
            */
        }
        // System.out.println(routeMap[N-1][N-1]);
        System.out.println(dp[N-1][N-1][0]+dp[N-1][N-1][1]+dp[N-1][N-1][2]);
    }
    /**
     * 방법의 수? bfs로면, 모든 경우를 다 넣으면.. move가 정해져있으니 겹치는 수는 안 나올 거 같은데 
     * bfs는 일단 시간초과?
     * 1초 512MB. 16*16 사이즈에 이동경우 [][]에 하나씩 추가
     * 
     */

    static class Pipe {
        public int leftX;
        public int leftY;
        public int rightX;
        public int rightY;

        @Override
        public String toString(){
            return "L:("+leftX+", " + leftY+")  R:("+rightX+", "+rightY+")";
        }

        public Pipe(){ //기본 생성자 
            this.leftX = 0;
            this.leftY = 0;
            this.rightX = 1;
            this.rightY = 0;
        }
        public Pipe(int nlx, int nly, int nrx, int nry){//생성자
            this.leftX = nlx;
            this.leftY = nly;
            this.rightX = nrx;
            this.rightY = nry;
        }

        public int getState(){ //1 = 가로, 2= 세로, 3=대각선
            int yGap = rightY-leftY;
            int xGap = rightX-leftX;

            if(xGap > 0 && yGap > 0){
                return 3;
            }else if(xGap==0 && yGap > 0 ){
                return 2;
            }else if(xGap > 0 && yGap == 0){
                return 1;
            }else{
                return -1; //에러 case
            }
        }

        public List<Pipe> getMoveable(int[][] map){
            List<Pipe> list = new ArrayList<>();
            int curState = getState(); 
            int N = map.length;
            if(curState==1){//가로
                if(this.rightX < N-1 && map[this.rightY][this.rightX+1] == 0){
                    Pipe p = new Pipe(this.leftX+1, this.leftY, this.rightX+1, this.rightY);//우측 빔.
                    list.add(p);
                    if(this.rightY < N-1 && map[this.rightY+1][this.rightX+1] == 0 && map[this.rightY+1][this.rightX] == 0){//해당 사항 및 대각 좌측 빔.
                        Pipe p2 = new Pipe(this.leftX+1, this.leftY, this.rightX+1, this.rightY+1);
                        list.add(p2);
                    }
                }
            }else if(curState == 2){//세로
                if(this.rightY < N-1 && map[this.rightY+1][this.rightX] == 0){
                    Pipe p = new Pipe(this.leftX, this.leftY+1, this.rightX, this.rightY+1);
                    list.add(p);
                    if(this.rightX < N-1 && map[this.rightY+1][this.rightX+1] == 0 && map[this.rightY][this.rightX+1] == 0){
                        Pipe p2 = new Pipe(this.leftX, this.leftY+1, this.rightX+1, this.rightY+1);
                        list.add(p2);
                    }
                }
            }else{ //대각선은 모든 케이스 가능 
                if(this.rightX < N-1 && map[this.rightY][this.rightX+1] == 0){
                    Pipe p = new Pipe(this.leftX+1, this.leftY+1, this.rightX+1, this.rightY);
                    list.add(p);
                }
                if(this.rightY < N-1 && map[this.rightY+1][this.rightX] == 0){
                    Pipe p2 = new Pipe(this.leftX+1, this.leftY+1, this.rightX, this.rightY+1);
                    list.add(p2);
                }
                if(this.rightY < N-1  && this.rightX < N-1
                    && map[this.rightY+1][this.rightX] == 0 && map[this.rightY][this.rightX+1] == 0
                    && map[this.rightY+1][this.rightX+1] == 0){
                    Pipe p3 = new Pipe(this.leftX+1, this.leftY+1, this.rightX+1, this.rightY+1);
                    list.add(p3);
                }
            }
            
            return list;
        }

    }   
}
 
