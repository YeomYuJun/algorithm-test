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
        
        // str = "40.35 45.90 386.52 318.15 162.81 260.36";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        String[] row = args[0].split(" ");

        double x1 = Double.parseDouble(row[0]); 
        double y1 = Double.parseDouble(row[1]); 
        double r1 = Double.parseDouble(row[2]);
        double x2 = Double.parseDouble(row[3]);
        double y2 = Double.parseDouble(row[4]);
        double r2 = Double.parseDouble(row[5]);

        double d = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        
        // 만나지 않는 경우
        if (d >= r1 + r2) {
            System.out.println("0.000");
            return;
        }
        // 포함되는 경우
        if (d <= Math.abs(r1 - r2)) {
            double minR = Math.min(r1, r2);
            System.out.printf("%.3f\n", Math.PI * minR * minR);
            return;
        }

        // 교차하는 경우
        // h 계산을 위한 d1 (중심1에서 교선까지의 거리)
        double d1 = (r1 * r1 - r2 * r2 + d * d) / (2 * d);
        double d2 = d - d1;
        double h = Math.sqrt(r1 * r1 - d1 * d1); // 교선의 절반 길이

        // 각 원에서의 부채꼴 중심각 (theta)
        double theta1 = 2 * Math.acos(d1 / r1);
        double theta2 = 2 * Math.acos(d2 / r2);

        // 부채꼴 넓이
        double SA = 0.5 * r1 * r1 * theta1;
        double SB = 0.5 * r2 * r2 * theta2;

        // 삼각형 넓이 (중심에서 교선까지의 거리 * 교선 높이 h)
        // d1이나 d2가 음수가 되면 자동으로 넓이가 가감되어 둔각 처리가 됨
        double tri1 = d1 * h;
        double tri2 = d2 * h;

        // 최종 넓이 = (부채꼴A - 삼각형A) + (부채꼴B - 삼각형B)
        double result = SA - tri1 + SB - tri2;

        System.out.printf("%.3f\n", result);
    }
    /**
     * thetaA각 넓이 + thetaB각넓이 - (AB * h) 가 교차하는 영역의 값인데
     * 각a를 구하려면 일단 A,B원의 교점을 찾아야함
     * AB선분의 길이
     * AB선분과 교점까지의 수직한 선의 길이 = h가 될 거고
     */
}