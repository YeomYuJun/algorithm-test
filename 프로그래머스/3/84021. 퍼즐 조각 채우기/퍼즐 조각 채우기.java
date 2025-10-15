import java.util.*;
class Solution {
    public int solution(int[][] game_board, int[][] table) {
        int answer = -1;
        int len = game_board.length;

        boolean[][] visited = new boolean[len][len];
        boolean[][] slotVisited = new boolean[len][len];
        
        //List<int[][]> blocks = new ArrayList<>();
        List<List<int[]>> blocks = new ArrayList<>();
        List<List<int[]>> slots = new ArrayList<>();
        //블럭 찾기 + 슬롯 찾기
        for(int i=0; i<len; i++){
            for(int j=0; j<len; j++){
                //블럭 찾기 영역
                if(visited[i][j]){
                    //기방문 테이블   
                }else{
                    if(table[i][j] == 1){//블럭 발견
                        //int[][] block = new int[len][2];
                        List<int[]> block = new ArrayList<>();
                        makeBlock(visited, table, i, j, 0, block, 1, 0);
                        blocks.add(block);
                    }
                }

                //슬롯 찾기 영역
                if(slotVisited[i][j]){
                    //기방문 테이블   
                }else{
                    if(game_board[i][j] == 0){//블럭 발견
                        List<int[]> slot = new ArrayList<>();
                        makeBlock(slotVisited, game_board, i, j, 0, slot, 0, 1);
                        slots.add(slot);
                    }
                }

                
            }
        }
        
        int count = findFitBlockInSlot(slots, blocks);
        //출력 디버깅
        /*
        for(int i=0; i<blocks.size(); i++){
            List<int[]> shape = blocks.get(i);
            for(int j=0; j<shape.size(); j++){
                System.out.printf(shape.get(j)[0] + ", " + shape.get(j)[1] + " | ");

            }
            System.out.println("");
        }
        */
        answer = count;
        return answer;
    }
    public int findFitBlockInSlot(List<List<int[]>> slots , List<List<int[]>> blocks ){
        int fitCount = 0;
        
        Loop1: 
        for(List<int[]> slot : slots){
            List<int[]> normalSlot = normalize(slot);
            int bSize = blocks.size();
            if(bSize == 0){ //블럭 전부 사용
                break;
            }

            Loop2:
            for(int b = 0; b<bSize; b++){
                List<int[]> block = blocks.get(b);
                

                if(block.size() != slot.size()){ //사이즈 같음= 최소 조건
                    continue;//다음 블럭 비교
                }

                for(int d = 0; d<4; d++){ // 0, 90, 180, 270 degree
                    List<int[]> turnedBlock = turn90DegreeBlock(block, d);
                    List<int[]> turnedNormalBlock = normalize(turnedBlock);

                    boolean isEqual = false;
                    for(int i=0; i<normalSlot.size(); i++){
                        //int[] centerSlotPos = new int[]{slot.get(i)[0] - slot.get(0)[0], slot.get(i)[1] - slot.get(0)[1]};
                        //System.out.println(Arrays.toString(normalSlot.get(i)) + " vs " + Arrays.toString(turnedNormalBlock.get(i))) ;
                        

                        //if(Arrays.equals(centerSlotPos, turnedBlock.get(i))){ 에서 같은 도형임에도 좌표 순서 및 정확한 중심점 차이로 인한 오류 발생
                        //최소값 빼고 맞춰야함.
                        if(Arrays.equals(normalSlot.get(i), turnedNormalBlock.get(i))){
                            isEqual = true;
                        }else{
                            isEqual = false;
                            break;
                        }
                    }
                    if(isEqual){
                        fitCount += slot.size();
                        blocks.remove(b); //사용한 블럭 제거
                        bSize--;
                        b--;
                        continue Loop1;
                    }
                }
                
            }
            
            //System.out.println("===SLOT END===");
        }
        

        return fitCount;
    }

