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

        // str = "8\r\n" + //
        //                 "71 39 44\r\n" + //
        //                 "32 83 55\r\n" + //
        //                 "51 37 63\r\n" + //
        //                 "89 29 100\r\n" + //
        //                 "83 58 11\r\n" + //
        //                 "65 13 15\r\n" + //
        //                 "47 25 29\r\n" + //
        //                 "60 66 19";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        int[][] d = new int[n+1][3];
        for(int i=1; i<=n; i++){
            String[] arr = args[i].split(" ");  
            int red = Integer.parseInt(arr[0]);
            int green = Integer.parseInt(arr[1]);
            int blue = Integer.parseInt(arr[2]);
            if(i==1){ // 처음
                d[i][0] = red;
                d[i][1] = green;
                d[i][2] = blue; 
            }else if(i>1 && i<=n){ //중간
                d[i][0] = Math.min(d[i-1][1] + red , d[i-1][2] + red);
                d[i][1] = Math.min(d[i-1][0] + green , d[i-1][2] + green);
                d[i][2] = Math.min(d[i-1][0] + blue , d[i-1][1] + blue);;
            }
        }
        System.out.println(Math.min(Math.min(d[n][0],d[n][1]), d[n][2]));
    }
}