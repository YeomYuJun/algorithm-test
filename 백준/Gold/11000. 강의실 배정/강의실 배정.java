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
        //                 "1 8\r\n" + //
        //                 "9 16\r\n" + //
        //                 "3 7\r\n" + //
        //                 "8 10\r\n" + //
        //                 "10 14\r\n" + //
        //                 "5 6\r\n" + //
        //                 "6 11\r\n" + //
        //                 "11 12";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        
        List<int[]> lecture = new ArrayList<>();
        for(int i=1; i<=N; i++){
            String[] l = args[i].split(" ");
            int st = Integer.parseInt(l[0]);
            int et = Integer.parseInt(l[1]);
            lecture.add(new int[]{st,et});
        }
        lecture.sort((o1, o2) -> {
            if(o1[0] != o2[0]){
                return Integer.compare(o1[0], o2[0]);
            }
            return Integer.compare(o1[1], o2[1]);
        });
        PriorityQueue<Integer> pq = new PriorityQueue<>(); //종료시간만? 
        pq.add(0); 

        for(int i=0; i<lecture.size(); i++){
            Integer minEndTime = pq.poll();
            int st = lecture.get(i)[0]; //강의 시작시간
            int et = lecture.get(i)[1]; //강의 종료시간
            if(minEndTime > st){ //강의 시작 시간이 현재 가장 빨리 끝나는 강의실보다 빠를 경우 = 신규 강의실 필요
                pq.add(minEndTime); //다시 등록
                pq.add(et);         //신규 강의실 등록
            }else{ //강의 시작 시간이 현재 가장빨리 끝나는 시간과 같거나 늦음 = 해당 강의실 배정 (minEndTime이 et로 전환)
                pq.add(et);
            }
        }
        int answer = pq.size();
        System.out.println(answer);
    }
    /**
     * 수업 개수 카운트 하는 그리디 처럼하다가 중복인 경우 강의실 추가
     * 추가 후에는 PQ 강의실 List(빨리 수업 마치는 순)에서 뽑아서 가능하면 update,
     * 불가능하면 강의실 List에 add
     * 
     * 이후 N개의 줄에 Si, Ti가 주어진다. (0 ≤ Si < Ti ≤ 10^9)
     * = int 
     */

}