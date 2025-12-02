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

        // str = "5\r\n" + //
        //                 "RRRBB\r\n" + //
        //                 "GGBBB\r\n" + //
        //                 "BBBRR\r\n" + //
        //                 "BBRRR\r\n" + //
        //                 "RRRRR";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        //r = 1 ; g = 2 ; b = 3;

        int[][] map = new int[n][n];
        for(int i=1; i<=n; i++){
            String[] row = args[i].split("");
            for(int j=0; j<row.length; j++){
                map[i-1][j] = row[j].equals("R") ? 1 : row[j].equals("G") ? 2 : 3;
            }
        }

        int area = 0;
        int rgArea = 0;
        boolean[][] visit = new boolean[n][n];
        boolean[][] rgVisit = new boolean[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(!visit[i][j]){
                    findArea(visit,map, i,j);
                    area++;
                }
                if(!rgVisit[i][j]){
                    findAreaWeek(rgVisit,map, i,j);
                    rgArea++;
                }
            }
        }
        System.out.println(area+ " " + rgArea);
    }
    public static void findArea(boolean[][] visit, int[][] map, int i, int j){
        Queue<int[]> q = new LinkedList<>();
        int len = visit.length;
        q.offer(new int[]{i,j});
        visit[i][j] = true;
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        while(!q.isEmpty()){
            int[] pos = q.poll();
            for(int drct =0; drct<4; drct++){
                int nextI = pos[0]+dx[drct];
                int nextJ = pos[1]+dy[drct];

                if(nextI >= len || nextJ >= len || nextI < 0 || nextJ < 0) continue; //장외
                if(visit[nextI][nextJ]) continue; //기방문
                if(map[nextI][nextJ] != map[i][j]) continue; //같은 영역만 탐색 
                q.offer(new int[]{nextI, nextJ});
                visit[nextI][nextJ] = true;
            }
        }
    }
    public static void findAreaWeek(boolean[][] visit, int[][] map, int i, int j){
        Queue<int[]> q = new LinkedList<>();
        int len = visit.length;
        q.offer(new int[]{i,j});
        visit[i][j] = true;
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        while(!q.isEmpty()){
            int[] pos = q.poll();
            for(int drct =0; drct<4; drct++){
                int nextI = pos[0]+dx[drct];
                int nextJ = pos[1]+dy[drct];

                if(nextI >= len || nextJ >= len || nextI < 0 || nextJ < 0) continue; //장외
                if(visit[nextI][nextJ]) continue; //기방문
                if(map[i][j] == 3 ){
                    if(map[nextI][nextJ] != 3) continue; //블루 영역만 탐색 
                }else{
                    if(map[nextI][nextJ] == 3) continue; //블루 아닌 영역만 탐색 
                }
                q.offer(new int[]{nextI, nextJ});
                visit[nextI][nextJ] = true;
            }
        }
    }
    /**
     * map에서 시작 char 에서 boolean처리하고 탐색, 종료 bfs끝날 때마다 구역++;
     */
}

