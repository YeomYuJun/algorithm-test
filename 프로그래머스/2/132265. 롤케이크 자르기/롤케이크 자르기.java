import java.util.*;
class Solution {
    public int solution(int[] topping) {
        int answer = 0;

        Map<Integer,Integer> total = new HashMap<>();
        Map<Integer,Integer> left = new HashMap<>();
        Map<Integer,Integer> right = new HashMap<>();

        for(int n : topping){
            total.put(n, total.getOrDefault(n, 0)+1);
        }
        right.putAll(total);

        for(int i=0; i<topping.length; i++){
            int num = topping[i];
            int lfNum = left.getOrDefault(num, 0);
            int ttNum = total.getOrDefault(num, 0);

            if(lfNum+1 == ttNum){ //전체를 왼쪽에 넣음
                right.remove(num);
            }
            left.put(num, left.getOrDefault(num, 0)+1);
            
            if(left.size() == right.size()){
                answer++;
            }
        }
        return answer;
    }
    /**
     * Set으로 일단 전체 수? 
     * 1,2,3 이란 것만 있어도
     * 12:23, 13:13, 등등  어려 경우 가능
     * 
     * 조기 조건이 있나?
     * 일단 left, right의 Map<수,수량> 으로 파악하면
     * 배열 순회하면서 left에 수가 수량과 같아지면? 다 소진 right에서 제거
     * 
     */
}