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
            int N = Integer.parseInt(args[0].split(" ")[0]);
            int K = Integer.parseInt(args[0].split(" ")[1]);
            
            boolean[][] reachable = new boolean[N][N];
            
            
            for(int i=0; i<K; i++){
                String[] row = args[i+1].split(" ");
                int pv = Integer.parseInt(row[0]) - 1;
                int nx = Integer.parseInt(row[1]) - 1;
                reachable[pv][nx] = true;
            }
            
            // 400^3=  64_000_000
            // int값으로 갱신 대신 그냥 boolean으로 도달 여부만 판단하면 될듯
            for(int k=0; k<N; k++){
                for(int i=0; i<N; i++){
                    for(int j=0; j<N; j++){
                        if(reachable[i][k] && reachable[k][j]){
                            reachable[i][j] = true;
                        }
                    }
                }
            }
            
            int S = Integer.parseInt(args[K+1]);
            StringBuilder sb = new StringBuilder();
            
            for(int i=K+2; i<K+2+S; i++){
                String[] row = args[i].split(" ");
                int pv = Integer.parseInt(row[0]) - 1;
                int nx = Integer.parseInt(row[1]) - 1;
                
                boolean isParent = reachable[pv][nx];
                boolean isChild = reachable[nx][pv];
                
                if(isParent){
                    sb.append(-1).append("\n");
                }else if(isChild){
                    sb.append(1).append("\n");
                }else{
                    sb.append(0).append("\n");
                }
            }
            System.out.println(sb.toString().trim());
        }
        /**
         *  p > n이 단방향 연결된 상태.
         *  a > b 에서   c>b or a>c가 들어오면 a>c>b가 되어야함.,
         *  parent찾아가는 구조...에서 
         *  시작 node에서 parent를 찾거나, 끝까지 갈 때 까지 재귀탐색해야함
         *  아 근데 a>b a>c a>d a>e a>f 같이 나오면? 인접리스트로 ..? 같은 레벨이잖아
         *  Set,List = 메모리 초과 ->흠.. 400^2 = 160,000, ^3 => 64,000,000
         *  플로이드 와샬 써야겠네;
         * 
         */
    }