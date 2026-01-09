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
        
        // str = "3 3\r\n" + //
        //                 "1 2 3\r\n" + //
        //                 "4 5 6\r\n" + //
        //                 "7 8 9";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    static final int MOD = 1_000;
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // N, 2~5
        long B = Long.parseLong(args[0].split(" ")[1]); //제곱 횟수 1000억 이하
        //100,000,000,000 < 2^37

        // 2^1000 =  2^500 + 2^500 =>   ( 2^500 % mod + 2^500 % mod  ) % mod => 2%mod =>>
        int[][] A = new int[N][N];
        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" "); 
            for(int j=0; j<N; j++){
                int e = Integer.parseInt(row[j])%MOD;
                A[i-1][j] = e;
            }
        }

        int[][] AA = new int[N][N];
        AA = A;
        AA = powerDivide(B, AA, A);

        StringBuilder sb = new StringBuilder();
    
        // System.out.println(Arrays.toString(AA[i]));
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                sb.append(AA[i][j]);
                if(j<N-1){
                    sb.append(" ");
                }
            }
            if(i<N-1){
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }
    
    //B제곱 연산 = B/2 제곱 연산 + B/2 제곱 연산 + B%2 제곱연산
    public static int[][] powerDivide(long B,int[][] AA, int[][] A){
        if(B==1){
            return AA;
        }
        long half = B/2; 
        boolean isRemain = B%2 == 1; // 홀수 남는지?
        
        int[][] AAA = powerDivide(half, AA, A);
        AAA = matrixPower(AAA, AAA);
        if(isRemain){
            AAA = matrixPower(AAA, A);
        }
        return AAA;
    }
    
    //행렬 곱 연산
    public static int[][] matrixPower(int[][] A, int[][] B){
        int N = A.length;
        int[][] AA = new int[N][N];

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                // 왼쪽 행렬의 행벡터(A) + 우측 행렬의 열벡터(A)의 내적
                int sum = 0;
                for(int k=0; k<N; k++){ //곱연산 후 저장할 때 mod 해야하는가.  
                    sum += A[i][k] * B[k][j];
                }
                sum %= MOD;
                AA[i][j] =  sum;
            }
        }

        return AA;
    }

    /**
     * 교환 X, 결합 O, 분배 O 인데 좌=우 니까 교환도 성립
     * 그럼 제곱연산에 대해 분할 정복해버리면?
     * matrixPower 를 B/2번 반복해버리는걸 넣어버리는거지
     */
}