import java.util.*;
class Solution {
    /*
    public static void main(String[] args){
        Solution sol = new Solution(); 
        long n = 30;
        String[] bans = {"d", "e", "bb", "aa", "ae"}; // "ah"
        long n2 = 7388;
        String[] bans2 = {"gqk", "kdn", "jxj", "jxi", "fug", "jxg", "ewq", "len", "bhc"}; // "jxk";
        long 3 = 27;
        String[] bans3 = {"ab","z"}; //"ac"

        try{
            // System.out.println(sol.solution(n, bans));
            // System.out.println(sol.solution(n2, bans2));
        }catch(Exception e){
            e.printStackTrace();
        }
    }  
    */
    public String solution(long n, String[] bans) {
        String answer = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        
        int jinbup = 26; 
        
        long[] bansIndex = new long[bans.length];
        long belowN = 0;
        
        //오름차순해야 낮은거부터 감지함
        Arrays.sort(bans, (a, b) -> {
            if (a.length() != b.length()) {
                return Integer.compare(a.length(), b.length());
            }
            return a.compareTo(b); // 같은 길이면 사전순
        });
        
        for(int i=0; i<bans.length; i++){ // 각 글자 ban의 정확한 위치 구하기 
            String ban = bans[i];
            int jarisu = ban.length();
            
            long jariIndex = 0;
            for(int ci = 0; ci<ban.length(); ci++){
                long a = ((int)ban.charAt(ci) - 96) * ((long)Math.pow(jinbup, (jarisu-ci-1)));  //a 의 경우 97이니까 1로 만들기 * n번째 26진법 
                jariIndex += a;
            }
            bansIndex[i] = jariIndex;
            //System.out.println(ban + "의 index : " + jariIndex); 낮은 순서부터 제거해야 이미 넘어간 다음이 정확히 포함됨. => sorting 해야할듯


            if(jariIndex <= (n+belowN)) { // 같아도 다음으로 넘어가니까 포함이고, 이미 넘어간 다음이 또 포함인지도?
                belowN++; //포함되는 숫자 = n번째 단어에 영향을 주는 것만 포함 
            }
        }
        //System.out.println(Arrays.toString(bansIndex) + ", belowN: " + belowN);
        long realN = n+belowN;
        StringBuilder sb = new StringBuilder();
        while (realN > 0) {
            realN--;
            sb.append(alphabet.charAt((int)(realN%jinbup)));
            realN /= jinbup;
        }
        answer = sb.reverse().toString();
        return answer;
    }
    /*
     * 1<= n <= 10^15 = 1,000,000,000,000,000 = 1천조? 2^49 ~ 2^50 정도..
     * 알파벳 수(a~z) = 26개이므로 26진법이라고 보면 26^14 ~ 26^15 사이
     * 
     * 즉, n이 26^x 보다 작을 경우 x자리수.
     * 좀 더 가면 26^(x-1) < n+bans.length <= 26^x 일 경우 x자리수  (x >=1 이며, bans는 n보다 작은 것으로 filtering하면 될 듯?)
     * 
     * bans 원소 길이 <= 26^11 
     * 
     * 그러면 26, 676, 17576, ... (26^1 , 26^2, 26^3, .. )으로 자리가 되면
     * 15개 정도면 구해놔도 될 거 같은데               
     * 
     * 1번 문제는
     *  34(n:30 + 30보다 작은 bans.length:4) >> 34/26 = 8 , abcdefg'h'= 첫번째 글자
     *  34/26 = 1, 'a' 첫번째 글자\
     */
}