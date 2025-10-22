import java.util.function.Function;

class Solution {
    public int solution(int n, Function<Integer, String> submit) {
        for(int i = 1000; i <= 9999; i++)
            if (submit.apply(i).equals("4S 0B")) return i;
        return 0;
    }
}