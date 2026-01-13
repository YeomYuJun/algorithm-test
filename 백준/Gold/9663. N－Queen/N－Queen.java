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
        
        // str = "15";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 퀸 수

        // N x N 격자에서 퀸이 N개 놓이는 경우의 수 

        int[][] board = new int[N][N];

        backtrack(N, 0, board, 0);

        System.out.println(cnt);
    }

    public static int cnt = 0;

    public static void backtrack(int N, int depth, int[][] board, int currentRow){
        if(depth == N){
            cnt++;
            return;
        }
        for(int i=0; i<N; i++){
            if(isPossible(board, currentRow, i)){ //방문 가능하면 넣고 backtrack.
                board[currentRow][i] = 1; //기물 놓기
                // scanMap(board);
                backtrack(N, depth+1, board, currentRow+1);
                board[currentRow][i] = 0; //기물빼기
            }
        }
    }

    public static void scanMap(int[][] board){
        int N = board.length;
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                System.out.print(board[i][j] +" ");
            }
            System.out.println("");
        }
        System.out.println("===========================");
    }

    public static boolean isPossible(int[][] board, int y, int x){
        if(board[y][x] != 0){
            return false;
        }
        if(!xTest(board,y,x)){
            return false;
        }
        if(!crossTest(board,y,x)){
            return false;
        }

        return true;
    }
    public static boolean xTest(int[][] board, int y, int x){
        int l = board.length;

        for(int i=1 ; i<l ; i++){
            if(!(y+i >= l || x+i >= l)){ //우하단
                if(board[y+i][x+i] != 0){
                    return false;
                }
            }

            if(!(y-i < 0 || x+i >= l)){ // 우상단
                if(board[y-i][x+i] != 0){
                    return false;
                }
            }
            if(!(y-i < 0 || x-i < 0)){// 좌상단
                if(board[y-i][x-i] != 0){
                    return false;
                }
            }
            if(!(y+i >= l || x-i < 0)){ // 좌하단
                if(board[y+i][x-i] != 0){
                    return false;
                }   
            }
        }
        return true;
    }
    public static boolean crossTest(int[][] board, int y, int x){
        for(int i=0; i<board.length; i++){
            if(board[y][i] != 0 || board[i][x] != 0){
                return false;
            }
        }
        return true;
    }

}