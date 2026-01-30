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
        
        // str = "5\r\n" + //
        //                 "100 100 100 100 10\r\n" + //
        //                 "0 0 4 0";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);  // 2~50

        String[] numsStr = args[1].split(" ");
        int[] nums = Arrays.stream(numsStr).mapToInt(Integer::parseInt).toArray();
        
        int plus = Integer.parseInt(args[2].split(" ")[0]);
        int minus = Integer.parseInt(args[2].split(" ")[1]);
        int multi = Integer.parseInt(args[2].split(" ")[2]);
        int divide = Integer.parseInt(args[2].split(" ")[3]);

        List<List<Integer>> result = new ArrayList<>();
        
        permutataion(new ArrayList<>(), result, N, plus, minus, multi, divide);

        //1,2,3,4 (+,-,*,/)으로 대칭시키고
        int S = result.size();
        // System.out.println("result Size: " + S);
        // for(int i=0; i<S; i++){
        //     System.out.println(result.get(i));
        // }
        int MIN = Integer.MAX_VALUE;
        int MAX = Integer.MIN_VALUE;
        for(int i=0; i<S; i++){
            List<Integer> notation = result.get(i);
            int num = nums[0];
            for(int j=0; j<N-1; j++){
                int exp = notation.get(j);
                if(exp == 1){
                    num += nums[j+1];
                }else if(exp == 2){
                    num -= nums[j+1];
                }else if(exp == 3){
                    num *= nums[j+1];
                }else if(exp == 4){ //음수 케이스 처리
                    if(num < 0){
                        num = -(Math.abs(num)/nums[j+1]);
                    }else{
                        num /= nums[j+1];
                    }
                }
            }
            MAX = Math.max(MAX, num);
            MIN = Math.min(MIN, num);
        }
        System.out.println(MAX);
        System.out.println(MIN);
    }

    public static void permutataion(List<Integer> current, List<List<Integer>> result, int N, int p, int m, int x, int d){

        
        if(current.size() == N-1){//종료 조건
            result.add(new ArrayList<>(current));
            return;
        }
        //111 112 113 121 122 123 131 132 133

        for(int i=1; i<=4; i++){
            if(p>0 && i==1){
                current.add(i);
                permutataion(current, result, N, p-1, m, x, d);
                current.remove(current.size()-1);
            }
            if(m>0 && i==2){
                current.add(i);
                permutataion(current, result, N, p, m-1, x, d);
                current.remove(current.size()-1);
            }
            if(x>0 && i==3){
                current.add(i);
                permutataion(current, result, N, p, m, x-1, d);
                current.remove(current.size()-1);
            }
            if(d>0 && i==4){
                current.add(i);
                permutataion(current, result, N, p, m, x, d-1);
                current.remove(current.size()-1);
            }
        }

    }
    /**
     * a + b + c + d + e 
     *   []  []  []  [] 에 들어갈 수 있느 식의 조합. 
     * 4개 종류 중 고르는, 개중에서 골라야하는, 순서가 유의미한 
     * 순열
     */

}

