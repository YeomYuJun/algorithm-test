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
        //                 "0 5 4 5 4 5 4 5\r\n" + //
        //                 "4 0 5 1 2 3 4 5\r\n" + //
        //                 "9 8 0 1 2 3 1 2\r\n" + //
        //                 "9 9 9 0 9 9 9 9\r\n" + //
        //                 "1 1 1 1 0 1 1 1\r\n" + //
        //                 "8 7 6 5 4 0 3 2\r\n" + //
        //                 "9 1 9 1 9 1 0 9\r\n" + //
        //                 "6 5 4 3 2 1 9 0"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        int size = N/2;

        int[][] table = new int[N][N];
        List<Integer> player = new ArrayList<>();
        for(int i=0; i<N; i++){
            player.add(i);
            String[] row = args[i+1].split(" ");
            for(int j=0; j<N; j++){
                table[i][j] = Integer.parseInt(row[j]);
            }
        }

        List<List<Integer>> result = combinations(N-1, size-1);
        int defaultRedPlayer = 0;

        int min = Integer.MAX_VALUE;
        for(List<Integer> redTeam : result){
            redTeam.add(defaultRedPlayer);

            List<Integer> blueTeam = new ArrayList<>(player);
            blueTeam.removeAll(redTeam);

            int redTotalAbil = getTeamAbility(table, redTeam);
            int blueTotalAbil = getTeamAbility(table, blueTeam);

            int gap = Math.abs(redTotalAbil - blueTotalAbil);
            min = Math.min(min,gap);
        }
        System.out.println(min);
    }
    public static int getTeamAbility(int[][] table, List<Integer> team){
        int sum = 0;
        for(int i=0; i<team.size()-1; i++){
            int playerA = team.get(i);
            for(int j=i+1; j<team.size(); j++){
                int playerB = team.get(j);
                sum += table[playerA][playerB];
                sum += table[playerB][playerA];
            }
        }
        return sum;
    }
    private static void backtrack(List<List<Integer>> result, List<Integer> current, int start, int n, int r) {
        if (current.size() == r) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrack(result, current, i + 1, n, r);
            current.remove(current.size() - 1);
        }
    }

    public static List<List<Integer>> combinations(int n, int r) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), 1, n, r);
        return result;
    }
    /**
     * 결국 조합으로 다 골라봐야할듯?
     * n 콤비 (n/2) = 
     * n! /  (n/2)! * (n/2)!
     * 20팩토리얼 나누기 10팩 10팩
     * 나머지 = 19*17*13*4*11*  = 184756 조합이고, 어차피 sum값은 나오니까
     * 아 근데 한 팀에 무조건 1은 포함될테니 1이 들어가는 모든 경우의 수만 하면 되는 거 아닌가
     * 6개 숫자중 2개. 1을 제외한 = N-1 콤비네이션 size-1 = 92378
     *   
     */
    //123 124 125 126 134 135 136 145 146 156
    //234 235 236 245 246 256
    //345 346 356
    //456

}