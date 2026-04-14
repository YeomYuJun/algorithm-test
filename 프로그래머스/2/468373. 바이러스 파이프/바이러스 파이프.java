import java.util.*;

class Solution {
    
    public int solution(int n, int infection, int[][] edges, int k) {
        int answer = 0;
        maxCnt = 0;
        //인접 행렬로 만들고
        List<List<int[]>> arrList = new ArrayList<>();
        for(int i=0; i<=n; i++){
            arrList.add(new ArrayList<>());
        }

        for(int i=0; i<n-1; i++){
            int[] edge = edges[i];

            int x = edge[0];
            int y = edge[1];
            int type = edge[2];

            arrList.get(x).add(new int[]{y,type});
            arrList.get(y).add(new int[]{x,type});
        }

        List<Integer> curNode = new ArrayList<>();
        curNode.add(infection);

        dfs(arrList, curNode, answer, k);

        answer=maxCnt;
        return answer;
    }

    static int maxCnt = 0;
    public static void dfs(List<List<int[]>> arrList, List<Integer> curNode, int depth, int k){
        int curCount = curNode.size();
        maxCnt = Math.max(maxCnt, curCount);
        //maxCnt가 노드 수랑 같아지면 그냥 k 버리고 탈출해도 될 거 같긴하네

        if(depth == k){
            return;
        }
        for (int type = 1; type <= 3; type++) {
            List<Integer> newlyAdded = getNewlyInfected(arrList, curNode, type);
            
            // 새로 감염될 노드가 있다면? -> 감염 시키고 다음 단계로
            if (!newlyAdded.isEmpty()) {
                List<Integer> nextNodeSet = new ArrayList<>(curNode);
                nextNodeSet.addAll(newlyAdded);
                
                dfs(arrList, nextNodeSet, depth + 1, k);
            }
        }
    }
    public static List<Integer> getNewlyInfected(List<List<int[]>> arrList, List<Integer> currentNodes, int type) {
        Queue<Integer> q = new LinkedList<>(currentNodes);
        Set<Integer> visited = new HashSet<>(currentNodes); // 이미 감염된 노드
        List<Integer> newlyInfected = new ArrayList<>();

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int[] edge : arrList.get(u)) {
                int v = edge[0];
                int t = edge[1];
                if (t == type && !visited.contains(v)) {
                    visited.add(v);
                    newlyInfected.add(v);
                    q.add(v);
                }
            }
        }
        return newlyInfected;
    }
}


