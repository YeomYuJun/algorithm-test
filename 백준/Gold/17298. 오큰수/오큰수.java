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
        
        // str = "4\r\n" + //
        //                 "9 5 4 8";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }


    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 점 수
        
        String[] row = args[1].split(" ");

        int[] arr = new int[N];
        for(int i=0; i<N; i++){
            int n = Integer.parseInt(row[i]);
            arr[i] = n;
        }

        int[] nge = new int[N];
        Stack<Integer> s = new Stack<>();
        
        nge[N-1] = -1;
        s.add(arr[N-1]);

        for(int j=N-1; j>=0; j--){
            int curArr = arr[j];
            while(!s.isEmpty()){
                int pop = s.pop();
                if(pop>curArr){
                    nge[j] = pop;
                    s.add(pop);
                    s.add(curArr);
                    break;
                }
            }
            if(s.empty()){
                nge[j] = -1;
                s.add(curArr);
                continue;
            }

            
        }
        // System.out.println(Arrays.toString(nge));
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<N; i++){
            sb.append(nge[i]).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}