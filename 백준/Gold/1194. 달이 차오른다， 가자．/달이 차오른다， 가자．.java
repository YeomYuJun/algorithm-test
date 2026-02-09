import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        
        // str = "7 8\r\n" + //
        //                 "a#c#eF.1\r\n" + //
        //                 ".#.#.#..\r\n" + //
        //                 ".#B#D###\r\n" + //
        //                 "0....F.1\r\n" + //
        //                 "C#E#A###\r\n" + //
        //                 ".#.#.#..\r\n" + //
        //                 "d#f#bF.1";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }

    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);

        int[] startPoint = new int[2];
        char[][] map = new char[N][M];

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split("");
            for(int j=0; j<M; j++){
                char v = row[j].charAt(0);

                if(v == '0'){ //민식 시작위치
                    startPoint[0] = i;
                    startPoint[1] = j;
                }
                map[i][j] = v;                
            }  
        }

        boolean[][][] visit = new boolean[N][M][64]; // 2^6

        Minsic ms = new Minsic(startPoint[0], startPoint[1], 0, 0);
        visit[ms.y][ms.x][0] = true;

        Queue<Minsic> q = new LinkedList<>();
        q.add(ms);
        
        while(!q.isEmpty()){
            Minsic choi = q.poll();
            
            for(int d=0; d<4; d++){
                int ny = choi.y + dy[d];
                int nx = choi.x + dx[d];

                if(ny < 0 || nx < 0 || ny >= N || nx >= M) continue;
                if(map[ny][nx] == '#') continue; //벽임
                if(visit[ny][nx][choi.keyBit]) continue; //이 키 조합으로 방문했음
                
                if(map[ny][nx] == '1'){
                    System.out.println(choi.moveCount+1);
                    return;
                }
                if(map[ny][nx] == '.' || map[ny][nx] == '0'){
                    visit[ny][nx][choi.keyBit] = true;
                    q.add(new Minsic(ny, nx, choi.moveCount+1, choi.keyBit));
                }
                
                if(map[ny][nx] >= 'A' && map[ny][nx] <= 'F'){ //문 
                    int needKeyBit = 1 << (map[ny][nx] - 'A');
                    if((choi.keyBit & needKeyBit) != 0){ //키 있음
                        visit[ny][nx][choi.keyBit] = true;
                        q.add(new Minsic(ny, nx, choi.moveCount+1, choi.keyBit));
                    }
                    // 키 없으면 그냥 continue (나중에 키 얻고 다시 올 수 있음)
                }
                if(map[ny][nx] >= 'a' && map[ny][nx] <= 'f'){ //열쇠
                    int newKeyBit = 1 << (map[ny][nx] - 'a');
                    int newKeyBitmask = choi.keyBit | newKeyBit;
                    
                    if(!visit[ny][nx][newKeyBitmask]){
                        visit[ny][nx][newKeyBitmask] = true;
                        q.add(new Minsic(ny, nx, choi.moveCount+1, newKeyBitmask));
                    }
                }
            }
        }
        
        System.out.println(-1);
    }
    
    static class Minsic {
        int y;
        int x;
        int moveCount;
        int keyBit; // 비트마스크로 키 상태 표현 (a=0번 비트, b=1번 비트, ...)

        public Minsic(int y, int x, int mc, int keyBit){
            this.y = y;
            this.x = x;
            this.moveCount = mc;
            this.keyBit = keyBit;
        }
    }
    /**
     * 맵에 key 존재 여부를 미리 파악해둘 필요가 있는가? 
     * 
     * 목적지를 달리하는 BFS 를 만들어놓고(input으로 받아서 사용)
     * 첫 각 열쇠를 먹고, 가는 데 걸린 최단시간,? 
     * 
     * 아 역으로 출구부터 거쳐야 하는 루트를 뚫고 지나가보면?
     * 아 이게 최적이 아님.. 흠..
     * 그럼 시작 위치 : 현재 위치 : 목표 위치 셋을 가지고 Queue를 돌리면?
     * 문 도착 => 키 없음( BFS니까 키가 더 멀다는 뜻)
     *        => 시작 위치(민식위치)에서 키까지 BFS 탐색,
     *        => 할 필요가 없음 최단거리로 이동하다보면 다시 반복될 거임
     * 키를 찾는 Queue에서 다시 이동, 방문 초기화 ?
     * 여기서 다시탐색 = visit 새거 사용? 키를 가지고 방문했는지 
     * [y][x][key종류] a를 들고 있고, b를 들고있고, ~ f를 들고있고. 드는 모든 조합에 따른 방문 여부가 필요함
     * 2^6-1 = 63
     * 
     * 
     * 
     */
}