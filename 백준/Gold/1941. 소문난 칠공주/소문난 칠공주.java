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

        // str = "SSSSS\r\n" + //
        //                 "SSSSS\r\n" + //
        //                 "SSSSS\r\n" + //
        //                 "SSSSS\r\n" + //
        //                 "SSSSS";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        String[][] map = new String[5][5];
        for(int i=0; i<5; i++){
            map[i] = args[i].split("");
        }

        List<Integer> selected = new ArrayList<>();
        Set<String> result = new HashSet<>();
        
        // 0~24 인덱스 중 7개 선택하는 조합
        selectCombination(map, 0, 0, 0, selected, result);
        
        System.out.println(result.size());
       
    }
    public static void selectCombination(String[][] map, int idx, int yCnt, int sCnt,
                                     List<Integer> selected, Set<String> result) {
        // 가지치기
        if (yCnt > 3) return;
        if (25 - idx < 7 - selected.size()) return;  // 남은 칸이 부족
        
        if (selected.size() == 7) {
            if (sCnt >= 4 && isConnected(selected)) {
                result.add(selected.toString());
            }
            return;
        }
        
        if (idx == 25) return;
        
        int y = idx / 5;
        int x = idx % 5;
        
        // 선택
        selected.add(idx / 5 * 10 + idx % 5);
        int newY = "Y".equals(map[y][x]) ? yCnt + 1 : yCnt;
        int newS = "S".equals(map[y][x]) ? sCnt + 1 : sCnt;
        selectCombination(map, idx + 1, newY, newS, selected, result);
        selected.remove(selected.size() - 1);
        
        // 선택 안 함
        selectCombination(map, idx + 1, yCnt, sCnt, selected, result);
    }
    public static boolean isConnected(List<Integer> coords) {
        if (coords.size() != 7) return false;
        
        // 좌표를 Set으로 변환
        Set<Integer> coordSet = new HashSet<>(coords);
        
        // 임의의 시작점 (첫 번째 좌표)
        Queue<Integer> q = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        
        int start = coords.get(0);
        q.offer(start);
        visited.add(start);
        
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 10, -10};  // 10 단위로 y 이동
        
        while (!q.isEmpty()) {
            int cur = q.poll();
            
            for (int d = 0; d < 4; d++) {
                int next = cur + dx[d] + dy[d];
                
                // 선택된 좌표 중에 있고, 아직 방문 안 했으면
                if (coordSet.contains(next) && !visited.contains(next)) {
                    visited.add(next);
                    q.offer(next);
                }
            }
        }
        
        return visited.size() == 7;  // 7개 모두 방문 가능하면 연결됨
    }
}