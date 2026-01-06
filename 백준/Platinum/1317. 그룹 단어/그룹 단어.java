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
        
        // str = "50\r\n" + //
        //                 "aaaaaaaaaaaaaaaaaa\r\n" + //
        //                 "bbbbbbbbbbbbbbbbbb\r\n" + //
        //                 "cccccccccccccccccc\r\n" + //
        //                 "dddddddddddddddddd\r\n" + //
        //                 "eeeeeeeeeeeeeeeeee\r\n" + //
        //                 "ffffffffffffffffff\r\n" + //
        //                 "gggggggggggggggggg\r\n" + //
        //                 "hhhhhhhhhhhhhhhhhh\r\n" + //
        //                 "iiiiiiiiiiiiiiiiii\r\n" + //
        //                 "jjjjjjjjjjjjjjjjjj\r\n" + //
        //                 "kkkkkkkkkkkkkkkkkk\r\n" + //
        //                 "llllllllllllllllll\r\n" + //
        //                 "mmmmmmmmmmmmmmmmmm\r\n" + //
        //                 "nnnnnnnnnnnnnnnnnn\r\n" + //
        //                 "oooooooooooooooooo\r\n" + //
        //                 "pppppppppppppppppp\r\n" + //
        //                 "qqqqqqqqqqqqqqqqqq\r\n" + //
        //                 "rrrrrrrrrrrrrrrrrr\r\n" + //
        //                 "ssssssssssssssssss\r\n" + //
        //                 "tttttttttttttttttt\r\n" + //
        //                 "uuuuuuuuuuuuuuuuuu\r\n" + //
        //                 "vvvvvvvvvvvvvvvvvv\r\n" + //
        //                 "wwwwwwwwwwwwwwwwww\r\n" + //
        //                 "xxxxxxxxxxxxxxxxxx\r\n" + //
        //                 "yyyyyyyyyyyyyyyyyy\r\n" + //
        //                 "yzzzzzzzzzzzzzzzzz\r\n" + //
        //                 "aaaaaaaabbbbbbbbbb\r\n" + //
        //                 "bbbbbbbbcccccccccc\r\n" + //
        //                 "ccccccccdddddddddd\r\n" + //
        //                 "ddddddddeeeeeeeeee\r\n" + //
        //                 "eeeeeeeeffffffffff\r\n" + //
        //                 "ffffffffgggggggggg\r\n" + //
        //                 "gggggggghhhhhhhhhh\r\n" + //
        //                 "hhhhhhhhiiiiiiiiii\r\n" + //
        //                 "iiiiiiiijjjjjjjjjj\r\n" + //
        //                 "jjjjjjjjkkkkkkkkkk\r\n" + //
        //                 "kkkkkkkkllllllllll\r\n" + //
        //                 "llllllllmmmmmmmmmm\r\n" + //
        //                 "mmmmmmmmnnnnnnnnnn\r\n" + //
        //                 "nnnnnnnnoooooooooo\r\n" + //
        //                 "oooooooopppppppppp\r\n" + //
        //                 "ppppppppqqqqqqqqqq\r\n" + //
        //                 "qqqqqqqqrrrrrrrrrr\r\n" + //
        //                 "rrrrrrrrssssssssss\r\n" + //
        //                 "sssssssstttttttttt\r\n" + //
        //                 "ttttttttuuuuuuuuuu\r\n" + //
        //                 "uuuuuuuuvvvvvvvvvv\r\n" + //
        //                 "vvvvvvvvwwwwwwwwww\r\n" + //
        //                 "wwwwwwwwxxxxxxxxxx\r\n" + //
        //                 "xxxxxxxxyyyyyyyyyy";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        String NO = "gg";
        String MANY = "-_-";
        
        List<String> words = new ArrayList<>();

        for(int i=1; i<=N; i++){
            String word = args[i];
            if(!isGroupWord(word)){
                System.out.println(NO);
                return;
            }
            words.add(word);
        }

        // 문자별로 합치기
        for(char c = 'a'; c <= 'z'; c++){
            List<String> cFirst = new ArrayList<>();  // c로 시작
            List<String> cLast = new ArrayList<>();   // c로 끝
            List<String> cAll = new ArrayList<>();    // c로만 이루어짐
            
            Iterator<String> iter = words.iterator();
            while(iter.hasNext()){
                String word = iter.next();
                char first = word.charAt(0);
                char last = word.charAt(word.length()-1);
                
                if(first == c && last == c){
                    cAll.add(word);
                    iter.remove();
                } else if(first == c){
                    cFirst.add(word);
                    iter.remove();
                } else if(last == c){
                    cLast.add(word);
                    iter.remove();
                }
            }
            
            if(cFirst.isEmpty() && cLast.isEmpty() && cAll.isEmpty()) continue;
            
            // c로 시작/끝나는 단어가 2개 이상이면 여러 방법 존재
            if(cFirst.size() > 1 || cLast.size() > 1){
                System.out.println(NO);
                return;
            }
            
            // 합치기: cLast + cAll들 + cFirst
            StringBuilder newWord = new StringBuilder();
            if(!cLast.isEmpty()) newWord.append(cLast.get(0));
            for(String w : cAll) newWord.append(w);
            if(!cFirst.isEmpty()) newWord.append(cFirst.get(0));
            
            String combined = newWord.toString();
            if(!isGroupWord(combined)){
                System.out.println(NO);
                return;
            }
            
            words.add(combined);
        }
        
        //결과
        if(words.size() == 0){
            System.out.println(NO);
        } else if(words.size() == 1){
            System.out.println(words.get(0));
        } else {
            //모두 그룹 단어인지 확인
            StringBuilder allCombined = new StringBuilder();
            for(String w : words){
                allCombined.append(w);
            }

            if(isGroupWord(allCombined.toString())){
                System.out.println(MANY);
            } else {
                System.out.println(NO);
            }
        }
    }

    public static boolean isGroupWord(String word){
        char[] cList = word.toCharArray();
        Set<Integer> used = new HashSet<>();
        
        char cur = cList[0];
        used.add((int)cur);
        boolean isGroup = true;
        for(char c : cList){
            if(used.contains((int)c)){
                if(cur == c){
                    continue;
                }else{
                    isGroup = false;    
                    break;
                }
            }else{
                cur = c;
                used.add((int)cur);
            }
        }
        
        return isGroup;
    }
    /**
     * 그룹단어 판별을 쉬운데,
     * N = 50,  각 최대 20글자 최대 합 = 1000자.
     * 각 소단어 별 연결 가능여부 = word.charAt(0)?  
     * 같은 단어가 나올 수 있나? 일단 "시작단어" : "전체단어" 로 구성
     * 나올 수 있으면 used...
     * 만들 수 있는 경우의 수 들을 구해야함
     * 
     */
}
 
