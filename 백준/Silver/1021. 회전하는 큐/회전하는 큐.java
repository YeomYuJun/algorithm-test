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

        // str = "10 3\r\n" + //
        //                 "2 9 5";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);
        String[] row = args[1].split(" ");

        Deque<Integer> deq = new ArrayDeque<>();
        
        int total = 0;
        for(int i=1; i<=N; i++){
            deq.add(i);
        }
        for(String event : row){
            int ele = Integer.parseInt(event);

            int top = deq.peek();
            //ele를 뽑기.
            if(ele == top){ //일치하면 그냥 뽑기
                deq.poll();  
            }else{
                Deque<Integer> deq1 = new ArrayDeque<>(deq);
                //우>좌 이동
                int right = top;
                int rightCnt = 0;
                while(right != ele){
                    right = deq1.pollLast();
                    deq1.addFirst(right);
                    rightCnt++;
                }
                deq1.pollFirst();
                
                Deque<Integer> deq2 = new ArrayDeque<>(deq);
                //1 2 ~ 9 10
                int left = deq2.peekFirst();
                int leftCnt = 0;
                while(left != ele){
                    left = deq2.pollFirst();
                    deq2.addLast(left);
                    leftCnt++;
                }
                leftCnt-=1;
                deq2.pollLast();

                if(rightCnt < leftCnt){
                    deq = new ArrayDeque<>(deq1);
                    total+=rightCnt;
                }else{
                    deq = new ArrayDeque<>(deq2);
                    total+=leftCnt;
                }
            }
        }
        System.out.println(total);


    }


}