import java.io.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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

        // str = "6 6\r\n" + //
        //                 "1\r\n" + //
        //                 "1 2 1\r\n" + //
        //                 "2 5 1\r\n" + //
        //                 "5 6 1\r\n" + //
        //                 "1 3 10\r\n" + //
        //                 "3 4 5\r\n" + //
        //                 "6 3 1";//

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int V = Integer.parseInt(args[0].split(" ")[0]);
        int E = Integer.parseInt(args[0].split(" ")[1]);

        int K = Integer.parseInt(args[1]);
        
        //Map<Integer,List<int[]>> nodes = new HashMap<>();
        Map<Integer,PriorityQueue<int[]>> nodes = new HashMap<Integer,PriorityQueue<int[]>>();


        for(int i=2; i<E+2; i++){
            String[] row = args[i].split(" ");

            int u  = Integer.parseInt(row[0]);
            int v  = Integer.parseInt(row[1]);
            int w  = Integer.parseInt(row[2]);

            PriorityQueue<int[]> destQ = nodes.getOrDefault(u, new PriorityQueue<int[]>((o1, o2) -> { //번호 낮은순, 같으면 w 낮은 순
                if(o1[0] == o2[0]){
                    return o1[1] - o2[1];
                }
                return o1[0]-o2[0];
            }));
            
            destQ.add(new int[]{v,w});
            
            nodes.put(u, destQ);
        }

        int[] vw = new int[V+1];    //노드 방문 weight
        for(int i=0; i<=V; i++){    //초기화, max = 방문 못함.
            vw[i] = Integer.MAX_VALUE;
        }
        boolean[] visit = new boolean[V+1]; //해당 노드 방문 여부 

        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> {
            return o1[1] - o2[1];
        });
        vw[K] = 0;
        visit[K] = true;
        int[] startNode = new int[]{K,0}; //시작 = K, 현재 weight은 0;
        q.add(startNode);

        while(!q.isEmpty()){
            int[] node = q.poll();
            int id = node[0];
            int weight = node[1];
            vw[id] = Math.min(weight, vw[id]); //최소값 갱신
            visit[id] = true;

            
            PriorityQueue<int[]> reachable = nodes.get(id);
            if(reachable != null && reachable.size() > 0){
                while(!reachable.isEmpty()){
                    int[] nextNode = reachable.poll();
                    int nextId = nextNode[0];
                    nextNode[1] += weight; //다음 노드에 가중치 쌓기

                    if(visit[nextId] && nextNode[1] > vw[nextId]){
                        continue;
                    }
                    q.add(nextNode);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        
        for(int i=1; i<=V; i++){
            if(i==K){
                sb.append("0");
            }else{
                if(!visit[i]){
                    sb.append("INF");
                }else{
                    sb.append(vw[i]);
                }
            }
            if(i<V){
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());



    }
    /**
     * 정점의 수(V)와 간선의 수(E), 시작번호(K),  
     * 노드(u) <-> 노드(v)의 가중치 (w)인 간선이 있음. 양방향이 아닌듯?
     * 1번,2번 노드 사이에 가중치가 다른 간선이 여럿 있을 수 있음
     * 늘 최저 w로 이동해야함.
     * 
     * 1. 현재 노드에서 이동할 수 있는 모든 노드를 찾는다.
     * 2. 이동할 수 있는 모든 노드 별 가장 낮은 가중치의 간선으로 이동한다.
     * 3. 이동할 수 있는 모든 노드중 가중치가 낮은 순으로 Queue 에 넣는다.
     * 4. 도착할 때까지 반복한다.
     * 
     * 시작점은 늘 같으므로 노드별 최저값을 갱신해놓으면? 근데 3~4번째에 도달해본 정점이 
     * 최저값이라도 보장할 수 있는가? a to b로 가장 낮은 가중치로 이동하다가 만난 c에 대해서 최저값이라 보장할 수 있나?
     * 가중치로 우선순위 큐 만들어야겠네 
     * 1~V까지 반복이 아니라 모든 정점을 방문하는 PQ에서 가장 낮은 방문 weight으로 배열에 value를 저장해놓으면?
     * 
     * a   -> d 1 > c 1-> b  1 => 3
     * a  -> c 4 -> b 2 -> 
     */
}
