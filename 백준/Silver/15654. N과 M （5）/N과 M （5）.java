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

        // str = "4 4\r\n" + //
        //                 "1231 1232 1233 1234";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
         int N = Integer.parseInt(args[0].split(" ")[0]);
         int M = Integer.parseInt(args[0].split(" ")[1]);

         String[] row = args[1].split(" ");

         int[] arr = new int[N];
         for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(row[i]);
         }

        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(arr);

        boolean[] visit = new boolean[N];
        backtrack(arr, visit, list, new ArrayList<>(), N, M, 0);
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

    public static void backtrack(int[] arr, boolean[] visit, List<List<Integer>> list, List<Integer> current, int N, int M, int start){
        if(current.size() == M){
            list.add(new ArrayList<>(current));
            return;
        }
        for(int i=start; i<N; i++){
            if(visit[i]) continue;
            current.add(arr[i]);
            visit[i] = true;
            backtrack(arr, visit, list, current, N, M, 0);
            visit[i] = false;
            current.remove(current.size()-1);
        }
    }
}
