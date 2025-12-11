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

        // str = "4040 160532";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // N
        int K = Integer.parseInt(args[0].split(" ")[1]); // K 
        if(N==K){
            System.out.println(0);
            return;
        }
        
        // 동생이 각 위치에 몇 틱에 도착하는지 사전 계산
        int[] brotherTime = new int[500001];
        Arrays.fill(brotherTime, -1);
        
        for(int t = 0; t <= 1000; t++) {
            int pos = K + (t*(t+1))/2;
            if(pos > 500000) break;
            if(brotherTime[pos] == -1) {
                brotherTime[pos] = t;
            }
        }
        
        // 수빈이 각 위치에 홀수/짝수 틱으로 도착하는 최소 시간
        int[][] subinTime = new int[500001][2]; // [위치][0:짝수, 1:홀수]
        for(int i = 0; i <= 500000; i++) {
            subinTime[i][0] = -1;
            subinTime[i][1] = -1;
        }
        
        Queue<int[]> q = new LinkedList<>(); // {위치, 시간}
        q.offer(new int[]{N, 0});
        subinTime[N][0] = 0; // 짝수(0틱)에 N 도착
        
        int answer = -1;
        
        while(!q.isEmpty()){
            int[] cur = q.poll();
            int curN = cur[0];
            int curTime = cur[1];
            int parity = curTime % 2;
            
            // 이미 더 빠른 경로로 방문했으면 스킵
            if(subinTime[curN][parity] < curTime) continue;
            
            // 현재 위치에 동생이 오는지 확인
            if(brotherTime[curN] != -1 && brotherTime[curN] >= curTime) {
                int bTime = brotherTime[curN];
                // 동생 도착 시간의 홀짝과 수빈 도착 시간의 홀짝이 같으면 만날 수 있음
                if(bTime % 2 == parity) {
                    if(answer == -1 || bTime < answer) {
                        answer = bTime;
                    }
                }
            }
            
            // 3가지 이동
            int[] nextPos = {curN+1, curN-1, curN*2};
            
            for(int nextN : nextPos) {
                if(nextN >= 0 && nextN <= 500000) {
                    int nextTime = curTime + 1;
                    int nextParity = nextTime % 2;
                    
                    // 아직 방문 안했거나, 더 빠른 경로면 갱신
                    if(subinTime[nextN][nextParity] == -1 || subinTime[nextN][nextParity] > nextTime) {
                        subinTime[nextN][nextParity] = nextTime;
                        q.offer(new int[]{nextN, nextTime});
                    }
                }
            }
        }
        
        System.out.println(answer);

    }
    /**
     *  K+1, k+1+2, 
     *  A0= K
     *  A1 = A0 + 1
     *  A2 = A1 + 2
     *  A3 = A2 + 3
     *  ...
     *  An = An-1 + n
     *  동생의 tik당 위치를 미리 구해놓고. 1 3 6 10 15
     *  An = n(n+1)/2
     * 500,000 < n(n+1)/2  = 1,000,000 < n^2+n  n이 1000보다 클 수 없음
     */

}