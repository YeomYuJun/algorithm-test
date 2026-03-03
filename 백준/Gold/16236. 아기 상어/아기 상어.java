import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import org.w3c.dom.Node;

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
        
        // str = "";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        int[][] map = new int[N][N];

        int sharkPosY = 0;
        int sharkPosX = 0;
        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<N; j++){
                int val = Integer.parseInt(row[j]);
                map[i][j] = val;
                if(val == 9){
                    sharkPosY = i;
                    sharkPosX = j;
                    map[i][j] = 0; //텅빈공간처리
                }
            }
        }
            
        //거리가 가까운, 많아도 가장 가까운 = BFS고
        //가장 위, 가장 왼쪽은 좌 상 우 하(하 우) 순으로 이동하란 뜻이고 
        //dy,dx 위치 설정으로 가장 위, 가장 위에서 왼쪽이 설정이안됨.

        //먹는 순간 이동도 count

        //0. 상어 초기화
        int[] shark = {sharkPosY, sharkPosX, 0, 0};  // y, x, 먹은 양, 이동 횟수.
        // 1. findAbleFish로 가장 가까운 fish로 이동하는 BFS. 
        
        while(true){
            int[] result = findAbleFish(map, shark);
            if(result == null || (result[0] == shark[0] && result[1] == shark[1])){
                break;
            }else{
                shark = result.clone();
            }
        }
        System.out.println(shark[3]);
    }
    static int[] dy = {-1,0,0,1};
    static int[] dx = {0,-1,1,0};

    public static int[] findAbleFish(int[][] map, int[] shark){
        int N = map.length;

        Queue<int[]> q = new LinkedList<>();
        // PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> {
        //     if(o1[3]==o2[3]){ //이동 거리가 같으면 위쪽 
        //         if(o1[0]==o2[0]){ //y가 같다면
        //             return o1[1] - o2[1]; //왼쪽 것
        //         }
        //         return o1[0] - o2[0];
        //     }
        //     return o1[3]-o2[3];
        // });
        
        List<int[]> candidates = new ArrayList<>();

        q.offer(shark);
        boolean[][] visit = new boolean[N][N];
        int dist = 1_000_000;
        while(!q.isEmpty()){
            int[] sh = q.poll();
            
            int shY = sh[0];
            int shX = sh[1];
            visit[shY][shX] = true;

            int curVal = map[shY][shX];
            if(sh[3] > dist){ //길이가 길면 일단 버리기(여기보다 더 갈 필요가 없음)
                continue;
            }
            if(curVal > 0 && isEatable(curVal, sh[2])){ //먹을 수 있음
                int[] eatenShark = new int[]{shY, shX, sh[2]+1, sh[3]};
                candidates.add(eatenShark);
                dist = sh[3];
                //return eatenShark; //끝내지말고 List에서 추리기.
                continue;
            }

            for(int d=0; d<4; d++){
                int ny = shY + dy[d];
                int nx = shX + dx[d];

                if(ny < 0 || nx < 0 || ny >= N || nx >= N) continue; //장외
                if(visit[ny][nx]) continue; //기방문

                int mapVal = map[ny][nx];
                
                if(mapVal > 0){
                    if(mapVal <= getSharkSize(sh[2])){ //같거나 작은 사이즈 = 이동만
                        visit[ny][nx] = true;
                        q.add(new int[]{ny, nx, sh[2], sh[3]+1});
                    }else {
                        continue;// 더큼. 못먹음.
                    }
                }else{ //0임.
                    visit[ny][nx] = true;
                    q.add(new int[]{ny, nx, sh[2], sh[3]+1}); //0 = 그냥 이동
                }
            }
        }
        if(candidates.size() == 0) return shark;
        candidates.sort(
            Comparator.comparingInt((int[] v) -> v[0])
                    .thenComparingInt(v -> v[1])
        );
        int[] closestShark = candidates.get(0);
        map[closestShark[0]][closestShark[1]] = 0;  // 이 줄 추가 //최선의 가까운 먹이 먹었다고 봐야함.
        return closestShark;
    }
    static int getSharkSize(int eatCount){
        return (int)((Math.sqrt(9 + 8.0 * eatCount) - 3 ) / 2) + 2;
    }
    static boolean isEatable(int fish, int eatCount){
        if(fish==0) return true;

        return (int)((Math.sqrt(9 + 8.0 * eatCount) - 3 ) / 2) + 1 >= fish; //역산된 현재 먹을 수 있는 물고기 수가 더 크면 먹을 수 있다는 뜻 
    }
    /**
     * 
     * 상어 크기
     * 상어 크기   2 3 4 5 6
     * 
     * 0~  = 2 (먹을 수 있는 거)= 1    
     * 2~  = 3 , 2
     * 5~  = 4 , 3
     * 9~  = 5 , 4
     * 14~ = 6 , 5
     * 20~ = 7 , 6(다 먹을 수 있음)
     * 0~1 / 2~4 / 5~8 / 9~13 / 14~19 / 20~
     * n(n+3)/2  <= x    n^2 + 3n - 2x = 0, n= Math.sqrt(9+8x)-3 / 2 +1
     */
}

