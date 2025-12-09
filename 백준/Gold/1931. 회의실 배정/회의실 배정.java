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

        // str = "2\r\n" + //
        //                 "1 2\r\n" + //
        //                 "1 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        
        //Map<Integer,Integer> meetings = new HashMap<>();
        List<int[]> meetings = new ArrayList<>();
        
        for(int i=1; i<=N; i++){
            String[] sted = args[i].split(" ");
            int st = Integer.parseInt(sted[0]);
            int ed = Integer.parseInt(sted[1]);
            meetings.add(new int[]{st, ed});
        }
        
        // 종료 시간 우선, 같으면 시작 시간으로 정렬
        meetings.sort((a, b) -> {
            if(a[1] != b[1]){
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });

        int curtime = 0;
        int meetCnt = 0;
        
        for(int[] meeting : meetings){
            int st = meeting[0];
            int et = meeting[1];
            if(st >= curtime){
                curtime = et;
                meetCnt++;
            }
        }
        System.out.println(meetCnt);
    }
    /**
     *  한 회의가 끝나는 것과 동시에 다음 회의가 시작될 수 있다. <= >= 
     *  회의의 시작시간과 끝나는 시간이 같을 수도 있다. 이 경우에는 시작하자마자 끝나는 것으로 생각하면 된다. <== default +1 되는 케이스
     * 종료시간으로 정렬해서 고르면 끝인가? 연속적으로 골라야하니까,
     * 
     * 2^31 -1 = int
     */

}