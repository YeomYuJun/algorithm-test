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
        
        // str = "6\r\n" + //
        //                 "1 48 1 1 48 1"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    static int maxLines = 0;
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        
        String[] row = args[1].split(" ");

        int[] numbers = new int[N];

        for(int i=0; i<N; i++){
            int val = Integer.parseInt(row[i]);
            if(val > 50){
                System.out.println(0);
                return;
            }
            numbers[i] = val;
        }

        permute(0, numbers);
        System.out.println(maxLines);
    }
    // 모든 순열을 생성하는 백트래킹 함수
    public static void permute(int depth, int[] numbers) {
        int N = numbers.length;
        if (depth == N) {
            calculateLines(numbers);
            return;
        }

        for (int i = depth; i < N; i++) {
            swap(depth, i,numbers);
            permute(depth + 1,numbers);
            swap(depth, i,numbers); // 원상복구
        }
    }

    public static void swap(int i, int j, int[] numbers) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    // 현재 순열에서 중심을 지나는 선의 개수 계산
    public static void calculateLines(int[] numbers) {
        int N = numbers.length;
        Set<Integer> cumulativeSums = new HashSet<>();
        int currentSum = 0;
        
        // 누적합을 Set에 저장 (차트의 경계 지점)
        for (int i = 0; i < N; i++) {
            currentSum += numbers[i];
            cumulativeSums.add(currentSum);
        }

        int lines = 0;
        if (cumulativeSums.contains(50)) {
            lines = 1;
        }

        for (int sum : cumulativeSums) {
            if (sum < 50 && cumulativeSums.contains(sum + 50)) {
                lines++;
            }
        }

        maxLines = Math.max(maxLines, lines);
    }

}