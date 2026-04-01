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
            
        // ////////////////////////////////////////////
        
        // str = "5\r\n" +
        //                 "0 6 15 2 6\r\n" +
        //                 "6 0 9 8 12\r\n" +
        //                 "15 9 0 16 18\r\n" +
        //                 "2 8 16 0 4\r\n" +
        //                 "6 12 18 4 0";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) {
        int N = Integer.parseInt(args[0]);

        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            String[] row = args[i + 1].split(" ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(row[j]);
            }
        }

        //i != j인데 0이면 불가능
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j && map[i][j] == 0) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        //플로이드 워셜?로 
        boolean[][] removable = new boolean[N][N];

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i == j || k == i || k == j) continue;
                    int via = map[i][k] + map[k][j];
                    if (via < map[i][j]) {
                        // 주어진 행렬이 최단거리 행렬이 아니면 ?? 불가능임
                        System.out.println(-1);
                        return;
                    } else if (via == map[i][j]) {
                        // k를 경유해도 동일 비용
                        removable[i][j] = true;
                    }
                }
            }
        }

        long answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (!removable[i][j]) {
                    answer += map[i][j];
                }
            }
        }

        System.out.println(answer);
    }
}