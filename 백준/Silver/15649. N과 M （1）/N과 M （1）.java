import java.io.*;
import java.util.*;

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

        // str = "2 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);

        boolean[] visit = new boolean[N+1];
        //1 ~ N        
        DFS(N, M, visit, new ArrayList<>());
        //조합이 아닌 수열
    }
    
    public static void DFS(int N, int M, boolean[] visit, List<Integer> current){
        if(current.size() == M){
            String str = current.toString();
            str.substring(1,str.length()-1);
            String result = String.join(" ",str.split(", "));
            result = result.substring(1, result.length()-1);
            System.out.println(result);
            return;
        }
        for(int i=1; i<=N; i++){
            if(visit[i]) continue;

            visit[i] = true;
            current.add(i);
            DFS(N,M,visit, current);
            visit[i] = false;
            current.remove(current.size()-1);
        }
    }
    
}