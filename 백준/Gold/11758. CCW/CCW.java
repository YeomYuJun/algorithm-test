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
        //                 "7 3\r\n" + //
        //                 "5 5";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        long px1 = Long.parseLong(args[0].split(" ")[0]);
        long py1 = Long.parseLong(args[0].split(" ")[1]);

        long px2 = Long.parseLong(args[1].split(" ")[0]);
        long py2 = Long.parseLong(args[1].split(" ")[1]);
        
        long px3 = Long.parseLong(args[2].split(" ")[0]);
        long py3 = Long.parseLong(args[2].split(" ")[1]);
 
        // CCW 공식: (x1*y2 + x2*y3 + x3*y1) - (y1*x2 + y2*x3 + y3*x1)

        long val = (px1 * py2 + px2 * py3 + px3 * py1) - (py1 * px2 + py2 * px3 + py3 * px1);

        if (val > 0) {
            System.out.println("1");  // 반시계
        } else if (val < 0) {
            System.out.println("-1"); // 시계
        } else {
            System.out.println("0");  // 일직선
        }
    }
    // P1(px1,py1) , P2(px2,py2) , P3(px3,py3) 3점이 있고  1 to 2 to 3이라는 선이
    //반시계 : 1, 시계 -1, 일직선 = 0;

}