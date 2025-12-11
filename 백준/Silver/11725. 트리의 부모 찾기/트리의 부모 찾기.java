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

        // str = "12\r\n" + //
        //                 "1 2\r\n" + //
        //                 "1 3\r\n" + //
        //                 "2 4\r\n" + //
        //                 "3 5\r\n" + //
        //                 "3 6\r\n" + //
        //                 "4 7\r\n" + //
        //                 "4 8\r\n" + //
        //                 "5 9\r\n" + //
        //                 "5 10\r\n" + //
        //                 "6 11\r\n" + //
        //                 "6 12";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 

        List<List<Integer>> tree = new ArrayList<>();
        for(int i = 0; i <= N; i++) {
            tree.add(new ArrayList<>());
        }

        // 간선 입력
        for(int i = 1; i<N; i++) {
            int a = Integer.parseInt(args[i].split(" ")[0]);
            int b = Integer.parseInt(args[i].split(" ")[1]);
            tree.get(a).add(b);
            tree.get(b).add(a); // 양방향
        }

        int[] parent = new int[N+1];
        boolean[] visited = new boolean[N+1];

        Queue<Integer> q = new LinkedList<>();
        q.offer(1); // 루트는 1
        visited[1] = true;

        while(!q.isEmpty()) {
            int cur = q.poll();
            
            for(int child : tree.get(cur)) {
                if(!visited[child]) {
                    visited[child] = true;
                    parent[child] = cur;
                    q.offer(child);
                }
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for(int i = 2; i <= N; i++) {
            bw.write(parent[i] + "\n");
        }

        bw.flush();
        bw.close();
    }
}