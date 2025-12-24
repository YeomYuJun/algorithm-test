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

        // str = "3\r\n" + //
        //                 "3\r\n" + //
        //                 "10 30 20\r\n" + //
        //                 "20 40 10\r\n" + //
        //                 "2\r\n" + //
        //                 "10 30\r\n" + //
        //                 "20 40\r\n" + //
        //                 "1\r\n" + //
        //                 "50\r\n" + //
        //                 "30";//

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int T = Integer.parseInt(args[0]);

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<T; i++){
            int n = i*3+1;
            int size = Integer.parseInt(args[n]);
            
            String[] floor1 = args[n+1].split(" ");
            String[] floor2 = args[n+2].split(" ");
            
            int[][] sticker = new int[2][size];
            
            for(int j=0; j<size; j++){
                sticker[0][j] = Integer.parseInt(floor1[j]);
                sticker[1][j] = Integer.parseInt(floor2[j]);
            }

            int[][] dp = new int[2][size];

            dp[0][0] = sticker[0][0];
            dp[1][0] = sticker[1][0];
            if(size==1){
                sb.append(Math.max(dp[0][0], dp[1][0]));
                if(i<T-1){    
                    sb.append("\n");
                }
                continue;
            }
            dp[0][1] = Math.max(sticker[0][1] + sticker[1][0], sticker[0][0]);
            dp[1][1] = Math.max(sticker[0][0] + sticker[1][1], sticker[1][0]);
            //dp[y][x] = 0,0 부터 해당위치까지에서의 최댓값.

            for(int d=2; d<size; d++){
                dp[0][d] = Math.max(sticker[0][d] + dp[1][d-1], sticker[0][d] + dp[1][d-2]);
                dp[1][d] = Math.max(sticker[1][d] + dp[0][d-1], sticker[1][d] + dp[0][d-2]);
            }

            int max = -1;
            //max값 찾기.
            for(int dd=dp[0].length-2; dd<dp[0].length; dd++){
                max = Math.max(Math.max(max,dp[0][dd]), dp[1][dd]); 
            }
            sb.append(max);
            if(i<T-1){    
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }
    /**
     * dp네
     */
}
