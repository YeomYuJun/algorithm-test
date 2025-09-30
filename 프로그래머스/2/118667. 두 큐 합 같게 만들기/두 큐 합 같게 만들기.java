import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int answer = -2;
        long q1Sum = Arrays.stream(queue1).sum();
        long q2Sum = Arrays.stream(queue2).sum();
        if(q1Sum == q2Sum){
            return 0; //이미 같음
        }
        if((q1Sum+q2Sum)%2 != 0){ //같게 불가능 
            return answer;
        }
        
        Queue<Integer> pq1 = new LinkedList<>();
        Queue<Integer> pq2 = new LinkedList<>();
        pq1.addAll(Arrays.stream(queue1).boxed().collect(Collectors.toList()));
        pq2.addAll(Arrays.stream(queue2).boxed().collect(Collectors.toList()));
        int movecnt = 0;
        while(true){
            //6 14
            //6 1 13 8
            //long gap = Math.abs(q1Sum - q2Sum); //차이 절대값
            if(q1Sum > q2Sum){
                int num = pq1.poll();
                pq2.offer(num);

                q2Sum+=num;
                q1Sum-=num;
                movecnt++;
            }else{ 
                int num = pq2.poll();
                pq1.offer(num);

                q1Sum+=num;
                q2Sum-=num;
                movecnt++;
            }
            if(q1Sum == q2Sum){
                break;
            }
            if(movecnt> (queue1.length*3-3)){
                return -1;
            }
        }
        
        answer = movecnt;
        return answer;
    }
}