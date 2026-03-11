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
            
        // ////////////////////////////////////// /////
        
        // str = "";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int M = Integer.parseInt(args[0].split(" ")[1]);

        String[] tokens = args[1].split(" ");
        int[] pos = new int[N];
        for (int i = 0; i < N; i++) {
            pos[i] = Integer.parseInt(tokens[i]);
        }

        List<Integer> positive = new ArrayList<>();
        List<Integer> negative = new ArrayList<>();
        for (int p : pos) {
            if (p > 0) positive.add(p);
            else negative.add(-p); // 절댓값으로 저장
        }
        Collections.sort(positive, Collections.reverseOrder());
        Collections.sort(negative, Collections.reverseOrder());

        long total = 0;

        for (int i = 0; i < positive.size(); i += M) {
            total += positive.get(i) * 2;
        }

        for (int i = 0; i < negative.size(); i += M) {
            total += negative.get(i) * 2;
        }

        long maxDist = 0;
        if (!positive.isEmpty()) maxDist = Math.max(maxDist, positive.get(0));
        if (!negative.isEmpty()) maxDist = Math.max(maxDist, negative.get(0));

        total -= maxDist;

        System.out.println(total);
    }
}

