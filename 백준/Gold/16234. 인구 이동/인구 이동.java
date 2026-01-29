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
        
        // str = "4 10 50\r\n" + //
        //                 "10 100 20 90\r\n" + //
        //                 "80 100 60 70\r\n" + //
        //                 "70 20 30 40\r\n" + //
        //                 "50 20 100 10";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);  // 1~50
        int L = Integer.parseInt(args[0].split(" ")[1]);  // 1~100
        int R = Integer.parseInt(args[0].split(" ")[2]);  // 1~100

        int[][] world = new int[N][N];
        for(int i=0; i<N;i ++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<N; j++){
                world[i][j] = Integer.parseInt(row[j]);
            }
        }
        
        int day = 0;
        
        boolean isMove = true;
        while(isMove){
            boolean[][] visit = new boolean[N][N];
            isMove = false; //매 시도에 초기화
            for(int i=0; i<N; i++){
                for(int j=0; j<N; j++){
                    if(!visit[i][j]){
                        int result = BFS(world, visit, L, R, i, j);
                        if(result>1 && !isMove){//list가 2 이상 = 인구 이동 발생 , false일 때만 업데이트하면 됨.
                            isMove = true;
                        }
                    }
                }
            }   
            if(isMove){
                day++; //day일차 . 하루 지남
            }
        }
        System.out.println(day);
        // for(int i=0; i<N; i++){
        //     System.out.println(Arrays.toString(world[i]));
        // }
    }
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    public static int BFS(int[][] world, boolean[][] visit, int L, int R, int sy, int sx){
        int N = world.length;
        Queue<int[]> q = new LinkedList<>();
        List<int[]> adjCountry = new ArrayList<>();
        int[] fir = new int[]{sy,sx};
        q.add(fir); // 0-based 로
        visit[sy][sx] = true;
        adjCountry.add(fir);
        while(!q.isEmpty()){
            int[] currnet = q.poll();
            int curP = world[currnet[0]][currnet[1]];
            for(int d=0; d<4; d++){
                int ny = currnet[0] + dy[d];
                int nx = currnet[1] + dx[d];
                if(nx < 0 || ny < 0 || ny >= N || nx >= N) continue; //장외
                if(visit[ny][nx]) continue;
                int GAP = Math.abs(world[ny][nx]-curP);
                if(GAP < L || GAP > R) continue; //갈 수 없는, 국경선이 열리지 않는 지역

                int[] next = new int[]{ny,nx};
                q.add(next);
                adjCountry.add(next);
                visit[ny][nx] = true;
            }
        }
        int total = adjCountry.stream().map(t -> {
            return world[t[0]][t[1]];
        }).mapToInt(value -> value).sum(); //총합
        int dividedVal = total/adjCountry.size(); //나눈 값

        for(int[] c : adjCountry){ //연합
            world[c[0]][c[1]] = dividedVal;
        }
        
        return adjCountry.size();
    }
    /**
     * 경계선은 알빠노고 상하좌우로만 움직일 수 있으며,
     * BFS의 이동 조건이 L<=GAP, GAP<=R 이어야한다. 이동 시 List에 담아
     */

}

