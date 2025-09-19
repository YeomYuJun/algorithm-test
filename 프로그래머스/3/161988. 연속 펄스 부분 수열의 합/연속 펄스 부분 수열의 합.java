class Solution {
    public long solution(int[] sequence) {
        long answer = 0;
        int[] pulseBlock = {1,-1};
        long maxHead = Long.MIN_VALUE, maxTail = Long.MIN_VALUE;
        long headSum = 0, tailSum = 0;

        for(int i=0; i<sequence.length; i++){
            long head = sequence[i] * pulseBlock[i%2];
            long tail = sequence[i] * pulseBlock[(i+1)%2];

            headSum = Math.max(head, headSum + head);
            tailSum = Math.max(tail, tailSum + tail);
            
            maxHead = Math.max(maxHead, headSum);
            maxTail = Math.max(maxTail, tailSum);
        }
        answer = Math.max(maxHead, maxTail);
        
        return answer;
    }
    /**
     * 연속 펄
     * 최대 50만이면 효율성 필수일 듯. 배열의 이진탐색?
     * 절댓값으로 가장 높은 숫자를 찾아가야하는가..? => 그것을 포함하는 수가 최대값인지 증명가능한가?
     * 1~50만 길이... pulse 2가지, 100만 길이로 바꾸고 이진탐색하면 2^20, 20번이면 찾음 > 증명 불가능할 듯
     * 
     * 다음과의 갭을 구하는 계산? X
     * 100,100, -1,-1, 1000, -1,-1, 100, 100 과 같이 큰 숫자가 중간??
     * 10만, -1, -1 ... , 10만 과같이 양 끝이 큰 숫자라면?
     * 
     * 양1, 음1, ..., 음n, 양2  = > 양1 + 음1~n까지가 양수라면  양1 ~ 양2를 감싸는 것이 맞음.
     * 즉 양수와 양수 사이의 음수가 건너갈 만큼 가치가 없다면? 버리고 양2부터 시작하면 됨.
     * 
		long newsum = headSum + head;

		if(newsum >= 0){
			headSum += head;
		}
		if(newsum < 0){
			headSum = 0;
		}
		모두 음수일 경우..? 0초기화 하면 안되네 
            
     */
}