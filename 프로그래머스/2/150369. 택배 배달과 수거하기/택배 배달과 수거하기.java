class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        int delSum = 0;  // 남은 배달 총량
        int pickSum = 0; // 남은 수거 총량

        for(int i = n-1; i >= 0; i--) {
            delSum += deliveries[i];
            pickSum += pickups[i];

            // 현재 위치에서 처리해야 할 작업이 있으면
            while(delSum > 0 || pickSum > 0) {
                delSum -= cap;
                pickSum -= cap;
                answer += (i + 1) * 2; // 왕복 거리 추가
            }
        }

        return answer;

    }
    /**
     * 최선의 선택은? (cap = 저장용량, n= 배달집 수 )
     * => 가는 길에 최대한 배달, 오는 길에 최대한 수거
     * => 왜냐하면 가져간 것을 놓아야 수거할 수 있으니, 끝에 놓고 앞에 끝보다 앞에 가져오는 것은,
     * 수거'만을' 위해 이동할 필요가 없으니까
     * 다만 n에 배송완료, n-2에 배송해야함, 근데 n-1이 cap보다 수거할 양이 더 많다면?  즉 ... {3/1} , {0,6} , {2,3} 인데 cap이 3이어도
     * n-2에 1개 배달, n에 2개 배달, n에 3개 수거. 뭐 이런 식일듯
     * 
     * 1~k , k번째 집은 거리가 k이다
     * 일단 가면서 다 주고, cap만큼 수거 가능하다고 보면
     */
}