import java.util.*;
class Solution {
    public int solution(int[] order) {
        int answer = 0;
        int len = order.length;


        //할 수 있는 액션이
        //순서가 맞지 않아 보조 컨테이너(stack)에 넣는다, 
        //순서가 맞아서, 바로 담는다. answer++
        //순서가 맞지 않아 보조 컨테이너(stack)에서 peek 해보고 맞으면 꺼내고 담는다 answer++
        //그냥 유기, 버리는 경우가 있나?

        //1~len 까지 
        Queue<Integer> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();

        for(int i=1; i<=len; i++){
            queue.add(i);
        }


        boolean stackCheck = false;
        int idx = 0;
        while((!queue.isEmpty() || stackCheck == false) && idx < len){
            
            int need = order[idx];

            if(queue.peek() != null &&  queue.peek().intValue() == need){ //default 컨테이너 벨트와 순서가 맞음.
                queue.poll();
                answer++;
                idx++;
            }else if(queue.peek() != null &&  queue.peek().intValue() != need){
                if(!stack.isEmpty() && stack.peek() != null && stack.peek().intValue() == need){
                    stack.pop();
                    answer++;
                    idx++;
                    stackCheck = false;
                }else if(queue.peek() != null){ //기존 컨테이너와 순서도 안맞고, 스택 최상단도 안맞으니 q에서 s로 삽입
                    stack.add(queue.poll()); 
                }
            }else{ //queue 는 비었지만 스택이 있을 경우 
                if(!stack.isEmpty() && !stackCheck){
                    if(stack.peek().intValue() == need){
                        stack.pop();
                        answer++;
                        idx++;
                        stackCheck = false;
                    }else{
                        stackCheck = true;
                    }
                }
            }

        }
        return answer;
    }
}