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
            
        ///////////////////////////////////////////
        
        // str = "10 0 30";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);
        int H = Integer.parseInt(args[0].split(" ")[2]);

        int[][] map = new int[H + 1][N + 1];
        
        for (int i = 0; i < M; i++) {
            if (i + 1 < args.length) {
                String[] row = args[i + 1].trim().split(" ");
                if (row.length >= 2) {
                    int a = Integer.parseInt(row[0]);
                    int b = Integer.parseInt(row[1]);
                    map[a][b] = 1;
                }
            }
        }

        for (int limit = 0; limit <= 3; limit++) {
            if (dfs(map, N, H, 1, 1, 0, limit)) {
                System.out.println(limit);
                return;
            }
        }
        System.out.println(-1);
    }

    // 백트래킹 함수
    private static boolean dfs(int[][] map, int N, int H, int r, int c, int count, int limit) {
        if (count == limit) {
            return check(map, N, H);
        }

        for (int i = r; i <= H; i++) {
            // 같은 행이면 현재 열(c)부터, 새로운 행이면 1열부터 탐색
            int startC = (i == r) ? c : 1;
            for (int j = startC; j < N; j++) {
                if (map[i][j] == 0 && map[i][j - 1] == 0 && map[i][j + 1] == 0) {
                    map[i][j] = 1; // 사다리 놓기
                    if (dfs(map, N, H, i, j + 2, count + 1, limit)) return true;
                    map[i][j] = 0; // 사다리 치우기
                }
            }
        }
        return false;
    }

    private static boolean check(int[][] map, int N, int H) {
        for (int i = 1; i <= N; i++) {
            int current = i;
            for (int h = 1; h <= H; h++) {
                if (map[h][current] == 1) {
                    current++; // 오른쪽으로 이동
                } else if (current > 1 && map[h][current - 1] == 1) {
                    current--; // 왼쪽으로 이동
                }
            }
            if (current != i) return false;
        }
        return true;
    }
}

