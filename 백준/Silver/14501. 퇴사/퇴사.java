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
        
        // str = "3\r\n" + //
        //                 "2 10\r\n" + //
        //                 "5 20\r\n" + //
        //                 "1 10";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);  // 1~15

        List<int[]> list = new ArrayList<>();
        list.add(new int[]{0,0});
        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            int ti = Integer.parseInt(row[0]); // 1~5
            int pi = Integer.parseInt(row[1]); // 1~1000
            list.add(new int[]{ti,pi});
        }

        int[][] dp = new int[17][6];

        for(int i=1; i<=N; i++){
            int[] tp = list.get(i);
            int T = tp[0];
            int P = tp[1];
            
            for(int j=0; j<5; j++){
                if(j==0){
                    dp[i][j] = Math.max(dp[i-1][0], dp[i-1][j+1]); //갱신 //대신 0번은 만료된 것중 max로만 갱신.
                }else{
                    dp[i][j] = dp[i-1][j+1]; //갱신 
                }
            }
            if(i+T <= N+1){// N번째에는 1까지 가능 
                dp[i][T] = Math.max(Math.max(dp[i-1][0]+P,dp[i-1][1]+P) , dp[i][T]); //현재거를 고르는 case
            }
        }
        // for(int i=0; i<=N; i++){
        //     System.out.println(Arrays.toString(dp[i]));
        // }
        System.out.println(Arrays.stream(dp[N]).max().getAsInt());
    }
    /**
     * . (1 ≤ Ti ≤ 5, 1 ≤ Pi ≤ 1,000)
     * 
     * 
     * dp인데
     * dp[i] = i일에 얻을 수 있는 최대 price 
     * dp[i]에서 이전 dp[i-x]를 빼고 i번째 상담을 잡는 게 유리한 경우...에서 지난 x를 찾는 방법? 안될 듯
     * dp[i][j] = i일에 남은 time(j) ?
     * [1][3] = 10
     * [2][1] = 10, [2][5] = 20
     * [3][1] = 10, [3][4] = 20, [3][1] = 10 (1,10 짜리르 집어도 동일함)
     * [4][1] = 30, [4][3] = 20 
     * [5][2] = 45, ([5][2] = 20)
     * [6][1] = 45...
     */
}

