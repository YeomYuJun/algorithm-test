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
        
        // str = "10000000 3";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int K = Integer.parseInt(args[0].split(" ")[1]);

        if(K>=N){ //조기 종료 조건
            System.out.println(0);
            return;
        }
        int[] tPowList = new int[25];
        tPowList[0] = 0;
        tPowList[1] = 2;
        for(int i=2; i<=24; i++){
            tPowList[i] = tPowList[i-1]*2;
        }

        int val = N;
        for(int i=1; i<=K; i++){ // k번 뺄 수 있음. 어차피 2의 거듭제곱 최대로 빼고, 나머지에서 다시임
            for(int pow = 24; pow>0; pow--){
                if(i<K){ //나머지가 있음
                    if(tPowList[pow] <= val){
                        val -= tPowList[pow];
                        break;
                    }
                }else{ //나머지가 없음.
                    if(tPowList[pow] <= val){
                        val -= tPowList[pow];
                        if(val>0){
                            val -= tPowList[pow];
                        }
                        break;
                    }
                }
            }
            
            // System.out.println(i+"번째 남은 수 : " + val);
            if(val == 0){ //종결 조건 
                break;
            }
            if(val >0 && val<=K-i && K != 1){ //나머지 병으로 나눠짐
                val = 0;
                break;   
            }
        }
        
        int ans = -val;
        System.out.println(ans);
        
    }
    /**
     * 물병 N to K개 2 2 2 2 2 2 1   1  1 1 
     *               4   4   4    2     2
     * 2^n 꼴이 보이는데
     * 
     * 10000000 - 2^24 < 0 
     * 최대 2^23
     * 1000000 5
        1
        1000000-2^19 
        = 475712    

        2
        475712-2^18
        = 213568

        3
        213568-2^17
        = 82496

        4.
        82496-2^16
        = 16960

        5
        16960-2^14
        = 576

        5
        16960-2^15
        = 15808 
     */

}