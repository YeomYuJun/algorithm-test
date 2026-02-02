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
        
        // str = "5 2 10\r\n" + //
        //                 "2 3 2 3 2\r\n" + //
        //                 "2 3 2 3 2\r\n" + //
        //                 "2 3 2 3 2\r\n" + //
        //                 "2 3 2 3 2\r\n" + //
        //                 "2 3 2 3 2\r\n" + //
        //                 "2 1 3\r\n" + //
        //                 "3 2 3";//

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);
        int K = Integer.parseInt(args[0].split(" ")[2]);

        int[][] S2D2 = new int[N][N];

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<N; j++){
                int val = Integer.parseInt(row[j]);
                S2D2[i][j] = val;
            }
        }

        
        int[][] bryo = new int[N][N];
        for(int i=0; i<N ;i++){
            Arrays.fill(bryo[i], 5); // 보유 양분
        }
        // int INF = 9999; //없는 것
        // int[][][] trees = new int[N][N][1001]; //1000년, 중복 가능? 흠...
        Map<Integer,Map<Integer, List<Integer>>> map = new HashMap<>();
        //get(y).get(x) = List<Integer>

        for(int i=0; i<N; i++){
            map.put(i, new HashMap<Integer, List<Integer>>());
            Map<Integer, List<Integer>> xMap = map.get(i);
            for(int j=0; j<N; j++){
                xMap.put(j, new ArrayList<>());
                // Arrays.fill(trees[i][j], INF);
            }
        }
        for(int i=N+1; i<=N+M; i++){
            String[] row = args[i].split(" ");
            
            int y = Integer.parseInt(row[0]); //(x,y,z) 에서 [x][y] ? 하
            int x = Integer.parseInt(row[1]); 
            int z = Integer.parseInt(row[2]); //나이
            
            // int[] t = trees[y][x];
            map.get(y-1).get(x-1).add(z);   
        }


        for(int year=0; year<K; year++){
            //봄
            spring(map, bryo, N);
            //여름
            summer(map, bryo, N);
            //가을
            autumn(map, bryo, N);
            //겨울 
            winter(bryo, S2D2, N);
        }
        long totalCnt = 0;
        for(int i=0; i<N; i++){
            Map<Integer,List<Integer>> treeRow = map.get(i);
            for(int j=0; j<N; j++){
                List<Integer> tree = treeRow.get(j);
                // System.out.println("i: " + i + ", j: " + j + "의 나무들: " + tree.toString());
                long cnt = tree.stream().filter(value -> value > 0).count();
                totalCnt+=cnt;
            }
        }
        // System.out.println("비로맵: ==> ");
        // for(int i=0; i<N; i++){
        //     for(int j=0; j<N; j++){
        //         System.out.println(Arrays.toString(bryo[i]));
        //     }
        // }
        System.out.println(totalCnt);
    }
    public static void spring(Map<Integer, Map<Integer, List<Integer>>> map, int[][] bryo, int N){
        //양분 흡수 > 작은 나이부터 흡수
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                // int[] tree = trees[i][j];
                List<Integer> tree = map.get(i).get(j);
                if(tree == null || tree.size() == 0 ) continue; //없음
                int remainBryo = bryo[i][j]; 
                
                Collections.sort(tree); //매번 정렬할거면 PQ로 해야하는 거 아닌가

                for(int t=0; t<tree.size(); t++){
                    int val = tree.get(t);
                    if(remainBryo == 0 ||remainBryo < tree.get(t)) { //전부 사망시켜야함.
                        tree.set(t, -val); 
                        //남는 비료 5, 나무 10이면, 비료 온존시키기
                    }else{
                        remainBryo -= val; //양분 뻇고
                        tree.set(t, val+1); //나이 먹고
                    }
                }
                bryo[i][j] = remainBryo; 
            }
        }
        //나이 증가
        //죽음 => 음수로 변환? 
    }
    

    public static void summer(Map<Integer, Map<Integer, List<Integer>>> map, int[][] bryo, int N){
        // 나이/2 -> 양분으로
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                List<Integer> tree = map.get(i).get(j);
                if(tree == null || tree.size() == 0 ) continue; //없음

                //음수 나무들 솎아내기

                int sum = tree.stream()
                .filter(value -> value < 0)
                .mapToInt(value -> Math.abs(value)/2)
                .sum();

                tree.removeIf(value -> value< 0);
                bryo[i][j] += sum;
                //아 전체 합이 아니라 abs의 /2 의 몪을 리턴해야 되겠네
            }
        }
        
    }
    static int[] dx = {-1,-1,-1,0,0,1,1,1};
    static int[] dy = {-1,0,1,-1,1,-1,0,1};
    public static void autumn(Map<Integer, Map<Integer, List<Integer>>> map, int[][] bryo, int N){
        //5의 배수면 번식
        int[][] plusMap = new int[N][N];
        for(int i=0; i<N; i++){
            Map<Integer,List<Integer>> trees = map.get(i);
            for(int j=0; j<N; j++){
                List<Integer> tree = trees.get(j);
                if(tree == null || tree.size() == 0 ) continue; //없음
                long cnt = tree.stream().filter(val -> val% 5 == 0).count();
                if(cnt > 0){
                    for(int d=0; d<8; d++){
                        int ny = i+dy[d];
                        int nx = j+dx[d];
                        if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                        plusMap[ny][nx] += cnt;
                    }
                }
            }
        }
        for(int i=0; i<N; i++){
            Map<Integer,List<Integer>> trees = map.get(i);
            for(int j=0; j<N; j++){
                if(plusMap[i][j] == 0 ) continue; //없으면 패스
                List<Integer> tree = trees.get(j);
                for(int k=0; k<plusMap[i][j]; k++){
                    tree.add(1);//신규 나무 추가.
                }
            }
        }

    }
    public static void winter(int[][] bryo, int[][] S2D2, int N){
        //S2D2가 양분 추가
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                bryo[i][j]+=S2D2[i][j];
            }
        }
    }
    /**
     * [y][x]에 깔려있는 나무들..[나이1, 나이2, 나이3 ...]
     */
}

 