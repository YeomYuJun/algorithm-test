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

        // str = "10";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        if(n==1){
            System.out.println(0);
            System.out.println(1);
            return;
        }

        int[] dp = new int[n+1];
        int[] root = new int[n+1];
        
        //1을 만들기 위해 2or3을 만들어야함
        //2or3을 만들기 위해 4or6or9를  ... 
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[0] = 0;
        dp[1] = 0;
        for(int i=2; i<=n; i++){
            int base = dp[i-1]+1; //기본 가능
            int db = i%2==0 ? dp[i/2]+1 : Integer.MAX_VALUE;
            int tp = i%3==0 ? dp[i/3]+1 : Integer.MAX_VALUE;
            
            if(base <= db && base <= tp){
                dp[i] = base;    
                root[i] = i-1;
            }else if(db <= base && db <= tp){
                dp[i] = db;    
                root[i] = i/2;
            }else if(tp <= base && tp <= db){
                dp[i] = tp;
                root[i] = i/3;
            }
        }

        
        StringBuilder sb = new StringBuilder();
        int rt = root[n];
        sb.append(n+" ");
        sb.append(rt+" ");
        while(rt > 1){
            int next = root[rt];
            if(next == 1){
                sb.append(next);    
            }else{
                sb.append(next+" ");
            }
            rt = next;
        }
        
        System.out.println(dp[n]);
        System.out.println(sb.toString());
    }
}