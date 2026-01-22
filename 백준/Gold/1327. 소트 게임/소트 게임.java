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
            
        ///////////////////////////////////////////
        
        // str = "8 4\r\n" + //
        //                 "7 2 1 6 8 4 3 5"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int K = Integer.parseInt(args[0].split(" ")[1]);

        String[] row = args[1].split(" ");
        int[] permu = new int[N];
        for(int i=0; i<N; i++){
            permu[i] = Integer.parseInt(row[i]);
        }

        //이미 오름차순인지 체크
        if(isAsc(permu)){
            System.out.println(0);
            return;
        }

        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(new State(permu, 0));
        visited.add(Arrays.toString(permu));
        
        while(!queue.isEmpty()){
            State curr = queue.poll();
            
            for(int i=0; i<=N-K; i++){
                int[] next = curr.arr.clone();
                swap(next, i, K);
                
                if(isAsc(next)){
                    System.out.println(curr.count + 1);
                    return;
                }
                
                String key = Arrays.toString(next);
                if(!visited.contains(key)){
                    visited.add(key);
                    queue.offer(new State(next, curr.count + 1));
                }
            }
        }
        
        System.out.println(-1);

        
    }
    static class State {
        int[] arr;
        int count;
        
        State(int[] arr, int count){
            this.arr = arr;
            this.count = count;
        }
    }

    public static void swap(int[] arr, int pos, int k){
        int left = pos;
        int right = pos + k - 1;
        
        while(left < right){
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    public static boolean isAsc(int[] arr){
        for(int i=0; i<arr.length; i++){
            if(arr[i] != i+1){
                return false;
            }
        }
        return true;
    }
    /**
     *  i번째에서 K-1을 더한 위치까지 스왑.
     * 오른쪽, 왼쪽 어디부터 퍼즐을 맞춰야하는가?
     * 같은 위치에 여러번 스왑할 일이 있는가? 
     * 뭔가 스왑이 전위,후위. 느낌이 좀..
     
     */

}