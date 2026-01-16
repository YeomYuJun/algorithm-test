import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        ////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
            
        ///////////////////////////////////////////
        
        // str = "abcdef\r\n" + //
        //                 "3\r\n" + //
        //                 "cab\r\n" + //
        //                 "def\r\n" + //
        //                 "fedabc"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    
    public static void solution(String[] args) throws IOException {
        String sentence = args[0];
        int N = Integer.parseInt(args[1]);

        String[] words = new String[N];
        char[] sen = sentence.toCharArray();
        int senSize = sen.length;

        for(int i = 0; i < N; i++){
            words[i] = args[i + 2];
        }
        
        int INF = 1_000_000;
        int[][] dp = new int[senSize + 1][senSize + 1];
        for(int i = 0; i <= senSize; i++){
            Arrays.fill(dp[i], INF);
        }
        
        // 길이별로 처리
        for(int len = 1; len <= senSize; len++){
            for(int start = 0; start <= senSize - len; start++){
                int end = start + len;
                
                // 각 단어로 매칭 시도
                for(String word : words){
                    if(word.length() != len) continue;
                    
                    // 애너그램 체크 (같은 문자로 구성되어 있는지)
                    if(isAnagram(sen, start, end, word)){
                        int cost = calculateCost(sen, start, end, word);
                        dp[start][end] = Math.min(dp[start][end], cost);
                    }
                }
                
                // 구간 분할
                if(len > 1){
                    for(int mid = start + 1; mid < end; mid++){
                        if(dp[start][mid] != INF && dp[mid][end] != INF){
                            dp[start][end] = Math.min(dp[start][end], 
                                                      dp[start][mid] + dp[mid][end]);
                        }
                    }
                }
            }
        }
        
        int ans = dp[0][senSize] >= INF ? -1 : dp[0][senSize];
        System.out.println(ans);
    }
    
    // 애너그램 체크: 같은 문자들로 구성되어 있는지
    private static boolean isAnagram(char[] sen, int start, int end, String word){
        int len = end - start;
        if(word.length() != len) return false;
        
        int[] freq1 = new int[26];
        int[] freq2 = new int[26];
        
        for(int i = start; i < end; i++){
            freq1[sen[i] - 'a']++;
        }
        
        for(char c : word.toCharArray()){
            freq2[c - 'a']++;
        }
        
        return Arrays.equals(freq1, freq2);
    }
    
    // 정위치가 아닌 문자 개수 계산
    private static int calculateCost(char[] sen, int start, int end, String word){
        int cost = 0;
        char[] wordArr = word.toCharArray();
        
        for(int i = 0; i < word.length(); i++){
            if(sen[start + i] != wordArr[i]){
                cost++;
            }
        }
        
        return cost;
    }
    /**
     * N개의 단어가 각 단어별 포지션에서의 포인트? 를 구해야하나
     */

}