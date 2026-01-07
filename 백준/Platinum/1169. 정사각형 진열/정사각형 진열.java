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
        
        // str = "5\r\n" + //
        //                 "5 1 1 1 1";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    static class Square {
        int id;
        int side;
        int x, left, right;

        Square(int id, int side) {
            this.id = id;
            this.side = side;
        }
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        String[] row = args[1].split(" ");

        Square[] squares = new Square[N];
        for (int i = 0; i < N; i++) {
            squares[i] = new Square(i + 1, Integer.parseInt(row[i]));
        }
        
        //각 정사각형의 중심 x 좌표 및 좌우 끝 좌표 계산
        for (int i = 0; i < N; i++) {
            int currentX = squares[i].side;
            for (int j = 0; j < i; j++) {
                int dist = 2 * Math.min(squares[i].side, squares[j].side);
                currentX = Math.max(currentX, squares[j].x + dist);
            }
            squares[i].x = currentX;
            squares[i].left = currentX - squares[i].side;
            squares[i].right = currentX + squares[i].side;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            List<int[]> intervals = new ArrayList<>();
            intervals.add(new int[]{squares[i].left, squares[i].right});

            for (int j = 0; j < N; j++) {
                if (i == j) continue;
                
                // y축 위쪽에서 보기 때문에, 더 높은 것만 가림막 역할을 함,
                if (squares[j].side <= squares[i].side) continue;

                List<int[]> nextIntervals = new ArrayList<>();
                int otherL = squares[j].left;
                int otherR = squares[j].right;

                for (int[] interval : intervals) {
                    int currL = interval[0];
                    int currR = interval[1];

                    if (otherR <= currL || otherL >= currR) {
                        nextIntervals.add(new int[]{currL, currR});
                    } else {
                        if (otherL > currL) nextIntervals.add(new int[]{currL, otherL});
                        if (otherR < currR) nextIntervals.add(new int[]{otherR, currR});
                    }
                }
                intervals = nextIntervals;
                if (intervals.isEmpty()) break;
            }

            if (!intervals.isEmpty()) {
                result.add(squares[i].id);
            }
        }

        Collections.sort(result);
        StringBuilder sb = new StringBuilder();
        for(Integer rs : result){
            sb.append(rs).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
    /**
     * 높이가..부동소수점, i+1이 i번째가 y축에서 보이는 지 안보이는지에 대해 영향을 주지않는듯
     * 정사각형이 x축에 접하는 점, 그리고 오른쪽 꼭짓점 위치만 잘 구하면?
     * 
     * 해당 정사각형에서 y축과 닫는지 확인하는 방법? 기울기가 거의 수직(\)인 경우 가능 
     * 현재 상자 좌,우에서 돔형태로 감싸는지? 체크 
     *  
     */

}
