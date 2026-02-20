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
        
        // str = "3\r\n" + //
        //                 "5\r\n" + //
        //                 "6\r\n" + //
        //                 "7";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        

        //원소 = 10억 int임

        int[] arr = new int[N];
        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(args[i+1]);
        }

        //일단 정렬 
        Arrays.sort(arr);

        int[][] dp = new int[N][5];
        for(int i=0; i<N; i++){
            dp[i][0] = 1;
        }
        // i번째 요소의 j연속 여부..?  j차이 나는 수가 있는지 0 1 여부
        for(int i=0; i<N; i++){
            int cur = arr[i];

            for(int j=1; j<5; j++){
                if(i+j >= N ) break;
                int nextIdx = i+j;

                int next = arr[nextIdx];
                //연속인지 더 큰 지 모르겠고 다음 숫자
                
                int gap = next - cur;
                if(gap < 5){
                    dp[i][gap] = 1; //같은 숫자가 있어도 겹치면 의미없음
                }else{//정렬이라 반대로
                    break; 
                }
            }
        }
        int max = 1;
        for(int i=0; i<N; i++){
            int continueCount = Arrays.stream(dp[i]).sum();
            max = Math.max(max, continueCount); //5연속 최대
            // System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println(5-max);

    }
    /**
     * 최장 연속 배열 찾기.
     * 배열 = 50 길이 
     * a[i] ~ a[i+4] 까지 +2
     * dp[i] = 1
     * dp[i][1] = arr[i+1]-1 == a[i] ? dp[i][0]+1 : dp[i][0]
     * 보다는 차이
     * 
     */
}
