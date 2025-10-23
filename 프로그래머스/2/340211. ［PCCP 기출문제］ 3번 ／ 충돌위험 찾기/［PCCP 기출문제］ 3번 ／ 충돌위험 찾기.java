import java.util.*;
class Solution {
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;

        int robotCnt = routes.length; // 로봇 수
        int waypoint = routes[0].length; // 이동해야하는 포인트 수, 0은 시작  waypoint >=2
        int[][] nowGoal = new int[robotCnt][1]; //로봇이 현재 [0]번째 way에서 [1]번째 way로이동중
        int[][] robotsPos = new int[routes.length][2]; //로봇의 위치 갱신(y,x)
        boolean allArrive = false; //전부 도착 여부
        boolean[] arrivedRobots = new boolean[robotCnt]; // 도착 여부(도착 후 이동 불필요);

        for(int i=0; i<robotCnt; i++){ //초기 pos 삽입
            robotsPos[i][0] = points[routes[i][0]-1][0];
            robotsPos[i][1] = points[routes[i][0]-1][1];
            nowGoal[i][0] = 1;
        }
        answer += isCrash(robotsPos, arrivedRobots);

        int turn = 0;
        while(!allArrive){//전부 도착  전까지
            turn++; //턴 증가
            int localArrive = 0;
            for(int i=0; i<robotCnt; i++){ //로봇들 하나씩 이동.
                //이동 후 체크가 아닌 도착 후 다음 턴에 체크해야함. 최종 도착점도 충돌로 간주하기 떄문에
                if((nowGoal[i][0]) == waypoint){ //마지막 웨이포인트의 도착점 도착(로봇 제외) 
                    arrivedRobots[i] = true;
                }
                if(arrivedRobots[i]){ //도착 후 이동 불필요
                    localArrive++;
                    if(localArrive == robotCnt){
                        allArrive = true;
                    }
                    continue;
                }
                int robotY = robotsPos[i][0];
                int robotX = robotsPos[i][1];
                int goal = nowGoal[i][0];

                int destinationY = points[routes[i][goal]-1][0];
                int destinationX = points[routes[i][goal]-1][1];
                
                ///목적지가 더크면(+1), 같으면 정지(0), 작으면(-1)
                int dy = destinationY - robotY > 0 ? 1 : destinationY - robotY == 0 ? 0 : -1;
                //수직 이동이 있으면 정지(0), 수직이동이 없고 목적지가 크면(+1)  같으면 정지(0), 작으면 (-1);
                int dx = dy == 0 ? destinationX - robotX > 0 ? 1 : destinationX - robotX == 0 ? 0 : -1 : 0;
                
                int moveX = robotX+dx;
                int moveY = robotY+dy;
                
                if(moveY == destinationY && moveX == destinationX){ //웨이포인트 도착
                    nowGoal[i][0] = goal+1; //도착해야할 웨이포인트 갱신
                }
                robotsPos[i][0] = moveY;
                robotsPos[i][1] = moveX;
                
            }
            
            //로봇 1칸씩 이동 완료 후 계산
            long cnt = isCrash(robotsPos, arrivedRobots);
            if(cnt>0){
                answer+=cnt;
                //System.out.printf("Turn: %d에 %d증가, 누적 = %d \n", turn, cnt,answer);
            }
        }    

        return answer;
    }

    private long isCrash(int[][] robotsPos, boolean[] arrivedRobots){
        Map<Long, Integer> countMap = new HashMap<>();

        for(int i=0; i<robotsPos.length; i++){
            int[] pair = robotsPos[i];
            if(arrivedRobots[i]) continue; //도달 로봇 제외하고
            //[0]은 왼쪽으로 시프트하고 [1]그대로  롱타입비트로 합치기
            long key = ((long) pair[0] << 32) | (pair[1] & 0xFFFFFFFFL);
            countMap.put(key, countMap.getOrDefault(key, 0) + 1);
        }

        // 충돌한 좌표의 개수
        long duplicateCount = countMap.values().stream()
            .filter(count -> count > 1)
            .count();

        return duplicateCount;
    }
    /*
     * 일단 point에서 0번쨰 row, col은 벽임.
     * routes[i][0]에서 routes[i][1]로 이동해야함.
     * 최단거리로 이동 = 좌표 도착-출발(양,음)방향으로 수직,수평 방향으로만 이동하고, 수직을 우선순위로 함
     * 도착하면 제외.
     * 조건은
     * - 도착 좌표에 2대 모이면 충돌, 
     * - n대가 모여도 1번의 충돌 횟수로 계산
     * - 출발 지점에서부터도 충돌 횟수로 계산 <--
     * - 도착 지점도 충돌 횟수 계산 <--
     * + 다른 좌표 = 다른 충돌.
     * 
     * 각 turn마다 
     * routes.length만큼 모두가 한번 이동 후에, 해당 로봇 좌표 체크
     * 일단 최적의 경로로 이동할 것(상하 우선 상하좌우이동)
     * 그럼 이동 각 턴마다 겹치는 좌표가 있으면 count++;
     * 좌표 유니크한 수 체크는 음 문자열 파싱? 아니면 비트화?
     * 이동 후 바로 체크 말고, 이동 후, 다음 이동 시 지금 도착 상태인지로 해야 도착지점 체크하고
     * 놓여지는 시점은 반복문 전으로 체크
     */
}