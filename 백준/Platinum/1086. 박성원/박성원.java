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
            
        ///////////////////////////////////////////
        
        // str = "15\r\n" + //
        //                 "12345678\r\n" + //
        //                 "81234567\r\n" + //
        //                 "78123456\r\n" + //
        //                 "67812345\r\n" + //
        //                 "56781234\r\n" + //
        //                 "45678123\r\n" + //
        //                 "34567812\r\n" + //
        //                 "23456781\r\n" + //
        //                 "12345678\r\n" + //
        //                 "81234567\r\n" + //
        //                 "78123456\r\n" + //
        //                 "67812345\r\n" + //
        //                 "56781234\r\n" + //
        //                 "45678123\r\n" + //
        //                 "34567812\r\n" + //
        //                 "98"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 1~15
        int K = Integer.parseInt(args[1+N]); // 1~100


        String[] nums = new String[N];
        int[] mods = new int[N];       // 각 숫자의 K에 대한 나머지
        int[] lens = new int[N];       // 각 숫자의 길이
        for (int i = 0; i < N; i++) {
            nums[i] = args[i + 1];
            lens[i] = nums[i].length();
            // 각 숫자의 초기 나머지 계산
            int r = 0;
            for (char c : nums[i].toCharArray()) {
                r = (r * 10 + (c - '0')) % K;
            }
            mods[i] = r;
        }

        // 10^n % K 값들을 미리 계산 (메모이제이션)
        int[] pow10 = new int[51]; // 숫자의 최대 길이가 50
        pow10[0] = 1 % K;
        for (int i = 1; i <= 50; i++) {
            pow10[i] = (pow10[i - 1] * 10) % K;
        }


        long[][] dp = new long[1 << N][K];
        dp[0][0] = 1; // 아무것도 선택하지 않았을 때 나머지는 0, 경우의 수는 1
        for (int currentMask = 0; currentMask < (1 << N); currentMask++) {
            for (int r = 0; r < K; r++) {
                if (dp[currentMask][r] == 0) continue;

                for (int next = 0; next < N; next++) {
                    // 아직 사용하지 않은 숫자

                    if ((currentMask & (1 << next)) == 0) {
                        int nextMask = currentMask | (1 << next);
                        int nextRemainder = (r * pow10[lens[next]]) % K;
                        nextRemainder = (nextRemainder + mods[next]) % K;

                        dp[nextMask][nextRemainder] += dp[currentMask][r];
                    }
                }
            }
        }

        long ans = dp[(1 << N) - 1][0]; // 모든 숫자를 사용했을 때 나머지가 0인 경우
        long totalPermutations = 0;
        for (int r = 0; r < K; r++) {
            totalPermutations += dp[(1 << N) - 1][r];
        }

        // 결과 출력 (기약 분수)
        if (ans == 0) {
            System.out.println("0/1");
        } else {
            long common = gcd(ans, totalPermutations);
            System.out.printf("%d/%d", ans / common, totalPermutations / common);
        }
    }


    public static long gcd(long a, long b) {
        if(b == 0) return a;
        return gcd(b, a%b);
    }

    public static void permutation(List<String> list, String current, int start, int depth, Set<String> result, boolean[] used){
        String next = list.get(start);
        current+=next;

        int N = list.size();
        if(depth == N){
            result.add(current);
        }

        for(int i=0; i<N; i++){
            if(used[i]) continue;

            used[i] = true;
            permutation(list,current,i,depth+1,result,used);
            used[i] = false;
        }
    }
    
    public static int mod(String number, int K){
        int remainder = 0;

        for(char c : number.toCharArray()){
            remainder = (remainder * 10 + (c-'0')) % K;
        }

        return remainder;
    }
    /**
     * 확률 => Set으로 , 순열 종류(String 조합이 같을 수도 있음, 1,11,111 같은 거)
     * 순열 종류, permutation에 Set으로 중복제거 한 것을 
     * K로 나누는데, 각 길이가 최대 50이고, 15개까지 가능하니까
     * 최대길이: 15*50 = 10^750 까지 가능한 숫자가 K로 나뉘는지
     * 즉  String % K 검증 메서드 필요함
     * 
     * 
     * 
     * 메모리초과... String을 StringBuilder로?
     * 흠..순열의 수는 15! = 너무 큰 거 같음
     * 아니면 아. 
     * String들의 모든 mod를 구한다음
     * 다른 String을 연결시키는 것 =>  해당 스트링을 현재 String 길이의 10^len 만큼 곱한 것을 modular한 것과 같은
     *  a mod K = a`
     *  b mod K = b`
     * 일 때 b*10^l + a 이니까 모듈러곱셈 + 모듈러 덧셈 이고
     *      = ((b mod K) * (10^l mod K) mod K + (a mod K)) mod K
     * 이니까 dp처럼 기존 String 및 조합에 mod 값을 저장해놓으면?
     * 
     * 
     */
}
