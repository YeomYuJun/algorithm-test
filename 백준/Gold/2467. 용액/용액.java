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
        
        // str = "7\r\n" + //
        //                 "-1000000000 -999999999 -999999998 -999999997 999999996 5 4";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        String[] row = args[1].split(" ");

        List<Long> liquidList = new ArrayList<>();
        for(int i=0; i<N; i++){
            long liquid = Long.parseLong(row[i]);
            liquidList.add(liquid);
        }

        Collections.sort(liquidList);
        
        int left = 0;
        int right = N-1;

        long[] liquidPair = new long[2];
        long closedZero = Long.MAX_VALUE;
        while(left<right){ //
            long leftVal = liquidList.get(left);
            long rightVal = liquidList.get(right);
            long gap = rightVal+leftVal;
            long absGap = Math.abs(gap);
            
            if(closedZero > absGap){
                liquidPair[0] = leftVal;
                liquidPair[1] = rightVal;
                closedZero = absGap;
            }
            if(gap > 0){
                right--;
            }else{
                left++;
            }
        }
        System.out.println(liquidPair[0] + " " + liquidPair[1]);
    }
}
