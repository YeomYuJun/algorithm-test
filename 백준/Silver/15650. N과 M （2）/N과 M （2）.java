import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        // ////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "8 4";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
         int N = Integer.parseInt(args[0].split(" ")[0]);
         int M = Integer.parseInt(args[0].split(" ")[1]);

         int[] arr = new int[N+1];
         for(int i=1; i<=N; i++){
            arr[i] = i;
         }

        List<List<Integer>> list = new ArrayList<>();
        backtrack(arr, list, new ArrayList<>(), N, M, 1);
        StringBuilder sb = new StringBuilder();
        
        for(int i=0; i<list.size(); i++){
            List<Integer> cur = list.get(i);
            for(int j=0; j<cur.size(); j++){
                sb.append(cur.get(j));
                if(j!=cur.size()-1){
                    sb.append(" ");
                }
            }
            if(i!=list.size()-1){
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
         
    }

    public static void backtrack(int[] arr, List<List<Integer>> list, List<Integer> current, int N, int M, int start){
        if(current.size() == M){
            list.add(new ArrayList<>(current));
            return;
        }
        for(int i=start; i<=N; i++){
            current.add(arr[i]);
            backtrack(arr, list, current, N, M, i+1);
            current.remove(current.size()-1);
        }
    }
}
