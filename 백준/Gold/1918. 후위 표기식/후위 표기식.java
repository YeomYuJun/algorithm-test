import java.io.*;
import java.util.*;

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
        
        // str = "A+(B-C)/D";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        String notation = args[0];

        Stack<Character> stack = new Stack<>();
        
        char[] array = notation.toCharArray();
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i<array.length; i++) {
    		if((int)array[i] >= 65 && (int)array[i] <= 90) {
    			sb.append(array[i]);
    		}else if(array[i] == '(') {
    			stack.push(array[i]);
    		}else if(array[i] == ')') {
    			if(!stack.empty()) {
    				while(stack.peek() != '(') {
        				sb.append(stack.pop());
        			}
    				stack.pop();
    			}
    		}else if(array[i] == '*' || array[i] == '/') {
    			while(!stack.empty() && (stack.peek() == '*' || stack.peek() == '/')) {
    				sb.append(stack.pop());
    			}
    			stack.push(array[i]);
    		}else if(array[i] == '+' || array[i] == '-') {
    			while(!stack.empty() && (stack.peek() == '*' || stack.peek() == '/' || stack.peek() == '+' || stack.peek() == '-')) {
    				sb.append(stack.pop());
    			}
    			stack.push(array[i]);
    		}
    	}
    	
    	while(!stack.empty()) {
    		sb.append(stack.pop());
    	}
    	
    	System.out.println(sb.toString());
    }
}