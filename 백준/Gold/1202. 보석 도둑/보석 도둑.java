import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        
        // str = "4 3\r\n" + //
        //                 "2 3\r\n" + //
        //                 "2 1\r\n" + //
        //                 "3 5\r\n" + //
        //                 "3 6\r\n" + //
        //                 "3\r\n" + //
        //                 "3\r\n" + //
        //                 "3"; //";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); //보석 수 
        int K = Integer.parseInt(args[0].split(" ")[1]); //가방 수 
        
        int[][] jewels = new int[N][2];
        for (int i = 0; i < N; i++) {
            String[] row = args[i + 1].split(" ");
            jewels[i][0] = Integer.parseInt(row[0]); // 무게
            jewels[i][1] = Integer.parseInt(row[1]); // 가치
        }
        Arrays.sort(jewels, (a, b) -> a[0] - b[0]); // 무게 오름차순

        List<Integer> bag = new ArrayList<>();
        for (int i = N + 1; i < N + K + 1; i++) {
            bag.add(Integer.parseInt(args[i]));
        }
        Collections.sort(bag); // 용량 오름차순

        // 가치 내림차순 MaxHeap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        long sum = 0;
        int jewelIdx = 0;

        for (int c : bag) {
            // 현재 가방에 담을 수 있는 보석을 모두 후보에 추가
            while (jewelIdx < N && jewels[jewelIdx][0] <= c) {
                maxHeap.offer(jewels[jewelIdx][1]);
                jewelIdx++;
            }
            // 후보 중 가장 비싼 보석 선택
            if (!maxHeap.isEmpty()) {
                sum += maxHeap.poll();
            }
        }

        System.out.println(sum);
        //무게 순으로 정렬 30만개..
        //1. 가방 중 용량이 작은 것부터 선택 
        //2. 해당 가방이 가질 수 있는 가장 가치가 높은 보석 선택 

    }
}