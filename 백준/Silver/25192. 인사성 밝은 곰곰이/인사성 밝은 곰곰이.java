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
        // str = "7\r\n" + //
        //                 "ENTER\r\n" + //
        //                 "pjshwa\r\n" + //
        //                 "chansol\r\n" + //
        //                 "chogahui05\r\n" + //
        //                 "ENTER\r\n" + //
        //                 "pjshwa\r\n" + //
        //                 "chansol";
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args){
        int n = Integer.parseInt(args[0]); // range
        int count = 0;;
        StringBuilder sb = new StringBuilder();
        for(int i=1; i<=n; i++){
            sb.append(args[i]).append(" ");
        }
        String[] chatSet = sb.toString().trim().split("ENTER");

        for(int i=0; i<chatSet.length; i++){
            String line = chatSet[i].trim();
            if(line.isEmpty()) continue;
            Set<String> set = new HashSet<>();
            String[] users = line.split(" ");
            if(users.length > 0){
                for(int j=0; j<users.length; j++){
                    String user = users[j].trim();
                    if(user.isEmpty()) continue;
                    if(set.add(user)) count++;
                }
            }
        }
        System.out.println(count);
    }
    /**
     * ENTER로 스플릿하면 흠. ASDEN, TERASD 같은 닉네임이 있을 수 있을지도
     */

}

