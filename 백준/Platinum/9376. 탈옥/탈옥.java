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
        
        // str = "1\r\n" + //
        //                 "6 6\r\n" + //
        //                 "*#****\r\n" + //
        //                 "*.$..*\r\n" + //
        //                 "***#**\r\n" + //
        //                 "***#**\r\n" + //
        //                 "*.$..*\r\n" + //
        //                 "**#***";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int TC = Integer.parseInt(args[0]); // TC 1~100

        int idx=0;
        for(int tc=0; tc<TC; tc++){
            String[] hw = args[++idx].split(" ");
            int h = Integer.parseInt(hw[0]);
            int w = Integer.parseInt(hw[1]);

            char[][] map = new char[h][w];

            List<int[]> crim = new ArrayList<>();
            for(int i=0; i<h; i++){
                String[] row = args[++idx].split("");
                for(int j=0; j<w; j++){
                    char cell = row[j].charAt(0);
                    if('$' == cell){
                        crim.add(new int[]{i,j}); //pos 추가
                    }
                    map[i][j] = cell;
                }
            }

            //상근이가 탈출 수행
            escape(crim, map);
        }
    }

    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    static int INF = 9999;
    public static void escape(List<int[]> crim, char[][] map) {
        int N = map.length;
        int M = map[0].length;

        int[][][] visit = new int[3][N][M];
        for (int i = 0; i < 3; i++) {
            for (int n = 0; n < N; n++) Arrays.fill(visit[i][n], INF);
        }

        run01BFS(crim.get(0), map, visit[1]);
        run01BFS(crim.get(1), map, visit[2]);
        
        runOutsideBFS(map, visit[0]);

        int minVal = INF;
        int oneMin = INF;
        int twoMin = INF;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '*') continue;
                if (visit[0][i][j] != INF && visit[1][i][j] != INF && visit[2][i][j] != INF){
                    int sum = visit[0][i][j] + visit[1][i][j] + visit[2][i][j]; 
                    // 만약 현재 위치가 둘다 겹친 거니까 -2
                    if (map[i][j] == '#') sum -= 2;
                    minVal = Math.min(minVal, sum);
                }
                if (visit[0][i][j] != INF && visit[1][i][j] != INF ){
                    int sum = visit[0][i][j] + visit[1][i][j];
                    // 한 명만 겹친 거니까 -1
                    if (map[i][j] == '#') sum -= 1;
                    oneMin = Math.min(oneMin, sum);
                }
                if (visit[0][i][j] != INF && visit[2][i][j] != INF){   
                    int sum = visit[0][i][j] + visit[2][i][j];
                    if (map[i][j] == '#') sum -= 1;
                    twoMin = Math.min(twoMin, sum);
                }
            }
        }

        System.out.println(Math.min(minVal, oneMin+twoMin));
    }

    public static void run01BFS(int[] start, char[][] map, int[][] dist) {
        int N = map.length;
        int M = map[0].length;
        Deque<int[]> dq = new ArrayDeque<>();

        dist[start[0]][start[1]] = (map[start[0]][start[1]] == '#') ? 1 : 0;
        dq.addFirst(new int[]{start[0], start[1]});

        while (!dq.isEmpty()) {
            int[] curr = dq.pollFirst();
            int y = curr[0];
            int x = curr[1];

            for (int d = 0; d < 4; d++) {
                int ny = y + dy[d];
                int nx = x + dx[d];

                if (ny < 0 || nx < 0 || ny >= N || nx >= M || map[ny][nx] == '*') continue;

                int cost = (map[ny][nx] == '#') ? 1 : 0;
                if (dist[ny][nx] > dist[y][x] + cost) {
                    dist[ny][nx] = dist[y][x] + cost;
                    if (cost == 0) dq.addFirst(new int[]{ny, nx});
                    else dq.addLast(new int[]{ny, nx});
                }
            }
        }
    }
    public static void runOutsideBFS(char[][] map, int[][] dist) {
        int N = map.length;
        int M = map[0].length;
        Deque<int[]> dq = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i == 0 || j == 0 || i == N - 1 || j == M - 1) {
                    if (map[i][j] == '*') continue;
                    int cost = (map[i][j] == '#') ? 1 : 0;
                    dist[i][j] = cost;
                    if (cost == 0) dq.addFirst(new int[]{i, j});
                    else dq.addLast(new int[]{i, j});
                }
            }
        }

        while (!dq.isEmpty()) {
            int[] curr = dq.pollFirst();
            int y = curr[0];
            int x = curr[1];

            for (int d = 0; d < 4; d++) {
                int ny = y + dy[d];
                int nx = x + dx[d];

                if (ny < 0 || nx < 0 || ny >= N || nx >= M || map[ny][nx] == '*') continue;

                int cost = (map[ny][nx] == '#') ? 1 : 0;
                if (dist[ny][nx] > dist[y][x] + cost) {
                    dist[ny][nx] = dist[y][x] + cost;
                    if (cost == 0) dq.addFirst(new int[]{ny, nx});
                    else dq.addLast(new int[]{ny, nx});
                }
            }
        }
    }
    /**
     * visit을 boolean이 아니라 문을 딴 횟수로 하고,
     * 해당 위치에 min으로 갱신 시키는 느낌으로 이동하면 될듯?
     * 문 3번따고 간다, 근데 이미 2번따고 갈 수 있다 ( 안감 ) 
     * 근데, 범죄자가 둘이니까 각각 으로 [N][M][2]로 하고, 탈출하는 순간 max로? fill 하기 힘드니까
     * 상근이 끼우면 [3][N][M]
     */
}