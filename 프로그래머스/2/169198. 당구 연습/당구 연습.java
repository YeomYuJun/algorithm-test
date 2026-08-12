class Solution {
    public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
        int[] answer = new int[balls.length];

        int sX = startX;
        int sY = startY;
        
        //공을 맞춘다고 startX, startY가 바뀌진않음
    
        for(int i=0; i<balls.length; i++){ 
            int tX = balls[i][0];
            int tY = balls[i][1];


            /*
              Y
              |
              |
              |
              |
              ㅡㅡㅡㅡㅡㅡㅡ x
            */

            // 1. x축으로 가까운 벽
            // int yGap = Math.min((n-sY) + (n-tY), (sY + tY)); //한번에 안하면 따로감
            int xDGap = (sY + tY);
            int xTGap = (n-sY) + (n-tY);
        
            // 2. y축으로 가까운 벽
            // int xGap = Math.min((m-sX) + (m-tX), (sX + tX)); 
            int yRGap = (m-sX) + (m-tX);
            int yLGap = (sX + tX); 
            
            // 3. x벽 y벽으로 진행방향에 t ball이 있는가? => 쿠션 전에 부딪히는가? => x축이나 y축으로 좌표가 일치하는가? 
            // 좌표도 일치하고 가까운 벽에 t ball이 더 가까운가?
            boolean yLAxisAble = true;
            boolean yRAxisAble = true;
            boolean xTAxisAble = true;
            boolean xDAxisAble = true;

            if(tY == sY){//y가 같다 = y축선 y axis 사용 못할 가능성이 있다 
                // 0 t s 순서 or s t m 순서
                if(yLGap < yRGap && tX < sX){
                    yLAxisAble = false;
                }else if(yRGap < yLGap && sX < tX){
                    yRAxisAble = false;
                }
            }
            if(tX == sX){ // x축이 같다 = xtjs x Axis를 못 쓸 가능성이 있다.
                if(xDGap < xTGap && tY < sY){ 
                    xDAxisAble = false;
                }else if(xTGap < xDGap && sY < tY){
                    xTAxisAble = false;
                }
            }

            // int yGap = Math.min(xTGap, xDGap); //x축 같으나, 최소가 아닌 최대갭, 즉 D or T 방향으로 해도 yGap보다 최선인 경우가 있음.
            // int xGap = Math.min(yLGap, yRGap);
            int min = Integer.MAX_VALUE;
            if(xDAxisAble){ //x축 원쿠션 가능
                int cushion = xDGap*xDGap;
                int len = Math.abs(tX-sX);
                int result = cushion+(len*len);
                min = Math.min(min, result);
            }
            if(xTAxisAble) {
                int cushion = xTGap*xTGap;
                int len = Math.abs(tX-sX);
                int result = cushion+(len*len);
                min = Math.min(min, result);
            }
            if(yLAxisAble){ //y축 원쿠션 가능
                int cushion = yLGap*yLGap;
                int len = Math.abs(tY-sY);
                int result = cushion+(len*len);
                min = Math.min(min, result);
            }
            if(yRAxisAble){
                int cushion = yRGap*yRGap;
                int len = Math.abs(tY-sY);
                int result = cushion+(len*len);
                min = Math.min(min, result);
            }
            
            answer[i] = min;
        }
        
        
        return answer;
    }
}