import java.io.*;
import java.util.*;

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
        
        // str = "5 15 5\r\n" + //
        //                 "2 4 5 20 3\r\n" + //
        //                 "1 2 6\r\n" + //
        //                 "2 3 7\r\n" + //
        //                 "3 4 9\r\n" + //
        //                 "1 3 2\r\n" + //
        //                 "1 5 14";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0].split(" ")[0]); //지역의 개수
        int m = Integer.parseInt(args[0].split(" ")[1]); //수색 범위
        int r = Integer.parseInt(args[0].split(" ")[2]); //길의 개수
        
        String[] irow = args[1].split(" ");
        int[] items = new int[n+1];

        for(int i=1; i<=n; i++){
            items[i] = Integer.parseInt(irow[i-1]); //아이템 
        }

        int[][] roadMap = new int[n+1][n+1];
        int INF = 987_654_321;
        for(int i=0; i<=n; i++){ 
            for(int j=0; j<=n; j++){
                roadMap[i][j] = INF; //갈 수 없음 표시
            }
        }

        for(int i=2; i<r+2; i++){
            String[] row = args[i].split(" ");

            int a = Integer.parseInt(row[0]);
            int b = Integer.parseInt(row[1]);
            int l = Integer.parseInt(row[2]);
            // road.get(a).add(new int[]{b,l}); // a가 b까지 l 
            // road.get(b).add(new int[]{a,l}); // b가 a까지 l 
            roadMap[a][b] = l;
            roadMap[b][a] = l;
        }

        //최단경로를 갱신해서, a지점에서 갈 수 있는 b를 전부 구한 다음 sum 으로 max 찾기 (n회로);
        for(int k=1; k<=n; k++){ //중단점
            for(int i=1; i<=n; i++){ // i 지역에서 시작
                for(int j=1; j<=n; j++){
                    roadMap[i][j] = Math.min(roadMap[i][j], roadMap[i][k] + roadMap[k][j]);
                }
            }
        }
        
        int max = Integer.MIN_VALUE;
        for(int i=1; i<=n; i++){ //a지역에서 시작
            int stack = 0;
            for(int j=1; j<=n; j++){ // b지역으로 도달(중간 모르겠고 최단경로로 갈 수 있는지만 판별)
                if(i==j) continue;
                if(roadMap[i][j] <= m){
                    stack += items[j];
                }
            }
            stack += items[i]; //시작점 아이템 줍기
            max = Math.max(max,stack);
        }
        System.out.println(max);

    }
}