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
        
        // str = "1 4\r\n" + //
        //                 "0010"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);

        int[][] rect = new int[N][M];
        int maxArea = Integer.MIN_VALUE;

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split("");
            for(int j=0; j<M; j++){
                int cell = Integer.parseInt(row[j]); // (i,j) cell
                if(cell == 1) {
                    if(i == 0 || j == 0) {
                        rect[i][j] = 1;
                    } else {
                        rect[i][j] = Math.min(Math.min(rect[i-1][j], rect[i][j-1]), rect[i-1][j-1]) + 1;
                    }
                    maxArea = Math.max(maxArea, rect[i][j]);
                }
            }
        }
        System.out.println(maxArea*maxArea);
    }
    /**
     * 1= 1이고
     * 1(1) 1(1) 1(1)
     * 1(1) 1(2) 1(2)
     * 1(1) 1(2) 1(3)
     * ...
     * 이 되는건데
     * 
     * 주변에서 가장 min + 1? 
     * 
     */

}