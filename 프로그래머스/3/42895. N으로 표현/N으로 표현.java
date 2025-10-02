import java.util.*;

class Solution {
    public int solution(int N, int number) {
        List<Set<Integer>> dp = new ArrayList<>();

        for(int i = 0; i <= 8; i++) {
            Set<Integer> hash = new HashSet<>();
            String numstr = String.valueOf(N).repeat(i);
            numstr = i == 0 ? "0" : numstr;
            hash.add(Integer.parseInt(numstr));
            dp.add(hash);
        }

        for(int i=1; i<=8; i++){
            // i개를 사용하는 모든 조합: (j개)  (i-j개)
            for (int j = 1; j < i; j++) { // 4~50
                for (int a : dp.get(j)) { //Set조합으로부터 반복
                    for (int b : dp.get(i - j)) {
                        dp.get(i).add(a + b);
                        dp.get(i).add(a - b);
                        dp.get(i).add(a * b);
                        if (b != 0) dp.get(i).add(a / b);
                    }
                }
            }
            if (dp.get(i).contains(number)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 할 수 있는 것 = 1.더하기 , 2.빼기, 3.곱하기, 4. 나누기 , 0.이어붙이기(default)
     * 최솟값이 8보다 크면 -1을 return 합니다.
     * 5^8 = 40만
     * dp[n] = number라고 가정
     * dp[n] = g(dp[n-1])
     * dp[n-1] = g(dp[n-2]);
     * f(N) = g(g(g(...g(5))))
     * dp[0] = 5;
     * dp[1] = g(dp[0]); // 10 0 25 55 
     * dp[2] = g(dp[1]); // 555 , 15 5 50 2 , 5 -5 0 0, 30 20 125 5, ... 중복제거해야할 듯 배열 대신 List<Set>? 
     * dp[i] => N을 i개 사용해서 만들 수 있는 모든 수의 집합
     */
}