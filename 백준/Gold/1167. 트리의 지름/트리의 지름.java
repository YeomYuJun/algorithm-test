import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static int PRE_MAX = Integer.MIN_VALUE;
    public static int PRE_MAX_IDX = Integer.MIN_VALUE;
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
        
        // str = "2\r\n" + //
        //                 "1 2 1 -1\r\n" + //
        //                 "2 1 1 -1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int V = Integer.parseInt(args[0]);

        //int[][] tree = new int[V+1][V+1];
        Map<Integer, List<int[]>> treeMap = new HashMap<>();
        

        for(int i=1; i<=V; i++){
            String[] row = args[i].split(" ");
            
            int startNode = Integer.parseInt(row[0]);
            
            int size = row.length-2; //시작, 끝 제거
            
            for(int j=1; j<=size; j+=2){
                int endNode = Integer.parseInt(row[j]);
                int lineLength = Integer.parseInt(row[j+1]);
                
                //tree[startNode][endNode] = Math.max(tree[startNode][endNode], lineLength);

                List<int[]> lineList = treeMap.getOrDefault(startNode, new ArrayList<>());
                lineList.add(new int[]{endNode, lineLength});
                treeMap.put(startNode, lineList);
            }   
        }
        
        boolean[] pre_visit = new boolean[V+1];
        pre_visit[1] = true;
        longestLeng(treeMap, pre_visit, 1, 0);

        boolean[] visit = new boolean[V+1];
        visit[PRE_MAX_IDX] = true;
        longestLeng(treeMap, visit, PRE_MAX_IDX, 0);

        System.out.println(PRE_MAX);
        
        // i= 0~v, j=i~v V
    }
    public static void longestLeng(Map<Integer, List<int[]>> treeMap, boolean[] visit, int current, int length){
        List<int[]> lines = treeMap.getOrDefault(current, new ArrayList<>());
        boolean hasNext = false;
        if(lines.size() != 0){
            for(int[] node : lines){
                if(!visit[node[0]]) hasNext = true;
            }
        }
        if(hasNext){//다음 방분 가능 노드 존재
            for(int i=0; i<lines.size(); i++){
                int[] node = lines.get(i);
                if(!visit[node[0]]){
                    visit[node[0]] = true;
                    longestLeng(treeMap, visit, node[0], length+node[1]);
                    visit[node[0]] = false;
                }
            }
        }else{ // 더이상 방문 가능 노드 없음
            if(PRE_MAX < length){
                PRE_MAX = length;
                PRE_MAX_IDX = current;
            }
        }

    }
    /**
     * 정점 V 가 1~V까지
     * 시작 V, 도착 V, 길이L, -1 은 종료 sign 
     * row[0]  ,row[row.length-1] 빼고  도착노드:간선길이 쌍
     * 양방향인가? 입력값은 쌍이긴한데
     * 루트가 없는 트리에서 최장거리... 어떻게? 
     * 행렬? Map? a to b의 최장거리만 넣으면 되나? 
     * 행렬은 가능 경로 찾기 너무 경우가 많을지도
     * 길이 합은 최대 10억 
     */
}
