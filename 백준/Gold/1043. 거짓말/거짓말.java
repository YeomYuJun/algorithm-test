import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        ////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////
        
        // str = "1 1\r\n" + //
        //                 "0\r\n" + //
        //                 "1 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); //사람 수 
        int M = Integer.parseInt(args[0].split(" ")[1]); //파티 수
        
        String[] tstr = args[1].split(" ");
        int T = Integer.parseInt(tstr[0]); //진실을 아는 사람 수

        int[] state = new int[N+1]; //n번 사람의 상태 
        // 0: 들은 바 없음 , 1: 사실을 들음, 2: 과장을 들음 , 2는 필요없을듯
        
        Set<Integer> tSet = new HashSet<>();
        for(int i=0; i<T; i++){
            int truth = Integer.parseInt(tstr[i+1]);
            tSet.add(truth);
            state[truth] = 1;
        }

        if(T==0){//진실을 아무도 모르면, 매 파티에서 구라
            System.out.println(M);
            return;
        }

        List<List<Integer>> partyList = new ArrayList<>();
        for(int i=2; i<M+2; i++){//파티 
            String[] row = args[i].split(" ");

            List<Integer> partyPeople = new ArrayList<>();
            boolean hasTruth = false;

            for(int j=1; j<row.length; j++){
                int poeple = Integer.parseInt(row[j]);
                if(tSet.contains(poeple)){
                    hasTruth = true;
                }
                partyPeople.add(poeple);
            }

            if(hasTruth){
                tSet.addAll(partyPeople); //여기는 전부 진실
            }
            partyList.add(partyPeople);  
        }
        //partyList =  파티 참석 인원 list(party list)
        recursiveUpdateTruth(tSet, state, partyList);

        int answer = 0;
        for(List<Integer> party : partyList){
            boolean noTruth = true;
            for(int pp : party){
                if(tSet.contains(pp)){
                    noTruth = false;
                    continue;
                }
            }
            if(noTruth) answer++;
        }
        System.out.println(answer);

    }
    public static void recursiveUpdateTruth(Set<Integer> tSet, int[] state, List<List<Integer>> partyList){
        //해당 사항으로 더 이상 추가적 0 -> 1이 없을 때까지 반복
        long startCnt = Arrays.stream(state).filter(t -> {return t == 1;}).count();
        for(int i=0; i<partyList.size(); i++){
            List<Integer> poeple = partyList.get(i);
            boolean tUpdate = false;
            for(int t : tSet){
                if(poeple.contains(t)){
                    tUpdate = true;
                    poeple.forEach(p -> { //state 업데이트로, 전후 비교
                        if(state[p] == 0){
                            state[p] = 1;
                        }
                    });
                    
                }
            }
            if(tUpdate){
                tSet.addAll(poeple); //한 명이라도 있으면 해당 파티 피플 다 추가
            }
        }
        long endCnt = Arrays.stream(state).filter(t -> {return t == 1;}).count();
        if(endCnt-startCnt == 0){
            return;
        }else{
            recursiveUpdateTruth(tSet, state, partyList);
        }
        
    }
    /**
     * 최댓값 => ?
     * 
     * 대전제
     * 1. 기존에 진실을 아는 사람이 있을 경우 진실만 말한다.
     * 2. 1번에 의해 진실을 알게된 사람이 있을 경우 진실을 말하게 된다.
     * 3. 1~2번이 아닐 경우 과장을 이야기한다 => 최적? X
     *  3.1 거짓을 들었다가, 진실을 듣게 되는 경우도 거짓임을 알게 되는가? ㅇㅇ
     * 
     * 이게 그리디한가? 입력 순서가 시간의 순서인가? 예제4 보니까 시간 순은 아니고 최대의 경우인듯 
     * 중요한건 상태 업데이트 버블링 => 거품 다 꺼지면, 남은 파티가 정답임.
     */
}
 
