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
        
        // str = "5\r\n" + //
        //                 "1 3\r\n" + //
        //                 "2 1\r\n" + //
        //                 "3 5\r\n" + //
        //                 "4 2\r\n" + //
        //                 "5 4";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        
        Map<Integer, Integer> line = new HashMap<>();

        //최대 최소 범위 필요한가?
        // int minA = 501;
        // int maxB = 0;

        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            int a = Integer.parseInt(row[0]);
            int b = Integer.parseInt(row[1]);
            // maxB = Math.max(Math.max(maxB, a), b);
            // minA = Math.min(Math.min(minA, a), b);
            line.put(a, b);
        }

        List<Integer> keySet = new ArrayList<>(line.keySet());
        Collections.sort(keySet);

        /** 정렬=
         *  1     8
            2     2
            3     9 
            4     1
            6     4
            7     6
            9     7
            10    10
         */
        //key : value 에서 value가 감소하면? 

        int[] dp = new int[N];
        int len = 0;
        dp[0] = 0;

        for(int i=0; i<N; i++){
            int keyA = keySet.get(i);
            int toA = line.get(keyA);
            int pos = Arrays.binarySearch(dp, 0, len, toA);
            if (pos < 0) pos = -(pos + 1); // 삽입 위치
            
            dp[pos] = toA;
            if (pos == len) len++;
        
        }
        int count = 0;
        for(int i=0; i<N; i++){
            if(dp[i]==0){
                count++;
            }
        }
        System.out.println(count);
        return;

    }
    /**
     * a to b 전깃줄이 교차하는 것인지 보려면, 
     * 정렬하고 모든 전깃줄에 대해 a보다 작고, b보다 작거나
     * a보다 크고, b보다 크거나 두 조건에 반하는 게 크로스임
     * 메모리 128
     * 전깃줄 1~100, 번호 1~500
     * 
     * from a to b 일 때 
     * a를 정렬해서, List<b> 라고 보면 
     * 전체 수 - b가 최장 증가 길이  = 역순으로 교차를 만들어버리는 전깃줄(나머지)
     */

}