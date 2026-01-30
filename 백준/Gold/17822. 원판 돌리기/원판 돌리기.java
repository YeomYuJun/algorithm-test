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
        
        // str = "";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);  // 2~50
        int M = Integer.parseInt(args[0].split(" ")[1]);  // 2~50
        int T = Integer.parseInt(args[0].split(" ")[2]);  // 1~50

        int[][] map = new int[N][M];

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(row[j]);
            }
        }   

        // printCircle(map);
        for(int i=N+1; i<N+1+T; i++){
            String[] row = args[i].split(" ");

            int x = Integer.parseInt(row[0]);
            int d = Integer.parseInt(row[1]);
            int k = Integer.parseInt(row[2]);

            int xp = x;
            while(xp <= N){
                rowSpin(map, xp-1, d, k);
                // printCircle(map);
                xp+=x;
            }
            int cnt = checkNearValue(map); //갱신
            if(cnt == 0){ // 찾은 게 없음. 
                updateMap(map);
                // printCircle(map);
            }
        }
        int sum = 0;
        for(int i=0; i<N; i++){
            sum += Arrays.stream(map[i]).sum();
        }
        System.out.println(sum);

    }
    public static void updateMap(int[][] map){
        int sum = 0;
        int cnt = 0;
        int N = map.length;
        for(int i=0; i<N; i++){
            sum += Arrays.stream(map[i]).sum();
            cnt += Arrays.stream(map[i]).filter(v -> {
                if(v>0){
                    return true;
                }else{
                    return false;
                }
            }).count();
        }
        double avg = (double)sum/(double)cnt;
        for(int i=0; i<N; i++){
            map[i] = Arrays.stream(map[i]).map(v -> {
                //근데 정수로 완전히 일치하면?흠..
                //뺄셈? 아 그대로인가? 
                if(v == 0 || (double)v == avg){
                    return v;
                }else if((double)v>avg){
                    return v-=1;
                }else{
                    return v+=1;
                }
            }).toArray();
        }

    }

    public static void printCircle(int[][] map){
        int N = map.length;
        int M = map[0].length;
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                System.out.print(map[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println("==========================");
    }

    public static int checkNearValue(int[][] map){

        int N = map.length;
        int M = map[0].length;
        List<int[]> toRemove = new ArrayList<>();

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                //3개가 인접해있으면? 흠... 쌍으로 되는 건가? 아닌가?
                //bfs로 주변 탐색해야할 수도    
                //역시 기존 거를 지우면서 나머지를 지우지 못하는 케이스 발생
                
                int cur = map[i][j];
                if(cur == 0) continue;
                // 4방향 체크 (원형 처리 포함)
                if (hasAdjacentSame(map, i, j, N, M, cur)) {
                    toRemove.add(new int[]{i, j});
                }
            }
        }
        // 일괄 제거
        for (int[] pos : toRemove) {
            map[pos[0]][pos[1]] = 0;
        }
        
        return toRemove.size();

    }
    public static boolean hasAdjacentSame(int[][] map, int i, int j, int N, int M, int cur) {
        return (i > 0 && map[i-1][j] == cur) ||
            (i < N-1 && map[i+1][j] == cur) ||
            (map[i][(j-1+M)%M] == cur) ||  // 왼쪽 wrap
            (map[i][(j+1)%M] == cur);       // 오른쪽 wrap
    }
    //  [1,2,3,4] => [4,1,2,3] or [2,3,4,1]
    public static void rowSpin(int[][] map, int row, int d, int k){
        int M = map[0].length;
        if(d==1){
            int[] sliced = Arrays.copyOfRange(map[row], 0,  k);
            for(int i=0; i<M-k; i++){
                map[row][i] = map[row][i+k];
            }                
            for(int j=M-k; j<M; j++){
                map[row][j] = sliced[j-M+k];
            }
        }
        if(d==0){
            int[] sliced = Arrays.copyOfRange(map[row], M-k,  M);
            for(int i=M-1; i>=k; i--){
                map[row][i] = map[row][i-k];
            }                
            for(int j=0; j<k; j++){
                map[row][j] = sliced[j];
            }
        }
    }



}

