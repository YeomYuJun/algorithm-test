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
        
        // str = "5\r\n" + //
        //                 "2\r\n" + //
        //                 "1\r\n" + //
        //                 "3\r\n" + //
        //                 "4\r\n" + //
        //                 "8\r\n" + //
        //                 "7\r\n" + //
        //                 "6\r\n" + //
        //                 "9";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    static class Node{
        int val;
        Node left;
        Node right;

        public Node(int val){
            this.val = val;
            this.left = null;
            this.right = null;
        }

        public void putLeft(int leftVal){
            this.left = new Node(leftVal);
        }
        public void putRight(int rightVal){
            this.right = new Node(rightVal);
        }

        @Override
        public String toString(){
            return "val: "+ this.val;
        }
    }

    public static void solution(String[] args) throws IOException {
        int size = args.length;

        
        int root = Integer.parseInt(args[0]); // root
        Node R = new Node(root);
        
        for(int i=1; i<size; i++){
            int key = Integer.parseInt(args[i]);
            insert(R, key);
        }
        
        StringBuilder sb = new StringBuilder();
        post(R, sb);
        System.out.println(sb.toString().trim());
    }

    //삽입
    public static void insert(Node root, int key){
        Node current = root;
        while(true){
            if(key < current.val){
                if(current.left == null){
                    current.putLeft(key);
                    break;
                }else{
                    current = current.left;
                }
            }else{
                if(current.right == null){
                    current.putRight(key);
                    break;
                }else{
                    current = current.right;
                }
            }
        }
    }

    //전위 테스트용
    public static void pre(Node tree){
        if(tree == null){
            return;
        }
        System.out.println(tree);
        pre(tree.left);
        pre(tree.right);
    }
    public static void post(Node tree, StringBuilder sb){
        if(tree == null){
            return;
        }
        post(tree.left, sb);
        post(tree.right, sb);
        sb.append(tree.val).append("\n");
        // System.out.println(tree.val);
    }
    //전위 복구 및 후위 탐색
    //메모리 괜찮나..?
}