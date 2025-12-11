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

        // str = "5\r\n" + //
        //                 "-1 1\r\n" + //
        //                 "-1 0\r\n" + //
        //                 "0 0\r\n" + //
        //                 "1 0\r\n" + //
        //                 "1 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // N

        List<int[]> points = new ArrayList<>();
        
        int count = 0;
        for(int i=1; i<=N; i++){
            int x = Integer.parseInt(args[i].split(" ")[0]);
            int y = Integer.parseInt(args[i].split(" ")[1]);
            points.add(new int[]{x,y});
        }
        
        for(int a=0; a<N-2; a++){
            //첫번째 점
            int[] f = points.get(a);
            for(int b=a+1; b<N-1; b++){
                //두번째 점
                int[] s = points.get(b);
                for(int c=b+1; c<N; c++){
                    //세번째 점 
                    int[] t = points.get(c);

                    if(is90d(f, s, t)){
                        count++;
                    } else{
                    }
                }

            }
        }
        System.out.println(count);
    }
    public static boolean is90d(int[] a, int[] b, int[] c){ // 대각선도 고려해야하네 
        // AC AB BC 선을 구헤야함. 
        // double ab = Math.pow(Math.abs(a[0] - b[0]), 2) + Math.pow(Math.abs(a[1] - b[1]), 2) ; 
        // double ac = Math.pow(Math.abs(a[0] - c[0]), 2) + Math.pow(Math.abs(a[1] - c[1]), 2) ; 
        // double bc = Math.pow(Math.abs(b[0] - c[0]), 2) + Math.pow(Math.abs(b[1] - c[1]), 2) ; 
        long ab = (long)(a[0] - b[0]) * (a[0] - b[0]) + (long)(a[1] - b[1]) * (a[1] - b[1]); 
        long ac = (long)(a[0] - c[0]) * (a[0] - c[0]) + (long)(a[1] - c[1]) * (a[1] - c[1]); 
        long bc = (long)(b[0] - c[0]) * (b[0] - c[0]) + (long)(b[1] - c[1]) * (b[1] - c[1]); 
        
        if(ab > ac){
            if(ab > bc){
                return ab == ac+bc;
            }else{
                return bc == ac+ab;
            }
        }else{
            if(ac > bc){
                return ac == ab+bc;
            }else{
                return bc == ab+ac;
            }
        }
    }

    /**
     * 90도 특징 = x or y의 gap이 0이다
     * 
     * 좌표값은 절댓값이 1,000,000,000
     * 
     */


}