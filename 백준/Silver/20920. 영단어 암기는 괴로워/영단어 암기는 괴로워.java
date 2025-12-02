import java.io.*;
import java.util.*;
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
        // str = "12 5\r\n" + //
        //                 "appearance\r\n" + //
        //                 "append\r\n" + //
        //                 "attendance\r\n" + //
        //                 "swim\r\n" + //
        //                 "swift\r\n" + //
        //                 "swift\r\n" + //
        //                 "swift\r\n" + //
        //                 "mouse\r\n" + //
        //                 "wallet\r\n" + //
        //                 "mouse\r\n" + //
        //                 "ice\r\n" + //
        //                 "age";
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException{
        int n = Integer.parseInt(args[0].split(" ")[0]); // size
        int m = Integer.parseInt(args[0].split(" ")[1]); // filter len
         
        Map<String, Integer> countMap = new HashMap<>();
        for(int i=1; i<=n; i++){
            String curStr = args[i];
            if(curStr.length() < m) continue;
            countMap.put(curStr, countMap.getOrDefault(curStr, 0)+1);
        }
        List<String> keySet = new ArrayList<>(countMap.keySet());
        //value로 내림차
        keySet.sort((o1,o2) -> {
            Integer o2s = countMap.get(o2);
            Integer o1s = countMap.get(o1);
            if(o2s > o1s){
                return 1;
            }else if(o2s == o1s){
                if(o2.length() > o1.length()){
                    return 1;
                }else if(o2.length() < o1.length()) {
                    return -1;
                }else{
                    return o1.compareTo(o2);
                }
            }else{
                return -1;
            }
        });
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(String key : keySet){
            bw.write(key+"\n");
        }
        bw.flush();
        bw.close();

    }
    /**
     * 정렬 커스텀?
     */

}