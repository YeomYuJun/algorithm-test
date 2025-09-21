class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        java.util.PriorityQueue<Integer> pq = new java.util.PriorityQueue<>();
        for(int s : scoville){
            pq.offer(s);
        }
        while(pq.size() > 1 && pq.peek() < K){
            int first = pq.poll();
            int second = pq.poll();
            int mixed = mixScoville(first, second);
            pq.offer(mixed);
            answer++;
        }
        if(pq.peek() < K) return -1;
        return answer;
    }
    public int mixScoville(int a, int b){ // a는 가장 낮은 스코빌, b는 두번째로 낮은 스코빌
        return a + (b * 2);
    }
}