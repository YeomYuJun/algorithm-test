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

        // str = "4 4\r\n" + //
        //                 "1 1 2 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);

        List<Integer> list = new ArrayList<>();
        String[] arr = args[1].split(" ");
        for(String s : arr){
            list.add(Integer.parseInt(s));
        }
        
        Collections.sort(list);
        
        boolean[] visit = new boolean[N+1];
        Set<String> strList = new LinkedHashSet<>();
        //1 ~ N        
        int lastPick = 0;
        DFS(N, M, visit, new ArrayList<>(), list, strList, lastPick);
        
        for(String str : strList){
            str.substring(1,str.length()-1);
            String result = String.join(" ",str.split(", "));
            result = result.substring(1, result.length()-1);
            System.out.println(result);
        }
        
        //조합이 아닌 수열
    }
    
    public static void DFS(int N, int M, boolean[] visit, List<Integer> current, List<Integer> list, Set<String> curStr, int lastPick){
        if(current.size() == M){
            String str = current.toString();
            curStr.add(str);
            return;
        }
        
        for(int i=1; i<=N; i++){
            int val = list.get(i-1);
            if(val<lastPick) continue;
            current.add(val);
            DFS(N,M,visit, current, list, curStr, val);
            current.remove(current.size()-1);
        }
    }
    
}