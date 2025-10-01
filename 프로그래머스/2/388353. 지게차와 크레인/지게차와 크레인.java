import java.util.*;
class Solution {
    private int popCount = 0;
    public int solution(String[] storage, String[] requests) {
        popCount = 0;
        int answer = 0;
        //꺼낸 곳 
        boolean[][] isPick = new boolean[storage.length][storage[0].length()];

        for(int i=0; i<requests.length; i++){
            char target = requests[i].charAt(0);
            int behavior = requests[i].length(); /*  1 = "지게차" 2= "크레인" */;
            if(behavior == 1){
                forklist(storage, target, isPick);
            }else if(behavior == 2){
                crane(storage, target, isPick);
            }
        }
        answer = (storage.length * storage[0].length()) - popCount;
        return answer;
    }
    public boolean bfs(int startRow, int startCol, String[] storage, boolean[][] isPick, int[] origin, List<int[]> pickList){
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] isVisit = new boolean[storage.length][storage[0].length()];
        
        queue.offer(new int[]{startRow, startCol});
        isVisit[startRow][startCol] = true;
        
        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            
            for(int direction = 0; direction < 4; direction++){
                int nextRow = row + dy[direction];
                int nextCol = col + dx[direction];
                
                if(nextRow < 0 || nextCol < 0 || nextRow > storage.length-1 || nextCol > storage[0].length()-1){
                    continue;
                }
                
                if(!isPick[nextRow][nextCol]){
                    continue;
                }
                
                // 외곽의 빈 공간 도달 → 탈출 성공
                if(nextRow == 0 || nextRow == storage.length-1 || nextCol == 0 || nextCol == storage[0].length()-1){
                    pickList.add(origin);
                    popCount++;
                    return true;
                }
                
                if(!isVisit[nextRow][nextCol]){
                    isVisit[nextRow][nextCol] = true;
                    queue.offer(new int[]{nextRow, nextCol});
                }
            }
        }
        
        return false; // 탈출 실패
    }

    public void forklist(String[] storage, char target, boolean[][] isPick){
        List<int[]> pickList = new ArrayList<>(); //일괄 처리를 위한 List
        boolean[][] isVisit = new boolean[storage.length][storage[0].length()];
        //외곽 먼저 처리
        for(int i = 0; i < storage.length; i++){
            for(int j = 0; j < storage[0].length(); j++){
                if((i == 0 || i == storage.length-1 || j == 0 || j == storage[0].length()-1)){
                    if(!isPick[i][j] && target == storage[i].charAt(j)){
                        pickList.add(new int[]{i, j});
                        popCount++;
                    }
                }
            }
        }

        //내부 탐색 (1 ~ length-2)
        for(int i = 1; i < storage.length - 1; i++){
            for(int j = 1; j < storage[0].length() - 1; j++){
                if(isPick[i][j] || isVisit[i][j]){ // 이미 방문했거나 pick된 곳은 스킵
                    continue;
                }
                if(target == storage[i].charAt(j)){
                    boolean findContainer = bfs(i, j, storage, isPick, new int[]{i,j}, pickList);
                }
            }
        }
        //request 처리 후 뽑기 일괄처리
        for(int[] pickPos : pickList){
            int row = pickPos[0];
            int col = pickPos[1];
            isPick[row][col] = true;
        }
    }

    public void crane(String[] storage, char target, boolean[][] isPick){
        for(int i=0; i<storage.length; i++){
            for(int j=0; j<storage[0].length(); j++){
                if(isPick[i][j]){
                    continue;
                }
                if(target == storage[i].charAt(j)){
                    isPick[i][j] = true;
                    popCount++;
                }
            }
        }
    }
    /**
     * 
     * 지게차로 뽑을 때는 해당 위치로부터 접근가능한 곳이 있으면 DFS? 를 통해
     * 탈출 가능한지(바깥 0, length-1에 도달 가능한지 보면 될 듯)
     * 
     * 탈출 경로가 여럿일 경우 카운트가 늘어나는 이슈가 있음..
     * dfs에서 조기 종료되게 해야할듯
     */
}