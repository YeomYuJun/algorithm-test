import java.util.*;
class Solution {
    public int solution(int n, int[][] computers) {
        int answer = 0;

        int connCount = 0;
        boolean[][] visited = new boolean[n][n];
        Set<Integer> visitNode = new HashSet<>(); //한번 방문한 숫자 찾을 필요 X 

        for(int i=0; i<n-1; i++){ //마지막은 할 필요 없음.
            if(visitNode.contains(i)){
                continue;
            }
            Set<Integer> connNode = new HashSet<>();//{1,2}, {1,3} 같은 기준점으로의 연속 재방문방지 
            for(int j=i+1; j<n; j++){
                if(visited[i][j]){ //방문 패스 
                    continue;
                }
                if(connNode.contains(j)){
                    continue;
                }
                if(computers[i][j] == 1){ //대칭니까 
                    visited[i][j] = true;
                    visited[j][i] = true;
                    connNode.add(i);
                    connNode.add(j);

                    //j를 고정하고 i를 증가시키는 것은 불가능하니
                    //1을 찾으면 해당 j노드의 연결점을 찾아서 
                    crossSideNode(visited, computers, j, n, connNode);
                }
            }
            if(connNode.size() > 0){
                visitNode.addAll(connNode);
                connCount++;
            }
            
        }
        answer = n - visitNode.size() + connCount; // (전체 - 미방문노드)  = 연결 없는 숫자(혼자서 1의 네트워크) + 찾은 연결 숫자 ;

        

        return answer;
    }

    public void crossSideNode(boolean[][] visited, int[][] computers, int y, int n, Set<Integer> connNode){
        int i = y;
        for(int j=0; j<n; j++){
            if(visited[i][j]){ //방문 패스 
                continue;
            }
            if(connNode.contains(j)){
                continue;
            }
            if(computers[i][j] == 1){//또다른 연결 노드 발견(방문한 것은 패스했으니)
                visited[i][j] = true;
                visited[j][i] = true;
                connNode.add(i);
                connNode.add(j);
                crossSideNode(visited, computers, j, n, connNode);
            }
        }
    }
}