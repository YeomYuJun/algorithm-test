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

        // str = "6\r\n" + //
        //                 "10 20 10 30 20 50";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        String[] tokens = args[1].split(" ");
        
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }
        
        // LIS 길이를 구하는 로직
        List<Integer> lis = new ArrayList<>();
        
        for (int num : arr) {
            int pos = binarySearch(lis, num);
            
            if (pos == lis.size()) {
                lis.add(num);
            } else {
                lis.set(pos, num);
            }
        }
        
        System.out.println(lis.size());
    }
    public static int binarySearch(List<Integer> list, int target) {
        int left = 0;
        int right = list.size();
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }

    
    /**
     * LIS? 
     */
}