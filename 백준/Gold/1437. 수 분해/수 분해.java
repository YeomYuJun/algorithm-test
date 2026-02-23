import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
            
        // ////////////////////////////////////// /////
        
        // str = "9931";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].trim());
        
        final int MOD = 10007;
        if (N == 0) { System.out.println(0); return; }
        if (N == 1) { System.out.println(1); return; }
        if (N == 2) { System.out.println(2); return; }
        if (N == 3) { System.out.println(3); return; }
        if (N == 4) { System.out.println(4); return; }

        long ans = 1;
        int rem = N % 3;

        if (rem == 0) {
            int cnt = N / 3;
            ans = pow3(cnt, MOD);
        } else if (rem == 1) {
            int cnt3 = N / 3 - 1;
            ans = pow3(cnt3, MOD) * 4 % MOD;
        } else { // rem == 2
            int cnt3 = N / 3;
            ans = pow3(cnt3, MOD) * 2 % MOD;
        }

        System.out.println(ans);
    }

    static long pow3(int exp, int mod) {
        long result = 1;
        long base = 3;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }
}
