class Solution {
    public int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];

        for(int i=0; i<numbers.length; i++){
            long num = numbers[i];
            String binaryString = getPerfectBinaryTree(num);
            
            if(canMakeNode(binaryString)){
                answer[i] = 1;
            }else{
                answer[i] = 0;
            }
        }
        return answer;
    }

    // 이진수로 변환 및 포화 이진트리 (0추가)
    public String getPerfectBinaryTree(long num) {
        String binaryString = Long.toBinaryString(num);
        int binLen = binaryString.length();

        // 포화 이진트리 길이: 2^k - 1 중 bLen 이상인 최소 값
        int realLen = ((binLen & (binLen + 1)) == 0) ? binLen : (Integer.highestOneBit(binLen) * 2 - 1);

        // 앞쪽에 '0' 채우기
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < realLen - binLen; i++){
            sb.append('0');
        }
        sb.append(binaryString);

        return sb.toString();
    }

    public boolean canMakeNode(String binaryString){
        int totalLength = binaryString.length();    
        
        // 길이가 1이면 무조건 true (단일 노드)
        if(totalLength == 1){
            return true;
        }
        
        int halfLength = totalLength / 2;
        int middleBitPos = halfLength; // 중간 비트 위치
        
        // 좌측 절반 (0 ~ halfLength-1)
        String leftHalf = binaryString.substring(0, halfLength);
        
        // 중간 비트 (halfLength 위치)
        char middleBit = binaryString.charAt(middleBitPos);
        
        // 우측 절반 (halfLength+1 ~ 끝)
        String rightHalf = binaryString.substring(halfLength + 1);
        
        // 중간 비트가 0이면 전체가 0이어야 함
        if(middleBit == '0'){ 
            // 전체 문자열이 모두 '0'인지 확인
            for(int i = 0; i < totalLength; i++){
                if(binaryString.charAt(i) == '1'){
                    return false;
                }
            }
            return true;
        }
        
        // 중간 비트가 1이면 좌우 서브트리 재귀 검사
        if(canMakeNode(leftHalf) && canMakeNode(rightHalf)){
            return true;
        }else{
            return false;
        }
    }
    
    /*
     * 
     * 1 ≤ numbers의 길이 ≤ 10,000
     * 1 ≤ numbers의 원소 ≤ 10^15 1,000,000,000,000,000 // 1000조. ~= 2^50
     * 
     * 대전제 1. 루트노드는 1이어야함 
     *      = 2진법으로 표현 시 정중앙이 1이어야함
     * 
     * 대전제 2. '00'으로 0이 2번이어지는 경우 끊긴 부분이 있음..대충 dfs하면 알아서 걸러질 듯 
     * 63 > 11 1111  011 1 111
     *          
     * 
     * => 루트노드가 1이라는 전제하에 좌측, 우측 노드로 나눈다는 것은?
     * 그리고일단 포화 이진트리가 필요함
     * 
     */
}