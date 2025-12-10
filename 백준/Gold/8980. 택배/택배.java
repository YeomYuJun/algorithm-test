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

        // str = "6 60\r\n" + //
        //                 "5\r\n" + //
        //                 "1 2 30\r\n" + //
        //                 "2 5 70\r\n" + //
        //                 "5 6 60\r\n" + //
        //                 "3 4 40\r\n" + //
        //                 "1 6 40";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); //마을 수
        int C = Integer.parseInt(args[0].split(" ")[1]); //용량 C 
        int M = Integer.parseInt(args[1]); //박스 개수


        List<int[]> deliver = new ArrayList<>();
        int[][] delMap= new int[N+1][N+1];

        for(int i=0; i<M; i++){ 
            String[] b = args[i+2].split(" ");
            int from = Integer.parseInt(b[0]);
            int to = Integer.parseInt(b[1]);
            int count = Integer.parseInt(b[2]);
            delMap[from][to] = count;
            deliver.add(new int[]{from, to, count});
        }

        //1. from 작고
        //2. to 작고
        //3. cost는 항상 최대 = 1,2,순으로 정렬 from to 가 같은 게 2개일 수가 없음. 
        deliver.sort((o1, o2) -> {
            if(o1[1] != o2[1]){
                return Integer.compare(o1[1], o2[1]);
            }
            return Integer.compare(o1[0], o2[0]);
        });

        int answer = 0;
        int[] truck = new int[N + 1]; // truck[i] = i번 마을까지 실린 박스 수
        //트럭 한 대로 배송할 수 있는 최대 박스 수를 한 줄에 출력한다.
        int curC = C;
        for(int[] box : deliver) {
            int from = box[0];
            int to = box[1];
            int count = box[2];

            // from~to 구간에서 가장 많이 실린 곳 찾기
            int maxLoad = 0;
            for(int i = from; i < to; i++) {
                maxLoad = Math.max(maxLoad, truck[i]);
            }

            // 실을 수 있는 최대 박스 수
            int canLoad = Math.min(count, C - maxLoad);
            
            // from~to 구간에 박스 적재
            for(int i = from; i < to; i++) {
                truck[i] += canLoad;
            }
            
            answer += canLoad;
        }

        System.out.println(answer);

    }
    /**
     * 2 <= N <= 2000
     * 1 <= C <= 10000
     * 1 <= M <= 10000
     * 
     * 박스를 받는 마을번호는 보내는 마을번호보다 크다. 
     *  40  40  30   0
     *  40  -10 -20- 10
     *   0  10  -10
     *          20  -20
     *   1 - 2 - 3 - 4
     */

}