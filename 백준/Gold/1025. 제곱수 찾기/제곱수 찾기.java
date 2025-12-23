import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

        // str = "5 9\r\n" + //
        //                 "135791357\r\n" + //
        //                 "357913579\r\n" + //
        //                 "579135791\r\n" + //
        //                 "791357913\r\n" + //
        //                 "913579135";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);
        
        int[][] map = new int[N][M];
        for(int i=0; i<N; i++){
            String[] rows = args[i+1].split("");
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(rows[j]);
            }
        }
        
        int max = -1;

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                //시작지점 row = i , col = j, [i][j];
                //0으로 시작 불가능
                if(map[i][j] == 0){
                    max = Math.max(max, 0);
                    continue;
                }
                
                //기본값.
                StringBuilder sb = new StringBuilder();
                String startNum = String.valueOf(map[i][j]);
                //제자리 검증
                if(Math.sqrt(map[i][j]) % 1 == 0.0){
                    max = Math.max(max, map[i][j]);
                }
                
            
                //-i부터 N-1-i까지  i일 때 -i 까지
                for(int yGap=-i; yGap<=N-1-i; yGap++){
                    for(int xGap=-j; xGap<=M-1-j; xGap++){
                        //여기부터 모든 gap 경우 확인 
                        //갭이 맵을 이탈할 때까지 append
                        int nextRow = i+yGap;
                        int nextCol = j+xGap;
                        boolean isInside = true;
                        //초기화
                        sb.setLength(0);
                        sb.append(startNum);

                        if(yGap == 0 && xGap == 0){
                            continue;
                        }

                        while(isInside){
                            //탈출 조건
                            if(nextRow < 0 || nextCol < 0 || nextRow >= N || nextCol >= M){
                                isInside = false;
                                break;
                            }
                            //추가
                            sb.append(map[nextRow][nextCol]);

                            //검증
                            String comStr = sb.toString();
                            int comple = Integer.parseInt(comStr);
                            if(Math.sqrt(comple) % 1 == 0.0){
                                max = Math.max(max, comple);
                            }

                            //갱신
                            nextRow+=yGap;
                            nextCol+=xGap;
                        }
                        
                    }
                }
                

            }
        }
        System.out.println(max);
    }
    /**
     * nm<=9 => 
     * 만들 수 있는 수 10^9 사이즈
     * sqrt가 정수인가? 를 보면 되나
     * 제곱수를 10^10-1 까지..는 좀 
     * (1,1),(2,4),(3,7) 등 등차로 만들 수 있는 등차수열? 
     * xGap, yGap의 변동이 없으면 진행? 보다는 조합을 골라야함
     * 
     * 모든 경우의 수를 뽑는 방법은? 
     * 등차 =  +,-,0 가능. 
     * x등차 -n, .. 0, ..n인 경우 
     * y등차 -n, .. 0, .. n 인경우
     * 흠 -n인 경우. n일 때 1번이지
     * 모든 시작지점에서 가능한 gap을 테스트?
     */



}