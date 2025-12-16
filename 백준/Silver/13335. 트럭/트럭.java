import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    
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

        // str = "1 1 10\r\n" + //
        //                 "10"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0].split(" ")[0]); //트럭 수
        int w = Integer.parseInt(args[0].split(" ")[1]); //길이 width
        int L = Integer.parseInt(args[0].split(" ")[2]); //최대하중

        List<Integer> truckList = Arrays.stream(args[1].split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        
        Queue<Integer> bridgeQ = new LinkedList<>();//다리 위 트럭
        Queue<Integer> bridgeIndex = new LinkedList<>();//다리 위 트럭 인덱스

        Queue<Integer> remainQ = new LinkedList<>();//출발 전 트럭
        for(int i=0; i<n; i++){
            remainQ.add(truckList.get(i));
        }
        //트럭 위치
        int[] trucks = new int[n];
        
        //1초가 지나면, 
        int t = 0;
        int passed = 0;
        while(passed < n){
            t++;
            //다리 위 트럭
            int sum = bridgeQ.stream().mapToInt(Integer::intValue).sum(); 
            
            int minus = 0;
            for(Integer ttt : bridgeIndex){
                //다리에서 이동하기
                trucks[ttt]++;
                if(trucks[ttt] == w){
                    minus+=bridgeQ.poll(); //빼기.
                    passed++;
                }
            }
            if(remainQ.size()>0){
                int curW = remainQ.peek();
                //다리에 올리기
                if(L >= sum-minus+curW){
                    bridgeIndex.add((n-remainQ.size()));
                    bridgeQ.add(remainQ.poll());
                }
            }
        }
        System.out.println(t);
    }

}