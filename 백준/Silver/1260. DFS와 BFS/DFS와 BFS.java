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

        // str = "2 1 2\r\n" + //
        //                 "1 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); //정점 개수
        int M = Integer.parseInt(args[0].split(" ")[1]); //간선 수
        int V = Integer.parseInt(args[0].split(" ")[2]); //시작 번호 

        //간선은 정점을 전부 나타내지 않을 수도 있음?
        if(N==1){
            System.out.println(V);
            System.out.println(V);
            return;
        }
        //노드 간선 연결
        Map<Integer,List<Integer>> fromTo = new HashMap<>();
        for(int i=1; i<=M; i++){
            String[] line = args[i].split(" ");
            int a = Integer.parseInt(line[0]);
            int b = Integer.parseInt(line[1]);
            
            List<Integer> aList = fromTo.getOrDefault(a, new ArrayList<Integer>());
            aList.add(b);
            fromTo.put(a, aList);
            List<Integer> bList = fromTo.getOrDefault(b, new ArrayList<Integer>());
            bList.add(a);
            fromTo.put(b, bList);
        }

        List<Integer> Dline = new ArrayList<>();
        List<Integer> Bline = new ArrayList<>();
        Dline.add(V);
        Bline.add(V);
        
        Queue<Integer> bfs = new LinkedList<>();
        Stack<Integer> dfs = new Stack<>();
        bfs.add(Dline.get(0));
        dfs.add(Bline.get(0));

        DFS(dfs, fromTo, Dline);
        BFS(bfs, fromTo, Bline);

        StringBuilder DFSSB = new StringBuilder();
        StringBuilder BFSSB = new StringBuilder();

        for(int i=0; i<Dline.size(); i++){
            DFSSB.append(Dline.get(i));
            BFSSB.append(Bline.get(i));
            if(i<Dline.size()-1){
                DFSSB.append(" ");
                BFSSB.append(" ");
            }
        }
        System.out.println(DFSSB.toString());
        System.out.println(BFSSB.toString());
    }
    public static void DFS(Stack<Integer> dfs, Map<Integer,List<Integer>> fromTo, List<Integer> line){
        //1.DFS
        while(!dfs.isEmpty()){
            Integer from = dfs.pop();
            List<Integer> toList = fromTo.getOrDefault(from, Collections.emptyList());
            Collections.sort(toList);
            if(!toList.isEmpty() && toList.size()>0){
                for(int i=0; i<toList.size(); i++){
                    if(!line.contains(toList.get(i))){
                        dfs.add(toList.get(i));
                        line.add(toList.get(i));
                        DFS(dfs, fromTo, line);
                    }
                }
            }
        }
    }
    public static void BFS(Queue<Integer> bfs, Map<Integer,List<Integer>> fromTo, List<Integer> line){
        //2.BFS
        while(!bfs.isEmpty()){
            Integer from = bfs.poll();
            List<Integer> toList = fromTo.getOrDefault(from, Collections.emptyList());
            Collections.sort(toList);
            if(!toList.isEmpty() && toList.size()>0){
                for(int i=0; i<toList.size(); i++){
                    if(!line.contains(toList.get(i))){
                        bfs.add(toList.get(i));
                        line.add(toList.get(i));
                    }
                }
            }
        }
    }
    
    /**
     *  단, 방문할 수 있는 정점이 여러 개인 경우에는 정점 번호가 작은 것을 먼저 
     */
}