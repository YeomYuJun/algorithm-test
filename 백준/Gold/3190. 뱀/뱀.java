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
        
        // str = "";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    static int snakeD = 1; //1동 2남 3서 4북
    static int[] dx = {1,0,-1,0};
    static int[] dy = {0,1,0,-1};
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 2~100
        int K = Integer.parseInt(args[1]); // 0~100 사과

        int[][] board = new int[N+2][N+2]; //
        Arrays.fill(board[0], 1);
        Arrays.fill(board[N+1], 1);
        for(int i=0;i<N+2; i++){
            board[i][0] = 1;
            board[i][N+1] = 1;
        }

        for(int i=0; i<K; i++){
            int appleY = Integer.parseInt(args[2+i].split(" ")[0]);
            int appleX = Integer.parseInt(args[2+i].split(" ")[1]);
            //매 이동마다 체크해야하네
            board[appleY][appleX] = 2; //사과 표시
        }

        // System.out.println("Board: ===================");
        // for(int i=0; i<N+2; i++){
        //     System.out.println(Arrays.toString(board[i]));
        // }

        int L = Integer.parseInt(args[2+K]);
        Map<Integer, String> commandMap = new HashMap<>();
        for(int i=0; i<L; i++){
            int xT = Integer.parseInt(args[3+K+i].split(" ")[0]);
            String direction = args[3+K+i].split(" ")[1];
            commandMap.put(xT, direction);
        }

        Deque<int[]> snake = new ArrayDeque<>();

        //좌표y, 좌표x, time, direction ?
        snake.add(new int[]{1, 1}); // y, x만 저장
        
        int t = 0;
        int direction = 0; // 0:동, 1:남, 2:서, 3:북
        
        while(true){
            t++;
            
            //머리를 다음 칸에 위치
            int[] head = snake.peekFirst();
            int newY = head[0] + dy[direction];
            int newX = head[1] + dx[direction];
            
            // 2. 벽이나 자기 몸과 부딪히면 게임 종료
            if(board[newY][newX] == 1){
                break;
            }
            
            // 뱀 몸통과 충돌 체크
            boolean collision = false;
            for(int[] body : snake){
                if(body[0] == newY && body[1] == newX){
                    collision = true;
                    break;
                }
            }
            if(collision){
                break;
            }
            
            // 3. 머리를 새 위치에 추가
            snake.addFirst(new int[]{newY, newX});
            
            // 4. 사과 처리
            if(board[newY][newX] == 2){
                board[newY][newX] = 0; // 사과 제거, 꼬리는 그대로
            } else {
                snake.pollLast(); // 꼬리 제거
            }
            
            // 5. 방향 전환 (X초가 끝난 뒤)
            if(commandMap.containsKey(t)){
                String cmd = commandMap.get(t);
                if(cmd.equals("L")){
                    direction = (direction + 3) % 4; // 좌회전
                } else {
                    direction = (direction + 1) % 4; // 우회전
                }
            }
        }
        
        System.out.println(t);
    }
    public static void printSnake(Deque<int[]> snake, int[][] board){
        int[][] newBoard = new int[board.length][board.length];

        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                newBoard[i][j] = board[i][j];
            }
        }
        for(int[] s : snake){
            newBoard[s[0]][s[1]] = 8;
        }

        for(int i=0; i<board.length; i++){
            System.out.println(Arrays.toString(newBoard[i]));
        }
    }


    /**
     * 뱀 시작 위치 = (0,0); ? 0-based?
     * 1. 머리를 진행방향으로 1칸 이동 
     * 2.1. 사과가 있다면 먹음 - 종료(1칸 늘어나는 것을 이동 대신하여 제자리로 볼 수 있음)
     * 2.2. 사과가 없다면 머리 다음 ~ 꼬리까지 1칸씩 이동
     * 3. 벽이나 자기자신의 몸과 부딪히면 게임이 끝난다. => game clear는 없고 game over만 있는 듯
     * 
     * 
     * L = 좌측 90도 회전
     * D = 우측 90도 회전, 
     * 3 D = 3초 뒤 우측으로 회전(아마 머리만 방향을 틀어놓고, 4초부터 해당방향으로 이동인듯)
     * 
     * 뱀을 Queue라고 만들어놓으면?
     * 
     */
}

