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

        // str = "4 6\r\n" + //
        //                 "a t c i s w";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int L = Integer.parseInt(args[0].split(" ")[0]);
        int C = Integer.parseInt(args[0].split(" ")[1]);

        
        Set<String> vow = new HashSet<>(){
            {
                add("a");
                add("e");
                add("i");
                add("o");
                add("u");
            }
        };

        boolean[] visit = new boolean[C];
        //3~15길이
        String[] strArr = args[1].split(" ");
        Arrays.sort(strArr); // { a c i s t w } 정렬
        
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        makePw(visit, strArr, C, L, sb, result, 0,vow);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(String str : result){
            bw.write(str);
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }
    
    public static void makePw(boolean[] visit, String[] strArr, int depth, int L, StringBuilder sb, List<String> result, int start, Set<String> vow){
        

        if(sb.length()==L){
            String tempResult = sb.toString();
            
            int vowCnt = 0;
            for(String s : tempResult.split("")){
                if(vow.contains(s)) vowCnt++;
            }
            if(vowCnt<1 || vowCnt>(L-2)) return; //모음이 1개 이상이어야 하며  최대 L-2개까지 가능 
            result.add(tempResult);
            return;
        }
        for(int i=start; i<strArr.length; i++){ // a 에서 배열 Limit까지 추가
            if(visit[i]) continue;

            visit[i] = true;
            sb.append(strArr[i]);

            makePw(visit,strArr,depth+1,L,sb,result,i+1,vow);

            visit[i] = false;
            sb.deleteCharAt(sb.length()-1);
        }
    }

    /**
     * least 1 {a e i o u}
     * least 2 {b c d ~}
     * alphabet = ASC
     * L는 만드는 길이
     * C 은 주어진 문자 수
     */
}