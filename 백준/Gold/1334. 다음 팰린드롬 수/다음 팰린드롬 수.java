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

        // str = "9"; 

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        String n = addStrings(args[0], "1");

        int z = n.length();

        boolean isEqual = checkEqual(n, z);
        if(isEqual){
            System.out.println(n);
            return;
        }
        
        String tempStr = n;
        while(true){ //다르다면 찾을 때까지

            boolean odd = tempStr.length()%2==1;
        
            int c = tempStr.length()/2;
            String prev = tempStr.substring(0, c);
            String next = new StringBuffer(prev).reverse().toString();
            StringBuilder sb = new StringBuilder();
            sb.append(prev);
            if(odd){//중심수가 있음.
                sb.append(tempStr.substring(c, c+1));
            }
            sb.append(next);
            String test = sb.toString();
            int result = test.compareTo(n); 
            if(result > 0){ 
                System.out.println(test);
                return;
            }else{
                // 10^c 만큼 더하기
                String increment = "1" + "0".repeat(c);
                tempStr = addStrings(tempStr, increment);
            }
        }
    }

    // 문자열 덧셈
    public static String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        
        while(i >= 0 || j >= 0 || carry > 0) {
            int digit1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int sum = digit1 + digit2 + carry;
            result.append(sum % 10);
            carry = sum / 10;
            i--;
            j--;
        }
        
        return result.reverse().toString();
    }
    
    // +1 연산
    public static String addOne(String num) {
        return addStrings(num, "1");
    }

    //팰린머시기 체크
    public static boolean checkEqual(String n, int size){
        for(int i=0; i<size/2; i++){
            char a = n.charAt(i);
            char b = n.charAt(size-1-i);
            if(a!=b){
                return false;
            }
        }

        return true;
    }

}