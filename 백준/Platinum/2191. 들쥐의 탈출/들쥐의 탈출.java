import java.io.*;
import java.util.*;

public class Main {
    private static int answer = 0;
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
        
        // str = "2 2 5 10\r\n" + //
        //                 "1.0 1.0\r\n" + //
        //                 "2.0 2.0\r\n" + //
        //                 "2.0 52.1\r\n" + //
        //                 "20.0 20.0";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    static class Rat {
        int id;
        long x;
        long y;

        @Override
        public String toString(){
            return "들쥐 "+id +" 의 좌표: ("+x+", "+y+")";
        }
        
        public Rat(int id, long x, long y){
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    static class Tunnel {
        int id;
        long x;
        long y;

        @Override
        public String toString(){
            return "터널 "+id +" 의 좌표: ("+x+", "+y+")";
        }
        
        public Tunnel(int id, long x, long y){
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }


    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); //들쥐 수
        int M = Integer.parseInt(args[0].split(" ")[1]); //땅굴 수
        int S = Integer.parseInt(args[0].split(" ")[2]); // Second. 매의 지상 도착 시간 
        int V = Integer.parseInt(args[0].split(" ")[3]); // 들쥐의 이동 속도 V/sec


        List<Rat> rats = new ArrayList<>();
        List<Tunnel> tunnels = new ArrayList<>();
        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            long x = (long)(Double.parseDouble(row[0]) * 1000);
            long y = (long)(Double.parseDouble(row[1]) * 1000);
            rats.add(new Rat(i,x, y));
        }


        for(int i=1+N; i<=N+M; i++){
            String[] row = args[i].split(" ");
            long x = (long)(Double.parseDouble(row[0]) * 1000);
            long y = (long)(Double.parseDouble(row[1]) * 1000);
            tunnels.add(new Tunnel(i-N,x, y));
        }

        Map<Integer, List<Tunnel>> RatMap = new HashMap<>(); //id가 key인 rat이 갈 수 있는 터널 목록

        //최대 이동거리 = SV
        long MAX = S*V*1_000;
        for(int i=1; i<=N; i++){
            Rat rat = rats.get(i-1);
            List<Tunnel> tList = RatMap.getOrDefault(rat.id, new ArrayList<>());
            for(int j=1; j<=M; j++){
                Tunnel tunnel = tunnels.get(j-1);
                //직선 이동 = (x-x)^2 + (y-y)^2
                long xGap = tunnel.x - rat.x;
                long yGap = tunnel.y - rat.y;
                if((MAX*MAX) >= ((xGap*xGap)+(yGap*yGap))){
                    tList.add(tunnel);//쥐:터널목록
                }
            }
            RatMap.put(rat.id, tList);
        }
        int[] tMap = new int[M+1];
        //input = t => t가 연결된 쥐 목록.
        
        for(int i=1; i<=N; i++){
            boolean[] checkRat = new boolean[N+1];
            Rat rat = rats.get(i-1);
            if(!ratMatchTunnel(RatMap, rat.id, tMap, checkRat)){
                answer++;
            };
        }
        System.out.println(answer);
    }

    public static boolean ratMatchTunnel(Map<Integer, List<Tunnel>> RatMap, int ratId, int[] tMap, boolean[] checkRat){
        List<Tunnel> tList = RatMap.get(ratId);
        if(checkRat[ratId]) return false; //한번 방 옮긴 쥐는 이제 안옮기기? (1<->2 왔다갔다가 되나?)
        checkRat[ratId] = true;
        
        if(tList==null || tList.size()==0) { //갈 수 있는 터널이 없는 친구
            return false;
        }else{
            for(Tunnel t : tList){ //ratId가 갈 수 있는 터널
                int otherRat = tMap[t.id];
                // if(otherRat == ratId) continue; //들어간 집을 또 들어가라할 수는 없으니까 => 방문처리로 바꾸자 
                if(otherRat == 0 || ratMatchTunnel(RatMap, otherRat, tMap, checkRat)){ //빈집 //otherRat은 딴 곳에 들어갔겠지
                    tMap[t.id] = ratId; //땅굴 차지해버리기
                    return true;
                }
            }
        }
        return false;
    }
        
    /**
     * 
     * 모든 좌표는 절댓값이 1,000을 넘지 않고, 소숫점 셋째 => +-999.999 까지 가능
     * 
     * S초 지난 후에 도착하지 못한 모든 쥐가 다 죽는 건가?
     * 쥐 a와 구멍p 와 매칭, 쥐 b와 구멍q와 매칭...경우를 전부 보자면 
     * 
     * 최적 선택이란 게 있는가? => 가장 먼 것? 
     * 모든 터널 중 가장 가까운 터널의 길이가 먼 순서로 배정시켜주면 최대 생존인가?
     * 반례?
     * a --99--- t1 ---99.1--- b ------99.9----t2
     * a b 둘다에게 100이 MAX일 때, 거리가 99,99.1인 터널이 있음. 
     * b는 99.9라는 아주 아슬아슬한 t2가 가능했음. 따라서 b가 t1을 쟁취하여 a가 죽음. (t2라는 선택지로 공동 생존이 가능함에도 불구하고)
     * 
     * 
     * 역으로 모든 터널과 쥐 그래프 연결을 한다면? t1~t100까지 가능한 모든 터널<->쥐 연결
     * 최종적으로 1개의 rat과 연결되도록 하기만 한다면
     * 1:다 매칭 쥐->터널
     * 
     * 쥐<-> 터널을 고정하는 룰? 뭘까 브루트포스는 아닐텐데.. 매칭하려는 터널에 쥐가 있으면 걔보고 딴 데 가라하면?
     * 스택오버플러우는 방문조건때문인 거 같고 
     */
}

