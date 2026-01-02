import java.io.*;
import java.util.*;

public class Main {
    private static int MIN = Integer.MAX_VALUE;
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
        
        // str = "2 1\r\n" + //
        //                 "1 1\r\n" + //
        //                 "2 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // 규모
        int M = Integer.parseInt(args[0].split(" ")[1]); // 치킨집 최대 갯수

        // int[][] map = new int[N][N]; //0 base로

    
        List<int[]> chicken = new ArrayList<>();
        List<int[]> home = new ArrayList<>();

        //초기화
        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            for(int j=0; j<N; j++){
                int val = Integer.parseInt(row[j]);
                if(val == 2){
                    chicken.add(new int[]{i-1,j,0});
                }
                if(val == 1){
                    home.add(new int[]{i-1,j});
                }
                // map[i-1][j] = val;
            }
        };

        backtrack(new ArrayList<>(), chicken, 0,0, M, home);        
        //cs개 중에서 M개 뽑기
        //    cs! / M! * (cs-M)!
        System.out.println(MIN);
    }

    public static void backtrack(List<int[]> current, List<int[]> chicken, int start, int depth, int M, List<int[]> home){
        if(depth == M){
            //calc
            int distSum = 0;
            // System.out.println("REACH");
            for(int[] customer : home){
                int mindist = 1_000_000;
                for(int[] a : current){
                    int dist = Math.abs(a[0]-customer[0]) + Math.abs(a[1] - customer[1]);
                    mindist = Math.min(dist, mindist);
                }
                distSum+=mindist;
            }
            MIN = Math.min(MIN, distSum);
            return;
        }
        
        for(int i=start; i<chicken.size(); i++){
            current.add(chicken.get(i));
            backtrack(current, chicken, i+1, depth+1, M, home);
            current.remove(current.size()-1);
        }

    }

}
 