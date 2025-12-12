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

        // str = "9\r\n" + //
        //                 "-13";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int X = Integer.parseInt(args[0]); // 
        int Y = Integer.parseInt(args[1]); // 
        
        if(X>0){
            if(Y>0){
                System.out.println(1);
            }else{
                System.out.println(4);
            }
        }else{
            if(Y>0){
                System.out.println(2);
            }else{
                System.out.println(3);
            }
        }
    }


}