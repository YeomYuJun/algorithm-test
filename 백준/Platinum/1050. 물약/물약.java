import java.io.*;
import java.util.*;

public class Main {
    
    final static long MOD = 1_000_000_007L;
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
        
        // str = "1 9\r\n" + //
        //                 "A 1\r\n" + //
        //                 "B=9A\r\n" + //
        //                 "C=9B\r\n" + //
        //                 "D=9C\r\n" + //
        //                 "E=9D\r\n" + //
        //                 "F=9E\r\n" + //
        //                 "G=9F\r\n" + //
        //                 "H=9G\r\n" + //
        //                 "I=9H\r\n" + //
        //                 "LOVE=9I";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // 재료의 개수ㅡ
        int M = Integer.parseInt(args[0].split(" ")[1]); // 만들 수 있는 물약의 공식 수

        Map<String,Integer> sourceMap = new HashMap<>(); //재료 : 구매비용
        
        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");

            String source = row[0];
            int price = Integer.parseInt(row[1]);
            sourceMap.put(source, price);
        }
        List<String[]> recipe = new ArrayList<>();
        for(int i=1+N; i<=N+M; i++){
            String[] row = args[i].split("=");
            recipe.add(row);
        }

        //noChange일 때까지
        boolean isChange = true;   
        while(isChange){
            isChange = false;
            for(String[] row : recipe){
                String result = row[0];
                String calc = row[1];
                int prevSize = sourceMap.size();
                boolean recipeResult = cuttingRecipe(result, calc, sourceMap);
                int nextSize = sourceMap.size();
                if(nextSize - prevSize > 0 || recipeResult){ //변경 시에만 업데이트
                    // System.out.println(nextSize +" , prev: " + prevSize);
                    isChange = true;
                    // for(String key : sourceMap.keySet()){
                    //     System.out.println("KEY: " +key + " cost: " + sourceMap.get(key) );
                    // }
                }
            }
        }
        
        //"A"란 재료를 만드는 데 필요한 재료 [ {"B"가 2개}, {"C": 2개}, {"D" : 5개} ]
        
        int LIMIT = 1_000_000_001; // _000보다 크면 limit 제출
        int answer = -1; //default, 못만들면 
        Integer loveCost = sourceMap.get("LOVE");
        
        if(loveCost == null){
            System.out.println(answer);
        }else{
            System.out.println(Math.min(loveCost, LIMIT));
        }
    }

    public static boolean cuttingRecipe(String result, String calc, Map<String,Integer> sourceMap){
        String[] matetials = calc.split("\\+");
        Integer realCost = 0;

        for(String m : matetials){
            int cnt = Integer.parseInt(m.substring(0,1));
            String source = m.substring(1);
            Integer purchaseCost = sourceMap.get(source);
            if(purchaseCost != null){
                long test = ((long)cnt*(long)purchaseCost); //덧셈에서 Integer 범위 넘어가는 것 방지
                // System.out.println(source + ":  " + test);
                test = Math.min(test, 1_000_000_001L); 
                realCost = Math.min(realCost+(int)test, 1_000_000_001); //최대 20억, 
            }else{ //null은 현재 materials를 만들 방도가 없음
                realCost = null;
                break;
            }
        }
        if(realCost != null){
            Integer prevCost = sourceMap.getOrDefault(result, 1_000_000_001);
            sourceMap.put(result, Math.min(prevCost, realCost)); //더 낮은 경우 추가
            if(realCost < prevCost){ // 변화 유
                return true;
            }else{ //변화 무
                return false;
            }
        }
        //변화 무.
        return false;
    }

}
 