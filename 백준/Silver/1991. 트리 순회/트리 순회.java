import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        // ////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "7\r\n" + //
        //                 "A B C\r\n" + //
        //                 "B D .\r\n" + //
        //                 "C E F\r\n" + //
        //                 "E . .\r\n" + //
        //                 "F . G\r\n" + //
        //                 "D . .\r\n" + //
        //                 "G . .";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);


        Map<String, List<String>> tree = new HashMap<>();
        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");

            String P = row[0];
            String L = row[1];
            String R = row[2];

            List<String> child = new ArrayList<String>();
            child.add(L);
            child.add(R);
            tree.put(P, child);
        }
        List<String> answer = new ArrayList<>();
        pre(tree,  answer , "A");
        System.out.println(answer.toString().replace("[", "").replace("]", "").replaceAll(", ", ""));

        List<String> answer2 = new ArrayList<>();
        in(tree,  answer2 , "A");
        System.out.println(answer2.toString().replace("[", "").replace("]", "").replaceAll(", ", ""));

        List<String> answer3 = new ArrayList<>();
        post(tree,  answer3 , "A");
        System.out.println(answer3.toString().replace("[", "").replace("]", "").replaceAll(", ", ""));
    }

    public static void pre(Map<String,List<String>> tree, List<String> answer, String key){
        if (".".equals(key)) return;  // 빈 노드
        
        answer.add(key);  // 루트 방문
        
        List<String> children = tree.get(key);
        pre(tree, answer, children.get(0));  // 왼쪽
        pre(tree, answer, children.get(1));  // 오른쪽
    }

    public static void in(Map<String,List<String>> tree, List<String> answer, String key){
        if (".".equals(key)) return;  // 빈 노드
        
        
        List<String> children = tree.get(key);
        in(tree, answer, children.get(0));  // 왼쪽
        answer.add(key); 
        in(tree, answer, children.get(1));  // 오른쪽
    }

    public static void post(Map<String,List<String>> tree, List<String> answer, String key){
        if (".".equals(key)) return;  // 빈 노드
        
        List<String> children = tree.get(key);
        post(tree, answer, children.get(0));  // 왼쪽
        post(tree, answer, children.get(1));  // 오른쪽
        answer.add(key);  // 루트 방문
    }

    /**
     * pre in post order 출력
     * A~Z를 int로 치환하고 할까...그냥할까...
     */

}
