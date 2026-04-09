import java.util.*;
class Solution {
    public int solution(String message, int[][] spoiler_ranges) {
        int answer = 0;

        String[] words = message.split(" ");
        int wCount = words.length;
        //1 ≤ message의 길이 ≤ 20,000
    
        //숫자 구간을 어떻게 인식할것인가. 5,5같은 경우
        //0~ 2만까지의 구간이 존재. 스포일러 구간 인식 방법.
        //1. 구간 자연수를 전부 Set으로 add시켜서 contain 체크.
        Set<Integer> spo_range = new HashSet<>();
        for(int[] spo : spoiler_ranges){
            for(int i=spo[0]; i<=spo[1]; i++){
                spo_range.add(i);
            }
        }
        
        Map<String,Integer> countMap = new HashMap<>(); //단어 등장횟수.
        for(String str : words){
            countMap.put(str, countMap.getOrDefault(str,0)+1);
        }

        Map<String,Integer> spoCountMap = new HashMap<>(); //스포일러 단어 등장횟수.
        
        Set<String> spoList = new HashSet<>();

        int curIdx = 0; 
        for(int i=0; i<wCount; i++){
        //String에서 배열 idx와 길이로 분석 가능한가? 현재 전체 길이 idx를 잡아야하거나
        //char[] 로 잡고하면 단어 구분이 힘들지도
            String w = words[i];
            int len = w.length();


            int sl = curIdx; //
            int el = curIdx+len-1;

            boolean isSpo = false;
            for(int j=sl; j<=el; j++){
                if(spo_range.contains(j)){
                    isSpo = true; 
                    spoCountMap.put(w, spoCountMap.getOrDefault(w,0)+1);
                    // 미리 넣어놔야함. 아닌가?  뒤에서 만나나?  전체길이가 맞는 순간에 한번 들어가니까..
                    break;
                }
            }
            //sl~el = 현재 단어의 시작 위치. 종료 위치.
            //길이 갱신 
            curIdx+=len;
            curIdx++;


            //현재 단어가 스포단어임.
            //1. countMap에서 1임 => 완전 유니크 => O
            //2. countMap에서 2 이상임 => spoCountMap 으로 확인 일임.
            
            //2.1 spoCountMap에서 1임 => O
            //2.2 spoCountMap에서 2이상임 => 추가된 중요 단어인지 체크
            
            if(!isSpo) continue;
            if(countMap.get(w) == 1){
                spoList.add(w);
            }else{
                //생각해보니까 set이니까 countMap이랑 spoCount랑 일치하는지만 보면 되겠네
                if(countMap.get(w) == spoCountMap.get(w)){
                    spoList.add(w);
                }
            }
        }
        
        
        answer = spoList.size();
        return answer;
    }
}