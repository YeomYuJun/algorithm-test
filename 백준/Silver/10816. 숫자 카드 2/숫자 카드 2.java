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

        // str = "10\r\n" + //
        //                 "6 3 2 10 10 10 -10 -10 7 3\r\n" + //
        //                 "8\r\n" + //
        //                 "10 9 -5 2 3 4 5 -10";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        String[] row1 = args[1].split(" ");
        int M = Integer.parseInt(args[2]);
        String[] row2 = args[3].split(" "); 
        
        // 상근이가 가진 카드의 개수를 카운팅
        Map<Integer, Integer> cardCount = new HashMap<>();
        for (String card : row1) {
            int num = Integer.parseInt(card);
            cardCount.put(num, cardCount.getOrDefault(num, 0) + 1);
        }
        
        // 결과 출력
        StringBuilder sb = new StringBuilder();
        for (String query : row2) {
            int num = Integer.parseInt(query);
            sb.append(cardCount.getOrDefault(num, 0)).append(" ");
        }
        
        System.out.println(sb.toString().trim());
    }
    
}