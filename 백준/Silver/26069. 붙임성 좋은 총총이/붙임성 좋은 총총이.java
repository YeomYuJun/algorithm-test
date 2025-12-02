import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        // str = "12\r\n" + //
        //                 "bnb2011 chansol\r\n" + //
        //                 "chansol chogahui05\r\n" + //
        //                 "chogahui05 jthis\r\n" + //
        //                 "jthis ChongChong\r\n" + //
        //                 "jthis jyheo98\r\n" + //
        //                 "jyheo98 lms0806\r\n" + //
        //                 "lms0806 pichulia\r\n" + //
        //                 "pichulia pjshwa\r\n" + //
        //                 "pjshwa r4pidstart\r\n" + //
        //                 "r4pidstart swoon\r\n" + //
        //                 "swoon tony9402\r\n" + //
        //                 "tony9402 bnb2011";
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args){
        int n = Integer.parseInt(args[0]); // range
        Set<String> set = new HashSet<>();
        set.add("ChongChong");
        int count = 1;
        for(int i=1; i<=n; i++){
            String humanA = args[i].split(" ")[0];
            String humanB = args[i].split(" ")[1];
            if(set.contains(humanA)){
                if(set.add(humanB)) count++;
            }else if(set.contains(humanB)){
                if(set.add(humanA)) count++;
            }
        }
        System.out.println(count);
    }
    /**
     * Set? 
     */
}

