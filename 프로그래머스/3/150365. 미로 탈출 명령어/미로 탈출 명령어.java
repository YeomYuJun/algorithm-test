import java.util.*;
class Solution {
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        int gap = Math.abs(x-r) + Math.abs(y-c); //최단경로 후 나머지.
        
        if((k-gap)%2 != 0 || gap>k){ // 0..인 경우는 없음
            return "impossible";
        }

        Queue<String[]> q = new LinkedList<>();
        String[] startPos = new String[]{String.valueOf(x-1),String.valueOf(y-1), String.valueOf(0), ""};//(x,y) [0]=y , [1]=x, [2]=이동 횟수, [3]=누적경로
        q.offer(startPos);

        int[] dy = {1, 0, 0, -1}; 
        int[] dx = {0, -1, 1, 0}; // d l r u 순서
        String[] dp = {"d","l","r","u"};

        while(!q.isEmpty()){
            int stepSize = q.size();
            for(int i=0; i<stepSize; i++){
                String[] pos = q.poll();
                int nowY = Integer.parseInt(pos[0]);
                int nowX = Integer.parseInt(pos[1]);
                int moveCnt = Integer.parseInt(pos[2]);
                String path = pos[3];
                
                if(nowX == c-1 && nowY == r-1){ //도착
                    if(moveCnt == k){
                        return path;
                    }
                }

                for(int dr = 0; dr<4; dr++){
                    int newY = nowY+dy[dr];
                    int newX = nowX+dx[dr];

                    if(newX < 0 || newY < 0 || newX >=m || newY >= n){ //미로 바깥 불가능. 
                        continue;
                    }

                    String newPath = path+dp[dr];
                    int localGap = Math.abs(newY-(r-1)) + Math.abs(newX-(c-1));
                    if(localGap>(k-moveCnt)) continue; //이제 더이상 탈출할 수 없는 곳으로 가버림.
                    

                    q.offer(new String[]{String.valueOf(newY),String.valueOf(newX), String.valueOf(moveCnt+1), newPath});
                    break;
                }
            }
        }
        return "impossible";
    }

    /**
     * x,y로 잡고 최단 탈출 경로인 척하면서 k번 탈출해야함.
     * 사전순 정렬이므로 List에 담고 나중에 sort? 아니면 우선적으로 탐색하면 정답을 보장하나?
     * 
     * 반복 방문 가능. BFS Queue로 처리하긴 하는데,
     * 우선순위가 사전순 d,l,r,u 이므로 {-1, 0, 0, 1}, {0, -1, 1, 0} 으로 dy dx잡고
     * 가로막는 경우가 없으므로 최단경로는 단순함.
     * 
     * k-(도착점-시작점의 차이)%2 != 0 인 경우 임파서블일 거 같은데, 또 k보다 멀 때 
     * k번째까지 Queue 쌓고
     * k로 제한해도 방문처리 없어도 되나? 조건 잘 주면 최선탐색
     * 
     */
}