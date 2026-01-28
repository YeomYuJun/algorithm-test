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
        
        // str = "2 2 0 0 16\r\n" + //
        //                 "0 2\r\n" + //
        //                 "3 4\r\n" + //
        //                 "4 4 4 4 1 1 1 1 3 3 3 3 2 2 2 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        String[] row = args[0].split(" ");

        int N = Integer.parseInt(row[0]); // 1~ 20
        int M = Integer.parseInt(row[1]); // ~20
        int X = Integer.parseInt(row[2]); //  0~N-1
        int Y = Integer.parseInt(row[3]); // 0~M-1
        int K = Integer.parseInt(row[4]); //1~1000

        int[][] map = new int[N][M];

        for(int i=0; i<N; i++){
            String[] mapRow = args[i+1].split(" ");
            //북쪽부터 남쪽. 0~N-1 까지 
            for(int j=0; j<M; j++){
                //서쪽~동쪽 0~M-1
                map[i][j] = Integer.parseInt(mapRow[j]);
            }
        }
        Dice dice = new Dice();
        
        String[] commands = args[N+1].split(" ");
        int[] dicePos = {X,Y};//y,x //x가 세로, y가 가로?

        for(int i=0; i<commands.length; i++){
            int d = Integer.parseInt(commands[i]);
            
            turnDice(dice, d, map, dicePos);
        }

    }
    static int[] dx = {0,1,-1,0,0};
    static int[] dy = {0,0,0,-1,1};

    public static void updatePos(int[] pos, int d){
        pos[0] += dy[d];
        pos[1] += dx[d];
    }
    
    static class Dice{
        int under;
        int right;
        int left;
        int front;
        int back;
        int top;
        
        public Dice(){
            this.under = 0;
            this.right = 0;
            this.left = 0;
            this.front = 0;
            this.back = 0;
            this.top = 0;
        }
    }
    public static void turnDice(Dice dice, int d, int[][] map, int[] dicePos){
        int N = map.length;
        int M = map[0].length;
        
        int prevY = dicePos[0]; 
        int prevX = dicePos[1];
        updatePos(dicePos, d);
        int posY = dicePos[0];
        int posX = dicePos[1];

        if(posX < 0 || posY < 0 || posX >= M || posY >= N){
            //장외 이동이라면 position 원복 후 종료
            dicePos[0] = prevY;
            dicePos[1] = prevX;
            return;
        }
        //d==1  동
        if(d==1){
            //top => right , right => under, under => left,  left => top 
            int copied = 0;
            if(map[posY][posX] == 0){
                 map[posY][posX] = dice.right;
                 copied = dice.right;
            }else{
                copied = map[posY][posX];
                map[posY][posX] = 0;
            }

            int temp = dice.top;
            dice.top = dice.left;
            dice.left = dice.under;
            dice.under = copied;
            dice.right = temp;
        }

        if(d==2){ //서
            //top => left , left => under, under => right,  right => top 
            int copied = 0;
            if(map[posY][posX] == 0){
                 map[posY][posX] = dice.left;
                 copied = dice.left;
            }else{
                copied = map[posY][posX];
                map[posY][posX] = 0;
            }

            int temp = dice.top;
            dice.top = dice.right;
            dice.right = dice.under;
            dice.under = copied;
            dice.left = temp;
        }

        if(d==3){ //북쪽으로
            //top => front , front => under, under => back,  back => top 
            int copied = 0;
            if(map[posY][posX] == 0){
                 map[posY][posX] = dice.front;
                 copied = dice.front;
            }else{
                copied = map[posY][posX];
                map[posY][posX] = 0;
            }

            int temp = dice.top;
            dice.top = dice.back;
            dice.back = dice.under;
            dice.under = copied;
            dice.front = temp;
        }

        if(d==4){ // 남쪽으로
            //top => back , back => under, under => front,  front => top 
            int copied = 0;
            if(map[posY][posX] == 0){
                 map[posY][posX] = dice.back;
                 copied = dice.back;
            }else{
                copied = map[posY][posX];
                map[posY][posX] = 0;
            }

            int temp = dice.top;
            dice.top = dice.front;
            dice.front = dice.under;
            dice.under = copied;
            dice.back = temp;
        }
        System.out.println(dice.top);
    }
    /**
     * 
     *    2
        4 1 3
          5
          6
     * 
     * 에서 1: under, 2: front, 3: right , 4: left, 5: back, 6: top
     * 육면체로 만들었다고 가정하고.
     * 
     * 면이 지도에 닿으면 0일 때 주사위 면이 지도로 복사,  (주사위면은 그대로인듯?)
     *          0이 아닐 때 주사위 면은 지도의값으로 바뀌며, 지도는 0이됨.
     * 아 N,M X,Y가 반대네 아
     */
}

