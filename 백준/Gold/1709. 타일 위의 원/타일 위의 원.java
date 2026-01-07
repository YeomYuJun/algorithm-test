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
        
        // str = "144444444";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }



    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        long half = N/2; //최대 7500만

        int quarter = 0;
        for(long x=1; x<=N; x++){ //딱 접하는 순간 필요?흠
            //y^2 =  ( N/2 + x ) * ( N/2 - x );
            long yPower = (half+x)*(half-x);
            long y = (long)Math.sqrt(yPower);
            if(yPower != y*y){
                quarter++;
            }
        }
        //45개인데
        System.out.println(4*quarter);
    }
    /**
     *  N(2 ≤ N ≤ 150,000,000)이 주어진다.
     * 1억 5천
     *  1cm, N*N 의 타일들 => 
     * 큰 정사각형에 접하는게 외접?내접? 이라고 치고
     * 정사각형의 방정식이  {
     *                      y=0, 0<=X<=N;
     *                      y=N, 0<=X<=N;
     *                      x=0, 0<=y<=N;
     *                      x=N, 0<=y<=N;  
     *                    } x축과 y축의 만나는 변 하나씩 있다고 치고 
     * 아니다 -N/2 ~ N/2 ,로 하고 원점을 (0,0)으로 해서 1사분면 *4 하면 될듯
     * 원의 둘레가 지나는 타일 수? 
     * 원의 중심점은 (N/2, N/2), R=N/2
     *  R^2 => N^2
     * (x-N/2)^2 + (y-N/2)^2 = (N/2)^2
     * 10^7 * 7.5 의 제곱은 좀...long이 10^18승까지니까
     */

}
