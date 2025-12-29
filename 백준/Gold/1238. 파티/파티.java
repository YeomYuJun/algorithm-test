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
        
        // str = "6 20 3\r\n" + //
        //                 "3 2 45\r\n" + //
        //                 "6 1 66\r\n" + //
        //                 "6 2 31\r\n" + //
        //                 "2 4 94\r\n" + //
        //                 "5 3 46\r\n" + //
        //                 "5 2 79\r\n" + //
        //                 "3 1 64\r\n" + //
        //                 "4 3 74\r\n" + //
        //                 "3 5 59\r\n" + //
        //                 "1 6 93\r\n" + //
        //                 "3 6 45\r\n" + //
        //                 "6 4 40\r\n" + //
        //                 "3 4 67\r\n" + //
        //                 "1 3 61\r\n" + //
        //                 "1 2 42\r\n" + //
        //                 "4 2 50\r\n" + //
        //                 "4 1 55\r\n" + //
        //                 "2 6 93\r\n" + //
        //                 "5 4 95\r\n" + //
        //                 "1 4 54";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); 
        int M = Integer.parseInt(args[0].split(" ")[1]); 
        int X = Integer.parseInt(args[0].split(" ")[2]); 

        int[][] village = new int[N+1][N+1];
        int a = Integer.MAX_VALUE;
        for(int i=0; i<=N; i++){
            Arrays.fill(village[i], a);
        }

        for(int i=1; i<=M; i++){ //초기화
            String[] row = args[i].split(" ");

            int from = Integer.parseInt(row[0]);   
            int to = Integer.parseInt(row[1]);   
            int time = Integer.parseInt(row[2]);   
            //from에서 to로 가는 단방향에 time만큼 소비됨.

            village[from][to] = time;
            //a to b , -> a to c to b 
        }
        for(int i=1; i<=N; i++){ //from 
            PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
                return o1[1] - o2[1];
            } );


            for(int d=1; d<village[i].length; d++){//i가 갈 수 있는 경로 
                if(village[i][d] != a){
                    int[] newint = new int[]{d,village[i][d]};
                    pq.add(newint);     //to(d), weight?
                }
            }
            
            //2,3,4, 에 4 2 7 이 가중치 

            while(!pq.isEmpty()){
                int[] curNode = pq.poll();
                int toNum = curNode[0];
                
                int weight = curNode[1];
                if(weight > village[i][toNum]) continue;

                for(int d=1; d<=N; d++){//i가 갈 수 있는 경로  => 인접노드의 갈 수 있는 경로 
                    int[] nextNode = village[toNum];
                    
                    if(nextNode[d] == a) continue; // 간선 없음

                    int nextWeight = nextNode[d];
                    int stackWeight = weight+nextWeight;
                    if(stackWeight < village[i][d]){
                        village[i][d] = stackWeight; // i to d 더 작으면 갱신
                        pq.add(new int[]{d, stackWeight});     //d, weight?
                    }

                }
            }
            // System.out.println(i+")의 이동 dist: " + Arrays.toString(village[i]));
        }
        
        int max = 0;
        for(int i=1; i<=N; i++){//양방향 이동 탐색
            if(i == X) continue; //같은 마을 = 0이며 값은 max라서 패스
            int sum = village[i][X] + village[X][i];
            max = Math.max(max, sum);
        }
        System.out.println(max);
    }
    /**
     * n명의 학생, 1~n 사이의 x번째 마을에 가려고함. 목적지, m개의 도로
     * n번째 학생은 n번째 마을에 살고있음.  a to b 도로는 최대1개
     * X로는 양쪽 방향으로 도로가 트여있음을 보장하지만, 다른 마을의 경우 아닐 수 있음.
     * 예를 들어  1 TO 2 TO 3 TO 4 TO 5 와 같이 거쳐갈 수 있는가?
     * 4 -> 2 -> 1 -> 3 -> 4 = 10 
     * "1 1 1"로 1명의 학생이 나올 수 없고, 시작 끝이 같을 수 없음 => 2명이어야함
     * N도, M도 2가 최저, 1 2 x1, 2 1 x2가 나와야함 
     */
}