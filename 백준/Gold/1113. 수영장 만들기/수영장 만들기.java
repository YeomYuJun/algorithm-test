import java.io.*;
import java.util.*;

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
        
        // str = "11 11\r\n" + //
        //                 "22222922222\r\n" + //
        //                 "21119191112\r\n" + //
        //                 "21119191112\r\n" + //
        //                 "21119191112\r\n" + //
        //                 "29999199992\r\n" + //
        //                 "91111111119\r\n" + //
        //                 "29999199992\r\n" + //
        //                 "21119191112\r\n" + //
        //                 "21119191112\r\n" + //
        //                 "21119191112\r\n" + //
        //                 "22222922222"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    static int[] dx = {1,-1, 0, 0};
    static int[] dy = {0, 0, 1,-1};
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);

        if(N<3 || M<3){ //물을 담을 수 없는 구조
            System.out.println(0);
            return;
        }

        int[][] pool = new int[N][M];

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split("");
            for(int j=0; j<M; j++){
                pool[i][j] = Integer.parseInt(row[j]);
            }
        }

        //외곽 boolean 초기화 => 담은곳도 방문할 필요가 없음 
        boolean[][] isFall = new boolean[N][M];
        for(int i=0; i<N; i++){
            isFall[i][0] = true;
            isFall[i][M-1] = true;;
        }
        Arrays.fill(isFall[0], true);
        Arrays.fill(isFall[N-1], true);
        
        int[][] pourMap = new int[N][M]; // 어느정도 부었는지? 
        boolean[][] visit = new boolean[N][M]; //우물 안 탐색 여부
        for(int i=1; i<N-1; i++){
            for(int j=1; j<M-1; j++){
                if(!isFall[i][j] && pourMap[i][j] == 0){
                    letsPour(i, j, pool, isFall, pourMap);
                }
            }
        }
        int total = 0;
        for(int i=0; i<N; i++){
            // System.out.println(Arrays.toString(pourMap[i]));
            int sum = Arrays.stream(pourMap[i]).sum();
            total += sum;
        }
        System.out.println(total);
    }

    public static void letsPour(int cy, int cx, int[][] pool, boolean[][] isFall, int[][] pourMap){
        int N = pool.length; int M = pool[0].length;

        int[] pour = new int[]{cy, cx, pool[cy][cx], 0}; // y, x, h, 
        int ch = pool[cy][cx];
        Queue<int[]> q = new LinkedList<>();
        q.add(pour);

        int closedH = 10; //현재 queue에서 가장 높은 벽
        List<int[]> pourList = new ArrayList<>();
        boolean[][] visit = new boolean[N][M];
        boolean localFall = false;
        while(!q.isEmpty()){
            int[] pours = q.poll();
            
            int y = pours[0];
            int x = pours[1];
            int h = pool[y][x];
            visit[y][x] = true; 
            int wall = 10;

            for(int d=0; d<4; d++){
                int ny = y+dy[d];
                int nx = x+dx[d];
                
                if(ny < 0 || nx < 0 || ny >= N || nx >= M) continue; // 아예 바깥.
                if(visit[ny][nx]) continue;
                
                int nh = pool[ny][nx];
                // if(pourMap[ny][nx] > 0) continue;
                
                if(nh>Math.max(ch,h)){ //갈 수 없는 벽 == 담을 수 있는 최소 조건
                    if(ch < nh){ //
                        wall = Math.min(wall,nh);
                    }
                }else{ //갈 수 있음.
                    if(isFall[ny][nx]){ //
                        // isFall[y][x] = true; // 흘러가는 곳이면
                        localFall = true;
                        q.clear();
                        break;
                    }else{
                        int[] newPour = new int[]{ny,nx};
                        visit[ny][nx] = true; 
                        pourList.add(newPour);
                        q.offer(newPour);
                    }
                }
            }
            closedH = Math.min(closedH, wall);

        }

        if(localFall){
            isFall[cy][cx] = true;
            return;
        }
        // if(closedH < ch){ //시작점이 제일 높은 경우.
        //     return;
        // }
        
        if(pourList.size() == 0){ //여기서 갈 수 있는 곳이 없음.
            pourMap[cy][cx] = Math.max(pourMap[cy][cx], closedH-pool[cy][cx]);
        }else{
            for(int[] p : pourList){
                int py = p[0];
                int px = p[1];

                if(!isFall[py][px]){
                    pourMap[py][px] = Math.max(pourMap[py][px], closedH-pool[py][px]);

                }
            }
            pourMap[cy][cx] = Math.max(pourMap[cy][cx], closedH-pool[cy][cx]);
        }
        
    }
    /**
     * 각 끝에 도달하면 물이 탈출하므로, 1~N-2/1~M-2까지로 구해야함.
     * BFS로, 끝에 도달하면 0.
     * 그리고 boolean으로, 놓았더니 물이 바깥 탈출하는 지점 [y][x]도 true로 전환해서
     * 거기 닿으면 당연히 탈출하게끔 설정. queue가 깊어지지않게?
     * 
     * 높이에 따른 물 붓는 양의 설정 = 높이차, 초기에는 흘러가는 공간이 존재할텐데,
     * min으로는 주면 같거나 낲은 높이도 확인됨.
     * max로주면 주변 사방이 6 5 4 1 일 때 4를 구할 수 없음.
     * 탈출할 수 없는 순간에 다시 탐색?
     * 낮은 곳으로 흘러갈 때, 갈 수 없는 주변 벽 중 가장 낮은 벽을 몰고 다니면?
     * 
     * 아니면, 떨어지지 않는 상승 구간
     * 
     */

}
