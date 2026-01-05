import java.io.*;
import java.util.*;

public class Main {
    private static int MIN = Integer.MAX_VALUE;
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
        
        // str = "4\r\n" + //
        //                 "1 0 0 0";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        String[] row = args[1].split(" ");
        int[] num = new int[N];

        if(N == 1){ // 다음 수 무한.
            System.out.println("A");
            return;
        }
        
        for(int i=0; i<N; i++){
            num[i] = Integer.parseInt(row[i]);
        }
        
        if(N==2){ //2개가 같으면 gap이 0으로 반복
            if(num[0] == num[1]){
                System.out.println(num[0]);
                return;
            }else{ // 2개가 같지 않으면 무한
                System.out.println("A");
                return;
            }
        }
        if(N>2){
            if(num[0] == num[1]){
                boolean allSame = true;
                for(int i=2; i<N; i++){
                    if(num[i] != num[0]){
                        allSame = false;
                        break;
                    }
                }
                if(allSame){ //모든 수가 같음
                    System.out.println(num[0]);
                    return;
                }else{  // x x 2x 3x ?  말이 안 됨.
                    System.out.println("B");
                    return;
                }
            }

            int x = num[0];
            int axb = num[1];
            //a^2x + ab + b = num[2];
            //ax+b = num[1];
            //a^2x + ab -ax =  num[2] - num[1];
            //a(ax+b-x) = num[2]-num[1];
            //a(num[1]-num[0]) = num[2]-num[1];
            //a^(n-1)*(num[1]-num[0]) = num[n] - num[n-1];
            int[] an = new int[N+1];
            int d = axb - x;
            int b = Integer.MAX_VALUE;
            boolean aInteger = true;
            boolean isCorrect = true;
            for(int i=2; i<N; i++){
                //a = a^(i-1);

                double a = (double)(num[i]-num[i-1]) / (double)d;

                if(a%1.0 != 0.0){ //a가 정수가 아님
                    aInteger = false;
                    break;
                }

                an[i-1] = (int)a;

                if(b == Integer.MAX_VALUE){
                    b = num[1] - (an[1]*num[0]);
                }

                if(num[i] != ((num[i-1] * an[1]) + b)){
                    isCorrect = false;
                    break;
                }
            }
            if(!aInteger){
                System.out.println("B");
                return;
            }
            if(!isCorrect){
                System.out.println("B");
                return;
            }
            
            int result = num[N-1]*an[1] + b;
            // System.out.println(Arrays.toString(an));
            System.out.println(result);

        }
    }
    /**
     * i=0 : x
     * i=1 : ax+b
     * i=2 : a(ax+b)+b
     * i=3 : a(a(ax+b)+b)+b 
     * i=4 : a(a(a(ax+b)+b)+b)+b 
     * 전개하면 
     * >    a^2x + ab + b = i2
     * >    a^3x +a^2b + ab + b = i3
     * >    a^4x + a^3b + a^2b + ab + b = i4
     * 일반식으로 보면?
     * 
     * ex 예제1 
     * x = 1;
     * a+b = 4
     * a^2 + ab + b = 13
     * a^3 + a^2b + ab + b = 40
     * 
     * a^4 + a^3b + a^2b + ab + b = X
     * a^4 + a^3b - a^3 = X - 40
     * 
     * a^3(a+b-1) = X-40
     * 
     * a^(i-1) * (a+b-1) + num[i-1] = X; 
     * a^3 * 3 = X-40
     * a^3 * 3 + 40 = X;
     * 
     * 3a^2 + 13 = 40;
     * 3a^2 = 27;
     * a^2 = 9;
     * a = 3;
     * b = 1;
     */

}
 
