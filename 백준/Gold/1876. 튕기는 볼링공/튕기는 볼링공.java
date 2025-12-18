import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

        // str = "5\r\n" + //
        //                 "18.00 80\r\n" + //
        //                 "16.00 10\r\n" + //
        //                 "17.00 10\r\n" + //
        //                 "16.00 45\r\n" + //
        //                 "16.39 16"; 

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        final double H = 85.0; 
        
        for(int i = 1; i <= N; i++){
            String[] row = args[i].split(" ");
            double T = Double.parseDouble(row[0]) * 100;
            int D = Integer.parseInt(row[1]);
            double theta = Math.toRadians(D);
            
            double a = Math.tan(theta);
            
            // 충돌 범위 계산
            double collisionRange = 16.0 / Math.sin(theta);
            double left = T - collisionRange;
            double right = T + collisionRange;
            
            // x축에서 중심선을 지나는 주기
            double step = H / a;
            
            double dist = 0;
            boolean isHit = false;
            
            while(dist < right){
                if(left < dist && dist < right){
                    isHit = true;
                    break;
                }
                dist += step;
            }
            
            System.out.println(isHit ? "yes" : "no");
        }
    }
    /**
     *     
     * 폭 105 레인,  지름 12인 핀, 지름 20인 공
     *   
     *    (0, 52.5) = 중심선 + 볼링공 포지션
     *    (0,0)을 중심선, 볼링공으로 잡고 , 52.5높이를 천장으로 치고.
     *    
     *    볼링공 범위 =
     *    (x+-10, y+-10)
     *    도착 범위 = 
     *    (T*100, 52.5+-6)
     *   
     * 
     */
}



    
