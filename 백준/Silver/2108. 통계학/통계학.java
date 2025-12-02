import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        // str = "5\r\n" + //
        //                 "-1\r\n" + //
        //                 "-2\r\n" + //
        //                 "-3\r\n" + //
        //                 "-1\r\n" + //
        //                 "-2";
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args){
        int n = Integer.parseInt(args[0]); // range
        long avg = 0;
        long mid = 0;
        long mt = 0;
        long range = 0;
        long sum = 0;
        List<Long> list = new ArrayList<>();
        Map<Long,Integer> countMap = new HashMap<>();
        for(int i=1; i<=n; i++){
            long l = Long.parseLong(args[i]);
            list.add(l);
            countMap.put(l, countMap.getOrDefault(l, 0)+1);
        }
        int maxcount = Integer.MIN_VALUE;
        List<Long> maxList = new ArrayList<>();
        for(Long ll : countMap.keySet()){
            int curCount = countMap.get(ll);
            if(maxcount < curCount){ //계산 전 더 큰 수 나오면 초기화
                maxList.clear();;
            }
            maxcount = Math.max(maxcount, curCount);
            if(maxcount == curCount){
                maxList.add(ll);
            }
        }

        Collections.sort(list);
        Collections.sort(maxList);
        
        sum = list.stream().mapToLong(Long::longValue).sum();
        //1.
        avg = (int)Math.round(((double)sum/(double)n));
        //2.
        mid = list.get((n/2));
        //3.
        if(maxList.size() > 1){ //2이상
            mt = maxList.get(1);
        }else{
            mt = maxList.get(0); //최소 1개
        }
        //4.
        range = list.get(n-1) - list.get(0);

        System.out.println(avg);
        System.out.println(mid);
        System.out.println(mt);
        System.out.println(range);
    }
    /**
     * -3999 ~ 3999 의 정수
     * 50만개 
     */

}

