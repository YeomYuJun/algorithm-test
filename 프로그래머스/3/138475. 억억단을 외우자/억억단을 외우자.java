import java.util.*;

class Solution {
    public int[] solution(int e, int[] starts) {
        int[] answer = new int[starts.length];
        int[] divisorCount = new int[e + 1];

        for(int i = 1; i <= e; i++) {
            for(int j = i; j <= e; j += i) {
                divisorCount[j]++;
            }
        }
        
        int[] maxFromRight = new int[e + 1];
        int maxCount = 0, maxNum = 0;
        
        for(int i = e; i >= 1; i--) {
            if(divisorCount[i] >= maxCount) {
                maxCount = divisorCount[i];
                maxNum = i;
            }
            maxFromRight[i] = maxNum;
        }
        
        for(int i = 0; i < starts.length; i++) {
            answer[i] = maxFromRight[starts[i]];
        }
        
        return answer;
    }

    /**
     * `e를 먼저 정하여 알려주고 e 이하의 임의의 수 s를 여러 개 얘기합니다. 
     * 영우는 각 s에 대해서 s보다 크거나 같고 e 보다 작거나 같은 수 중에서 억억단에서 가장 많이 등장한 수를 답`
     * 
     * ???? 질문이 억억단에서  
     * starts[i] <= X{starts[i], starts[i]+1, ... ,e-1, e} <= e 를 만족하는
     * X 집합에서 약수가 가장 많은 것을 구하라는 뜻인 거 같은데
     * 
     * e까지의 약수를 전부 구햐놓고 
     * e부터 역순으로 큰 수부터 반복하면서 찾으면 될 듯? 큰 수가 약수가 일반적으로 더 많을테니
     * 같은 약수 count에서 작은 수도 역순이라 바로 될듯
     * 
     */
}