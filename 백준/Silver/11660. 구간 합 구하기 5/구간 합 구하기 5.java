import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        // ////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "4 3\r\n" + //
        //                 "1 2 3 4\r\n" + //
        //                 "2 3 4 5\r\n" + //
        //                 "3 4 5 6\r\n" + //
        //                 "4 5 6 7\r\n" + //
        //                 "2 2 3 4\r\n" + //
        //                 "3 4 3 4\r\n" + //
        //                 "1 1 4 4";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
         int N = Integer.parseInt(args[0].split(" ")[0]);
         int M = Integer.parseInt(args[0].split(" ")[1]);

         int[][] map = new int[N+1][N+1];
         for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            for(int j=1; j<=N; j++){
                int value = Integer.parseInt(row[j-1]);
                map[i][j] = value
                            + map[i-1][j]
                            + map[i][j-1]
                            - map[i-1][j-1];
            }
        }

        StringBuilder sb = new StringBuilder();

        for(int i=1+N; i<1+N+M; i++){
            String[] row = args[i].split(" ");
            
            int x1 = Integer.parseInt(row[0]);
            int y1 = Integer.parseInt(row[1]);
            int x2 = Integer.parseInt(row[2]);
            int y2 = Integer.parseInt(row[3]);
            // (x1, y1) ~ (x2, y2) 구간합

            int sum = map[x2][y2]
                    - map[x1-1][y2]
                    - map[x2][y1-1]
                    + map[x1-1][y1-1];
            // System.out.println(sum);
            sb.append(sum);
            if(i<N+M){
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }
    /**
     * 2중 for문돌면 x2-x1+1 * y2-y1+1 사이즈,
       * => 104,857,600,000 => 천억;
     * 
     * 메모이제이션? 
     * x1 ~ x2 까지의 구간합을 저장해두면
     * int[y][x] = value
     * int[y][x][0] = 0~50까지의 누적합
     * 공간은? 최대 2^10 *  2^10 * 2^10 / 2  = 1,073,741,824 5억  너무큼,,
     * 
     * 그렇다면 x1,y1, x2,y2를 정렬하고, yn의 x1~x2값을 저장해놓으면?
     * 다음 구간합 처리 중에서 new x1, new x2가 기존 저장 구간과 일치하는 부분이 있다면
     * 거기서 꺼내고, 나머지 모자라는 부분만 map에서 수행? 누적합 맵을 같이 넣고 계산
     * ㅁㅁㅁ     ㅁㅁㅁ     ㅁㅁ ?     ㅁㅁ ?    ? ? ?
     * ㅁㅁㅁ  => ㅁㅁㅁ  +  ㅁㅁ ?  -  ㅁㅁ ? +  ? ? ?
     * ㅁㅁㅇ     ? ? ?     ㅁㅁ ?     ? ? ?    ? ? ㅁ
     * 
     */


}
