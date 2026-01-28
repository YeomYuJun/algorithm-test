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
        
        // str = "4 4\r\n" + //
        //                 "1 1 1 1\r\n" + //
        //                 "1 1 1 2\r\n" + //
        //                 "1 1 1 2\r\n" + //
        //                 "1 2 2 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); //4~500
        int M = Integer.parseInt(args[0].split(" ")[1]); //4~500


        // [500][500] 
        int[][] map = new int[N][M];

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(row[j]);
            }
        }

        List<int[][]> tetromino = new ArrayList<>();
        // int[][] stick = {{0,0},{0,1},{0,2},{0,3}};
        // int[][] cube = {{0,0},{0,1},{1,1},{1,0}};
        // int[][] convex1 = {{0,0},{0,1},{1,1},{0,2}};
        // int[][] L1 = {{0,0},{1,0},{2,0},{2,1}};
        // int[][] L2 = {{0,0},{1,0},{2,0},{2,-1}};
        // int[][] zig1 = {{0,0},{1,0},{1,1},{2,1}};
        // int[][] zig2 = {{0,0},{1,0},{1,-1},{2,-1}};
        // tetromino.add(stick);
        // tetromino.add(cube);
        // tetromino.add(convex1);
        // tetromino.add(L1);
        // tetromino.add(L2);
        // tetromino.add(zig1);
        // tetromino.add(zig2);
        tetromino.add(new int[][]{{0,0},{0,1},{0,2},{0,3}});
        tetromino.add(new int[][]{{0,0},{1,0},{2,0},{3,0}});
        tetromino.add(new int[][]{{0,0},{1,0},{0,1},{1,1}});
        tetromino.add(new int[][]{{0,0},{1,0},{2,0},{2,1}});
        tetromino.add(new int[][]{{0,1},{1,1},{2,1},{2,0}});
        tetromino.add(new int[][]{{0,0},{0,1},{1,1},{2,1}});
        tetromino.add(new int[][]{{0,0},{0,1},{1,0},{2,0}});
        tetromino.add(new int[][]{{0,0},{1,0},{1,1},{1,2}});
        tetromino.add(new int[][]{{0,2},{1,1},{1,2},{1,0}});
        tetromino.add(new int[][]{{0,0},{0,1},{0,2},{1,2}});
        tetromino.add(new int[][]{{0,0},{1,0},{0,1},{0,2}});
        tetromino.add(new int[][]{{0,0},{1,0},{1,1},{2,1}});
        tetromino.add(new int[][]{{0,1},{1,1},{1,0},{2,0}});
        tetromino.add(new int[][]{{1,0},{1,1},{0,1},{0,2}});
        tetromino.add(new int[][]{{0,0},{0,1},{1,1},{1,2}});
        tetromino.add(new int[][]{{0,1},{1,0},{1,1},{1,2}});
        tetromino.add(new int[][]{{0,0},{0,1},{0,2},{1,1}});
        tetromino.add(new int[][]{{0,0},{1,0},{1,1},{2,0}});
        tetromino.add(new int[][]{{0,1},{1,1},{1,0},{2,1}});

        int MAX = -2;
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                for(int[][] ttm : tetromino){
                    int val = allocCoor(ttm, i, j, N, M , map);
                    MAX = Math.max(MAX, val);
                }
            }
        }
        System.out.println(MAX);

    }
    public static void printTetro(int[][] ttm){
        for(int i=0; i<4; i++){
            System.out.print("{"+ttm[i][0] + "," + ttm[i][1] +"}  ");
        }
        System.out.println();
    }
    public static int allocCoor(int[][] tetro, int y, int x, int N, int M, int[][] map){
        int sum = 0;
        for(int i=0; i<4; i++){
            int tempY = tetro[i][0] + y;
            int tempX = tetro[i][1] + x;
            if(tempY < 0 || tempX < 0 || tempY >= N || tempX >= M){ //장외 탈출 체크하며 값 추가
                return -1;
            }
            sum += map[tempY][tempX];
        }

        return sum;
    }

    public static int[][] rotateTetro(int[][] tetro){
        int[][] rotateT = new int[4][2];
        for(int i=0; i<4; i++){
            rotateT[i][0] = -tetro[i][1];
            rotateT[i][1] = tetro[i][0];
        }
        return rotateT;
    }

    /**
     * 테트리스는 ㄱㄴ 뒤집은 대칭까지 모양 만들어놓고 
     * 테트리스 모양을 기준점 좌표로 만들기, 90도 이동하기, 
     * [i][j] 순회하며 모든 케이스 체크?
     * 500 500 7 4 ? N , M, 7종  테트리스, 4방향,
     * = 7_000_000 ,7백만이면 낫밷
     * 회전말고, 우,하 방향의 테트리스만 사용하면?
     * 흠 - | ㅁ 대칭 ..19개
     */

}

