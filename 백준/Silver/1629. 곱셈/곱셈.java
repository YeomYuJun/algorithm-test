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

        // str = "2147483647 2147483647 100001";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
         int A = Integer.parseInt(args[0].split(" ")[0]);
         int B = Integer.parseInt(args[0].split(" ")[1]);
         int C = Integer.parseInt(args[0].split(" ")[2]);

         if(C==1){//1로 나누면 무조건 0임
            System.out.println(0);
            return;
         }
         //계산 시 mod하면 되는데, B가 커지면? B를 쪼개
         long result = multiple(A, B, C);
         System.out.println(result%C);
         //C-1이 최대값, C가 너무 크면? 21억, 21억, 21억+1 이라면? 
         
    }

    public static long multiple(int A, int B, int C){
        if(B==0){
            return 1;
        }
        if(B==1){
            return A%C;
        }
        long half = multiple(A, B/2, C);
        long result =  (half*half) % C;

        if(B%2==1){
            result = (result*A)%C;
        }
        return result;
    }

    /**
     * 
     * 2,147,483,647 미만
     * 최악 = ( 10*10*10*10 ...) mod C 
     * mod 법칙 (x+y) mod C = ( x mod C + y mod C ) % C
     *  ( A * B ) mod C = ( ( A mod C ) * ( B mod C ) ) mod C...
     * 흠.
     *     ( 5 * 5 * 5 * 5 ) % C 
     * =>   ( (5*5)%C * (5*5)%C ) % C
     * => ( (5%C) * (5%C))%C * (5%C) * (5%C))%C ) % C
     *
     * str = "2147400000 2 2147400001"; 에서 오버플로우 발생 
     * C가 int max/2 보다 클 경우 left,right를 mod 연산 후 곱해도 오버플로우 발생. => long
     */
    
}
