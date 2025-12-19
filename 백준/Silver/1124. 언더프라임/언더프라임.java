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

        // str = "123 456"; 

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int A = Integer.parseInt(args[0].split(" ")[0]);
        int B = Integer.parseInt(args[0].split(" ")[1]);
        
        // 에라토스테네스 체로 10만까지 소수 판정 배열 생성
        boolean[] isPrime = che(100000);

        // 메모이제이션
        int[] primeFactorCount = new int[100001];
        for(int i = 2; i <= 100000; i++){
            primeFactorCount[i] = primeCnt(i);
        }

        // A~B 범위에서 언더프라임 카운트
        int count = 0;
        for(int i = A; i <= B; i++){
            int factorCount = primeFactorCount[i];
            if(isPrime[factorCount]){
                count++;
            }
        }
        System.out.println(count);

        //1 1
        //2 프라임
        //3 프라임
        //4 2 2 = 언더프라임
        //5 프라임
        //6 2 3 = 언더프라임
        //7 프라임
        //8 2 4 = 언더프라임
        //9 3 3 = 언더프라임
        //10 2 5 = 언더프라임 
        //16 2 2 2 2= x 
        //32 2 2 2 2 2, 길이는 5= 소수 = 언더프라임
        //64 2 2 2 2 2 2 = 길이는 6 = 아무것도 아님
    }

    // 에라토스테네스 체
    public static boolean[] che(int max){
        boolean[] isPrime = new boolean[max + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        
        for(int i = 2; i * i <= max; i++){
            if(isPrime[i]){
                for(int j = i * i; j <= max; j += i){
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }

    // 소인수 개수 카운트 (중복 포함)
    public static int primeCnt(int n){
        int count = 0;
        
        for(int i = 2; i * i <= n; i++){
            while(n % i == 0){
                count++;
                n /= i;
            }
        }
        
        if(n > 1) count++; // 남은 소인수
        
        return count;
    }
}