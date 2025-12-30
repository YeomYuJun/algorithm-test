import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
        
        // str = "5\r\n" + //
        //                 "-100000 100000\r\n" + //
        //                 "-100000 -100000\r\n" + //
        //                 "100000 -100000\r\n" + //
        //                 "100000 99999\r\n" + //
        //                 "99999 100000";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        double sum = 0.0;

        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            long x = Long.parseLong(row[0]);
            long y = Long.parseLong(row[1]);
            
            String[] nextRow = new String[2];
            if(i==N){
                nextRow = args[1].split(" ");
            }else{
                nextRow = args[i+1].split(" ");
            }
            long nx = Long.parseLong(nextRow[0]);
            long ny = Long.parseLong(nextRow[1]);

            sum += (x*ny) - (nx*y);
        }
        double answerDb = Math.abs(sum)/2.0;
        String answer = String.format("%.1f", answerDb);
        System.out.println(answer);
    }

    /**
     *  소수점 둘째자리에서 반올림하여 첫째 자리 까지 출력.
     * 
     * 다각형 넓이 구하는 공식
     * '다각형일 이루는 순서대로' 니까 순서를 건들면 안됨.
     * 반시계?라면 괜찮은데, 음수일 수 있으니 일단 절대값쓰고
     * 사이즈가 큰 경우 E로 나옴..format
     */
}
