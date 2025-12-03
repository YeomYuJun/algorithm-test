import java.io.*;
import java.util.*;
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        /////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "9\r\n" + //
        //                 "10 30 20 40 1 2 3 4 5";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        if(n==1){
            System.out.println(1);
            System.out.println(args[1]);
            return;
        }
        String[] arr = args[1].split(" ");

        int[] dp = new int[n+1];
        int[] root = new int[n+1];

        dp[0] = 0;
        dp[1] = 1;
    
        int ttm = Integer.MIN_VALUE;
        int maxIdx = Integer.MIN_VALUE;
        
        for(int i=2; i<=n; i++){
            int cur = Integer.parseInt(arr[i-1]);

            int cnt = 1;
            int curMax = cur;
            int curIdx = i-1;

            for(int j=i-1; j>0; j--){
                if(curMax > Integer.parseInt(arr[j-1])){
                    if(1+dp[j] > cnt){
                        curIdx = j;
                    }
                    cnt = Math.max(1+dp[j], cnt);
                }
            }
            root[i] = curIdx;
            dp[i] = cnt;
            if(cnt>ttm){
                maxIdx = i; //마지막 인덱스 갱신
            }
            ttm = Math.max(cnt,ttm);
        }

        //담기
        List<Integer> list = new ArrayList<>();
        int loofIdx = maxIdx;
        for(int i=ttm; i>0; i--){
            list.add(Integer.parseInt(arr[loofIdx-1]));
            loofIdx = root[loofIdx];
        }
        
        //정렬
        Collections.sort(list);
        //둘째 줄 담기
        StringBuilder sb = new StringBuilder();
        for(Integer str : list){
            sb.append(str+ " ");
        }
        System.out.println(ttm);
        System.out.println(sb.toString().trim());
    }
}