    public void makeBlock(boolean[][] visited, int[][] table, int i, int j, int count, List<int[]> block, int yes, int no){ 
        Queue<int[]> q = new LinkedList<>();
        
        
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        block.add(new int[]{i,j});
        //block[c] = new int[]{i,j};
        visited[i][j] = true;
        q.offer(new int[]{i,j,count});

        while(!q.isEmpty()){
            int[] pos = q.poll();
            int y = pos[0];
            int x = pos[1];
            int c = pos[2];

            for(int direction = 0; direction < 4; direction++){
                int newX = x + dx[direction];
                int newY = y + dy[direction];

                if(newX < 0 || newX >= table.length
                    || newY < 0 || newY >= table.length){ //외곽
                        continue;
                }
                if(visited[newY][newX]){ //다음 영역 방문함
                    continue;
                }
                if(table[newY][newX] == no){ //벽 table = 0, board = 1 
                    visited[newY][newX] = true; //벽 방문할 필요 x
                    continue;
                }
                if(table[newY][newX] == yes){ //연결부 table = 1, board = 0;
                    visited[newY][newX] = true; //간 곳 방문할 필요 x
                    //block[c+1] = new int[]{newY,newX};
                    block.add(new int[]{newY,newX});
                    q.offer(new int[]{newY, newX,c+1});
                }
            }
        } //블럭 방문 완료(벽까지)
    }

    public List<int[]> turn90DegreeBlock(List<int[]> block, int degree){
        List<int[]> turnedBlock = new ArrayList<>();
        for(int[] cell : block){
            int y = cell[0] - block.get(0)[0]; //y = row (0,0을 기준으로 만들기 위해 감소) 
            int x = cell[1] - block.get(0)[1]; //x = col

            int newX = 0;
            int newY = 0;
            
            if(degree == 0){ //0도 제자리
                newY = y;
                newX = x;
            } else if (degree == 1) {        // 90도 시계방향
                newX = y;
                newY = -x;
            } else if (degree == 2) {  // 180도
                newX = -x;
                newY = -y;
            } else if (degree == 3){                     // 270도 시계방향
                newX = -y;
                newY = x;
            }
            
            //System.out.printf("%d도: (%d, %d) -> (%d, %d)\n", degree * 90, y, x, newY, newX);
            
            turnedBlock.add(new int[]{newY, newX});
        }
        return turnedBlock;
    }

    public List<int[]> normalize(List<int[]> coords) {
        if (coords.isEmpty()) return coords;
        
        // 1. 최소 y, x 찾기
        int minY = Integer.MAX_VALUE;
        int minX = Integer.MAX_VALUE;
        for (int[] coord : coords) {
            minY = Math.min(minY, coord[0]);
            minX = Math.min(minX, coord[1]);
        }
        
        // 2. 정규화
        List<int[]> normalized = new ArrayList<>();
        for (int[] coord : coords) {
            normalized.add(new int[]{coord[0] - minY, coord[1] - minX});
        }
        
        // 3. 정렬 (y 우선, 그 다음 x 사전순)
        normalized.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        return normalized;
    }



    /*
     * table에서 만나면 bfs 블럭 찾고
     * 모양이 중요하니까 블럭좌표들을 모아놓고(뒤집을 수 없음);
     * 
     * turn 하는 메서드 만들어두고, 보드에서 빈공간 만나면
     * bfs로 재탐색,  보드에 조각을 1개씩 놓을 때 빈공간이 있으면 안된다. = 모양이 정확히 일치해야한다
     * 빈공간 좌표들 탐색 => 블럭 x 4(0,90,180,270도 회전 유형) 
     * 찾아놓은 블럭도 좌표인데, 흠 같은지 보려면 
     * 0번째 index의 좌표값을 0으로 두고 gap만큼 다음 좌표들을 빼면 x,y값이 +-1일 수 밖에 없으니 통일될 듯.
     * 블럭 모양을 유지하는, 좀 더 간단한 방법이 있을 거 같은데...
     * 
     * 예를들어 ㅗ 모양의 블럭 = 기준블럭{0,0}으로부터, {{0,1},{0,2},{1,1}} 인데
     * 회전을 하면 {{0,1},{0,2},{1,1}} : ㅏ
     * 또 회전하면 {{0,-1},{0,-2},{1,-1}} : ㅜ
     * 또 회전하면 {{-1,0},{-2,0},{-1,-1}} : ㅓ 
     * 
     * 
     * 보드도 놓을 수 없는 공간, 찾았으나 놓을 수 없는 공간, 놓은 공간 전부 방문처리를하고,
     * 처음에 놓을 공간 발견 후 queue가 비면, empty면, 해당 좌표들 리턴.
     * 
     * makeBlock과 똑같지 않나 재사용하고
     * 
     * 정확히 일치하는 블럭을 놓는 게 최적의 해. 라고 한다면.. 여기까지?
     * 근데 turn한 게 음수 좌표가 나오고, 중심점이 {0,0}이 사실상 블럭의 끝이아닌 중간 같은 경우 다르게 나옴
     * 정규화해야할듯
     */
}