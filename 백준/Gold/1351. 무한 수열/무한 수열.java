import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

        // str = "75 5 3"; 

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        long N = Long.parseLong(args[0].split(" ")[0]);
        long P = Long.parseLong(args[0].split(" ")[1]);
        long Q = Long.parseLong(args[0].split(" ")[2]);

        Map<Long,Long> dp = new HashMap<>();
        dp.put(0L, 1L);
        Long answer = getDP(dp, P, Q, N);
        System.out.println(answer);
    }
    public static Long getDP(Map<Long,Long> map, long P, long Q, long Key){

        Long val = map.get(Key);
        if(val != null){
            return val;
        }
        
        // 계산
        Long result = getDP(map, P, Q, Key/P) + getDP(map, P, Q, Key/Q);
        map.put(Key, result);
        return result;
    }
    /**
     * An = An/p + An/q = A((n/p)/p) + A((n/p)/q) + A((n/q)/p) + A((n/q)/q)
     */
}