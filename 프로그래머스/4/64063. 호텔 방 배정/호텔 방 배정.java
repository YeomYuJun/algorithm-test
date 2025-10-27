import java.util.*;
class Solution {
    public long[] solution(long k, long[] room_number) {
        long[] answer = new long[room_number.length];
        Map<Long,Long> hashMap = new HashMap<>();

        for(int i = 0; i<room_number.length; i++){//인당 1번씩은 돌아야지..20만번? 
            long needK = room_number[i];
            long assignRoom = findEmptyRoom(needK, hashMap);
            answer[i] = assignRoom;
            hashMap.put(assignRoom, assignRoom + 1); // 배정된 방의 다음 빈 방으로 갱신
        }
        return answer;
    }
    private long findEmptyRoom(long needK, Map<Long,Long> map){
        List<Long> visited = new ArrayList<>();
        
        while(map.getOrDefault(needK, 0L) != 0L){
            //needK를 다음 찾기, 예를 들어 1000을 찾았더니 있네? 1000:1001 이었던 것 -> get(1001)로보니까 비어서, 1001에 배정. 
            //다시 needK가 1000일 때 get(1002)했더니 비어서 1002를 배정.. 반복?
            //아 근데 여기서 while에서 찾아본 수들 전부 최종 needK+1로 업데이트해줘야 while 덜 돌겠다
            visited.add(needK);
            needK = map.get(needK);
        }
        for(Long key : visited){
            map.put(key, needK+1);
        }
        return needK;
    }
    /**
     * 1<= k <= 10^12 이니까 , 단순 1회만 반복해도 안될듯. 1조 개.  logk <= 12..
     * 손님의 room 길이보다 k가 작은 경우는 없으므로 괜찮음.
     * 
     * 가장 먼저 생각나는건.. boolean인데, 아니면 Set으로 ? 
     * 아니면 room 순회 하다가 겹치는 수 Set으로 배정하다가 나오면 ++ 
     */
}