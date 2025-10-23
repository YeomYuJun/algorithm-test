class Solution {
    public long solution(int k, int d) {
        long answer = 0;
        long maxA = d/k; // k최대

        for(long a = 0; a <= maxA; a++) {
            // a*k 위치에서 가능한 최대 y좌표
            long maxB = (long)Math.sqrt(((long)d * d) - (a*k)*(a*k));
            answer += (maxB/k) + 1;
        }
        return answer;
    }
    /**
     * 점이 찍히는 곳 = k의 배수
     * (ak,bk)가 d보다 같거나 작아야하며, 1사분면만 취급함.
     * a^2 + b^2 <= d^2;
     * b <= {sqrt}(d^2-a^)
     * b/k= max b 
     */
}