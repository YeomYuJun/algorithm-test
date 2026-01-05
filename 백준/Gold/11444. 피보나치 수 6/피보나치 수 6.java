import java.io.*;
import java.util.*;

public class Main {
    
    final static long MOD = 1_000_000_007L;
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
        
        // str = "1000000000000000000";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        String N = args[0];
        long n = Long.parseLong(N);
        System.out.println(fibonacci(n));

    }
    public static long fibonacci(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        
        long[][] matrix = {{1, 1}, {1, 0}};
        long[][] result = matrixPow(matrix, n - 1);
        
        return result[0][0];
    }
    
    //행렬 제곱연산
    private static long[][] matrixPow(long[][] matrix, long exp) {
        if (exp == 1) {
            return matrix;
        }
        
        long[][] half = matrixPow(matrix, exp / 2);
        long[][] result = matrixMultiply(half, half);
        
        if (exp % 2 == 1) {
            result = matrixMultiply(result, matrix);
        }
        
        return result;
    }
    
    // 2x2 행렬 곱셈 (mod 연산 포함)
    private static long[][] matrixMultiply(long[][] a, long[][] b) {
        long[][] result = new long[2][2];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    result[i][j] = (result[i][j] + (a[i][k] * b[k][j]) % MOD) % MOD;
                }
            }
        }
        
        return result;
    }


    /**
     *  n은 1,000,000,000,000,000,000 ??? 100경
     * 10^16번째 피보나치 수를 10억 7로 나눈 나머지 출력
     *  1,000,000,007 로 나머지 출력
     * //Fn = Fn-1 + Fn-2
        //Fn-1 = Fn-2 + Fn-3
        //Fn-2 = Fn-3 + Fn-4
        //Fn = Fn-3 + Fn-4 + Fn-3 + Fn-3 + Fn-4
        //Fn = 3*Fn-3 + 2*Fn-4
        //Fn = 5*Fn-4 + 3*Fn-5
        //Fn = a / Fn-1 = b / Fn-2 = c.. base가 100경 그밑 이면 좀..역으로? 
        //Fn = p
        //Fn+1 = q
        //p, q, p+q, p+2q, 2p+3q, 3p + 5q, 5p + 8q, 8p+ 13q
        //0, 1,   2,    3,     4,       5,       6,       7
        //p계수 = 1,0,1,1,2,3,5,8, ...
        //q계수 = 0,1,1,2,3,5,8, ...
        Fn+x = Fibo(n-1)p + Fibo(n)q
            = Fibo(n-1)fibo(x)+ Fibo(n)fibo(x+1) ? /fibo(x) = p ,x+1 = q

        f(x) = g(a-1)p + g(a)q
        f(x) = g(a-1)g(b) + g(a)g(b+1)
        f(x) = g(x) = g(a-1)g(b) + g(a)g(b+1)  <-- 즉 a번째 원하는 거를 임의로 나눈걸로 선형적으로 계산가능?
        
        대충 분할정복하려면 반으로 치고
        x = 2n = 2m, 100경이면
        f(n+m) = f(n-1)f(m) + f(n)f(m+1)
        f(100경) = f(50경-1)f(50경) + f(50경)f(50경+1)
        f(100경) = f(50경) ( f(50경-1) + f(50경+1) ) 
        
        f(100경) = f(50경) ( f(50경-1) + f(50경+1) ) 
        f(50경-1) = f(25경-1)f(25경-1) + f(25경)f(25경) // n=25경, m=25경-1

        f(50경-1) = f(25경-1)^2 + f(25경)^2
        f(50경+1) = f(25경+1)^2 + f(25경)^2
        
         f(100경) = f(50경) * ( f(25경-1)^2 + f(25경)^2 + f(25경+1)^2 + f(25경)^2 ) ????????
        
        
     */
}
 