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
        
        
        // str = "4\r\n" + //
        //                 "1\r\n" + //
        //                 "2"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        int N  = Integer.parseInt(args[0]);
        int M  = Integer.parseInt(args[1]);

        if(N==1 || M>=N-1){
            System.out.println(1);
            return;
        }
        if(N==2){
            if(M==0){
                System.out.println(2);
                return;
            }else{
                System.out.println(1);
                return;
            }
        }

        int[] seat = new int[N+1];
        seat[0] = 0;
        seat[1] = 1; // 1
        seat[2] = 2; // 12 21
        // seat[3] = 3; // 123 132 213  // 2가 뒤에있는 경우 1개. 2+1
        // seat[4] = 5; // 3이 제자리에 있는 경우에서 변형 가능 = 5 , 2개 3+2
        for(int i=3; i<=N; i++){
            seat[i] = seat[i-1] + seat[i-2];
        }
        
        int answer = 1; 
        int pV = 0;
        for(int i=2; i<M+2; i++){
            int vip = Integer.parseInt(args[i]);
            int s = vip - pV - 1; //이전 vip, 고정위치는 
            pV = vip;
            if(s==0){
                continue;
            }

            answer *= seat[s];
            
        }
        
        int s = N - pV;
        if(s!=0){
            answer *= seat[s];    
        }
        
        System.out.println(answer);
        return;
    }
    /**
     * vip까지의 길이를 좌석 변동 가능 범위로 보고, (vip = 벽)
     * 경우1 | 경우2 | 경우3 ... 으로 곱연산하면될듯
     */
}