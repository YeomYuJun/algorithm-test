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
            
        // ////////////////////////////////////// /////
        
        // str = "0 0 0 0 0 0 0 0 9\r\n" + //
        //                 "0 0 0 0 0 0 0 0 8\r\n" + //
        //                 "0 0 0 0 0 0 0 0 7\r\n" + //
        //                 "0 0 0 0 0 0 0 0 6\r\n" + //
        //                 "0 0 0 0 0 0 0 0 5\r\n" + //
        //                 "0 0 0 0 0 0 0 0 4\r\n" + //
        //                 "0 0 0 0 0 0 0 0 3\r\n" + //
        //                 "0 0 0 0 0 0 0 0 2\r\n" + //
        //                 "0 0 0 0 0 0 0 0 1";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int[][] sudoku = new int[9][9];

        List<int[]> zeroList = new ArrayList<>();

        for(int i=0; i<9; i++){
            String[] row = args[i].split(" ");
            for(int j=0; j<9; j++){ 
                int val = Integer.parseInt(row[j]);
                if(val == 0){
                    zeroList.add(new int[]{i,j});
                }
                sudoku[i][j] = val;
            }
        }

        DFS(sudoku, zeroList, 0);
        
    }

    public static boolean isSolved = false;
    public static void DFS(int[][] sudoku, List<int[]> zeroList, int idx){
        if(isSolved){
            return;
        }
        if(idx >= zeroList.size()){ 
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<9; i++){
                for(int j=0; j<9; j++){
                
                    sb.append(sudoku[i][j]);
                    if(j!=8){
                        sb.append(" ");
                    }

                }
                if(i!=8){
                    sb.append("\r\n");
                }
            }
            System.out.println(sb.toString());
            isSolved = true;
            return;
        }


        int[] curPos = zeroList.get(idx);
        int i = curPos[0];
        int j = curPos[1];
        
        List<Integer> numList = new ArrayList<>();
        
        for(int k=1; k<=9; k++){
            if(!rowJudge(sudoku, i, j, k)){
                continue;
            }
            if(!colJudge(sudoku, i, j, k)){
                continue;
            }
            if(!cubeJudge(sudoku, i, j, k)){
                continue;
            }
            numList.add(k);
        }
        if(numList.size() > 0){
            for(int ableNum : numList){
                sudoku[i][j] = ableNum;
                DFS(sudoku, zeroList, idx+1);
                if(isSolved){
                    return;
                }
                sudoku[i][j] = 0;
            }
        }
    }

    final static List<Integer> ableList = List.of(1,2,3,4,5,6,7,8,9);

    public static boolean rowJudge(int[][] sudoku, int curY, int curX, int num){
        for(int i=0; i<9; i++){
            if(sudoku[curY][i] == num) return false;
        }
        return true;
    }

    public static boolean colJudge(int[][] sudoku, int curY, int curX, int num){
        for(int i=0; i<9; i++){
            if(sudoku[i][curX] == num) return false;
        }
        return true;
    }

    public static boolean cubeJudge(int[][] sudoku, int curY, int curX, int num){
        // 0~2
        // 3~5
        // 6~8
        int sy = (curY/3)*3;
        int sx = (curX/3)*3;

        for(int i=sy; i<sy+3; i++){
            for(int j=sx; j<sx+3; j++){
                if(i==curY && j==curX) continue;
                if(sudoku[i][j] == num) return false;;
            }
        }
        return true;

    }
    /**
     * 해당 cell에 1,2,3,... 여러 경우가 가능하니까 그 경우의 DFS로 완탐해야할 거 같은데,
     * 
     */

}

