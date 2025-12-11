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

        // str = "8\r\n" + //
        //                 "1 2\r\n" + //
        //                 "1 3\r\n" + //
        //                 "2 4\r\n" + //
        //                 "3 4\r\n" + //
        //                 "4 3\r\n" + //
        //                 "4 2\r\n" + //
        //                 "3 1\r\n" + //
        //                 "2 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // T
        List<int[]> points = new ArrayList<>();
        
        double maxSize = Double.MIN_VALUE;
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

                    double area = triangleSize(f, s, t);
                    maxSize = Math.max(maxSize, area);
                }

            }
        }
        System.out.println(maxSize);
    }
    public static double triangleSize(int[] a, int[] b, int[] c){
        double area = Math.abs(
            a[0] * (b[1] - c[1]) + 
            b[0] * (c[1] - a[1]) + 
            c[0] * (a[1] - b[1])
        ) / 2.0;
        return area;
    }

    /**
     *             .
     *                .
     *       |  . 
     *        -----------
     *  좌표는 10,000보다 작거나 같은 자연수이다.
     */


}