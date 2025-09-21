function solution(word) {
    var answer = 0;
    var order = ['a','e','i','o','u'];
    /*
    우선 순위 
    logic 1. 알파벳 : a e i o u = 알파벳 순, 컴퓨터속 정렬도 마찬가지
    logic 2. 길이 : 길이가 짧을수록, 즉 index 0~4중 0이 2번째가 되면 5자리의 경우의 수
    => 중복 허락 5개 뽑는 중복순열
    
    arr = [a,e,i,o,u];
    625,125,25,5,1
    
    first index
    = f(x) = 781x+1,
    
    second index
    = g(x) = 156x + 1
    
    third index
    = p(x) = 31x + 1
    
    fourth index
    = q(x) = 5x + 1
    
    fifth index
    = t(x) = x
    
    answer = f(x) + g(x) + p(x) + q(x) + t(x);
    */
    function idxfn(idx, t){
        if(idx===0){
            return 781 * t + 1;
        } else if( idx === 1 ) {
            return 156 * t + 1;
        } else if( idx === 2 ) {
            return 31 * t + 1;
        } else if( idx === 3 ) {
            return 6 * t + 1;
        } else if( idx === 4 ) {
            return t + 1;
        }
        
        
    }
    
    var arr = word.toLowerCase().split('');
    
    for(let i=0; i < arr.length; i++ ){
        const char = arr[i];
        const charIdx = order.indexOf(char);
        let tempNum = idxfn(i,charIdx);
        answer += tempNum;
    }
    return answer;
}