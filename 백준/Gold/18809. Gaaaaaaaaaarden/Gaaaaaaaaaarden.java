import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

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

        // str = "2 2 1 1\r\n" + //
        //                 "2 1\r\n" + //
        //                 "1 2";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        String[] FISRT_LINE = args[0].split(" ");
        int N = Integer.parseInt(FISRT_LINE[0]); //행(row, y)
        int M = Integer.parseInt(FISRT_LINE[1]); //열(col, x)
        int G = Integer.parseInt(FISRT_LINE[2]); //배양액 그린
        int R = Integer.parseInt(FISRT_LINE[3]); //배양액 레드

        List<int[]> ableLand = new ArrayList<>();
        
        //1. 맵 만들기
        int[][] map = new int[N][M];
        for(int i=1; i<=N; i++){
            String[] rows = args[i].split(" ");
            for(int j=0; j<M; j++){
                int land = Integer.parseInt(rows[j]);
                map[i-1][j] = land;
                if(land == 2) ableLand.add(new int[]{i-1,j});
            }
        }

        // "0" 못감
        // "1" 그냥 땅
        // "2" 배양 땅

        // G:indexes , M:Indexes 
        int[] indexes = IntStream.range(0, ableLand.size()).toArray();
        
        // 배양 땅 좌표 List 중 G,R 을 꼽을 조합 생성 필요 DFS
        List<Entry> cases = findCases(indexes, ableLand.size(), G, R);
        
        //배양액 R,G를 꼽는 경우 순회하여 최고를 찾아야함
        int answer = 0;
        for(Entry e : cases){
            List<Integer> gList = e.g();
            List<Integer> rList = e.r();
            int local = timeIsGone(map, gList, rList, ableLand);
            answer = Math.max(answer,local);
        }
        System.out.println(answer);
        
    }
    public static int timeIsGone(int[][] map, List<Integer> gList, List<Integer> rList, List<int[]> ableLand){
        int count = 0;

        Queue<int[]> q = new LinkedList<>();
        int[][] visit = new int[map.length][map[0].length]; //방문 여부 공유 해야함 => 배양액 놓인 공간, 꽃 생긴 공간, 배양액 퍼진 공간 
        int[][] visitTime = new int[map.length][map[0].length]; //배양액 퍼져서 방문한 time
        //G = 1; R= 2; 초기배양액 = 3; 꽃 = 4;
        for(Integer g : gList){
            int[] gPosition = ableLand.get(g);
            
            visit[gPosition[0]][gPosition[1]] = 3;
            q.offer(new int[]{gPosition[0], gPosition[1], 1, 0 });
            //int[] = {y,x, GR구분, time}; // G=1, R=2
        }
        for(Integer r : rList){
            int[] rPosition = ableLand.get(r);
            visit[rPosition[0]][rPosition[1]] = 3;
            q.offer(new int[]{rPosition[0], rPosition[1], 2, 0 });
            //int[] = {y,x, GR구분, time};
        }
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};

        while(!q.isEmpty()){
            int[] baeyang = q.poll();
            int y = baeyang[0];
            int x = baeyang[1];
            int isGR = baeyang[2];
            int time = baeyang[3];
            //배양된 후 다른 배양액이 침투하면서 꽃이 되면 진행하면 안됨 = 현재 좌표 visit이 꽃인 경우를 제외
            if(visit[y][x] == 4) continue;

            for(int d=0; d<4; d++){
                int ny = baeyang[0]+dy[d];
                int nx = baeyang[1]+dx[d];
                int nt = time+1;
                if(nx<0 || ny <0 || ny>=map.length || nx >= map[0].length) continue; //장외 탈락
                if(map[ny][nx] == 0) continue; //호수 못 감
                int visitValue = visit[ny][nx];
                if(visitValue == 3 || visitValue == 4 || visitValue == isGR) continue; //배양액 놓인 공간 + 꽃 생긴 공간 + 같은 배양액 퍼진 공간
                
                if(((visitValue == 1 && isGR == 2) || (visitValue == 2 && isGR == 1) )){
                    if(visitTime[ny][nx] == nt) {
                        //같은 시간대에 다른 배양액이 만나는 순간
                        visit[ny][nx] = 4;
                        count++;
                        continue;
                    }else{//다른 시간대에 다른 배양액이 서로 만나는 순간
                        continue;//이동 불가
                    }
                } 
                if(visitValue==0){
                    q.offer(new int[]{ny,nx,isGR,nt});
                    visit[ny][nx] = isGR;
                    visitTime[ny][nx] = nt;
                }
                //언제 방문했는지? 또 다른 방문 배열? 배열 차원을 늘리는 것보다 이게 더 명확하지않나? 방문한 좌표에서의 time만 기록해놓으면
            }
        }
        return count;
    }

    static class Entry {
        private final List<Integer> g;
        private final List<Integer> r;
        
        public Entry(List<Integer> g, List<Integer> r) {
            this.g = g;
            this.r = r;
        }
        
        public List<Integer> g() { return g; }
        public List<Integer> r() { return r; }
    }

    public static List<Entry> findCases(int[] array, int size, int G, int R){
        // G에서 gSize개 선택하는 모든 조합
        boolean[] used = new boolean[size];
        List<Entry> cases = new ArrayList<>();
        generateCombinations(array, G, R, 0, new ArrayList<>(), new ArrayList<>(), used, cases, true);
        return cases;
    }
    public static void generateCombinations(int[] array, int gSize, int rSize, 
                                         int start,
                                         List<Integer> gPick, List<Integer> rPick,
                                         boolean[] visited, List<Entry> result,
                                         boolean pickingG) {
        if (pickingG) {
            // G 선택 중
            if (gPick.size() == gSize) {
                // G 완성, 이제 R 선택 시작
                generateCombinations(array, gSize, rSize, 0, 
                                gPick, rPick, visited, result, false);
                return;
            }
            
            for (int i = start; i < array.length; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    gPick.add(array[i]);
                    generateCombinations(array, gSize, rSize, i + 1, 
                                    gPick, rPick, visited, result, true);
                    gPick.remove(gPick.size() - 1);
                    visited[i] = false;
                }
            }
        } else {
            // R 선택 중
            if (rPick.size() == rSize) {
                result.add(new Entry(new ArrayList<>(gPick), new ArrayList<>(rPick)));
                return;
            }
            
            for (int i = start; i < array.length; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    rPick.add(array[i]);
                    generateCombinations(array, gSize, rSize, i + 1, 
                                    gPick, rPick, visited, result, false);
                    rPick.remove(rPick.size() - 1);
                    visited[i] = false;
                }
            }
        }
    }
}