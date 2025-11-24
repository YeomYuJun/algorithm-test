class Solution {
    public int[] solution(int target) {        
        int[] answer = new int[2];
        int shot = 0; 
        int sbShoot = 0;
        
        //60까지 메모이제이션 
        int[][] d = new int[10000][2];
        for(int i=1; i<=20 ;i++){
            d[i][0] = 1; //슛
            d[i][1] = 1; //싱글
        }
        for(int i=21; i<=40; i++){
            
            d[i][0] = i%2 == 0 || i%3 == 0 ? 1 : 2;
            //더블 , 트리플로 1번에 가능이면 싱글은 아님
            d[i][1] = i%2 == 0 || i%3 == 0 ? 0 : 2; 
            //안 나뉘면 싱글 두번 했음 (ex 23 : 13 + 10)
        }
        for(int i=41; i<=60; i++){
            if(i==50){
                d[i][0] = 1;
                d[i][1] = 1;
                continue;
            }
            d[i][0] = i%3 == 0 ? 1 : 2;
            d[i][1] = i%3 == 0 ? 0 : i>50 ? 2 : 1; //50 이상 3 안나뉨 = 불+싱글
        }
        //60까지의 정리 끝
        // 61~310: DP로 계산
        for(int i = 61; i <= 310; i++) {
            d[i][0] = Integer.MAX_VALUE;
            d[i][1] = 0;
            
            // 모든 가능한 한 발 점수 시도
            for(int single = 1; single <= 20; single++) {
                updateDP(d, i, single, 1);  // 싱글/불 카운트
            }
            
            //더블
            for(int doubleScore = 2; doubleScore <= 40; doubleScore += 2) {
                updateDP(d, i, doubleScore, 0);
            }
            
            //트리플
            for(int tripleScore = 3; tripleScore <= 60; tripleScore += 3) {
                updateDP(d, i, tripleScore, 0);
            }
            
            //불
            updateDP(d, i, 50, 1);  // 싱글/불 카운트
        }
        
        // target 처리
        if(target <= 310) {
            answer[0] = d[target][0];
            answer[1] = d[target][1];
        } else {
            // 310 초과: 60씩 빼서 250~310 범위로 환원
            int quotient = (target - 250) / 60;
            int remainder = (target - 250) % 60;
            int adjusted = 250 + remainder;  // 250~310 범위
            
            answer[0] = d[adjusted][0] + quotient;  //60은 싱글/불 아님
            answer[1] = d[adjusted][1];
        }
        
        return answer;
    }
    // DP 업데이트 
    private void updateDP(int[][] dp, int target, int score, int isSingleOrBull) {
        if(target - score < 0) return;
        
        int prevDarts = dp[target - score][0];
        int prevSB = dp[target - score][1];
        
        if(prevDarts == Integer.MAX_VALUE) return;
        
        int newDarts = prevDarts + 1;
        int newSB = prevSB + isSingleOrBull;
        
        // 더 나은 경로인지 확인
        if(newDarts < dp[target][0] || 
        (newDarts == dp[target][0] && newSB > dp[target][1])) {
            dp[target][0] = newDarts;
            dp[target][1] = newSB;
        }
    }
    /**
     * target으로 제시된 점수를 0으로 만들기
     * = 0부터 점수 쌓아서 target 만들기
     * 싱글(1~20), 불(50)이 우선순위임
     * return new int{던진 수 : 싱글+불 수} 
     * = 던진 수가 낮을 수록 좋고, 같으면 싱글+불 수가 높은 경우가 이김.
     * dp[n][던진수,싱글/불]하면 될듯
     * 
     * 1. 50 이상은 불을 던지는 게 무조건 좋은가?
     * => 73점 => 불+싱글+1슛 > 트리플60+13싱글 가능
     * => 즉, 아님. 
     * 
     * 60+n 인 경우 n을 계속 쪼개야함 
     * 싱글or불 + 60의 경우를 보면 
     * 121 = 60 + 60 + 1 =  3,1 => 50 60 11 = > 3,2로 더 우세
     * 그럼 130까지만 검증하면 가능한가? 
     * 181 = 60 60 60 1 => {4,1} => 50 60 60 11 => {4,2}로 더 우세
     * 160 = 60 60 40 = {3,0} => 50 50 60 => {3,2} 로 더 우세
     * 220 = 60 60 60 40 = {4,0} => 50 50 60 60 => {4,2}로 더 우세
     * 그러면 
     * 241 = 60 60 60 60 1 =>  50 60 60 60 11  => {5,2}; 50 60 60 11 과 동일
     * ...이하 동일한 패턴
     * 60 50 공배수 300 기준까지
     * 307 =  57+50+50+50+50+50  싱글/불 외 60을 넘지 않으며 마지막 3배수
     * 310 = 60+50+50+50+50+50 => 기존 -60 패턴을 유지함
     * 대충 400기준 
     */
}