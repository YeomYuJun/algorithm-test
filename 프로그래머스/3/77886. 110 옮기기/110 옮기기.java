class Solution {
    public String[] solution(String[] s) {
        String[] answer = new String[s.length];

        for(int i=0; i<s.length; i++){
            String str = s[i];
            int ooz = 0; //oneonezero
            StringBuilder sb = new StringBuilder();

            for(char c : str.toCharArray()){
                sb.append(c);
                int length = sb.length();
                if(length >= 3){
                    if( sb.charAt(length-3) == '1' && sb.charAt(length-2) == '1' && sb.charAt(length-1) == '0'){
                        sb.delete(length-3, length); // start The beginning index, inclusive. / end The ending index, exclusive.
                        ooz++;
                    }
                }
            }
            int oi = sb.lastIndexOf("0");
            String oozStr = "110".repeat(ooz);
            sb.insert(oi+1, oozStr);//The offset argument must be greater than or equal to 0, and less than or equal to the length of this sequence.
            answer[i] = sb.toString();
        }
        return answer;
    }
    /*
     * 순서가
     * 000 > 001 > 010 > 011 > 100 > 101 > 110 > 111 와 같음.
     * => 1앞, 0뒤 
     */
}