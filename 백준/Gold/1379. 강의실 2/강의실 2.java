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

        // str = "4\r\n" + //
        //                 "1 1 2\r\n" + //
        //                 "2 1 3\r\n" + //
        //                 "3 3 5\r\n" + //
        //                 "4 2 6";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        List<int[]> classList = new ArrayList<>();
        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            int k = Integer.parseInt(row[0]);
            int st = Integer.parseInt(row[1]);
            int et = Integer.parseInt(row[2]);
            classList.add(new int[]{k, st, et});
        }

        // 시작 시간 순으로 정렬
        classList.sort((o1, o2) -> {
            if(o1[1] == o2[1]){
                return Integer.compare(o1[2], o2[2]);
            }
            return Integer.compare(o1[1], o2[1]);
        });

        // {종료시간, 강의실번호}를 저장
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> 
            Integer.compare(o1[0], o2[0])
        );

        int[] classRoomAssignment = new int[N + 1]; // 강의 번호별 강의실
        int roomCounter = 0;

        for(int[] clss : classList){
            int id = clss[0];
            int st = clss[1];
            int et = clss[2];

            if(pq.isEmpty() || pq.peek()[0] > st){
                // 새 강의실 필요
                roomCounter++;
                classRoomAssignment[id] = roomCounter;
                pq.add(new int[]{et, roomCounter});
            }else{
                // 가장 빨리 끝나는 강의실 재사용
                int[] earliest = pq.poll();
                int reuseRoom = earliest[1];
                classRoomAssignment[id] = reuseRoom;
                pq.add(new int[]{et, reuseRoom});
            }
        }

        System.out.println(roomCounter);
        StringBuilder sb = new StringBuilder();
        for(int i=1; i<=N; i++){
            sb.append(classRoomAssignment[i]);
            if(i != N){
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }
    /**
     * 
     * 2           14
     *   3    8 12     18  20      25
     *     6               20
     *     6                           27
     *      7    13  15      21
     */
}
