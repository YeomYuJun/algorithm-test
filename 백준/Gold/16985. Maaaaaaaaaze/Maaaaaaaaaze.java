import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static int min = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        /////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "0 0 0 1 0\r\n" + //
        //                 "0 0 0 0 0\r\n" + //
        //                 "1 0 1 1 1\r\n" + //
        //                 "0 0 0 1 0\r\n" + //
        //                 "0 0 1 0 0\r\n" + //
        //                 "0 1 0 0 0\r\n" + //
        //                 "1 1 0 0 0\r\n" + //
        //                 "1 0 0 1 0\r\n" + //
        //                 "0 1 1 1 0\r\n" + //
        //                 "0 1 0 1 0\r\n" + //
        //                 "0 0 1 0 0\r\n" + //
        //                 "1 0 0 0 0\r\n" + //
        //                 "0 1 0 0 0\r\n" + //
        //                 "0 0 1 0 0\r\n" + //
        //                 "1 1 1 0 0\r\n" + //
        //                 "1 0 0 0 1\r\n" + //
        //                 "1 0 0 0 0\r\n" + //
        //                 "0 0 1 0 1\r\n" + //
        //                 "0 1 1 0 0\r\n" + //
        //                 "0 1 0 0 0\r\n" + //
        //                 "0 0 0 1 0\r\n" + //
        //                 "1 0 0 0 0\r\n" + //
        //                 "0 0 1 0 0\r\n" + //
        //                 "0 1 0 0 1\r\n" + //
        //                 "0 1 0 0 0"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int[][][] cube = new int[5][5][5];

        //큐브제작
        for(int i=0; i<5; i++){ //5^3 연산
            int[][] pan = new int[5][5];
            for(int j=0; j<5; j++){
                String[] row = args[i*5+j].split(" ");
                int[] p = new int[5];
                for(int k=0; k<5; k++){
                    p[k] = Integer.parseInt(row[k]);
                }
                pan[j] = p;
            }
            cube[i] = pan;
        }
        //sysout
        /*
        for(int[][] pan : cube){
            for(int[] p : pan){
                // System.out.println(Arrays.toString(p));
            }
        }
        int[][] testPan = cube[0];
        for(int[][] rot : rotatePan(testPan)){
            for(int[] p : rot){
                System.out.println(Arrays.toString(p));
            }
            System.out.println("=======================");
        }
        */
        //1~5까지 판들을 돌리는 경우 1-1 ~ 1-4 || 2-1 ~ 2-4 || 5-1 ~ 5-4
        //11111 ~ 44444 = 4^5 = 1024개...
    
        List<List<int[][]>> nnList = new ArrayList<>();

        for(int i=0; i<5; i++){ //5^4 연산
            int[][] pan = cube[i];
            List<int[][]> nDegree = rotatePan(pan);
            nnList.add(nDegree);
        }
        
        //단순히는 (5*4)*(5*4)*(5*4)*(5*4)*(5*4) 이런느낌인가?
        //320만, 3초..흠.
        //1층 후보 20개, 2층 후보 16개 .. 5층 후보 4개? => 120만 좀 아슬아슬한데

        //첫 큐브 선택
        //12345 고른 상태에서 전부 90도를 뒤틀면 똑같은 거 잖아
        Set<List<Integer>> unique = getUniqueConfigurations();
        
        permu(new int[]{0,1,2,3,4}, new ArrayList<>(), new boolean[5], nnList, unique);

        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }
    
    //1~5중 5개 고르는 선택 순열 
    public static void permu(int[] arr, List<Integer> current, boolean[] used , List<List<int[][]>> nnList, Set<List<Integer>> unique){
            if (current.size() == arr.length) {    
            //120가지
            uniqueDegreeTest(current, nnList, unique);
            return;
        }
        
        for (int i = 0; i < arr.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(arr[i]);
                permu(arr, current, used, nnList, unique);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }

    //유니크한 조합으로 테스트 
    public static void uniqueDegreeTest(List<Integer> permuEntry, List<List<int[][]>> nnList, Set<List<Integer>> unique){
        int n0 = permuEntry.get(0);
        int n1 = permuEntry.get(1);
        int n2 = permuEntry.get(2);
        int n3 = permuEntry.get(3);
        int n4 = permuEntry.get(4);

        List<int[][]> f1List = nnList.get(n0); //해당 층의 회전 조합 목록
        List<int[][]> f2List = nnList.get(n1);
        List<int[][]> f3List = nnList.get(n2);
        List<int[][]> f4List = nnList.get(n3);
        List<int[][]> f5List = nnList.get(n4);

        //유니크한 경우 280
        for(List<Integer> dList : unique){
            int[][][] fcube = new int[5][5][5];
            fcube[0] = f1List.get(dList.get(0)); //해당 층의 회전 유니크 경우 뽑아서 테스트
            fcube[1] = f2List.get(dList.get(1));
            fcube[2] = f3List.get(dList.get(2));
            fcube[3] = f4List.get(dList.get(3));
            fcube[4] = f5List.get(dList.get(4));
            bfs(fcube);
        }
    }

    public static void bfs(int[][][] cube){
        //임의로 선택한 꼭짓점에 위치한 칸이고 출구는 입구와 면을 공유하지 않는 꼭짓점에 위치한 칸이다
        //아..이럴거면 걍 다 테스트할걸
        // 4가지 대각선 쌍 모두 시도
        int[][][] pairs = {
            {{0,0,0}, {4,4,4}},
            {{0,0,4}, {4,4,0}},
            {{0,4,0}, {4,0,4}},
            {{0,4,4}, {4,0,0}}
        };
        
        for(int[][] pair : pairs){
            int[] start = pair[0];
            int[] end = pair[1];
            
            if(cube[start[0]][start[1]][start[2]] == 0 || 
            cube[end[0]][end[1]][end[2]] == 0) continue;
            
            bfsHelper(cube, start, end);
        }
    }

    public static void bfsHelper(int[][][] cube, int[] start, int[] end){
        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[5][5][5];
        
        queue.offer(new int[]{start[0], start[1], start[2], 0});
        visited[start[0]][start[1]][start[2]] = true;
        
        int[] dz = {-1, 1, 0, 0, 0, 0};
        int[] dy = {0, 0, -1, 1, 0, 0};
        int[] dx = {0, 0, 0, 0, -1, 1};
        
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            int z = cur[0], y = cur[1], x = cur[2], dist = cur[3];
            
            if(z == end[0] && y == end[1] && x == end[2]){
                min = Math.min(min, dist);
                return;
            }
            
            for(int i = 0; i < 6; i++){
                int nz = z + dz[i];
                int ny = y + dy[i];
                int nx = x + dx[i];
                
                if(nz < 0 || nz >= 5 || ny < 0 || ny >= 5 || nx < 0 || nx >= 5) continue;
                
                if(!visited[nz][ny][nx] && cube[nz][ny][nx] == 1){
                    visited[nz][ny][nx] = true;
                    queue.offer(new int[]{nz, ny, nx, dist + 1});
                }
            }
        }
    }

    //유니크 경우 만들기
    public static Set<List<Integer>> getUniqueConfigurations() {
        Set<List<Integer>> unique = new HashSet<>();
        
        // 4^5 = 1024가지 모든 조합 생성
        generateAllCombinations(5, new ArrayList<>(), unique);
        
        return unique;
    }

    //모든 조합
    public static void generateAllCombinations(int remaining, 
                                               List<Integer> current, 
                                               Set<List<Integer>> unique) {
        if (remaining == 0) {
            // 정규화된 형태만 저장
            List<Integer> canonical = getCanonicalForm(current);
            unique.add(canonical);
            return;
        }
        
        for (int rotation = 0; rotation < 4; rotation++) {
            current.add(rotation);
            generateAllCombinations(remaining - 1, current, unique);
            current.remove(current.size() - 1);
        }
    }

    //유니크만 남기기
    public static List<Integer> getCanonicalForm(List<Integer> rotations) {
        List<Integer> min = new ArrayList<>(rotations);
        
        // 큐브 전체를 90도씩 회전
        for (int cubeRotation = 1; cubeRotation < 4; cubeRotation++) {
            int temp = cubeRotation;
            List<Integer> rotated = rotations.stream()
                    .map(r -> (r + temp) % 4)
                    .collect(Collectors.toList());
            
            if (compareLexicographically(rotated, min) < 0) {
                min = rotated;
            }
        }
        
        return min;
    }

    //사전 정렬
    public static int compareLexicographically(List<Integer> a, List<Integer> b) {
        for (int i = 0; i < a.size(); i++) {
            int cmp = Integer.compare(a.get(i), b.get(i));
            if (cmp != 0) return cmp;
        }
        return 0;
    }
    //큐브 판 회전
    public static List<int[][]> rotatePan(int[][] pan){
        List<int[][]> rotate4 = new ArrayList<>();
        int[][] temp = pan;
        
        for(int i=0; i<4; i++){
            int[][] newPan = new int[5][5];
            for(int x=0; x<5; x++){
                for(int y=0; y<5; y++){
                    newPan[x][y] = temp[y][4-x];
                }
            }
            rotate4.add(newPan);
            temp = newPan;
        }
        
        return rotate4;
    }
    /**
     * 90도 회전
     * 0 1 2  0,0  0,1 0,2
     * 3 4 5  1,0  1,1 1,2
     * 6 7 8  2,0  2,1 2,2
     * 
     * 6 3 0  2,0 -> 0,0 | 1,0 -> 0,1 | 0,0 -> 0,2
     * 7 4 1  2,1 -> 1,0 | 1,1 -> 1,1 | 0,1 -> 1,2  
     * 8 5 2  2,2 -> 2,0 | 1,2 -> 2,1
     * ..            (y,2-2) | (y,2-1) | y
     * (x,y) 에서 (y, len-x) 
     */

}