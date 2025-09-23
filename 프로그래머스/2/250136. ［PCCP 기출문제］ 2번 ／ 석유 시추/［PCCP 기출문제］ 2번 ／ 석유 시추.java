import java.util.*;
class Solution {
    public int solution(int[][] land) {
        int answer = 0;

        int width  = land[0].length;
        int height  = land.length;

        Map<Integer, Integer> oilGroupSize = new HashMap<>(); // 그룹ID -> 크기
        List<Set<Integer>> columnToGroups = new ArrayList<>(); // 각 열 -> 해당 열의 그룹ID들
        for(int i = 0; i < width; i++) {
            columnToGroups.add(new HashSet<>());
        }
        int groupId = 1;  

        boolean[][] foundOil = new boolean[land.length][land[0].length]; 
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(land[y][x] == 1 && !foundOil[y][x]) {
                    int oilSize = findConnOil(x, y, land, foundOil, groupId, oilGroupSize, columnToGroups);
                    //System.out.println(oilSize);
                    groupId++;
                }
            }
        }
        for(int c = 0; c < width; c++) {
            int totalOil = 0;
            for(int gId : columnToGroups.get(c)) {
                totalOil += oilGroupSize.get(gId);
            }
            answer = Math.max(answer, totalOil);
        }


        return answer;
    }

    public int findConnOil(int x, int y, int[][] land, boolean[][] foundOil, int groupId, Map<Integer, Integer> oilGroupSize, List<Set<Integer>> columnToGroups){
        int localFind = 1;
        int[] dx = {1,-1, 0, 0};
        int[] dy = {0, 0, 1,-1};

        Queue<int[]> q = new LinkedList<>();
        int[] pos = {x,y};
        foundOil[y][x] = true;
        columnToGroups.get(x).add(groupId); // 시작점의 열에 그룹 추가
        q.offer(pos);

        while(!q.isEmpty()){
            int[] now = q.poll();
            int nx = now[0];
            int ny = now[1];
            for(int direction = 0; direction < 4; direction++){
                int sumX = nx+dx[direction];
                int sumY = ny+dy[direction];
                if(sumY >= land.length || sumX >= land[0].length
                    || sumY < 0 || sumX < 0){ //바깥 탈출
                    continue;
                }
                if(land[sumY][sumX] == 0 || foundOil[sumY][sumX] ){ //벽 or 방문함
                    continue;
                }else{
                    foundOil[sumY][sumX] = true;
                    columnToGroups.get(sumX).add(groupId); // 해당 열에 이 그룹 추가
                    localFind++;
                    q.offer(new int[]{sumX,sumY});
                }
            }  
        }
        //System.out.println("깊이: "+ y + ", 열: "+ x + " 찾은 수 : "+ localFind);
        oilGroupSize.put(groupId, localFind);
        return localFind;
    }
    /**
     * y축으로 수직의 선에서 1을 만나면 해당 위치에서 만날 수 있는 1의 수를 받으면 되는데
     * visit으로 방문처리
     * 
     * 효율성 에러 > 그러면 찾은건 그냥 넘어가야하는데 방법은? 찾는 순서로 size, 걸치는 x,y값들을 넣어놓고?
     * 그럼 map, set으로 한번 찾은 건 저장하고 visit으로 패스해야할듯.. oil이 x*y급 1덩어리면 x*x*y bfs 최악이 좀 큰 듯
     */
}