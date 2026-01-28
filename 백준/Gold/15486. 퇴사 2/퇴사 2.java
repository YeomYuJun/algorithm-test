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
        
        // str = "7\r\n" + //
        //                 "3 10\r\n" + //
        //                 "5 20\r\n" + //
        //                 "1 10\r\n" + //
        //                 "1 20\r\n" + //
        //                 "2 15\r\n" + //
        //                 "4 40\r\n" + //
        //                 "2 200";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);  // 1~15

        // List<int[]> list = new ArrayList<>();
        // list.add(new int[]{0,0});
        // for(int i=0; i<N; i++){
        //     String[] row = args[i+1].split(" ");
        //     int ti = Integer.parseInt(row[0]); // 1~5
        //     int pi = Integer.parseInt(row[1]); // 1~1000
        //     list.add(new int[]{ti,pi});
        // }

        int[][] dp = new int[N+1][51];

        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            int T = Integer.parseInt(row[0]); // 1~50
            int P = Integer.parseInt(row[1]); // 1~1000
            
            for(int j=0; j<50; j++){
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
     * 
     * 1_500_000_000 = int네?
     */
}

