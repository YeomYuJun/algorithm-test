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
        
        // str = "1 5\r\n" + //
        //                 "3";

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

        if(coins.size() == 0 ){ //불가능 케이스 
            System.out.println(-1);
            return;
        }

        coins.sort(Collections.reverseOrder());

        int INF = 1_000_000_000;
        int[] dp = new int[K+1];
        //갈 수 없음으로 초기화
        Arrays.fill(dp, INF);

        //각 coins으로 나눈 횟수만큼 일단 (더 작은 걸로 ) 초기화
        for(int i=0; i<K; i++){ 
            for(int j=0; j<coins.size(); j++){
                int coin = coins.get(j);
                if(i%coin==0){
                    dp[i] = Math.min(dp[i], i/coin);
                }

            }    
        }

        //동전을 이전 거에서 +1개로 추가해서 만들 수 있음을 초기화 (더 작은 걸로)
        for(int j=0; j<coins.size(); j++){
            int coin = coins.get(j);
            for(int i=1; i<=K; i++){
                if(i>=coin){ //현재 코인보다 크다면 만들 수 있을 수도 있음.
                    dp[i] = Math.min(dp[i],dp[i-coin]+1);
                }
            }
        }

        // System.out.println(Arrays.toString(dp));
        System.out.println(dp[K] == INF ? -1 : dp[K]);
    }
    /** 
     *  dp[i]란? i 값어치에 도달할 때의 최소 동전의 수
     */
}