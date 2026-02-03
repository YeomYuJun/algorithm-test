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
            
        // ///////////////////////////////////////////
        
        // str = "5 2\r\n" + //
        //                 "1 2 3 4 5\r\n" + //
        //                 "2 3 3 1\r\n" + //
        //                 "3 5 4 1";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0].trim()); // 2~ 20만
        int Q = Integer.parseInt(args[0].split(" ")[1].trim()); // 2~20만

        String[] strarr = args[1].split(" ");
        Tree root = new Tree(0, N-1);
        for(int i=0; i<N; i++){
            int val = Integer.parseInt(strarr[i]);
            root.update(i, val);
        }

        for(int i=2; i<2+Q; i++){
            String[] row = args[i].split(" ");
        
            int x = Integer.parseInt(row[0]);
            int y = Integer.parseInt(row[1]);
            int a = Integer.parseInt(row[2]);
            int b = Integer.parseInt(row[3]);
            long val = root.query(Math.min(x,y)-1, Math.max(x,y)-1);
            System.out.println(val);
            root.update(a-1, b);
        }
        //System.out.println(root.query(0, N-1));
    }
    static class Tree {
        int startIndex;
        int endIndex;
        long val;
        Tree left;
        Tree right;

        public Tree(int s, int e){
            this.startIndex = s;
            this.endIndex = e;
            this.val = 0;
        }

        void update(int index, int newVal){
            if(this.startIndex == this.endIndex){
                this.val = newVal;
                return;
            }

            int mid = (this.startIndex + this.endIndex) / 2;
            if(left == null){ left = new Tree(this.startIndex, mid); }
            if(right == null){ right = new Tree(mid+1, this.endIndex); }

            if (index <= mid) {
                left.update(index, newVal);
            } else {
                right.update(index, newVal);
            }
            this.val = left.val + right.val; //업데이트 된 좌우 합
            return;
        }
        long query(int qs, int qe){

            //구간 아니면 ..
            if (qe < this.startIndex || this.endIndex < qs) {
                return 0;
            }
            
            //완전히 포함됨
            if (qs <= this.startIndex && this.endIndex <= qe) {
                return this.val;
            }
            if (left == null) return 0;
            if (right == null) return 0;

             // Case 3: 부분적으로 겹침 - 자식들에게 위임
            return this.left.query(qs, qe) 
                + this.right.query(qs, qe);
        }
    }
    /**
     * 구간합 -> 세그먼트?
     * 모든 수는 -2^31보다 크거나 같고, 2^31-1보다 작거나 같은 정수이다
     * 
     */
}