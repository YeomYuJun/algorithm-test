class Solution {
    public int[] solution(int n, long left, long right) {
        //right-left < 10^5 = 10만이니까 int 가능 
        int[] answer = new int[(int)(right-left+1)];
        
        int index = 0;
        for(long i = left; i<=right; i++){ 
            long row = i/(long)n + 1; 
            long col = i%(long)n + 1; 
            //구간으로 나누면, 열 기준. 행보다 작은가? 큰가?
            if(col <= row){
                answer[index] = (int)row;
            }else if(col > row){
                answer[index] = (int)col;
            }
            index++;
        }
        return answer;
    }
    /*
     * n의 순서로 반복하는..
     * 새롭게 생성된 array 길이는 n^2  (0~n^2-1)
     * n = 7, left = 11,  right = 31 이라고 할 때
     * left 부터 right까지 1회를 계산식을 통해 해당 index를(사실상 2차원의 좌표를 찾는 느낌으로) 구하면 될 거 같은데
     * ex 11일 때 newarr[11] : 11/7=1, 11%7=4, (1+1)행, (4+1)열이라는 뜻이니까 
     * 행의 숫자까지 열이 동일 숫자..
     * 
     */
}