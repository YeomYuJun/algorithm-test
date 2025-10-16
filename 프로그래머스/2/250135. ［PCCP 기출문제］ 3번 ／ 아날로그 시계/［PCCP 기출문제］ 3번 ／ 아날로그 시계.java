class Solution {
    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int answer = 0;

        int leftTime = (3600*h1) + (60*m1) + s1;
        int rightTime = (3600*h2) + (60*m2) + s2;
        if(leftTime == rightTime) return 0;
        double as = (86400.0 / 60.0);
        double am = (86400.0 / 3600.0);
        double ah = (86400.0 / 43200.0);

        // 시작 시각에서 이미 겹쳐있는지 체크
        int sStartPos = leftTime % 60;
        int mStartPos = leftTime % 3600;
        int hStartPos = leftTime % 43200;
        
        double sStartY = as * (double)sStartPos;
        double mStartY = am * (double)mStartPos;
        double hStartY = ah * (double)hStartPos;
        
        if(Math.abs(sStartY - mStartY) < 0.01 || Math.abs(sStartY - hStartY) < 0.01) {
            answer++;
        }
        
        for(int tik = leftTime+1; tik <= rightTime; tik++){
            int prevTik = tik-1;
            
            int sPrevPos = prevTik%60;
            int mPrevPos = prevTik%3600;
            int hPrevPos = prevTik%43200;

            int sPos = tik%60;
            int mPos = tik%3600;
            int hPos = tik%43200;

            double sPrevY = as * (double)sPrevPos;
            double mPrevY = am * (double)mPrevPos;
            double hPrevY = ah * (double)hPrevPos;
            
            double sY = as * (double)sPos;
            double mY = am * (double)mPos;
            double hY = ah * (double)hPos;

           // 초침이 0으로 돌아가는 경우 처리
            if(sPos == 0) { sY = 86400; }
            // 분침이 0으로 돌아가는 경우 처리
            if(mPos == 0 && sPrevPos != 0) { mY = 86400; }
            
            // 시침이 0으로 돌아가는 경우 처리
            if(hPos == 0 && (prevTik % 43200) != 0) { hY = 86400; }
            
            // 0시 또는 12시 정각 (세 바늘 모두 겹침)
            if(sPos == 0 && mPos == 0 && hPos == 0) {
                answer++;
                continue;
            }
            
            int count = 0;
            // 초침-분침 추월 체크 (이전: 초침이 뒤, 현재: 초침이 앞 or 같음)
            if(sPrevY < mPrevY && sY >= mY) count++;
            
            // 초침-시침 추월 체크
            if(sPrevY < hPrevY && sY >= hY) count++; 
            
            // 중복 제거: 분침도 시침을 추월한 경우.
            // 즉, 초침이 시침/분침 둘 다 추월했는데, 분침도 시침을 추월했다면
            // 세 바늘이 한 점에서 만난 것이므로 1번만 카운트
            if(count == 2 && mPrevY < hPrevY && mY >= hY) {
                count = 1;
            }
            
            answer += count;
        }

        return answer;
    }

    
    /**
     * 시,분침과 초침이 겹치는 순간의 수.
     * 3600*h + 60*m + s 
     * 330~420 = 90초 차이
     * 
     * 분의 위치는..
     * 일단 
     * 시계의 둘레를 펼쳐서 해당 위치를 좌표라고 치면, 초단위가 1초 = (0,1)이 될 수 있고, 86400이 둘레가 되게하는 반지름이라 가정. 
     * 반지름이 대충 x 라고 치고. 아무튼. 2*pi*x = 86400임, 둘레를 펼친 길이 86400임
     * 
     * 즉 시분초의 xAxis position을 생각하면 될듯.
     * 시침의 xAxis 위치의 변화: xh+ym+zs 인데 1시간은 3600이니까  3600h + 60m+ s임. (단 h<24, m<60, s<60)
     * 분침의 xAxis 위치의 변화: 0h + 1440m + 24s (h는 영향을 안줌, m<60, s<60 이고 m은 60분까지 있으니 86400/60으로, s는 86400/60/60인 24로.)
     * 시침의 xAxis 위치의 변화: 0h + 0m + 1440s (h 영향 안줌, m 영향 안줌, s < 60, 시계는 60초마다, 86400/60으로 계산.)
     * 좌표평면에서 보면 x축을 시간 , y축을 pos값이라고 치면 일정 주기의 패턴이 나옴(둘레를 8)
     *
     * ---------------------------------------------------------------
     * |         . s(60,86400){s}     .(3600,86400) => m고점 (3600,0)=> m초기화)             .(43200,86400) => h고점 -> (43200,0) => h초기화 
     * |        /
     * |       / 
     * |      /  
     * |     /   
     * |    /                
     * |   /     
     * |  /      
     * | /                                
     * |/        .(60,0){s}         .(3600,0){m}                                           .(43200,0){h}
     * |----... 60 61 ... -----... 3600 ... -----------------------------------------...  43200 ...-------86400
     *  => 톱니파 3개
     */
}