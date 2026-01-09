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
        
        // str = "5 10\r\n" + //
        //                 "1 0 0 0 1\r\n" + //
        //                 "1 0 0 0 1\r\n" + //
        //                 "1 0 0 0 1\r\n" + //
        //                 "1 0 0 0 1\r\n" + //
        //                 "1 0 0 0 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    static final int MOD = 1_000;
    

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // N, 2~5
        long B = Long.parseLong(args[0].split(" ")[1]); //제곱 횟수 1000억 이하
        //100,000,000,000 < 2^37

        int[][] ONE = new int[N][N];
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(i==j) {
                    ONE[i][j] = 1;
                }else{
                    ONE[i][j] = 0;
                }
            }
        }

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
        AA = powerDivideSum(B, AA, A, ONE);

        StringBuilder sb = new StringBuilder();
    
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

    //B제곱 연산 = B/2 제곱 연산 + B/2 제곱 연산 + B%2 제곱연산
    public static int[][] powerDivideSum(long B,int[][] AA, int[][] A, int[][] ONE){
        if(B==1){
            return AA;
        }
        long half = B/2; 
        boolean isRemain = B%2 == 1; // 홀수 남는지?

        // 25라면 halfMatrix = 12, halfAndOne = 1+12,  bPower = 25
        // A^12(A+A^12)+A25 = A1~A25 sum 
        // > (A+A^12)가 아니라 (단위행렬+A^12) 였음

        int[][] AAA = new int[A.length][A.length];
        int[][] halfMatrix = powerDivideSum(half, AA, A, ONE); //

        // (1+half) 담당  1과 half 제곱값 1이 단위행렬임
        int[][] halfAndOne = matrixSum(ONE, powerDivide(half, AA, A));
        
        AAA = matrixPower(halfMatrix, halfAndOne);
        if(isRemain){
            
            int[][] bPower = powerDivide(B,AA,A);
            AAA = matrixSum(AAA, bPower);
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
                for(int k=0; k<N; k++){ //곱연산 후 저장할 때 mod
                    sum += A[i][k] * B[k][j];
                }
                sum %= MOD;
                AA[i][j] =  sum;
            }
        }
        return AA;
    }

    //행렬 덧셈 연산
    public static int[][] matrixSum (int[][] A, int[][] B){
        int N = A.length;
        int[][] AA = new int[N][N];

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                AA[i][j] =  (A[i][j] + B[i][j])%MOD;
            }
        }
        return AA;
    }

    /**
     * A^100 + A^99 + .. A^2 + A^1. 이거는 ... A^100 = A^50 * A^50 으로 구했는데
     * 제곱 계수 별 행렬을 저장해나가면서 있으면 꺼내쓰고, 없으면 divide하면서 추가?
     * 1000,500,250,125,63,62,32,31,16,15,8,7,4,3,2,1 까지 한번에 되는데 나머지는?
     * 999,499, ..=> , 998,498,249, ..=>  애매함.
     * 
     * A { a11 a12     
     *     a21 a22 }  a[ij]라고 할 때 규칙? a[11]이 A^100까지 누적합... A^100+A^1  + A^99+A^2 
     * 
     *  A^1 ~ A^B  =  >   divide(B) => (1+2/B)*(divie)
     * 
     * x1 x2 x3 x4 x5 x6 x7
     * =>  (1+2+3) + 3(1+2+3)  + x7  (x^1+x^2+x^3) = t
     * B-1 = (x+3x)t + x^7. 
     * 50(1~50) > 50 * (25(1~25)) > 50*(25*(12(1~12)+25))
     * 
     */
}