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
        
        // str = "3\r\n" + //
        //                 "2 2 2\r\n" + //
        //                 "0 2 0\r\n" + //
        //                 "0 2 0";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 1~20

        int[][] board = new int[N][N];

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<N; j++){
                int val = Integer.parseInt(row[j]);
                board[i][j] = val;
            }
        }
        //2^5*1024 = 32768

        int[][] dp = new int[5][4];
        //[i][j] = i번째에 j(1~4)를 했을 때 가질 수 있는 최대 숫자
        //그 당시의 board 모양새는 => 5*4*20*20=8000

        int[][][][] boardTree = new int[6][1025][N][N];
        int[][] b = new int[N][N];
        for(int k=0; k<N; k++) {
            b[k] = Arrays.copyOf(board[k], N);
        }
        boardTree[0][0] = b;


        int MAX = Integer.MIN_VALUE;
        for(int i=1; i<=5; i++){
            for(int d=1; d<=4; d++){

                int val = 0;
                int variation = (int)Math.pow(4,i-1);

                for(int pd=0; pd<variation; pd++){  // 0부터 variation-1까지
                    int[][] prev = boardTree[i-1][pd];
                    
                    int[][] copy = new int[N][N];
                    for(int k=0; k<N; k++) {
                        copy[k] = Arrays.copyOf(prev[k], N);
                    }

                    int localVal = tilt(d, copy);
                    if(pd == 0 || localVal >= val){  // 첫번째거나 더 큰 경우
                        val = localVal;
                        boardTree[i][pd*4 + d - 1] = copy;  // 0-based 인덱스
                    }
                }
                MAX = Math.max(MAX, val);
                // System.out.println();
                // printBoard(i, d, boardTree[i][d]);
            }
            // System.out.println(MAX);
        }
        System.out.println(MAX);
    }

    public static void printBoard(int n, int d, int[][] board){
        System.out.printf("==%d번째=====%d각도==================================\r\n", n,d);
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("=========================================");
    }

    public static int tilt(int degree, int[][] board){ // 1동 2남 3서 4북
        int N = board.length;
        int curMax = 0;
        if(degree==1){ //동쪽으로
            for(int i=0; i<N; i++){
                for(int j=N-1; j>0; j--){
                    int cur = board[i][j];
                    if(cur==0) continue;
                    int k=j; //왼쪽 숫자 (방향> 의 반대 )

                    while(k-->0){
                        if(board[i][k] == 0){
                            continue;
                        }else if(board[i][k] == cur){
                            board[i][j]*=2;
                            board[i][k]=0;
                            j=k; //다음부터 찾도록 인덱스 변경
                            break;
                        }else{
                            break;
                        }
                    }
                }
                arraySortRight(board, i);
            }
        }

        if(degree==2){ //남쪽
            for(int i=0; i<N; i++){
                for(int j=N-1; j>0; j--){
                    int cur = board[j][i];
                    if(cur==0) continue;
                    int k=j; //위 숫자

                    while(k-->0){
                        if(board[k][i] == 0){
                            continue;
                        }else if(board[k][i] == cur){
                            board[j][i]*=2;
                            board[k][i]=0;
                            j=k; //다음부터 찾도록 인덱스 변경
                            break;
                        }else{
                            break;
                        }
                    }
                }
                arraySortDown(board, i);
            }
        }
        
        if(degree==3){ // 서쪽
            for(int i=0; i<N; i++){
                for(int j=0; j<N-1; j++){
                    int cur = board[i][j];
                    if(cur==0) continue;
                    int k=j; //왼쪽 숫자 (방향> 의 반대 )

                    while(k++<N-1){
                        if(board[i][k] == 0){
                            continue;
                        }else if(board[i][k] == cur){
                            board[i][j]*=2;
                            board[i][k]=0;
                            j=k; //다음부터 찾도록 인덱스 변경
                            break;
                        }else{
                            break;
                        }
                    }
                }
                arraySortLeft(board, i);
            }
        }

        if(degree==4){ // 북쪽
            for(int i=0; i<N; i++){
                for(int j=0; j<N-1; j++){
                    int cur = board[j][i];
                    if(cur==0) continue;
                    int k=j; //왼쪽 숫자 (방향> 의 반대 )

                    while(k++<N-1){
                        if(board[k][i] == 0){
                            continue;
                        }else if(board[k][i] == cur){
                            board[j][i]*=2;
                            board[k][i]=0;
                            j=k; //다음부터 찾도록 인덱스 변경
                            break;
                        }else{
                            break;
                        }
                    }
                }
                arraySortUp(board, i);
            }
        }
        return getMax(board);

    }
    public static int getMax(int[][] board){
        int max = -1;
        for(int i=0; i<board.length; i++){
            max = Math.max(max, Arrays.stream(board[i]).max().getAsInt());
        }
        return max;
    }

    //동쪽으로 정렬
    public static void arraySortRight(int[][] board, int y){
        int N = board.length;
        int[] newLine = new int[N];
        int t=N-1;
        for(int i=N-1; i>=0; i--){
            if(board[y][i] != 0){
                newLine[t] = board[y][i];
                t--;
            }
        }
        board[y] = newLine;
    }
    //서쪽 정렬
    public static void arraySortLeft(int[][] board, int y){ 
        int N = board.length;
        int[] newLine = new int[N];
        int t=0;
        for(int i=0; i<N; i++){
            if(board[y][i] != 0){
                newLine[t] = board[y][i];
                t++;
            }
        }
        board[y] = newLine;
    }

    // 아래 정렬
    public static void arraySortDown(int[][] board, int x) {
        int N = board.length;
        int[] newLine = new int[N];
        int idx = N - 1;
        
        for (int i = N - 1; i >= 0; i--) {
            if (board[i][x] != 0) {
                newLine[idx--] = board[i][x];
            }
        }
        
        for (int i = 0; i < N; i++) {
            board[i][x] = newLine[i];
        }
    }

    // 상단 정렬
    public static void arraySortUp(int[][] board, int x) {
        int N = board.length;
        int[] newLine = new int[N];
        int idx = 0;
        
        for (int i = 0; i < N; i++) {
            if (board[i][x] != 0) {
                newLine[idx++] = board[i][x];
            }
        }
        
        for (int i = 0; i < N; i++) {
            board[i][x] = newLine[i];
        }
    }


    /**
     * 최대 5번 이동
     * DP 인가?
     * 방향에 따른 이동 최대 합을 매번 구해놔야할 거 같기도 하고 그럼 완탐?
     */
}

