import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        /////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "bca\r\n" + //
        //                 "ba"; 

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        //b c a bb bc ba cb cc ca ab ac aa
        String alpha = args[0];
        int len = alpha.length();
        String password = args[1];
        int pwLen = password.length();
        // 1~alpha.length까지 
        int sum = 0;
        char[] pwChar = password.toCharArray();

        for(int i=0; i<pwLen; i++){
            char c = pwChar[i];
            int index = alpha.indexOf(c)+1;
            
            long pow = modPow(len, pwLen - i - 1, 900528);
            long cCount = (pow * index) % 900528;
            cCount %= 900528;
            sum+=cCount;
            sum %= 900528;
        }
        System.out.println(sum);
    }
    private static long modPow(long base, int exp, int mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }


}