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
            
        // ////////////////////////////////////// /////
        
        // str = "XX.XX";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        char[] chars = args[0].toCharArray();

        int len = chars.length;

        //BB 만들고, BBBB 는 AAAA 만들면 되지않나
        for(int i=0; i<len; i++){
            if(i==0) continue;
            if(chars[i] == '.') continue;

            if(chars[i-1] == 'X'){
                chars[i-1] = 'B';
                chars[i] = 'B';
            }
            
        }

        for(int i=3; i<len; i++){
            if(chars[i] == '.') continue;

            if(
            chars[i-3] == 'B' && 
            chars[i-2] == 'B' &&
            chars[i-1] == 'B' &&
            chars[i] == 'B'
            ){
                chars[i-3] = 'A';
                chars[i-2] = 'A';
                chars[i-1] = 'A';
                chars[i] = 'A';
            }
        }
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            sb.append(c);
        }
        String ans = sb.toString();
        if(ans.contains("X")){
            System.out.println(-1);
        }else{
            System.out.println(ans);
        }
        

    }
}
