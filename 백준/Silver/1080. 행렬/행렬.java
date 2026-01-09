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
        
        // str = "1 1\r\n" + //
        //                 "1\r\n" + //
        //                 "0";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]); 

        //NxM 크기의 행렬
        int[][] A = new int[N][M];
        int[][] B = new int[N][M];
        int[][] C = new int[N][M];


        for(int i=1; i<=N; i++){
            String[] row = args[i].split("");
            for(int j=0; j<M; j++){
                A[i-1][j] = Integer.parseInt(row[j]);
            }
        }
        for(int i=1+N; i<=2*N; i++){
            String[] row = args[i].split("");
            for(int j=0; j<M; j++){
                B[i-N-1][j] = Integer.parseInt(row[j]);
                C[i-N-1][j] = B[i-N-1][j] != A[i-N-1][j] ? 1 : 0;
            }
        }

        //C라는 변환이 필요한 여부의 맵으로 완성시키면?
        //각 영역의 최소 전환 횟수?
        //어차피 [0][0]의 전환 가능여부는 [0][0]밖에 없고 , 
        // [0][1]도 [0][0]의 확정 전환 이후 전환이 고정되니까 리니어하게 스캔하면?
        
        int answer = 0;

        boolean remainOne = false; //3x3보다 작으면 어차피 for문 안돌아서 안걸릴듯
        for(int i=0; i<=N-3;i++){
             for(int j=0; j<=M-3; j++){
                if(C[i][j] == 1){
                    reverse(C, i, j);
                    answer++;
                }
             }
        }
        checkLoop:
        for(int i=0; i<N;i++){
            for(int j=0; j<M; j++){
                if(C[i][j] == 1){
                    remainOne = true;
                    break checkLoop;
                }
            }
        }
        if(remainOne){
            System.out.println(-1);
        }else{
            System.out.println(answer);
        }
    }

    public static void reverse(int[][] map, int y, int x){
        for(int i=y; i<y+3; i++){
            for(int j=x; j<x+3; j++){
                map[i][j] = (map[i][j]+1)%2;
            }
        }
    }
    /**
     * A->B 의 최소 연산 
     * 뒤집기는 3x3 크기의 1->0->1 변환 연산.
     * 해당 횟수의 최소값 
     * 
     * 
     */

}