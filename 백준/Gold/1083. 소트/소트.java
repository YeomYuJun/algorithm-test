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

        // str = "5\r\n" + //
        //                 "10 20 30 40 50\r\n" + //
        //                 "9"; 

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        String[] arr  = args[1].split(" ");
        int S = Integer.parseInt(args[2]);

        List<Integer> list = new ArrayList<>();

        for(int i=0; i<N; i++){
            list.add(Integer.parseInt(arr[i]));
        }
        
        //list는 고작 50.. 
        if(S >= N*(N-1)/2){
            Collections.sort(list, Collections.reverseOrder());
        }else{
            for(int targetPos = 0; targetPos < N && S > 0; targetPos++){
                int maxIdx = targetPos;
                int searchLimit = Math.min(targetPos + S, N - 1);
                
                for(int i = targetPos + 1; i <= searchLimit; i++){
                    if(list.get(i) > list.get(maxIdx)){
                        maxIdx = i;
                    }
                }
                
                // bubble up
                while(maxIdx > targetPos){
                    Collections.swap(list, maxIdx, maxIdx-1);
                    maxIdx--;
                    S--;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for(Integer a : list){
            sb.append(a).append(" ");
        }
        System.out.println(sb.toString().trim());
        
        
    }
}