import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
public static void main(String[] args) throws IOException {
        //args = new String[]{"11"};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String num = br.readLine().trim();
        
        System.out.println(solution(num));
    }
public static String solution(String num){

    long n = Long.parseLong(num);

    StringBuilder sb = new StringBuilder();
    for(int digit = 0; digit <= 9; digit++){
        long count = countDigit(n, digit);
        sb.append(count).append(" ");
    }
    //System.out.println(sb.toString().trim());
    return sb.toString().trim();
}

private static long countDigit(long n, int target) {
    if(n < 0) return 0;

    long count = 0;
    long factor = 1;

    while(factor <= n) {
        long higherNumbers = n / (factor * 10); // 나머지(위)
        long currentDigit = (n / factor) % 10; //현재숫자
        long lowerNumbers = n % factor; //나머지

        // 현재 자릿수보다 높은 부분에서의 기여
        if(currentDigit > target) {
            count += (higherNumbers + 1) * factor;
        } else if(currentDigit == target) {
            count += higherNumbers * factor + (lowerNumbers + 1);
        } else { // currentDigit < target
            count += higherNumbers * factor;
        }

        // 0의 경우 선행 0 제거
        if(target == 0) {
            count -= factor;
        }

        factor *= 10;
    }

    return count;
}
}