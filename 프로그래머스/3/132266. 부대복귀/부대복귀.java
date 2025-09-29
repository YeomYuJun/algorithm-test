import java.util.*;

class Solution {
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        int[] answer = new int[sources.length];
        
        // destination부터 역방향 BFS로 모든 노드까지의 최단거리 계산
        int[] distances = walkNodeReverse(n, roads, destination);
        
        // 각 source에 대해 미리 계산된 거리 조회
        for(int i = 0; i < sources.length; i++){
            answer[i] = distances[sources[i]];
        }
        
        return answer;
    }

    public int[] walkNodeReverse(int n, int[][] roads, int destination){
        // 각 노드까지의 최단거리 저장 (-1로 초기화)
        int[] distances = new int[n + 1];
        for(int i = 0; i <= n; i++){
            distances[i] = -1;
        }
        
        // 인접 리스트로 그래프 구성 (O(roads.length) 탐색 최적화)
        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }
        
        // BFS 시작
        Queue<Integer> q = new LinkedList<>();
        q.offer(destination);
        distances[destination] = 0;
        
        while(!q.isEmpty()){
            int current = q.poll();
            int currentDist = distances[current];
            
            // 인접 노드 탐색
            for(int neighbor : graph.get(current)){
                if(distances[neighbor] == -1){  // 미방문 노드
                    distances[neighbor] = currentDist + 1;
                    q.offer(neighbor);
                }
            }
        }
        
        return distances;
    }
    /**
     * sources[i]와 destination까지연결된 노드가 있는지 확인하면 되는 문제.
     * BFS로 road의 시작지점, 도착지점에 있는지 확인
     * 근데 갔던 노드를 다시 방문할 필요가 없는 게 확실한가? 갈 일이 있는가? 없음.
     * 
     * startNode부터 dest까지 반복해서 그런가 시간 초과
     * destination으로 부터 BFS해서 각 노드 방문 후 sources중 하나에 일치하면 해당 인덱스의 값을 answer에 추가하면 BFS 1회만으로 가능할 듯
     */
}