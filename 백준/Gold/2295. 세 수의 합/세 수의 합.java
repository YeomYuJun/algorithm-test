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

        // str = "5\r\n" + //
        //                 "2\r\n" + //
        //                 "3\r\n" + //
        //                 "5\r\n" + //
        //                 "10\r\n" + //
        //                 "18";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        List<Integer> U = new ArrayList<>();
        for(int i=1; i<=N; i++){
            U.add(Integer.parseInt(args[i]));
        }
        
        U.sort(Collections.reverseOrder());

        //sumCheck에 2개의 합 모든 유형 추가.
        Set<Integer> sumCheck = new HashSet<>();
        for(int i=0; i<N; i++){
            for(int j=i; j<N; j++){ //중복 허용
                sumCheck.add(U.get(i) + U.get(j));
            }
        }

        //큰 수로부터 z를 빼면.근데 다시존재하는 지 탐색하면
        for(int i=0; i<N-1; i++){ // {1,3, ... n}에서 3이 최대인 경우?
            int k = U.get(i);
            for(int j=0; j<N; j++){  // z 순회
                int z = U.get(j);
                if(sumCheck.contains(k - z)){
                    System.out.println(k);
                    return;  // 최대값 찾았으므로 종료
                }
            }
        }
        
        
    }
    /**
     * n>=5, n<=1000
     * 995C2 = 494515 , 할만한데?
     * 
     */

}