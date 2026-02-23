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
        
        // str = "6\r\n" + //
        //                 "2 3 3 5 2 -1\r\n" + //
        //                 "3";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        String[] row = args[1].split(" ");

        int M = Integer.parseInt(args[2]);

        //무조건 root가 0이 아님.
        int r = 0;

        Node root = new Node(0, 0);

        Node[] nArr = new Node[N];
        nArr[0] = root;
        //아 순서,, 미리 초기화
        for(int i=0; i<N; i++){
            Node node = new Node(i, i);
            nArr[i] = node;
        }

        for(int i=0; i<N; i++){

            int num = i;
            int p = Integer.parseInt(row[i]);

            if(p==-1){
                r = i;
                continue;
            }
            Node node = new Node(num, p);
            nArr[i].parent = p;
            nArr[p].children.add(node);
        }
        

        if(M==r){
            System.out.println(0);
            return;
        }
        //dfs 완탐
        dfs(nArr, r, M);
        
        System.out.println(leaf);

    }
    static int leaf = 0;
    public static void dfs(Node[] arr, int current, int limit){
        if(arr[current].children.size() == 0){
            leaf++;
            return;
        }
        
        List<Node> children = arr[current].children;
        for(Node child : children){
            if(child.val == limit){ 
                if(children.size()==1){ //현재 node 가 leaf가 되는 경우
                    leaf++; 
                }
                continue;
            }
            dfs(arr, child.val , limit);
        }
        
    }

    static class Node {
        int val;
        int parent;
        List<Node> children;

        public Node(int val, int parent){
            this.val = val;
            this.parent = parent;
            this.children = new ArrayList<>();
        }
    }
}
