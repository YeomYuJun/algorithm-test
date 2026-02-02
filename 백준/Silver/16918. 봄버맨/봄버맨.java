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
            
        // ///////////////////////////////////////////
        
        // str = "6 7 2\r\n" + //
        //                 ".......\r\n" + //
        //                 "...O...\r\n" + //
        //                 "....O..\r\n" + //
        //                 ".......\r\n" + //
        //                 "OO.....\r\n" + //
        //                 "OO.....";//

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int R = Integer.parseInt(args[0].split(" ")[0]); // 1 ~200
        int C = Integer.parseInt(args[0].split(" ")[1]); // 1~200
        int N = Integer.parseInt(args[0].split(" ")[2]); // 1~200

        int[][][] map = new int[R][C][2]; //번갈아가는 순서
        for(int i=0; i<R ; i++){
            String[] row = args[i+1].split("");
            for(int j=0; j<C; j++){
                if("O".equals(row[j])){
                    map[i][j][1] = 1;
                }
            }
        }

        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        //0. 0초
        int turn = 0;
        //1. 1초 대기
        turn++; //1초

        while(turn < N){
            //2. 나머지 모든 곳에 폭탄 설치
            turn++; //2초 => 짝수초에 설치. 2초엔 0 4초엔 1, 
            boolean turnChange = turn%4==0;
            for(int i=0; i<R ; i++){
                for(int j=0; j<C; j++){
                    if(!turnChange){
                        if(map[i][j][1] == 0){
                            map[i][j][0] = 1;
                        }
                    }else{
                        if(map[i][j][0] == 0){
                            map[i][j][1] = 1;
                        }
                    }
                    
                }
            }
            
            if(turn>=N) break;

            //3.  
            turn++; //3초 홀수초에 터짐.
            boolean turnChange2 = turn%4 == 1;
            for(int i=0; i<R ; i++){
                for(int j=0; j<C; j++){
                    if(!turnChange2){
                        if(map[i][j][1] == 1){
                            for(int d=0; d<4; d++){
                                int ny = i+dy[d];
                                int nx = j+dx[d];
                                if(nx < 0 || ny < 0 || nx >= C || ny >= R) continue; //장외
                                map[ny][nx][0] = 0; //폭탄 있을 경우 제거
                            }
                            map[i][j][1] = 0; //터진 폭탄 제거
                        }
                    }else{
                        if(map[i][j][0] == 1){
                            for(int d=0; d<4; d++){
                                int ny = i+dy[d];
                                int nx = j+dx[d];
                                if(nx < 0 || ny < 0 || nx >= C || ny >= R) continue; //장외
                                map[ny][nx][1] = 0; //폭탄 있을 경우 제거
                            }
                            map[i][j][0] = 0; //터진 폭탄 제거
                        }
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<R ; i++){
            for(int j=0; j<C; j++){
                if(map[i][j][1] == 1 || map[i][j][0] == 1){ 
                    sb.append("O");
                }else{
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString().trim());
        
    }
    /**
     * 가장 처음에 봄버맨은 일부 칸에 폭탄을 설치해 놓는다. 모든 폭탄이 설치된 시간은 같다.
    다음 1초 동안 봄버맨은 아무것도 하지 않는다.
    다음 1초 동안 폭탄이 설치되어 있지 않은 모든 칸에 폭탄을 설치한다. 즉, 모든 칸은 폭탄을 가지고 있게 된다. 폭탄은 모두 동시에 설치했다고 가정한다.
    1초가 지난 후에 3초 전에 설치된 폭탄이 모두 폭발한다.
    3과 4를 반복한다.
     */
    
}

 