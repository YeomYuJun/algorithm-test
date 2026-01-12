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
        
        // str = "";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].trim()); // 1<= N <=100

        int[][] matrix = new int[N][N];

        for(int i=1; i<=N; i++){
            String[] row = args[i].trim().split(" ");

            for(int j=0; j<N; j++){
                matrix[i-1][j] = Integer.parseInt(row[j]);
            }
        }

        int[][][] stacking = new int[N][N][2]; //[0] = 좌하단(-), [1] = 우하단(+)
        int max = Integer.MIN_VALUE;

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                int size = Math.min(i,j)+1; // 현재 부분행렬 최대 사이즈
                if(i>0){
                    if(j>0){
                        stacking[i][j][1] = stacking[i-1][j-1][1] + matrix[i][j];
                        //(i,j에서는 해당 사이즈의 모든 누적 연산이 이미 끝나있음)
                        if(size>1){ //5,4,3,2 까지 해당 모든 부분행렬체크
                            for(int k=size; k>1; k--){
                                int leftTop = (size==k) ? 0 : stacking[i-k][j-k][1];
                                int rightTop = stacking[i-k+1][j][0];
                                int rightBottom = stacking[i][j-k+1][0];
                                int rightTopToBottom = rightBottom - rightTop + matrix[i-k+1][j];
                                int cur = stacking[i][j][1];
                                int val = cur - (leftTop) - rightTopToBottom;
                                max = Math.max(max, val);
                            }
                        }
                    }else{
                        stacking[i][j][1] = matrix[i][j];
                    }

                    if(j<N-1){
                        stacking[i][j][0] = stacking[i-1][j+1][0] + matrix[i][j];
                    }else{
                        stacking[i][j][0] = matrix[i][j];
                    }
                }else{
                    stacking[i][j][1] = matrix[i][j];
                    stacking[i][j][0] = matrix[i][j];
                }
            }
        }
        System.out.println(max);
    }

    /** 
     *  부분행렬의 누적으로 보면
     * 
     */
}