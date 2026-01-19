import java.io.*;
import java.util.*;
import java.util.Map.Entry;

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
        
        
        // str = "50"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        Set<String> numSet = new HashSet<>(Set.of("10"
        ,"11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26"));

        int MOD = 1_000_000;
        String NUM = args[0];
        int len = NUM.length();

        int[] dp = new int[len+1];
        dp[0] = 0;
        if(NUM.charAt(0) == '0'){
            System.out.println(0);
            return;
        }
        if(len == 1){ //0이 아니며 길이가 1= 1자리
            System.out.println(1);
            return;
        }
        dp[1] = 1;
        
        if(numSet.contains(NUM.charAt(0) +""+ NUM.charAt(1))){
            if(NUM.charAt(1) != '0'){
                dp[2] = 2;        
            }else{
                dp[2] = 1;
            }
        }else{
            if(NUM.charAt(1) != '0'){ //결합이 안되고 0일 수 없음.
                dp[2] = 1;
            }
        }

        for(int i=3; i<=len; i++){
            String prev = NUM.charAt(i-2) +""+ NUM.charAt(i-1);
            
            boolean isNumber = numSet.contains(prev);

            if(NUM.charAt(i-1) != '0'){
                dp[i] = dp[i-1]; //결합 안 해도 되는 경우=> 그대로
            }
            if(isNumber){ //결합으로 숫자가 만들어지는 경우
                dp[i] += dp[i-2];
            }
            dp[i] %= MOD;
        }
        // System.out.println(Arrays.toString(dp));
        System.out.println(dp[len]);
    }

}