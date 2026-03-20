import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        solution(br);
    }

    public static void solution(BufferedReader br) throws IOException {
        int T = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int tc = 0; tc < T; tc++) {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            PriorityQueue<Integer> reversePq = new PriorityQueue<>(Collections.reverseOrder());
            Map<Integer, Integer> deletedFromPq = new HashMap<>();
            Map<Integer, Integer> deletedFromReverse = new HashMap<>();

            int k = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < k; i++) {
                String line = br.readLine().trim();
                String cmd = line.split(" ")[0];
                int n = Integer.parseInt(line.split(" ")[1]);

                if ("D".equals(cmd)) {
                    if (n == 1) { // 최댓값 삭제
                        while (!reversePq.isEmpty() && deletedFromReverse.getOrDefault(reversePq.peek(), 0) > 0) {
                            deletedFromReverse.merge(reversePq.poll(), -1, Integer::sum);
                        }
                        if (reversePq.isEmpty()) continue;
                        int val = reversePq.poll();
                        deletedFromPq.merge(val, 1, Integer::sum);
                    } else { // 최솟값 삭제
                        while (!pq.isEmpty() && deletedFromPq.getOrDefault(pq.peek(), 0) > 0) {
                            deletedFromPq.merge(pq.poll(), -1, Integer::sum);
                        }
                        if (pq.isEmpty()) continue;
                        int val = pq.poll();
                        deletedFromReverse.merge(val, 1, Integer::sum);
                    }
                } else { // INSERT
                    reversePq.add(n);
                    pq.add(n);
                }
            }

            // 최종 lazy cleanup
            while (!pq.isEmpty() && deletedFromPq.getOrDefault(pq.peek(), 0) > 0) {
                deletedFromPq.merge(pq.poll(), -1, Integer::sum);
            }
            while (!reversePq.isEmpty() && deletedFromReverse.getOrDefault(reversePq.peek(), 0) > 0) {
                deletedFromReverse.merge(reversePq.poll(), -1, Integer::sum);
            }

            if (!pq.isEmpty() && !reversePq.isEmpty()) {
                sb.append(reversePq.poll()).append(" ").append(pq.poll());
            } else {
                sb.append("EMPTY");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString().trim());
    }
}