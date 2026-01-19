import java.io.*;
import java.util.*;
import java.util.Map.Entry;

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
        
        
        // str = "5 5\r\n" + //
        //                 "-10 -10 11 -10 -10\r\n" + //
        //                 "-10 -10 11 -10 -10\r\n" + //
        //                 "-10 -10 20 10 -10\r\n" + //
        //                 "13 12 10 10 -10\r\n" + //
        //                 "-10 -10 -10 -10 -10"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);

        int[][] matrix = new int[N][M];

        int max = Integer.MIN_VALUE;

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<M; j++){
                int val = Integer.parseInt(row[j]);

                matrix[i][j] = val;
                if(i>0){
                    matrix[i][j] += matrix[i-1][j];
                }
                if(j>0){
                    matrix[i][j] += matrix[i][j-1];
                }
                if(i>0 && j>0){
                    matrix[i][j] -= matrix[i-1][j-1];
                }
                

                //[i-x][j-x]부터 [i-1][j-1]까지 누적합 max 테스트
                //(i,j)에서 (0,0)까지 만들 수 있는 부분행렬 테스트
                for(int x=1; x<=j+1; x++){
                    for(int y=1; y<=i+1; y++){
                        int topVal = y>i ? 0 :  matrix[i-y][j];
                        int leftVal = x>j ? 0 :  matrix[i][j-x];
                        int diaVal = y>i||x>j ? 0 :  matrix[i-y][j-x];
                        
                        int partSum = matrix[i][j] - topVal - leftVal + diaVal;
                        if(partSum>max){
                            max = Math.max(max,partSum);
                        }
                    } 
                }
            }
        }

        // for(int i=0; i<N; i++){
        //     System.out.println(Arrays.toString(matrix[i]));
        // }
        System.out.println(max);
    }
    /**
     * 200*200*100000 = 400,000,000b int고 일단
     */

}