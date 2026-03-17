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
            
        // ////////////////////////////////////// /////
        
        // str = "20 20\r\n" + //
        //                 "ABCDEFGHIJKLMNOPQRST\r\n" + //
        //                 "BCDEFGHIJKLMNOPQRSTA\r\n" + //
        //                 "CDEFGHIJKLMNOPQRSTAA\r\n" + //
        //                 "DEFGHIJKLMNOPQRSTAAA\r\n" + //
        //                 "EFGHIJKLMNOPQRSTAAAA\r\n" + //
        //                 "FGHIJKLMNOPQRSTAAAAA\r\n" + //
        //                 "GHIJKLMNOPQRSTAAAAAA\r\n" + //
        //                 "HIJKLMNOPQRSTAAAAAAA\r\n" + //
        //                 "IJKLMNOPQRSTAAAAAAAA\r\n" + //
        //                 "JKLMNOPQRSTAAAAAAAAA\r\n" + //
        //                 "KLMNOPQRSTZAAAAAAAAA\r\n" + //
        //                 "LMNOPQRSTAYAAAAAAAAA\r\n" + //
        //                 "MNOPQRSTAAXAAAAAAAAA\r\n" + //
        //                 "NOPQRSTAABWXAAAAAAAA\r\n" + //
        //                 "OPQRSTAAAAVAAAAAAAAA\r\n" + //
        //                 "PQRSTAAAAACAAAAAAAAA\r\n" + //
        //                 "QRSTAAAAAAAAAAAAAAAA\r\n" + //
        //                 "RSTAAAAAAAAAAAAAAAAA\r\n" + //
        //                 "STAAAAAAAAAAAAAAAAAA\r\n" + //
        //                 "TAAAAAAAAAAAAAAAAAAA";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int r = Integer.parseInt(args[0].split(" ")[0]);
        int c = Integer.parseInt(args[0].split(" ")[1]);

        String[][] map = new String[r][c];
        for(int i=0; i<r; i++){
            String[] row = args[i+1].split("");
            for(int j=0; j<c ; j++){
                String s = row[j];
                map[i][j] = s;
            }
        }
        dfs(map, 0, 0); 

        System.out.println(MAX);

    }
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    static int MAX = Integer.MIN_VALUE;
    static int count = 0;
    static boolean[] visited = new boolean[26];  // 알파벳 26개 체크


    public static void dfs(String[][] map, int curX, int curY) {
        int R = map.length;
        int C = map[0].length;
        int idx = map[curY][curX].charAt(0) - 'A';
        visited[idx] = true;
        count++;  // 현재 depth 추적
        
        MAX = Math.max(MAX, count);
        
        for (int d = 0; d < 4; d++) {
            int nx = curX + dx[d];
            int ny = curY + dy[d];
            if (nx < 0 || ny < 0 || nx >= C || ny >= R) continue;
            int nIdx = map[ny][nx].charAt(0) - 'A';
            if (visited[nIdx]) continue;
            dfs(map, nx, ny);
        }
        
        visited[idx] = false;  // 
        count--;
    }
    /**
     * 최대의 칸 수 = DFS? 
     * 각 밟는 시점에 알파벳의 방문 여부 
     * 그냥 Stack을 BFS로 돌리면?  20x20 = 400, 400*26=  10,400
     * 
     */
        
}

