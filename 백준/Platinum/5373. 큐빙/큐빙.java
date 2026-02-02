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
        
        // str = "6\r\n" + //
        //                 "10\r\n" + //
        //                 "L+ U+ U- B+ F+ U- U+ L+ L- D+\r\n" + //
        //                 "10\r\n" + //
        //                 "B- U- L- L+ L- B+ L- R- U- F+\r\n" + //
        //                 "11\r\n" + //
        //                 "D+ F- D+ B+ R- L+ L+ U+ U+ B+ U-\r\n" + //
        //                 "11\r\n" + //
        //                 "D- U+ U+ R+ D- U+ B+ U- D+ F+ D-\r\n" + //
        //                 "11\r\n" + //
        //                 "D+ L- B- D- B- D+ U- B- L+ D- L-\r\n" + //
        //                 "11\r\n" + //
        //                 "B+ L- B+ R- F- U+ B+ U- B+ U+ U+";//

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    static class Cube {
        char[][] up;
        char[][] down;
        char[][] front;
        char[][] back;
        char[][] left;
        char[][] right;

        public Cube(){
            this.up = new char[3][3];
            this.down = new char[3][3];
            this.front = new char[3][3];
            this.back = new char[3][3];
            this.left = new char[3][3];
            this.right = new char[3][3];
        }
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);  // 1~1000


        // char[][][] cube = new char[5][5][5];
        //최대 상,좌,우..는 값이 달라야하센
        //ㅁㅁㅁㅁㅁ
        //ㅁㅁㅁㅁㅁ <
        //ㅁㅁㅁㅁㅁ < 
        //ㅁㅁㅁㅁㅁ < 가운데 3개만 사용?
        //ㅁㅁㅁㅁㅁ
        //0,4행,열은 각 다른 옆면이 사용할 row로치고
        // cube[0][1][1]  ~ cube[0][3][3] = 'W'

        Cube cube = new Cube();
        setCube(cube);
        // printCube(cube);
        StringBuilder sb = new StringBuilder(); 
        for(int i=0;i<N;i++){
            int L = Integer.parseInt(args[(i*2)+1]);
            String[] order = args[(i*2)+2].split(" ");
            setCube(cube);
            for(int j=0; j<L; j++){
                char direction = order[j].charAt(0);
                boolean isPlus = order[j].charAt(1) == '+';
                rotateCube(cube, direction, isPlus);
                // printCube(cube);
            }
            answerPrint(cube, sb);
        }
        System.out.println(sb.toString().trim());
    }
    public static void answerPrint(Cube cube, StringBuilder sb){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                sb.append(cube.up[i][j]);
            }
            sb.append("\n");
        }
    }
    public static void setCube(Cube cube){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                cube.up[i][j] = 'w'; 
                cube.down[i][j] = 'y'; 
                cube.front[i][j] = 'r'; 
                cube.back[i][j] = 'o'; 
                cube.left[i][j] = 'g'; 
                cube.right[i][j] = 'b'; 
            }
        }
    }
    public static void rotateCube(Cube cube, char d, boolean r){
        turnAroundCube(cube, d, r);
        for(int i=1; i<=3; i++){
            for(int j=1; j<=3; j++){
                if(d=='U'){ 
                    cube.up = turnCube(cube.up, r);
                }else if(d=='D'){
                    cube.down = turnCube(cube.down, r);
                }
                else if(d=='F'){
                    cube.front = turnCube(cube.front, r);
                }
                else if(d=='B'){
                    cube.back = turnCube(cube.back, r);
                }
                else if(d=='L'){
                    cube.left = turnCube(cube.left, r);
                }
                else if(d=='R'){
                    cube.right = turnCube(cube.right, r);
                } 
            }
        }
    }
    
    public static char[][] turnCube(char[][] cubeSurface, boolean R){
        //[y][x] => [1][2] => [2-x][y] = L
        //          [1][2] => [x][2-y] = R
        char[][] newSurface = new char[3][3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(R){ //시계
                    newSurface[i][j] = cubeSurface[2-j][i];
                }else{ //반시계
                    newSurface[i][j] = cubeSurface[j][2-i];
                }
            }
        }
        return newSurface;
    }
    public static void turnAroundCube(Cube cube, char d, boolean R){
        if(d=='U'){
            char[] temp = new char[3];
            temp = cube.left[0].clone();
            if(R){
                cube.left[0] = cube.front[0].clone();
                cube.front[0] = cube.right[0].clone();
                cube.right[0] = cube.back[0].clone();
                cube.back[0] = temp.clone();
            }else{
                cube.left[0] = cube.back[0].clone();
                cube.back[0] = cube.right[0].clone();
                cube.right[0] = cube.front[0].clone();
                cube.front[0] = temp.clone();
            }
        }else if(d=='D'){
            char[] temp = new char[3];
            temp = cube.left[2].clone();
            if(R){
                cube.left[2] = cube.back[2].clone();
                cube.back[2] = cube.right[2].clone();
                cube.right[2] = cube.front[2].clone();
                cube.front[2] = temp.clone();
            }else{
                cube.left[2] = cube.front[2].clone();
                cube.front[2] = cube.right[2].clone();
                cube.right[2] = cube.back[2].clone();
                cube.back[2] = temp.clone();
            }
        }else if(d=='L'){
            for(int i=0; i<3; i++){
                char temp = cube.up[i][0];
                if(R){
                    cube.up[i][0] = cube.back[2-i][2];
                    cube.back[2-i][2] = cube.down[i][0];
                    cube.down[i][0] = cube.front[i][0];
                    cube.front[i][0] = temp;
                }else{
                    cube.up[i][0] = cube.front[i][0];
                    cube.front[i][0] = cube.down[i][0];
                    cube.down[i][0] = cube.back[2-i][2];
                    cube.back[2-i][2] = temp;
                }
            }
        }else if(d=='R'){
            for(int i=0; i<3; i++){
                char temp = cube.up[i][2];
                if(R){
                    cube.up[i][2] = cube.front[i][2];
                    cube.front[i][2] = cube.down[i][2];
                    cube.down[i][2] = cube.back[2-i][0];
                    cube.back[2-i][0] = temp;
                }else{
                    cube.up[i][2] = cube.back[2-i][0];
                    cube.back[2-i][0] = cube.down[i][2];
                    cube.down[i][2] = cube.front[i][2];
                    cube.front[i][2] = temp;
                }
            }
        }else if(d=='B'){
            for(int i=0; i<3; i++){
                char temp = cube.up[0][2-i];
                if(R){
                    cube.up[0][2-i] = cube.right[2-i][2];
                    cube.right[2-i][2] = cube.down[2][i];
                    cube.down[2][i] = cube.left[i][0];
                    cube.left[i][0] = temp;
                }else{
                    cube.up[0][2-i] = cube.left[i][0];
                    cube.left[i][0] = cube.down[2][i];
                    cube.down[2][i] = cube.right[2-i][2];
                    cube.right[2-i][2] = temp;
                }
            }
        }else if(d=='F'){
            for(int i=0; i<3; i++){
                char temp = cube.up[2][2-i];
                if(R){
                    cube.up[2][2-i] = cube.left[i][2];
                    cube.left[i][2] = cube.down[0][i];
                    cube.down[0][i] = cube.right[2-i][0];
                    cube.right[2-i][0] = temp;
                }else{
                    cube.up[2][2-i] = cube.right[2-i][0];
                    cube.right[2-i][0] = cube.down[0][i];
                    cube.down[0][i] = cube.left[i][2];
                    cube.left[i][2] = temp;
                }
            }
        }
    }
    public static void printCube(Cube cube){
        // System.out.println("======================================================");
        for(int i=0; i<3; i++){
            System.out.print("       ");
            for(int j=0; j<3; j++){
                System.out.print(cube.back[i][j] + " ");   
            }
            System.out.println();
        }
        System.out.println("--------------------------------");
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(cube.left[i][j] + " ");
            }
            System.out.print('|');
            for(int j=0; j<3; j++){
                System.out.print(cube.up[i][j] + " ");
            }
            System.out.print('|');
            for(int j=0; j<3; j++){
                System.out.print(cube.right[i][j] + " ");
            }
            System.out.print('|');
            for(int j=0; j<3; j++){
                System.out.print(cube.down[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------------------");
        for(int i=0; i<3; i++){
            System.out.print("       ");
            for(int j=0; j<3; j++){
                System.out.print(cube.front[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * 단순 구현 문제인가?
     * [0][0][0] = 큐브 정면 바라볼 때 왼쪽 상단 안쪽
     * [y][x][z] => |y -x \z
     * UP: 흰색('W')b      cube[0][1][1] ~ cube[0][3][3]
     * DOWN: 노란색('Y')   cube[5][1][1] ~ cubue[5][3][3]
     * FRONT: 빨간색('R')  cube[1][1][5] ~ cubue[3][3][5] 
     * BACK: 오렌지색('O') cube[1][1][0] ~ cubue[3][3][0]
     * LEFT: 초록색('G')  cube[1][0][1] ~ cube[3][0][3]
     * RIGHT: 파란색('B') cube[1][5][1] ~ cube[3][5][3]
     * 
     * cube class로 하는 게 나을듯?
     * 
     */

}

 