import java.io.*;
import java.util.*;

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
        
        // str = "5 1\r\n" + //
        //                 "1 1 5 5\r\n" + //
        //                 "2 2 6 6\r\n" + //
        //                 "1 2 5 6\r\n" + //
        //                 "2 1 6 5\r\n" + //
        //                 "3 2 5 4";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }


    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]); // 사각형 수
        int K = Integer.parseInt(args[0].split(" ")[1]); // 색칠 가능 횟수

        List<long[]> list = new ArrayList<>();

        Map<Integer, List<int[]>> minusMap = new HashMap<>();
        for(int i=1; i<=N; i++){
            minusMap.put(i, new ArrayList<>());
        }

        for(int i=1; i<=N; i++){
            String[] row = args[i].split(" ");
            int id = i;
            int x1 = Integer.parseInt(row[0]);
            int y1 = Integer.parseInt(row[1]);
            int x2 = Integer.parseInt(row[2]);
            int y2 = Integer.parseInt(row[3]);

            for(int j=1; j<i; j++){ //기존 것 중 현재 상자와 겹치면 size 감소
                long[] rect = list.get(j-1);
                
                List<int[]> minusList = minusMap.get(j);

                int rx1 = (int)rect[1];
                int ry1 = (int)rect[2];
                int rx2 = (int)rect[3];
                int ry2 = (int)rect[4];

                if(rx2 <= x1 || ry2 <= y1 || rx1 >= x2 || ry1 >= y2){ //바깥
                    continue;
                }

                int minX2 = Math.min(rx2, x2);
                int maxX1 = Math.max(x1, rx1);
                int minY2 = Math.min(ry2, y2);
                int maxY1 = Math.max(y1, ry1);

                // 교집합 영역
                List<int[]> fragments = new ArrayList<>();
                fragments.add(new int[]{maxX1, maxY1, minX2, minY2});

                // 이미 가려진 영역들과 순차적으로 차집합 수행
                for(int k=0; k<minusList.size(); k++){
                    int[] m = minusList.get(k);
                    List<int[]> nextFragments = new ArrayList<>();
                    
                    for(int[] frag : fragments){
                        int fx1 = frag[0], fy1 = frag[1], fx2 = frag[2], fy2 = frag[3];
                        
                        // frag와 m의 교집합 계산
                        int ix1 = Math.max(fx1, m[0]);
                        int iy1 = Math.max(fy1, m[1]);
                        int ix2 = Math.min(fx2, m[2]);
                        int iy2 = Math.min(fy2, m[3]);

                        if(ix1 >= ix2 || iy1 >= iy2){
                            // 겹치지 않음 - 그대로 유지
                            nextFragments.add(frag);
                        } else {
                            // 차집합 결과 (최대 4개 조각)
                            if(fy1 < iy1) nextFragments.add(new int[]{fx1, fy1, fx2, iy1}); // 하단
                            if(iy2 < fy2) nextFragments.add(new int[]{fx1, iy2, fx2, fy2}); // 상단
                            if(fx1 < ix1) nextFragments.add(new int[]{fx1, iy1, ix1, iy2}); // 좌측
                            if(ix2 < fx2) nextFragments.add(new int[]{ix2, iy1, fx2, iy2}); // 우측
                        }
                    }
                    fragments = nextFragments;
                    if(fragments.isEmpty()) break;
                }

                // 최종 남은 영역의 총 면적 계산
                long remainingArea = 0;
                for(int[] frag : fragments){
                    remainingArea += (long)(frag[2] - frag[0]) * (frag[3] - frag[1]);
                    minusList.add(new int[]{frag[0], frag[1], frag[2], frag[3]});
                }
                
                rect[5] -= remainingArea;
            }
            
            long size = (long)(x2-x1)*(y2-y1);
            list.add(new long[]{id, x1, y1, x2, y2, size});
        }

        Collections.sort(list, (o1, o2) -> {
            if(o2[5] != o1[5]){
                return Long.compare(o2[5], o1[5]);
            }
            return Long.compare(o1[0], o2[0]);
        });

        List<Integer> answerList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<K; i++){
            answerList.add((int)list.get(i)[0]);
        }
        Collections.sort(answerList);
        for(int i=0; i<K; i++){
            sb.append(answerList.get(i)).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}