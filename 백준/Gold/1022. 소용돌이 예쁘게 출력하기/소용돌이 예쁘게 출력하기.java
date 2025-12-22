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

        // str = "-2 2 0 3";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int r1 = Integer.parseInt(args[0].split(" ")[0]);
        int c1 = Integer.parseInt(args[0].split(" ")[1]);
        int r2 = Integer.parseInt(args[0].split(" ")[2]);
        int c2 = Integer.parseInt(args[0].split(" ")[3]);

        int rows = r2-r1+1;
        int cols = c2-c1+1;

        int[][] map = new int[rows][cols]; // 3--3=1, 0--3+1 6행,4열
        
        
        // 0 <= r1 <= r2 <= 5000
        // 0 <= c1 <= c2 <= 5000
        
        //[0][0] = 1 , 
        // [n][n] = (2n+1)^2, [n][-n] = (2n+1)^2 - 6n
        // [-n][n] = (2n+1)^2 - 2n, [-n][-n] = (2n+1)^2 - 4n
        //기준라인은 X 모양 라인에 좌우, 8사분면

        //[-2][2] 
        for(int i=r1; i<=r2; i++){
            for(int j=c1; j<=c2; j++){
                //System.out.println("col: " + i+", row: "+j);
                //map 주소 
                int mapRowAddr = i-r1; 
                int mapColAddr = j-c1;
                //System.out.println("map col: " + mapXAddr+",map  row: "+mapYAddr);
                int absI = Math.abs(i);
                int absJ = Math.abs(j);
                int N = Math.max(absI, absJ); // 라인 최대

                if(i>=0 && j>=0){ // [n][n] = (2n+1)^2                    
                    if(i>=j){// 우하단선 좌측
                        int value = (2*N+1)*(2*N+1) - (N-j);
                        map[mapRowAddr][mapColAddr] = value;
                    }else{//
                        int value = (2*(N-1)+1)*(2*(N-1)+1) + (N-i);
                        map[mapRowAddr][mapColAddr] = value;
                    }
                }else if(i>=0 && j<0){ //  [n][-n] = (2n+1)^2 - 2n 
                    //[1][-1] = 7  
                    if(absI>=absJ){ //3사분면에서 좌측,
                        int value = (2*N+1)*(2*N+1) - 2*N;
                        value += (N-absJ);
                        map[mapRowAddr][mapColAddr] = value;
                    }else{  //3사분면 좌하단선에서 우측 
                        int value = (2*N+1)*(2*N+1) - 2*N;
                        value -= (N-absI);
                        map[mapRowAddr][mapColAddr] = value;
                    }
                }else if(i<0 && j>=0){ // [-n][n] = (2n+1)^2 - 6n
                    if(absI>=absJ){ //1사분면에서 좌측,
                        int value = (2*N+1)*(2*N+1) - 6*N;
                        value += (N-absJ);
                        map[mapRowAddr][mapColAddr] = value;
                    }else{  //1사분면 좌하단선에서 우측 
                        int value = (2*N+1)*(2*N+1) - 6*N;
                        value -= (N-absI);
                        map[mapRowAddr][mapColAddr] = value;
                    }
                }else if(i<0 && j<0){ // [-n][-n] = (2n+1)^2 - 4n
                    if(absI>=absJ){// 좌상단 우측
                        int value = (2*N+1)*(2*N+1) - 4*N;
                        value -= (N-absJ);
                        map[mapRowAddr][mapColAddr] = value;
                    }else{// 좌상단 좌측
                        int value = (2*N+1)*(2*N+1) - 4*N;
                        value += (N-absI);
                        map[mapRowAddr][mapColAddr] = value;
                    }
                }
            }
        }

        // for(int i=0; i<map.length; i++){
        //     System.out.println(Arrays.toString(map[i]));
        // }
        int maxLength = 0;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                int length = String.valueOf(map[r][c]).length();
                maxLength = Math.max(maxLength, length);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                sb.append(String.format("%" + maxLength + "d", map[r][c]));
                if (c != map[r].length - 1) {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        
        System.out.print(sb.toString());
        
    }
    /**
     *  A               B
     *  (r1, c1) ...  (r1,c2)
     *  
     *   ...
     * 
     *  C           D
     *  (r2, c1) ... (r2, c2)
     *  
     * 최악으로 보면
     * D가 (0,0)이고 ( r2 = 0, c2 = 0)
     * r1 = -5000, c1= 4
     * 0+5000+1  / 0-4
     * D = 5000,5000 / r1 = 4950 / r2 = 4996
     * 흠 실제 map의 좌표와 모눈종이 위치를 잡아야겠네
     */
    
}