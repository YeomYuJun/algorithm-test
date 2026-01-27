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
        
        // str = "5\r\n" + //
        //                 "1000000 1000000 1000000 1000000 1000000\r\n" + //
        //                 "5 7";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]); // 1~ 100만
        int B = Integer.parseInt(args[2].split(" ")[0]); // 1~100만
        int C = Integer.parseInt(args[2].split(" ")[1]); // 1~100만        

        String[] row = args[1].split(" ");
        int roomCnt = row.length;

        List<Integer> roomPeople = new ArrayList<>();
        for(int i=0; i<N; i++){
            int people = Integer.parseInt(row[i]);
            people = Math.max(people-B, 0);
            if(people > 0){
                roomPeople.add(people);
            }
        }
        long answer = roomCnt;

        for(int needs : roomPeople){
            int sub = needs/C;
            if(needs%C>0){
                sub++;
            }
            answer+=sub;
        }
        System.out.println(answer);

    }
    /**
     * 필요한 감독관 수의 최솟값:
     * 각 시험장이 서로 영향 X => 그냥 x시험장마다 최소 합 > 정답?
     * 각 시험장 최소 총감독 1명 배정, (room 수 만큼)
     * 나머지에서 부감독으로 나눈 몫 + 나머지가 0이면 1명 더 필요
     */
}

