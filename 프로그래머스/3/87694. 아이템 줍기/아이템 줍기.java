import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        // (1,3)에서 이동할 수 있는 방향 = dx,dy에 방향 전부  에 대해 
        // 1. rectangle 전체에 대해 외곽인가?
        // 2. 다른 rect 내부는 아닌가? != 내부면 못감
        // 3. 상자를 밟고 점프하여 넘어가는 경우 (두꼐가 1인 상자)
        // 4. 골짜기가 틈이 1인경우 다른 상자로 점프해감 등등 
        
        
        // 2배 확장
        for(int i = 0; i < rectangle.length; i++){
            for(int j = 0; j < 4; j++){
                rectangle[i][j] *= 2;
            }
        }
        
        characterX *= 2;
        characterY *= 2;
        itemX *= 2;
        itemY *= 2;
        
        Queue<int[]> q = new LinkedList<>();
        int[] p = new int[]{characterX, characterY, 0};
        boolean[][] isVisit = new boolean[101][101];

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        isVisit[p[0]][p[1]] = true;
        q.offer(p);
        
        while (!q.isEmpty()) {
            int[] player = q.poll();
            
            for(int drct = 0; drct < 4; drct++){
                int px = player[0] + dx[drct];
                int py = player[1] + dy[drct];

                if(px < 0 || px >= 101 || py < 0 || py >= 101){
                    continue;
                }
                if(isVisit[px][py]){
                    continue;
                }

                boolean isAble = getMoveable(player[0], player[1], px, py, rectangle);
                if(isAble){
                    if(px == itemX && py == itemY){
                        return (player[2] + 1) / 2;  // 결과를 2로 나눔
                    }
                    int[] next = new int[]{px, py, player[2] + 1};
                    isVisit[px][py] = true;
                    q.offer(next);
                }
            }
        }
        return 0;
    }
    
    public boolean getMoveable(int originX, int originY, int x, int y, int[][] rectangle){
        boolean isXMove = y - originY == 0; 
        boolean isYMove = x - originX == 0;
        boolean isEdge = false;

        for(int i = 0; i < rectangle.length; i++){
            int leftX = rectangle[i][0];
            int leftY = rectangle[i][1];
            int rightX = rectangle[i][2];
            int rightY = rectangle[i][3];

            // 도착점이 rect 내부에 있는 조건 
            if(leftX < x && leftY < y && rightX > x && rightY > y){
                return false;
            }
            
            // 이동 경로가 이 사각형과 완전히 무관한 경우 스킵
            if(leftX > x || leftY > y || rightX < x || rightY < y){
                continue;
            }
            
            // 내부 관통 체크
            if(isXMove){
                int minX = Math.min(originX, x);
                int maxX = Math.max(originX, x);
                
                if(y != leftY && y != rightY && leftY < y && rightY > y){
                    if(minX < rightX && maxX > leftX){
                        return false;
                    }
                }
            } else if(isYMove){
                int minY = Math.min(originY, y);
                int maxY = Math.max(originY, y);
                
                if(x != leftX && x != rightX && leftX < x && rightX > x){
                    if(minY < rightY && maxY > leftY){
                        return false;
                    }
                }
            }

            // 외곽에 존재하는 경우 
            if((leftX == x || rightX == x) && isYMove){
                int minY = Math.min(originY, y);
                int maxY = Math.max(originY, y);
                if(minY >= leftY && maxY <= rightY){
                    isEdge = true;
                }
            } else if((leftY == y || rightY == y) && isXMove){
                int minX = Math.min(originX, x);
                int maxX = Math.max(originX, x);
                if(minX >= leftX && maxX <= rightX){
                    isEdge = true;
                }
            }
        }
        return isEdge;
    }
    /**
     * 조건이 명료하긴함
     * BFS로 조건 타고 이동하면 될듯
     * 조건 명확하면 => 도착 == 최단 루트
     */
}