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

        // str = "1\r\n" + //
        //                 "0 0 3 2 0 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 


        for(int i=1; i<=N; i++){
            String[] xyr = args[i].split(" ");

            int x1 = Integer.parseInt(xyr[0]);
            int y1 = Integer.parseInt(xyr[1]);
            int r1 = Integer.parseInt(xyr[2]);

            int x2 = Integer.parseInt(xyr[3]);
            int y2 = Integer.parseInt(xyr[4]);
            int r2 = Integer.parseInt(xyr[5]);
            if(x1 == x2 && y1 == y2){
                if(r1==r2) { //무한대
                    System.out.println(-1);
                }else{ // 없음
                    System.out.println(0);
                }
                continue;
            }
            double joebackGap = Math.sqrt(Math.pow(Math.abs(x2-x1),2) + Math.pow(Math.abs(y2-y1),2));
            double len = (double)(r1+r2);
            double circleGap = (double)(Math.abs(r1-r2));
            if(joebackGap <= circleGap){ //큰 원안에 작은 원이 있음
                double min = Math.min(r1, r2) + joebackGap;
                double max = Math.max(r1, r2);
                if(min == max){ //작은 원 + 터렛 거리가 큰 원을 넘는지?
                    System.out.println(1);
                }else if(min > max ){
                    System.out.println(2);
                }else{ // 못넘음. 포함관계
                    System.out.println(0);
                }
            }else{
                if(len == joebackGap){
                    System.out.println(1);
                }else if(len >  joebackGap){
                    System.out.println(2);
                }else{
                    System.out.println(0);
                }
            }
        }
    }
    /**
     * 원의 교점 출력= (x1,y1) 과 (x2,y2)의 사이의 거리 = 
     * 
     */
}