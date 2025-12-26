import java.io.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Main {
    public static int PRE_MAX = Integer.MIN_VALUE;
    public static int PRE_MAX_IDX = Integer.MIN_VALUE;
    public static int MAX = Integer.MIN_VALUE;
    public static int MAX_IDX = Integer.MIN_VALUE;

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

        // str = "5\r\n" + //
        //                 "1 2 3\r\n" + //
        //                 "1 3 4\r\n" + //
        //                 "1 4 5\r\n" + //
        //                 "1 5 6";//

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        if(n==1){
            System.out.println(0);
            return;
        }

        List<List<int[]>> tree = new ArrayList<>(n+1);
        
        List<int[]> childTree = new ArrayList<>(n+1);
        Map<Integer, int[]> childMap = new HashMap<>();
        
        for(int i=0; i<=n; i++){ //초기화
            tree.add(new ArrayList<>());
            childTree.add(new int[2]);
        }
        
        for(int i=1; i<n; i++){
            String[] row = args[i].split(" ");

            int p = Integer.parseInt(row[0]);
            int c = Integer.parseInt(row[1]);
            int w = Integer.parseInt(row[2]);

            tree.get(p).add(new int[]{c,w});//자식 정보 및 w 추가
            childMap.put(c, new int[]{p,w});//자식의 부모 정보 및 w 추가
        }
        boolean[] visit = new boolean[n+1];
        //1 to node Weight = 
        int[] weightArr = new int[n+1];

        pre(tree, 1, 0, weightArr);
        post(tree, childMap, PRE_MAX_IDX, PRE_MAX, visit, weightArr);
        // System.out.println(PRE_MAX);
        System.out.println(MAX);

    }
    public static int commonParent(List<List<int[]>> tree, Map<Integer, int[]> childMap, int nodeA, int nodeB){
        List<Integer> pathA = new ArrayList<>();
        List<Integer> pathB = new ArrayList<>();

        pathA.add(nodeA);
        while(childMap.get(nodeA) != null){
            nodeA = childMap.get(nodeA)[0];
            pathA.add(nodeA);
        }

        pathB.add(nodeB);
        while(childMap.get(nodeB) != null){
            nodeB = childMap.get(nodeB)[0];
            pathB.add(nodeB);
        }
        pathA.retainAll(pathB);
        //공통 최상위 부모 = pathA.get(0);
        int parent = pathA.get(0);
        return parent;
        
    }
    public static void post(List<List<int[]>> tree, Map<Integer, int[]> childMap, int current, int weightSum, boolean[] visit, int[] weightArr){
        visit[current] = true;

        int[] parentNode = childMap.get(current); //부모 노드
        List<int[]> children = tree.get(current); //부모의 자식노드, 

        boolean visitAll = true;
        if(children != null){
            for(int[] child : children){
                visitAll = visitAll && visit[child[0]];
            }
        }
        if(parentNode != null){
            visitAll = visitAll && visit[parentNode[0]];
        }
        

        if(visitAll){ 
            if(weightSum > MAX){
                int parent = commonParent(tree, childMap, PRE_MAX_IDX, current);
                int calcWeight = weightSum - (weightArr[parent]); //공통 부모까지의 weight 제거
                MAX = Math.max(MAX, calcWeight);
            }
            return;
        }

        //부모 있을 경우 상위로 이동
        if(parentNode != null && parentNode.length > 0){
            if(!visit[parentNode[0]]){ //방문하지 않았을 경우
                post(tree, childMap, parentNode[0], weightSum, visit, weightArr);
            }
        }
        if(children != null){ //자식 있는 경우 = 1 정점 노드
            for(int[] child : children){
                if(visit[child[0]]) continue; // 방금 올라온 노드
                post(tree, childMap, child[0], weightSum+child[1], visit, weightArr);
            }
        }
    }
    

    public static void pre(List<List<int[]>> tree, int current, int weightSum, int[] weightArr){
        if(tree.get(current).size() == 0){ //더 이상 자식 노드가 없음 = tail
            
            if(PRE_MAX < weightSum){
                PRE_MAX = weightSum;
                PRE_MAX_IDX = current;
            }
            return;
        }

        List<int[]> children = tree.get(current); //부모 노드의 번호가 같으면 자식 노드의 번호가 작은 것이 먼저 입력
        //1개면 left, 2개면 left-right 순으로 입력됨.
        weightArr[current] = weightSum;
        //path.add(current);
        if(children != null){
            for(int[] child : children){
                pre(tree, child[0], child[1] + weightSum, weightArr);
            }
        }
        //2진트리가 아님..!
        // int[] left = children.get(0);
        // pre(tree, left[0], left[1] + weightSum, weightArr);
        // if(children.size()>1){
        //     int[] right = children.get(1);  
        //     pre(tree, right[0], right[1] + weightSum, weightArr);
        // }
        //path.remove(path.size()-1);
    }
    /**
     *  노드의 수(n) n
     *  간선 (n-1)개  
     *  부모노드 번호(p) 와 자식 노드(c) 가중치(w) 가 주어짐
     *  w 가중치 = 길이,  최장거리 구하기 => 길면 길수록 좋음, 
     *  
     *  트리노드는 보통 인접리스트?
     * 
     *  근데 길이순으로 1,2,3번이 같은 부모로 엮여있으면 최장이 아니니까
     * 
     * 
     */
}


