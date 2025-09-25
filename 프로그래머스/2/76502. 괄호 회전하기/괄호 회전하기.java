import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
class Solution {
    public int solution(String s) {
        int answer = 0;
        Map<Integer, Integer> pair = new HashMap<>();
        pair.put(40, 41);
        pair.put(123, 125);
        pair.put(91, 93);

        Stack<Integer> stack = new Stack<>();
        int len = s.length(); 
        
        Loop1: 
        for(int i=0; i<len; i++){
            //시작지점 i
            stack.clear();
            Loop2: 
            for(int j=i; j<len+i; j++){
                int index = j%len; 
                //System.out.println("from: "+i + " to: " + j + " , charAt: " + index);
                int val = (byte)s.charAt(index);
                
                //Stack에 담기
                if(stack.isEmpty() && (val == 40 || val == 123 || val == 91)){ //비어있고 여는 괄호이면
                    stack.push(val);
                }else{ //있는 경우
                    if(val == 40 || val == 123 || val == 91){ //있지만 또 여는 괄호
                        stack.push(val);
                    }else{ //닫는 괄호일 경우
                        if(stack.isEmpty()){
                            break;
                        }
                        int lastest = stack.peek();
                        if(pair.get(lastest) == val){ // 페어가 맞는 닫히는 괄호면
                            stack.pop();
                        }else{ // 쌍이 안맞음 패스.? // 근데 안맞으면 여태까지 쌓인 인덱스만큼 패스해도 되지않나?
                            int skipSize = j;
                            i+=skipSize;
                            /*
                                [ [ [ [ ] } [ ]
                                0 1 2 3 4 5 6 7
                            i = 0;
                            j = 5인거지 i=6부터 시작해버리면 되잖아 
                            */
                            break;
                        }
                    }
                }

                if(j == len+i-1){
                    if(stack.isEmpty()) {
                        answer++;
                    }
                }
            }
        }
        return answer;
    }
        /*
     * ( : 40, ) : 41 / { : 123 , } : 125 / [ : 91 , ] : 93
     * 
     * 1. [0]이 (,{,[ 가 아니면 X,
     * 2. 정답 O 다음에는 무조건 X 
     * 
     */
}