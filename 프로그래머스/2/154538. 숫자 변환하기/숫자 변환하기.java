import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
class Solution {
    public int solution(int x, int y, int n) {
        int answer = 0;

        Queue<int[]> q = new LinkedList<>();

        int[] first = new int[]{y,0};

        q.offer(first);
        Set<Integer> set = new HashSet<>();
        while (!q.isEmpty()) {
            int[] num = q.poll();
            int val = num[0];
            int calCnt = num[1];
            if(val == x){
                return calCnt;
            }

            int minus = nMinus(val, n);
            int d2 = divide2(val);
            int d3 = divide3(val);
            if(minus>=x && set.add(minus)){
                q.offer(new int[]{minus,calCnt+1});
            }
            if(val%2==0 && set.add(d2)){
                q.offer(new int[]{d2,calCnt+1});
            }
            if(val%3==0 && set.add(d3)){
                q.offer(new int[]{d3,calCnt+1});
            }
        }
        return -1;
    }

    public int nMinus(int y, int n){
        return y-n;
    }  
    public int divide2(int y){
        return y/2;
    }
    public int divide3(int y){
        return y/3;
    }


    /**
     * 가능한 방법
     * nSum
     * multiply2
     * multiply3
     * 앞의 선택이 3갈래. BFS라고 봐야하나? DP?
     * 근데 수가 너무 큼 
     * 나머지가 0이고, 2,3의 배수. 2,3,4,6,8,9... 이라면? 바로 답을 찾을 수 있지않을까. 
     * divide2, divide3로 
     */
}