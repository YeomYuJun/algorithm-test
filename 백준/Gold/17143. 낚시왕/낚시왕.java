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
        
        // str = "4 4 1\r\n" + //
        //                 "3 3 10 1 3";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int R = Integer.parseInt(args[0].split(" ")[0]);  // 2~100
        int C = Integer.parseInt(args[0].split(" ")[1]);  // 2~100
        int M = Integer.parseInt(args[0].split(" ")[2]);  //상어의 수 0~RC

        if(M==0){
            System.out.println(0);
            return;
        }

        int[][] pool = new int[R+1][C+1]; // 1-based 
        Map<Integer, Shark> sharkMap = new HashMap<>();
        for(int i=0; i<M; i++){
            String[] row = args[i+1].split(" ");

            int r = Integer.parseInt(row[0]); 
            int c = Integer.parseInt(row[1]); //[r][c] position
            int s = Integer.parseInt(row[2]); //speed
            int d = Integer.parseInt(row[3]); //direction
            int z = Integer.parseInt(row[4]); //size

            Shark shark = new Shark(r, c, s, d, z);
            pool[r][c] = z;//size=id, 로 위치시키기
            sharkMap.put(z, shark);
        }


        int player = 0;
        int bucket = 0;

        while(player<C){
            //0. 잡기 전 조회
            // for(int i=0; i<=R; i++){
            //     System.out.println(Arrays.toString(pool[i]));
            //     for(int j=0; j<C; j++){
            //     }
            // }
            // System.out.println("===========================================");
            //1. player가 오른쪽 1칸 이동
            player++;
            //2. 상어잡기.
            for(int i=1; i<=R; i++){
                int point = pool[i][player];
                if(point != 0){
                    bucket+=point;
                    pool[i][player]= 0;
                    sharkMap.remove(point);
                    break;
                }
            }
            //3. 상어 이동 turn.
            sharkshark(pool, sharkMap);
        }
        System.out.println(bucket);
       
    }
    public static void sharkshark(int[][] pool, Map<Integer,Shark> sharkMap){
        int R = pool.length-1;
        int C = pool[0].length-1;
        List<Integer> sharks = sharkMap.keySet().stream().collect(Collectors.toList());

        //맵에서 for{for{}} 하면 이동한 shark가 또 걸릴 수 있음
        int sharkCount = sharks.size();
        List<Integer> deadSharks = new ArrayList<>(); //잡아먹힌 녀석
        List<int[]> sharkmoveList = new ArrayList<>();
        for(int i = 0; i<sharkCount; i++ ){
            Shark shark = sharkMap.get(sharks.get(i));
            //d: 1북 2남 3동 4서
            int r = shark.r;
            int c = shark.c;
            int s = shark.s;
            int d = shark.d; // 1:북, 2:남, 3:동, 4:서

            int laneLen, pos, mod;
            
            if (d <= 2) { // 상하
                mod = (R - 1) * 2;
                if (mod > 0) {
                    int currentPos = (d == 1) ? (mod - (r - 1)) : (r - 1);
                    int nextPos = (currentPos + s) % mod;

                    if (nextPos < R) {
                        r = nextPos + 1;
                        d = 2; // 남쪽 (내려가는 중)
                    } else {
                        r = mod - nextPos + 1;
                        d = 1; // 북쪽 (올라가는 중)
                    }
                }
            } else { // 좌우
                mod = (C - 1) * 2;
                if (mod > 0) {
                    // d=4(서)는 감소 방향이므로 보정
                    int currentPos = (d == 4) ? (mod - (c - 1)) : (c - 1);
                    int nextPos = (currentPos + s) % mod;

                    if (nextPos < C) {
                        c = nextPos + 1;
                        d = 3; // 동쪽 (증가 중)
                    } else {
                        c = mod - nextPos + 1;
                        d = 4; // 서쪽 (감소 중)
                    }
                }
            }

            pool[shark.r][shark.c] = 0;
            shark.d = d;
            sharkmoveList.add(new int[]{shark.z, r, c});
        }
        for(int[] sMove : sharkmoveList){
            int z = sMove[0];
            Shark curShark = sharkMap.get(z);
            if(pool[sMove[1]][sMove[2]] == 0){ //안전
                pool[sMove[1]][sMove[2]] = z;
                curShark.r = sMove[1];
                curShark.c = sMove[2];
            }else if(pool[sMove[1]][sMove[2]] > sMove[0]){ //이동할 녀석 사망
                deadSharks.add(sMove[0]);
            }else { //기존 애 사망처리
                deadSharks.add(pool[sMove[1]][sMove[2]]);
                pool[sMove[1]][sMove[2]] = sMove[0];
                curShark.r = sMove[1];
                curShark.c = sMove[2];
            }           
        }
        for(Integer z : deadSharks){
            sharkMap.remove(z);
        }
    }

    static class Shark {
        int r;
        int c;
        int s;
        int d;
        int z;
        
        public Shark(int r, int c, int s, int d, int z){
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.z = z;
        }
    }

}

