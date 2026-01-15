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
        
        // str = "7 120\r\n" + //
        //                 "20 91 92 93 94 95 100\r\n" + //
        //                 "1 2 2 2 2 2 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);
                
        String[] r1 = args[1].split(" ");
        String[] r2 = args[2].split(" ");

        List<int[]> appList = new ArrayList<>();
        List<int[]> zeroList = new ArrayList<>();

        int cSum = 0;

        int[][] map = new int[N+1][101];
        for(int i=0; i<N; i++){
            // int appId = i+1;
            int m1 = Integer.parseInt(r1[i]);
            int c1 = Integer.parseInt(r2[i]);
            
            map[i+1][c1] = m1;
            cSum += c1;
            int[] app1 =  new int[]{m1,c1};
            if(c1 == 0){
                zeroList.add(app1);
            }else{
                appList.add(app1);
            }
        }
        
        Collections.sort(appList, (o1, o2) -> {
            if(o1[1] == o2[1]){
                return o2[0] - o1[0];
            }
            return o1[1] - o2[1];
        });

        
        int requireM = M;
        for(int[] app : zeroList){
            requireM-=app[0];
        }

        if(requireM <= 0 ){ // 조기종료
            System.out.println(0);
            return;
        }
        //c1이 0인 , cost가 0인 애들을 일단 뺴고 시작하며, 충족된 M 만큼 뺴고 다시 계산 
        M = requireM;
        
        // int[][] dp = new int[101][101];
        int[] dp = new int[10001];
        
        
        for(int i=0; i<appList.size(); i++){
            int[] app = appList.get(i);
            int m = app[0];
            int c = app[1];
            for(int j=cSum; j>=c; j--){
                dp[j] = Math.max(
                    dp[j-c]+m,
                    dp[j]
                ); 
            }
        }
        for(int i=0; i<=cSum; i++){
            if(dp[i]>=M){
                System.out.println(i);
                return;
            }
        }
        
    }
    /**
     * //M을 확보하기 위한 최소의 비용
     * 135  to M으로 감소시키는 최소 c1 
     * 이 아니라 135에서 M을 확보하는 최소 c1.
     * dp[i] 는 c1=i 으로 만들 수 있는 최대 M?으로 해야하나< 이걸로 해야할 듯
     * dp[i][j], i개를 골라서 만든 c1값(j) = M.  ?? 
     * c = 100*100= 10000이 최대.
     * dp[i] = i개를 골라서 만드는 최대 M? 
     * c1 <=100
     */

}