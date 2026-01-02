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
        
        // str = "2\r\n" + //
        //                 "2 0 0\r\n" + //
        //                 "1 0 99";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 계단 수

        //초기화
        int[][] stair = new int[N+1][3];
        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            stair[i][0] = Integer.parseInt(row[0]);
            stair[i][1] = Integer.parseInt(row[1]);
            stair[i][2] = Integer.parseInt(row[2]);
        }

        int[] maxPrev = new int[3];  // i-1층
        int[] maxCurr = new int[3];  // i층
        int[] minPrev = new int[3];
        int[] minCurr = new int[3];
        
        // 첫 층 초기화
        maxPrev[0] = minPrev[0] = stair[1][0];
        maxPrev[1] = minPrev[1] = stair[1][1];
        maxPrev[2] = minPrev[2] = stair[1][2];
        
        for(int i = 2; i <= N; i++){
            maxCurr[0] = Math.max(maxPrev[0], maxPrev[1]) + stair[i][0];
            minCurr[0] = Math.min(minPrev[0], minPrev[1]) + stair[i][0];
            
            maxCurr[1] = Math.max(Math.max(maxPrev[0], maxPrev[1]), maxPrev[2]) + stair[i][1];
            minCurr[1] = Math.min(Math.min(minPrev[0], minPrev[1]), minPrev[2]) + stair[i][1];
            
            maxCurr[2] = Math.max(maxPrev[1], maxPrev[2]) + stair[i][2];
            minCurr[2] = Math.min(minPrev[1], minPrev[2]) + stair[i][2];
            
            int[] tempMax = maxPrev;
            maxPrev = maxCurr;
            maxCurr = tempMax;
            
            int[] tempMin = minPrev;
            minPrev = minCurr;
            minCurr = tempMin;
        }
        
        int maxResult = Math.max(Math.max(maxPrev[0], maxPrev[1]), maxPrev[2]);
        int minResult = Math.min(Math.min(minPrev[0], minPrev[1]), minPrev[2]);
        System.out.println(maxResult + " " + minResult);
    }

}
 