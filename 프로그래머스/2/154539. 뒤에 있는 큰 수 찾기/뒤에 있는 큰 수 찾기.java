import java.util.Stack;
class Solution {
    public int[] solution(int[] numbers) {
        int[] answer = new int[numbers.length];
        Stack<Integer> stack = new Stack<>();
        for(int i=numbers.length-1; i>=0; i--){
            while (!stack.isEmpty() && numbers[stack.peek()] <= numbers[i]) {
                stack.pop();
            }
            if(stack.isEmpty()){
                answer[i] = -1;
            }else{
                answer[i] = numbers[stack.peek()];
            }
            stack.push(i);
        }
        return answer;
    }
    /**
     * 4 ≤ numbers의 길이 ≤ 1,000,000
        1 ≤ numbers[i] ≤ 1,000,000
        뒷 큰수..
        n+n-1+n-2+...+1

        초기 탐색 이후 i보다 큰 수를 찾은 j 인덱스를 Map으로 저장.
        아니다 한번 순회하면서 바로 map에다 넣고 찾아서 꺼내면?
        근데 같은 숫자가 여럿, 인덱스의 중간중간 있을 경우에는? 
        근데 i보다 큰 j가 뭐가 있는지 모르니까 Set? TreeSet이 좋으려나 
        근데 가장 가까운이 아니니까 뒤에서 접근하면? Stack으로?
     */
}