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
        
        // str = "5 636";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int K = Integer.parseInt(args[0].split(" ")[1]);

        if(K <= N){
            System.out.println(N-K);
            return;
        }

        int[] timePosition = new int[100_001]; // 400,000
        Arrays.fill(timePosition, Integer.MAX_VALUE); //최대값 초기화

        Queue<int[]> q = new LinkedList<>();

        int cur = N;
        timePosition[N] = 0;
        q.add(new int[]{N,0});

        while(2*cur<100001 && cur != 0){//순간이동 초기화
            cur*=2;
            timePosition[cur] = 0;
            q.add(new int[]{cur,0});
            if(cur == K){
                System.out.println(0);
                return;
            }
        }

        int max = Integer.MAX_VALUE;
        while(!q.isEmpty()){
            int[] subin = q.poll();
            int sp = subin[0];  //수빈 위치
            int tik = subin[1]; //현재 수빈이 쓴 시간  

            if(sp == K){
                System.out.println(Math.min(tik,max));
                return;
            }

            int frontTik = 0;
            if(sp<100000){
                frontTik = timePosition[sp+1];
            }
            
            int backTik = 0;
            if(sp>0){   
                backTik = timePosition[sp-1];
            }

            if(sp < K){
                if(2*sp <= 100000 && timePosition[2*sp] > tik){
                    q.add(new int[]{2*sp, tik});    //2배 텔레포트
                    timePosition[2*sp] = tik;
                }
                if(backTik > tik+1){ //이동할 곳의 방문 시간ㅣ;
                    q.add(new int[]{sp-1,tik+1});   //1칸 후퇴
                    timePosition[sp-1] = tik+1; //q넣으면서 갱신해줘야 다음에 바로 방문안함(동 시간대 방문 불필요)
                }
                if(frontTik > tik+1){
                    q.add(new int[]{sp+1, tik+1});  // 1칸 전진
                    timePosition[sp+1] = tik+1;
                }
            }else{
                //후퇴는, 앞으로 후퇴밖에 못하니까 현재 초에서 sp-k초 만큼 걸림
                max = Math.min(sp-K+tik, max);
            }

            
        }

        System.out.println("error");
        return;   
    }
    /**
     * 곱, 빼기, 더하기
     * 넘어갔다가 음수로 빼기 하는 게 이득인 경우가 있나? 
     * 
     * 5 10 20 40 80 160 320 640 
     * ex 5 635 => 317 = 320-3= 4; 
     *  5 636 => 318 = 320-2 => 160-1  * 2 => 1
     * 없는듯, /2로 최대한 나눠진 값에서 +- 이동이 관건인듯
     */

}
