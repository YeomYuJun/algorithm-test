import java.util.*;
class Solution {
    public int solution(int[] elements) {
        int answer = 0;
        Set<Integer> set = new HashSet<>();
        for(int i=0; i<elements.length; i++){
            for(int j=1; j<=elements.length;j++){
                int startIndex = i;
                int endIndex = (i+j)%(elements.length+1); //4+1=0 , 4+2=1 이므로 %5

                if(startIndex>endIndex){ //2개구간
                    int zero = 0;
                    int to = endIndex;
                    
                    int from = startIndex;
                    int max = elements.length;

                    int[] copyarr1 = Arrays.copyOfRange(elements, zero, to);
                    int[] copyarr2 = Arrays.copyOfRange(elements, from, max);
                    int rs = Arrays.stream(copyarr1).sum() + Arrays.stream(copyarr2).sum();    
                    set.add(rs);
                }else{ //정상 범위
                    int[] copyarr = Arrays.copyOfRange(elements, startIndex, endIndex);
                    int rs = Arrays.stream(copyarr).sum();
                    set.add(rs);
                }
                
            }
        }
        answer = set.size();
        return answer;
    }
    /**
     * 연속이니까  0,1,2,3,4, / 01,12,23,34,40
     * i = 시작 위치
     * j = 길이 
     */
}