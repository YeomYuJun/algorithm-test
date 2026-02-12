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
            
        // ///////////////////////////////////////////
        
        // str = "4\r\n" + //
        //                 "1 1\r\n" + //
        //                 "2 1\r\n" + //
        //                 "4 7\r\n" + //
        //                 "3 3";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        int[] first = new int[N];
        int[] second = new int[N];
        
        for(int i = 0; i < N; i++){
            String[] row = args[i+1].split(" ");
            first[i] = Integer.parseInt(row[0]);
            second[i] = Integer.parseInt(row[1]);
        }
        
        Integer[] indices = new Integer[N];
        for(int i = 0; i < N; i++) indices[i] = i;
        Arrays.sort(indices, Comparator.comparingInt(i -> second[i]));

        long[] prefixB = new long[N + 1];
        for(int i = 0; i < N; i++){
            prefixB[i + 1] = prefixB[i] + second[indices[i]];
        }
        
        long[] minBenefitPrefix = new long[N];
        minBenefitPrefix[0] = first[indices[0]] - second[indices[0]];
        for(int i = 1; i < N; i++){
            int idx = indices[i];
            long benefit = first[idx] - second[idx];
            minBenefitPrefix[i] = Math.min(minBenefitPrefix[i-1], benefit);
        }
        
        long[] minASuffix = new long[N];
        minASuffix[N-1] = first[indices[N-1]];
        for(int i = N-2; i >= 0; i--){
            minASuffix[i] = Math.min(minASuffix[i+1], first[indices[i]]);
        }
        
        StringBuilder sb = new StringBuilder();
        
        for(int k = 1; k <= N; k++){
            long minCost = Long.MAX_VALUE;
            
            //rank < k인 음식 중 선택 (min(A[i] - B[i]) + prefixB[k])
            if(k > 0){
                minCost = Math.min(minCost, minBenefitPrefix[k-1] + prefixB[k]);
            }
            
            //rank >= k인 음식 중 선택 (min(A[i]) + prefixB[k-1])
            if(k < N){
                minCost = Math.min(minCost, minASuffix[k] + prefixB[k-1]);
            }
            
            sb.append(minCost).append("\n");
        }
        
        System.out.print(sb);
    }
    /**
     * (0 ≤ Ai, Bi ≤ 1,000,000,000) ??
     * 가격이 10억
     * 2~50만 
     *               500,000
     *        x1,000,000,000
     * = 500,000,000,000,000 =>  500조 => long 
     *  500,000^2 = 
     *  250,000,000,000, 제곱만 해도 2500억 연산 
     * 
     * 같은 종류 음식을 여러 번 중복해서 주문할 수 없다.
     * 2번째에 가능한 시나리오 = f에서 제일 싼거와 s에서 제일 싼거가 다른 음식일 때, 해당 index 고름
     * 같은 인덱스일 경우 f에서 두번째로 싼 거 + .s 제일 싼거 vs f에서 제일싼거 + s 두번째로 싼 거 
     * ... 이외에도 나중에 첫번째로 고르는 게 이득인 경우가 있을 수 있음 (f=5 , s=100 같은 경우) 즉 인덱스를 알아야함
     * 
     * 근데 위 조건으로 두번째 고른 이후로는 그냥 정렬 후 누적합 아닌가?
     */
}
