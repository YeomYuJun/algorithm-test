import java.io.*;
import java.util.*;

public class Main {
    private static int answer = 0;
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
        
        // str = "6 9\r\n" + //
        //                 "1 2 9\r\n" + //
        //                 "1 3 4\r\n" + //
        //                 "1 4 3\r\n" + //
        //                 "1 5 1\r\n" + //
        //                 "2 4 4\r\n" + //
        //                 "2 5 5\r\n" + //
        //                 "3 6 6\r\n" + //
        //                 "4 5 2\r\n" + //
        //                 "4 6 8";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }


    public static void solution(String[] args) throws IOException {
        int V = Integer.parseInt(args[0].split(" ")[0]); // 정점 수
        int E = Integer.parseInt(args[0].split(" ")[1]); // 간선 수 

        List<int[]> list = new ArrayList<>();

        for(int i=1; i<=E; i++){
            String[] row = args[i].split(" ");
            int l = Integer.parseInt(row[0]);
            int r = Integer.parseInt(row[1]);
            int w = Integer.parseInt(row[2]);

            list.add(new int[]{l,r,w});
        }
        Collections.sort(list, (o1, o2) -> {
            return o1[2] - o2[2];
        });

        int[] parent = new int[V+1];
        for(int i=0; i<=V; i++){ //자기 자신 
            parent[i] = i; 
        }

        List<int[]> spanning = new ArrayList<>();

        for(int i=0; i<E; i++){
            int[] curE = list.get(i);

            int v1 = curE[0];
            int v2 = curE[1];
            //부모가 같은가? => 사이클이 형성되는가?
            int v1p = findRoot(v1, parent);
            int v2p = findRoot(v2, parent);
            if(v1p == v2p) continue; 

            
            spanning.add(curE);
            unionRoot(v1,v2,parent);
            if(spanning.size() == V) break;
        }
        int sum = spanning.stream().mapToInt(value -> {
            return value[2];
        }).sum();
        System.out.println(sum);
        // for(int[] s : spanning){
        //     System.out.println(Arrays.toString(s));
        // }
        
    }

    public static int findRoot(int v,int[] parent){
        if(parent[v] == v) return v;
        return parent[v] = findRoot(parent[v], parent);
    }

    //부모 합치기
    public static void unionRoot(int x, int y, int[] parent) {
        x = findRoot(x, parent);
        y = findRoot(y, parent);
 
        if (x != y) parent[y] = x;
    }
    /**
     * 양방향에서 V-1개의 간선을 고른다..
     * 최소 가중치 순서로 선택
     * 선택했을 때, 사이클이 구성되면 false, 아니면 true
     * 반복. 음수는 무조건 좋은 것인가?
     */

}

