import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        // str = "7 8\r\n" + //
        //                 "0 1 3\r\n" + //
        //                 "1 1 7\r\n" + //
        //                 "0 7 6\r\n" + //
        //                 "1 7 1\r\n" + //
        //                 "0 3 7\r\n" + //
        //                 "0 4 2\r\n" + //
        //                 "0 1 1\r\n" + //
        //                 "1 1 1";
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static String solution(String[] args){
        int n = Integer.parseInt(args[0].split(" ")[0]); // range
        int m = Integer.parseInt(args[0].split(" ")[1]); // len
    
        int[] set = new int[n+1];
        int[] rank = new int[n+1];
        for(int i=0; i<=n; i++){
            set[i] = i;
            rank[i] = 0;
        }
        
        for(int i=1; i<=m; i++){
            int[] list = Arrays.stream(args[i].split(" ")).mapToInt(Integer::parseInt).toArray();
            
            if(list[0] == 0){ //합집합 
                union(set, rank, list[1], list[2]);
            }else {//==1 검증 연산
                System.out.println(findSet(set, list[1], list[2]));
            }

        }
        return "";
    }
    public static String findSet(int[] set, int a, int b){
            if(a == b || getSet(set, a) == getSet(set, b)){
                return "YES";
            }else{
                return "NO";
            }

    }
    //실제 값 = 간선으로의 연결 , 인덱스 = 숫자 
    public static int getSet(int[] set, int a){
        if(set[a] == a) {
            return a;
        }
        set[a] = getSet(set, set[a]);
        return set[a];
    }
    public static void union(int[] set,int[] rank, int a , int b){
        int rootA = getSet(set, a);
        int rootB = getSet(set, b);

        if(rootA == rootB) return;

        // rank가 낮은 트리를 rank가 높은 트리 아래에 붙임
        if(rank[rootA] < rank[rootB]){
            set[rootA] = rootB;
        } else if(rank[rootA] > rank[rootB]){
            set[rootB] = rootA;
        } else { // rank가 같으면 한쪽을 다른쪽에 붙이고 rank 증가
            set[rootB] = rootA;
            rank[rootA]++;
        }
    }
    /**
     * n+1개의 집합을 합치는 방법, 집합을 표현하는 방법.
     * 유니온파인드, 루트가 같다 = 같은 집합 소속이다
     * 메모리 초과 = 최악 > 전체 탐색 경우인가?
     * 128MB ->... 100만 getSet>? 아 sb...
     */
}