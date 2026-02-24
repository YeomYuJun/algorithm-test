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
        
        // str = "5\r\n" + //
        //                 "0 10 100 20 100\r\n" + //
        //                 "100 0 10 100 1\r\n" + //
        //                 "100 1 0 100 100\r\n" + //
        //                 "100 100 5 0 100\r\n" + //
        //                 "100 100 100 100 0\r\n" + //
        //                 "YNNNN\r\n" + //
        //                 "5";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);


        int[][] cost = new int[N][N];
        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<N; j++){
                int val = Integer.parseInt(row[j]);
                cost[i][j] = val;
            }
        }
        
        boolean[] onStation = new boolean[N];
        String[] onandoff = args[N+1].split("");
        int YCount = 0;
        int NCount = 0;
        int startBit = 0;
        for(int i=0; i<N; i++){
            String onoff = onandoff[i];
            if(onoff.equals("Y")){
                YCount++;
                onStation[i] = true; //켜짐
                startBit |= (1 << i);
            }else if(onoff.equals("N")){
                NCount++;
            }
        }

        int P = Integer.parseInt(args[N+2]);
        if(P<=YCount){
            System.out.println(0);
            return;
        }else if(YCount == 0){
            System.out.println(-1);
            return;
        }
        int needOn = P-YCount; //켜야하는 수

        findMinCost(cost, onStation, needOn, startBit);
        if(MINCOST == 1000){//MIN 이 그대로면, require 만큼 키지 못했음.
            System.out.println(-1);
        }else{   
            System.out.println(MINCOST);
        }
        
    }
    static int MINCOST = 1000;

    static int[] dp;

    public static void findMinCost(int[][] cost, boolean[] onStation, int requireP, int startBit){
        int N = onStation.length;
        
        dp = new int[1 << N];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[startBit] = 0;

        for(int i=0; i<N; i++){ 
            if(!onStation[i]) continue;
            for(int j=0; j<N; j++){
                if(j==i) continue; 
                if(onStation[j]) continue; 
                
                boolean[] visit = onStation.clone();
                visit[j] = true;
                
                int nextBit = startBit | (1 << j);
                if(dp[nextBit] > cost[i][j]) {
                    dp[nextBit] = cost[i][j];
                    dfs(cost, visit, cost[i][j], requireP, 1, nextBit);
                }
            }

        }
    }

    public static void dfs(int[][] cost, boolean[] visit, int sum, int requireP, int onCount, int currentBit){
        if(onCount==requireP){
            MINCOST = Math.min(sum,MINCOST);
            return;
        }
        int N = cost.length;
        
        for(int i=0; i<N; i++){ 
            if(!visit[i]) continue;
            for(int j=0; j<N; j++){
                if(j==i) continue; 
                if(visit[j]) continue; 
                int nextVal = sum+cost[i][j];
                int nextBit = currentBit | (1 << j);

                if(dp[nextBit] <= nextVal) continue; 

                visit[j] = true;
                dp[nextBit] = nextVal;
                dfs(cost, visit, nextVal, requireP, onCount+1, nextBit);
                visit[j] = false;
            }

        }
    }

    /**
     * 1번으로 2번 고치고, 2번으로 3번 고치는 경우.
     * 즉 한번 고치고 업데이트된 상태로 다음을 수행해야함..
     * 
     * 예를 들어
     * 3
     * 0 5 10
     * 100 0 9
     * 100 1 0
     * YNN
     * 3
     * 
     * 1번으로 2번이 5  
     * 1번에서 3번이 10
     * 
     * 2번에서 3번이 9
     * 
     * 3번에서 2번이 1
     * YNN인 경우, MIN으로 하면 1번에서 2번으로 =5, 2번에서 3번으로 =9 , => 14의 cost 사용
     * 역으로 1번에서 3번으로 =10, 3번에서 2번으로 =1, => 11의 cost 사용.
     * 즉 끝까지 가봐야 알 수 있음. DFS로 모든 경우 완탐 해봐야 최소 cost 알 수 있을 듯.
     *
     * 16개, 15개, 14개, ... 깊이가 너무 깊어질 수 있음  => i번 station을 j번쨰를 고르는 cost dp로 제한
     * 같은 j번째라도 켜진 상태에 따라 다를 수 있으니까 [2][2][2].. 비트마스킹으로 켜진 상태별 cost로 해야겠네
     */
}

