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
        
        // str = "1 55\r\n" + //
        //                 "5";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // 1<= N <=100
        int K = Integer.parseInt(args[0].split(" ")[1]); // 1<= K <=10000

        List<Integer> coins = new ArrayList<>();

        for(int i=1; i<=N; i++){
            int coin = Integer.parseInt(args[i]); //10만보다 작음
            if(coin>K){
                continue;
            }
            coins.add(coin);
        }
    
        //고르는 수 기반은 좀 그렇고, 제약 정도로만?
        // int[] dp = new int[K+1];
        
        Collections.sort(coins);

        int[] dp = new int[K+1];
        dp[0] = 1;

        for(int j=0; j<coins.size(); j++){
            int coin = coins.get(j);
            for(int i=1; i<=K; i++){
                if(i>=coin){ //현재 코인보다 크다면 만들 수 있을 수도 있음.
                    dp[i] += dp[i-coin];
                }
            }
        }
        // System.out.println(Arrays.toString(dp));
        System.out.println(dp[K]);
    }
    /**
     * 순서가 달라도 조합이 같으면 같다 => 순열이 아니라 조합임
     * nCr 에서 r은 다를 수 있고, 합 결과가 k이기만 하면 됨.
     * 
     *  dp[i]란? i 값어치에 도달할 수 있는 경우의 수? 
     *  dp[i]
     * 
     */
}