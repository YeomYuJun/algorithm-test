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

        // str = "5 5\r\n" + //
        //                 "0 0 0 0 0\r\n" + //
        //                 "0 0 10 10 0\r\n" + //
        //                 "0 10 0 10 0\r\n" + //
        //                 "0 0 10 10 0\r\n" + //
        //                 "0 0 0 0 0";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);

        int[][] iceberg = new int[N][M];
        //y=5, x=7 

        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            for(int j=0; j<M; j++){
                int val = Integer.parseInt(row[j]);
                iceberg[i-1][j] = val;
            }
        }
        int answer = 0;
        
        while(true){
            int result = isConnected(iceberg);
            if(result == 0){
                break;
            }else if(result == 2){
                answer = 0;
                break;
            }else {
                answer++;
            }
        }

        //bfs로 탐색 및 감소 시작
        System.out.println(answer);
    }


    public static int isConnected(int[][] iceberg){
        int startX = -1;
        int startY = -1;
        int iceCnt = 0;
        boolean[][] visit = new boolean[iceberg.length][iceberg[0].length];
        int[][] minusMap = new int[iceberg.length][iceberg[0].length];
        
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};

        for(int i=0; i<iceberg.length; i++){
            for(int j=0; j<iceberg[0].length; j++){
                if(iceberg[i][j] != 0){
                    iceCnt++;
                    if(startX == -1 && startY == -1){ //초기 시작점 탐색 확인
                        startY = i;
                        startX = j;
                    }
                }else{//감소값 계산
                    for(int d=0; d<4; d++){
                        int nx = j+dx[d];
                        int ny = i+dy[d];
                        if(nx<0 || ny<0 || nx>=iceberg[0].length || ny>= iceberg.length) continue;
                        minusMap[ny][nx] += 1;
                    }
                }
            }
        }
        if(iceCnt == 0) return 2;

        Queue<int[]> q = new LinkedList<>();

        int[] sc = new int[]{startX,startY,0};
        q.offer(sc);
        visit[startY][startX] = true;

        int realCnt = 0;
        while(!q.isEmpty()){
            int[] pos = q.poll();
            int x = pos[0];
            int y = pos[1];
            realCnt++;
            
            iceberg[y][x] = iceberg[y][x] < minusMap[y][x] ? 0 : iceberg[y][x] - minusMap[y][x];
            for(int d=0; d<4; d++){
                int nx = x+dx[d];
                int ny = y+dy[d];
                if(nx<0 || ny<0 || nx>=iceberg[0].length || ny>= iceberg.length) continue;// 장외
                if(iceberg[ny][nx]==0) continue; //바다
                if(visit[ny][nx]) continue; //기 방문
                
                
                q.offer(new int[]{nx,ny});
                visit[ny][nx] = true;
            }
        }
        return realCnt == iceCnt ? 1 : 0; //같다가 같지않아지면 갈라진 것으로 간주
    }
    /**
     * isConn? 으로 초기 분리 확인 (BFS로 연결 확인) => 전체 탐색 후 빙산 크기 계산, => 계산만큼 이동 가능 = 연결
     * 
     */
}