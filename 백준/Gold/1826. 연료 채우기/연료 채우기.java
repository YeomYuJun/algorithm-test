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
            
        // ////////////////////////////////////// /////
        
        // str = "8\r\n" + //
        //                 "1 5\r\n" + //
        //                 "2 1\r\n" + //
        //                 "3 1\r\n" + //
        //                 "9 1\r\n" + //
        //                 "10 1\r\n" + //
        //                 "11 2\r\n" + //
        //                 "13 5\r\n" + //
        //                 "18 1\r\n" + //
        //                 "19 3";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); 
        int L = Integer.parseInt(args[N+1].split(" ")[0]); //필요 거리
        int P = Integer.parseInt(args[N+1].split(" ")[1]); //기존 보유량
        //L,P,a <=100만, b<=100
        //즉 직선좌표 100만 초히대 
        if(P>=L){
            System.out.println(0);
            return;
        }
        
        PriorityQueue<Integer> highVal = new PriorityQueue<>(Collections.reverseOrder());
        int truck = P;
        int count = 0;
        List<int[]> gas = new ArrayList<>();
        for(int i=0; i<N; i++){ //1만개
            String[] row = args[i+1].split(" ");
            int a = Integer.parseInt(row[0]);
            int b = Integer.parseInt(row[1]);
            gas.add(new int[]{a,b});
        }
        Collections.sort(gas, (o1, o2) -> {
            return o1[0]-o2[0];
        });

        for(int[] g : gas){
            int a = g[0];
            int b = g[1];

            if(a <= truck){
                highVal.add(b);
            }else{ //a가 커지면 갈 수 없는 주유소
                if(highVal.isEmpty()){
                    System.out.println(-1);
                    return;
                }else{
                    while(!highVal.isEmpty() && a>truck){ //다음 주유소 방문가능할 때 까지
                        int mostVal = highVal.poll();
                        truck+=mostVal;
                        count++;
                    }
                    if(a<=truck){ //방문 가능하면 queue에 넣고 다음 주유소 방문하면서 판단하도록
                        highVal.add(b);
                    }
                }
                
            }
            if(truck >= L){
                System.out.println(count);
                return;
            }
        }
        while(!highVal.isEmpty()){
            int mostVal = highVal.poll();
            truck+=mostVal;
            count++;
            if(truck>=L){
                System.out.println(count);
                return;
            }
            
        }
        System.out.println(-1);
        return;
    }
    /**
     * 현재 갈 수 있는 주유소 중에 주유를 하고 갈 수 있는 최대의 거리로 가면? greedy 아닌가  
     *    4  2           5     10                          goal
     *    |  |           |     |                            |
     *  4, 5 받고 15에서 10을 받으면 17을 받아서 25까지 도착 가능. or 2.
     * 그럼 역으로 앞에 4를 받아야만 가능한 경우는?
     * 
     *   4 4            1 1 1 1 1 1 1 1 10        goal
     *   | |            | | | | | | | | |           | 
     *   4 5            0 1 2 3 4 5 6 7 8           25
     *                =(10)      
     *  갈 수 있는 거리까지 담긴 pq에서 poll 한개. 현재 위치 ~ 보유량 까지 갈 수 있는 거리까지 다시 pq에 추가
     * 다시 poll
     *  그럼 4받고, 4받아서 최소 정거(poll)로 goal 까지 . pq가 비면 탈출실패 -1
     */
}

