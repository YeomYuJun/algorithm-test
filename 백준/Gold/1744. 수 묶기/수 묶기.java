import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        /////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "3\r\n" + //\
        //                 "-2\r\n" + //
        //                 "-1\r\n" + //
        //                 "-5";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        
        boolean zExist = false;
        int minVal = Integer.MAX_VALUE; // 0보다 크며 제일 작은 수 ( 0 다음 index )

        List<Integer> seq = new ArrayList<>();
        for(int i=1; i<=N; i++){
            int val = Integer.parseInt(args[i]);
            if(val == 0){
                zExist = true;
            }
            if(val > 0){
                minVal = Math.min(val, minVal); //최저값 갱신( 인덱스 찾기용 )
            }
            seq.add(val);
        }
        int sum = 0; // 합은 항상 2^31-1 

        Collections.sort(seq); //오름차순 정렬. 
        int zeroIndex = seq.indexOf(0);
        //음수만 있을 경우 없을 수 있음 => 양수 첫번째 index
        int minIndex = seq.indexOf(minVal); 

        // 두 쌍씩 양수 만들다가 1개가 남은 경우, 0이 있을 경우 곱하여 0을 만드는 게 좋을 듯
        int remainMinus = 0; 
        int lastIndex; // 음수 영역의 끝

        if(zeroIndex == -1 && minIndex == -1) { // 음수만 있음
            lastIndex = seq.size();
        } else if(zeroIndex == -1) { // 0 없고 양수 있음
            lastIndex = minIndex;
        } else { // 0 있음
            lastIndex = zeroIndex;
        }
        // 음수 영역 처리
        for(int i = 0; i < lastIndex; i += 2) {
            if(i == lastIndex - 1) { // 홀수개라 하나 남음
                remainMinus = seq.get(i);
                break;
            }
            sum += (seq.get(i) * seq.get(i+1));
        }

        // 0이 없을 때만 remainMinus 더하기
        if(zeroIndex == -1) {
            sum += remainMinus;
        }
        
        if(minIndex != -1){
            for(int j=seq.size()-1; j>=minIndex; j-=2){//역순 탐색
                if(j== minIndex){//마지막 덧셈
                    sum += seq.get(j); 
                    break;
                }
                sum += Math.max( (seq.get(j) + seq.get(j-1)) , (seq.get(j) * seq.get(j-1))); // 1이 껴있는 경우 덧셈이 이득
            }
        }
        System.out.println(sum);
    }
    /**
     * 1 <= N < 50
     * -1000 ~ 1000
     * 큰수끼리 곱, 음수끼리 곱, 0은 단일로 놔두기.
     * 
     * case 1: 그대로
     * case 2: 묶어서 곱하기
     *  case 2.1: 제일큰 수 An * An-1
     *  case 2.2: 제일 작은 수(음수) A0 * A1
     */

}