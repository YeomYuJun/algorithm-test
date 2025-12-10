import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        List<int[]> lines = new ArrayList<>(N);
        
        for(int i = 0; i < N; i++){
            String[] parts = br.readLine().split(" ");
            int st = Integer.parseInt(parts[0]);
            int ed = Integer.parseInt(parts[1]);
            lines.add(new int[]{st, ed});
        }
        
        // 시작점 오름차순, 같으면 종료점 오름차순
        lines.sort((a, b) -> {
            if(a[0] != b[0]){
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        long lineLength = 0;  // long으로 변경
        int curPosSt = lines.get(0)[0];
        int curPosEd = lines.get(0)[1];
        
        for(int i = 1; i < lines.size(); i++){
            int st = lines.get(i)[0];
            int ed = lines.get(i)[1];
            
            if(st >= curPosEd){  // 겹치지 않음
                lineLength += (curPosEd - curPosSt);
                curPosSt = st;
                curPosEd = ed;
            } else if(ed > curPosEd){  // 겹치지만 연장
                curPosEd = ed;
            }
        }
        lineLength += (curPosEd - curPosSt);  // 마지막 구간
        
        System.out.println(lineLength);
    }
}