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
        
        // str = "10\r\n" + //
        //                 "ABB\r\n" + //
        //                 "BB\r\n" + //
        //                 "BB\r\n" + //
        //                 "BB\r\n" + //
        //                 "BB\r\n" + //
        //                 "BB\r\n" + //
        //                 "BB\r\n" + //
        //                 "BB\r\n" + //
        //                 "BB\r\n" + //
        //                 "BB"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        List<String> notation = new ArrayList<>();
        Set<String> alphabet = new HashSet<>();

        Map<String,Integer> weightMap = new HashMap<>();

        for(int i=0; i<N; i++){
            String str = args[i+1];
            notation.add(str);

            String[] row = str.split("");
            
            int pos = 1;
            for(int j=row.length-1; j>=0; j--){
                String alpha = row[j]; 

                alphabet.add(alpha);
                
                int weight = (int)Math.pow(10, pos-1);
                weightMap.put(alpha, weightMap.getOrDefault(alpha, 0) + weight);
                
                pos++;
            }
        }

        List<String> keyList = alphabet.stream().collect(Collectors.toList());

        keyList.sort((o1, o2) -> {
            int o1Weight = weightMap.get(o1);
            int o2Weight = weightMap.get(o2);
            return o2Weight - o1Weight;
        });
        
        int start = 9;
        Map<String, Integer> valueMap = new HashMap<>();
        for(String key : keyList){
            valueMap.put(key, start--);
        }

        int sum = 0;
        for(String s : notation){
            String finalS = String.join("",
                Arrays.stream(s.split("")).map(t -> {
                    return String.valueOf(valueMap.get(t));
                }).collect(Collectors.toList())
                    .toArray(new String[s.length()])
            );
            sum += Integer.parseInt(finalS);
        }
        System.out.println(sum);
    }

    /**
     * 길이 desc,  알파벳의 숫자 desc 순으로 map에 정렬해서 사용하면..?
     * A : len: 5, count: 1 
     * G : len: 3, count: 1
     * C : len: 4,2 count: 2  
     * 흠
     * 아 1개가 n길이, 9개가 n-1길이라면 다르니까 
     * 길이에 따른 정렬을 가중치를 부여해서 알파벳 별 가중치 정렬?
     * 
     */


}