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
        
        // str = "5 4\r\n" + //
        //                 "1\r\n" + //
        //                 "3\r\n" + //
        //                 "5\r\n" + //
        //                 "10\r\n" + //
        //                 "11";//

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0].trim()); // 2~ 20만
        int C = Integer.parseInt(args[0].split(" ")[1].trim()); // 2~20만

        List<Integer> houses = new ArrayList<>(); //20만개
        for(int i=0; i<N ;i++){
            int x = Integer.parseInt(args[i+1]);
            houses.add(x);
        }
        Collections.sort(houses);
        //size/c = 1 n n n n size니까  양 끝 베이스에 등분한 중간값?
        
        int start = 1;
        int end = houses.get(houses.size()-1);
        
        int answer = 0;
        while(start<=end){
            int mid = (start+end) / 2;
            int cur = houses.get(0);
            int cnt=1;
            for(int i=0; i<N; i++){
                if(houses.get(i)-mid >= cur){
                    cnt++;
                    cur = houses.get(i);
                }
            }
            if(cnt >= C){
                start=mid+1;
                answer = mid;//정답 계속 갱신, 탈출 시 정답임
            }else{
                end = mid-1;
            }
        }
        System.out.println(answer);

    }
    /**
     *  x = 10억 미하 = int
     */
}