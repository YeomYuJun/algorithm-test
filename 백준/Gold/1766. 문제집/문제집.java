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
            
        // ///////////////////////////////////////////
        
        // str = "6 7\r\n" + //
        //                 "5 6\r\n" + //
        //                 "5 2\r\n" + //
        //                 "2 4\r\n" + //
        //                 "4 3\r\n" + //
        //                 "2 1\r\n" + //
        //                 "6 1\r\n" + //
        //                 "1 3";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);


        // Map<Integer, List<Integer>> map = new HashMap<>();
        // Map<Integer, List<Integer>> childMap = new HashMap<>();
        List<List<Integer>> cList = new ArrayList<>();
        for(int i=0; i<=N; i++){
            cList.add(new ArrayList<>());
        }


        int[] pCnt = new int[N+1];
        for(int i=0; i<M; i++){
            String[] row = args[i+1].split(" ");
            int prev = Integer.parseInt(row[0]);
            int next = Integer.parseInt(row[1]);

            // List<Integer> pList = map.getOrDefault(next, new ArrayList<>());
            // List<Integer> cList = childMap.getOrDefault(prev, new ArrayList<>());
            // pList.add(prev);
            // cList.add(next);
            // map.put(next, pList);
            // childMap.put(prev, cList);
            cList.get(prev).add(next);
            pCnt[next]++;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int i=1; i<=N; i++){
            if(pCnt[i]==0) pq.add(i);
        }


        // int first = pq.poll();
        // System.out.println(first);
        // List<Integer> cL = childMap.get(first);
        // for(Integer child : cL){
        //     // List<Integer> pL = map.get(child);
        //     map.get(child).remove((Object)first);
        //     //map 요소가 제거 되며 pq의 순서가 갱신되면 좋을텐데 안되네
        //     //그럼 
        // }
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int cur = pq.poll();
            sb.append(cur).append(" ");

            for (int child : cList.get(cur)) {
                pCnt[child]--;
                if (pCnt[child] == 0) {
                    pq.add(child);   // 0이면 삽입
                }
            }
        }
        System.out.println(sb.toString().trim());

    }
    /**
     * 앞에 없는 5 풀고
     * 이제 2 6 중 2 풀고
     * 이제 1,4,6중 부모있는 1 제외, 4를 풀고
     * 6을 풀고
     * 5 > 2 > 4 > 6 > 1 > 3 
     * 
     * 즉, 1) 부모 여부, 부모가 없는 순으로
     *  2) 부모 제거 후 부모가 없는 순으로
     * 우선순위 큐? 우선순위 = 부모 여부
     * 부모 제거 시 어떻게 인식? 
     * 5라는 숫자 visit 여부 처리 후. 
     * 
     */
}
