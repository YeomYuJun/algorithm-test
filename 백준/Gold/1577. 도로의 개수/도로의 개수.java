import java.io.*;
import java.util.*;
import java.util.Map.Entry;

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
        
        
        // str = "2 2\r\n" + //
        //                 "3\r\n" + //
        //                 "0 0 1 0\r\n" + //
        //                 "1 2 2 2\r\n" + //
        //                 "1 1 2 1"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N  = Integer.parseInt(args[0].split(" ")[0]);
        int M  = Integer.parseInt(args[0].split(" ")[1]);  //1~100
        int K  = Integer.parseInt(args[1]); // 0~50


        long[][] dp = new long[N+1][M+1]; // >N ^M

        Map<Point, List<Point>> map = new HashMap<>();
        for(int i=0; i<K; i++){
            String[] row = args[i+2].split(" ");

            int a = Integer.parseInt(row[0]);
            int b = Integer.parseInt(row[1]);
            int c = Integer.parseInt(row[2]);
            int d = Integer.parseInt(row[3]);

            int mx = Math.max(a, c);
            int my = Math.max(b, d);
            int minX = Math.min(a, c);
            int minY = Math.min(b, d);
            map.computeIfAbsent(new Point(mx, my), k -> new ArrayList<>())
                .add(new Point(minX, minY));
            //(a,b) ~ (c,d);
            //c,d 로 
        }



        dp[0][0] = 1;
        for(int i=0; i<=N;i++){
            for(int j=0; j<=M; j++){
                List<Point> results = map.getOrDefault(new Point(i, j), List.of());
                
                boolean hasLeft = false;
                boolean hasUnder = false;
                
                
                for(int k=0; k< results.size(); k++){
                    Point from = results.get(k);
                    if(from.x == i && from.y == j-1){
                        hasLeft = true;
                        continue;
                    }else if(from.x == i-1 && from.y == j){
                        hasUnder = true;
                        continue;
                    }
                }
                if(i>0 && !hasUnder){
                    dp[i][j] += dp[i-1][j];
                }
                if(j>0 && !hasLeft){
                    dp[i][j] += dp[i][j-1];   
                }

                //if(results.size() == 0){
                //    
                //} else if(results.size() == 2){ 중복 입력 가능..
                // 
                //     dp[i][j] = 0;
                // }else {
                //     Point AB = results.get(0);
                //     if(AB.x == i-1 && j>0){ // i-1, j라는 뜻
                //         dp[i][j] = dp[i][j-1];
                //     }else if(AB.y == j-1 && i>0){
                //         dp[i][j] = dp[i-1][j];
                //     }
                // }
            }
            // System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println(dp[N][M]);
    }

    static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}