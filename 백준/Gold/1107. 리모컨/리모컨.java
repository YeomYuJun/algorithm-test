import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
            
        // ///////////////////////////////////////////
        
        // str = "99933\r\n" + //
        //                 "2\r\n" + //
        //                 "3 9";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);

        if(N==100){ //조기 종료 조건
            System.out.println(0);
            return;
        }
        
        boolean[] isAble = new boolean[10];
        Arrays.fill(isAble, true);

        if(M>0){
            String[] row = args[2].split(" ");
            int[] disable = Arrays.stream(row).mapToInt(Integer::parseInt).toArray();
            for(int dis : disable){
                isAble[dis] = false;
            }
        }

        //100에서 +- 이동
        int defaultGap = Math.abs(N-100);
        int minClick = defaultGap;
        
        // 0부터 999999까지 완전탐색 50만정도
        for(int channel = 0; channel <= 999999; channel++){
            if(canMake(channel, isAble)){
                int digitCount = String.valueOf(channel).length();
                int moveCount = Math.abs(N - channel);
                int totalClick = digitCount + moveCount;
                minClick = Math.min(minClick, totalClick);
            }
        }
        
        System.out.println(minClick);
    }

    public static boolean canMake(int channel, boolean[] isAble){
        if(channel == 0){
            return isAble[0];
        }
        
        while(channel > 0){
            int digit = channel % 10;
            if(!isAble[digit]){
                return false;
            }
            channel /= 10;
        }
        return true;
    }

    /**
     * N (0 ≤ N ≤ 500,000)
     * 이면 각 자릿수에서 가장 가까운 번호로 가는 경우
     * n=123456  , 1234678을 누를 수 없다면? 99,999를 누르고 가겠지
     * 즉, 각 자리별 가장 가까운 숫자를 고른다 or 안고른다의 선택지가 있음 (==못고름)
     * 조합을 만드는데, 가장 가까운 위 아래가 되는 2케이스만 나두고 버리면? 
     * 
     */
}