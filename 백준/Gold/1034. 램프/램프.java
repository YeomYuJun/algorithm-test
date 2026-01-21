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
        
        // str = "5 2\r\n" + //
        //                 "01\r\n" + //
        //                 "10\r\n" + //
        //                 "01\r\n" + //
        //                 "01\r\n" + //
        //                 "10\r\n" + //
        //                 "1"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);
        int K = Integer.parseInt(args[1+N]);

        Map<String,int[]> map = new HashMap<>();
        for(int i=0; i<N; i++){
            // String[] row = args[i+1].split("");

            String sen = args[i+1];
            int zeroCnt = (int)sen.chars().filter(ch -> ch == '0').count();
            int min = zeroCnt;
            int[] results = map.getOrDefault(sen, new int[]{Integer.MAX_VALUE, 0});
            results[0] = Math.min(results[0], min);
            results[1]++; //헤당 숫자가 나온 수.
            map.put(sen, results);
        }

        List<String> keyList = map.keySet().stream().collect(Collectors.toList());
        int max = Integer.MIN_VALUE;
        for(String str : keyList){
            // System.out.println(str);
            int[] rs = map.get(str);
            // System.out.println(Arrays.toString(rs));
            if(rs[0] > K){ //최소 변경 횟수가 변경횟수보다 크면 못만듬
                continue;
            }else{ 
                int remain = rs[0] - K;
                if(remain%2 == 0){ //원복 가능.
                    max = Math.max(max, rs[1]); //해당 str의 숫자만큼 만들 수 있음
                }else{ //홀수면 못만듬. 
                    continue;
                }
            }
        }
        
        System.out.println(max == Integer.MIN_VALUE ? 0 : max);
    }
    /**
     * 
     * 1110000 3
     * 0111000 3
     * 0011100 3
     * 0001110 3
     * 0000111 3
     * 0000011 2
     * 0000011 2
     * 1233343
     * 
     * 가로에서 가장 많은 행에서
     * 세로에서 가장 많은 열..도 아니고 
     * 
     * 만드는 건 같은 문장밖에 없으니까?
     *  "str" : 같은 문장 수(만들 수 있는 , 켜져 있는 열의 수)
     *          ,켜기 위해 필요한 횟수(K에 따른 가능 유무 판단용)
     * 
     * 
     */

}