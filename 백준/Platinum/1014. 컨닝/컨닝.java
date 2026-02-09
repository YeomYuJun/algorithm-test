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
            
            // str = "";// 

            ////////////////////////////////////////////
            
            String[] list = str.split("\r\n");  
            solution(list);
        }
        
        public static void solution(String[] args) throws IOException {
            int C = Integer.parseInt(args[0]); //

            int idx = 0;
            for(int c=0; c<C; c++){
                String[] nm = args[++idx].split(" ");
                
                int N = Integer.parseInt(nm[0]);
                int M = Integer.parseInt(nm[1]);
                int[][] map = new int[N][M];

                for(int n=0; n<N; n++){
                    String[] row = args[++idx].split("");
                    for(int m=0; m<M; m++){
                        if(".".equals(row[m])){
                            map[n][m] = 0;
                        }else{
                            map[n][m] = 1;//막힘
                        }
                    }
                }
                int MAX = bestSeat(map);
                System.out.println(MAX);
            }
        }
        

        public static void generateRowPatterns(int[] original, int[] cur, List<int[]> patterns, int col){
            int M = original.length;
            
            if(col >= M){
                patterns.add(cur.clone());
                return;
            }
            //현재 칸에 앉히지 않음
            generateRowPatterns(original, cur, patterns, col + 1);
            
            //앉힐 수 있으면 앉힘
            if(original[col] == 0 && isRowSeatable(cur, col)){
                cur[col] = 2;
                generateRowPatterns(original, cur, patterns, col + 1);
                cur[col] = 0;
            }
        }

        // 해당 row 가능 여부
        public static boolean isRowSeatable(int[] row, int col){
            // 왼쪽 체크
            if(col > 0 && row[col - 1] == 2) return false;
            // 오른쪽은 아직 배치 안 됨
            return true;
        }

        // 이전행 보고 가능 여부
        public static boolean isCompatible(int[] prevRow, int[] curRow){
            int M = prevRow.length;
            for(int j = 0; j < M; j++){
                if(curRow[j] == 2){
                    // 왼쪽 위 대각선
                    if(j > 0 && prevRow[j - 1] == 2) return false;
                    // 오른쪽 위 대각선
                    if(j < M - 1 && prevRow[j + 1] == 2) return false;
                }
            }
            return true;
        }

        //학생 수 계산
        public static int getSeatCnt(int[] pattern){
            int cnt = 0;
            for(int val : pattern){
                if(val == 2) cnt++;
            }
            return cnt;
        }

        public static int bestSeat(int[][] map){
            int N = map.length;
            int M = map[0].length;
            
            // 각 행별 가능한 패턴 생성
            List<List<int[]>> allPatterns = new ArrayList<>();
            for(int i = 0; i < N; i++){
                List<int[]> patterns = new ArrayList<>();
                int[] cur = new int[M];
                generateRowPatterns(map[i], cur, patterns, 0);
                allPatterns.add(patterns);
            }
            
            List<int[]> prevPatterns = allPatterns.get(0);
            int[] dp = new int[prevPatterns.size()];
            
            // 첫 행 초기화
            for(int p = 0; p < prevPatterns.size(); p++){
                dp[p] = getSeatCnt(prevPatterns.get(p));
            }
            
            // 2번째 행부터 DP
            for(int row = 1; row < N; row++){
                List<int[]> curPatterns = allPatterns.get(row);
                int[] newDp = new int[curPatterns.size()];
                
                for(int cur = 0; cur < curPatterns.size(); cur++){
                    for(int prev = 0; prev < prevPatterns.size(); prev++){
                        if(isCompatible(prevPatterns.get(prev), curPatterns.get(cur))){
                            newDp[cur] = Math.max(newDp[cur], 
                                                dp[prev] + getSeatCnt(curPatterns.get(cur)));
                        }
                    }
                }
                
                dp = newDp;
                prevPatterns = curPatterns;
            }
            
            // 최댓값 반환
            int max = 0;
            for(int val : dp){
                max = Math.max(max, val);
            }
            return max;
        }

        public static int getSeatCnt(int[][] map){
            int cnt = 0;
            for(int i=0; i<map.length; i++){
                cnt += Arrays.stream(map[i]).filter(v -> v==2).count();
            }
            return cnt;
        }

        //안되는 경우 [i-1][j-1], [i][j-1], [i-1][j+1] [i][j+1]
        public static boolean isSeatable(int[][] map, int y, int x){
            int N = map.length;
            int M = map[0].length;
            //장외
            if(y<0 || x<0 || y>=N || x>=M) return false;
            //ny,mx,px는 장외여도 됨. 장외면 무시.
            int ny = y-1;
            int mx = x-1;
            int px = x+1;
            //장내
            if(mx>=0){
                if(ny>=0){
                    if(map[ny][mx] == 2) return false; // 2는 사람있음(벽은 판별안함)
                }   
                if(map[y][mx] == 2) return false;
            }
            if(px<M){
                if(ny>=0){
                    if(map[ny][px] == 2) return false;
                }
                if(map[y][px] == 2) return false;
            }
            
            return true;
        }
        /**
         * nqueen처럼 전부 놓아보는 DFS? 완전탐색?
         * 2초 512MB면 될 거 같은데
         * 흠..최적 선택이 되나?
         * o.o.xxxo.o.o
         * o.x.o..o.o.o
         * => 5 5 = 10
         * o..oxxxo.o.o
         * o.xo.o.o.o.o
         * => 5 6 = 11
         *  
         * 다 테스트 해보는 게 맞나? 아니면 건너..니까 케이스가 매번 2개뿐인가?
         * 앉X앉 vs 앉XX앉? /  앉XXX앉? 은 그냥 손해
         */
    }