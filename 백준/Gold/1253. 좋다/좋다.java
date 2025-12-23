import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
        //                 "-1 0 1 -1 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        String[] row = args[1].split(" ");
        //3 - 2 = 1

        List<Integer> numList = new ArrayList<>();
        for(int i=0; i<N; i++){
            numList.add(Integer.parseInt(row[i]));
        }

        Collections.sort(numList);
        int cnt = 0;
        for(int i=0; i<N; i++){
            int target = numList.get(i);
            int left = 0;
            int right = N-1;
            
            while(left < right){
                // target 자신은 제외
                if(left == i){
                    left++;
                    continue;
                }
                if(right == i){
                    right--;
                    continue;
                }
                
                int sum = numList.get(left) + numList.get(right);
                if(sum == target){
                    cnt++;
                    break;  // 찾으면 다음 수로
                } else if(sum < target){
                    left++;
                } else {
                    right--;
                }
            }
        }
        System.out.println(cnt);

    }
    /**
     * Set으로 하면 끝 아닌가
     * 10억+10억-1 = int , 2000^2 = 4,000,000연산
     * 아 다른 수 두 개의 합이라는 다른 조건  = "1 0 -1 1 -1" 은 5이고, "1 0 -1";은 1이어야하네 
     * 메모리 초과 => 투포인터로 첫 순회?
     */
}