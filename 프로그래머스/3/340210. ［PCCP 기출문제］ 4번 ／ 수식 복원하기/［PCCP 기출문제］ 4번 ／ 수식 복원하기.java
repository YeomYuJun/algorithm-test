import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public String[] solution(String[] expressions) {
        String[] answer = {};
        
        Queue<String> q = new LinkedList<>();
        List<Integer> expectRetainList = new ArrayList<>();
        
        for(int i=0; i<expressions.length; i++){
            //Question / Answer
            String qst = expressions[i].split("=")[0].trim();
            String ans = expressions[i].split("=")[1].trim();
            Set<Integer> bases = new HashSet<>();
            if("X".equals(ans)){ // 풀어야하는 문제.
                String leftNum = "";
                String rightNum = ""; 
                if(qst.indexOf("+") > 0){ // +있음 
                    leftNum = qst.split("\\+")[0].trim();
                    rightNum = qst.split("\\+")[1].trim();
                }else if(qst.indexOf("-") > 0){ //  -있음
                    leftNum = qst.split("-")[0].trim();
                    rightNum = qst.split("-")[1].trim();
                }
                String[] numlist = (leftNum+rightNum).split("");
                int max = Collections.max(Arrays.asList(numlist).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
                for(int k =max+1; k<10; k++){
                    bases.add(k);
                }
                q.offer(qst);

            }else{ // 풀려있는 문제.
                //A, B는 음이 아닌 두 자릿수 이하의 정수입니다.
                //두 자릿수 이하이므로 1의 자리가 중요함. 덧셈도 뺄셈도 십의 자리를 2씩 변경할 수 없음.\
                
                if(qst.indexOf("+") > 0){ // +있음 
                    String leftNum = qst.split("\\+")[0].trim();
                    String rightNum = qst.split("\\+")[1].trim();
                    bases = findPossibleBases(leftNum, rightNum, ans, true);
                }else if(qst.indexOf("-") > 0){ //  -있음 
                    String leftNum = qst.split("-")[0].trim();
                    String rightNum = qst.split("-")[1].trim();
                    bases = findPossibleBases(leftNum, rightNum, ans, false);
                }
                
            }

            if(expectRetainList.isEmpty()){
                expectRetainList.addAll(bases);
            }else{
                expectRetainList.retainAll(bases);
            }
        }
        //System.out.println(Arrays.toString(expressions) + "의 최종 예상범위입니다. => "+ expectRetainList.toString());
        answer = new String[q.size()];
        int j=0;
        while(!q.isEmpty()){
            String restQst = q.poll();
            String result = "";
            int base = expectRetainList.get(0);
            boolean isConfusion = expectRetainList.size()>1; //많으면 혼란임..
            //그래도 1개면 단일, 여럿이면 min value의미로 사용하면 될 듯

            if(restQst == null) break;
            if(restQst.indexOf("+") > 0){ // 플러스 문제
                String leftNum = restQst.split("\\+")[0].trim();
                String rightNum = restQst.split("\\+")[1].trim();
                if(!isConfusion){ //명백
                    result = add(leftNum, rightNum, base);
                }else{ //혼란
                    //혼란이지만 알 수 있는 case?
                    //1. x+y < min보다 작을 때 
                    int ln = Integer.parseInt(leftNum);
                    int rn = Integer.parseInt(rightNum);
                    int onesum = (ln%10 + rn%10);
                    int tensum = (ln/10 + rn/10);
                    if(onesum >= base || tensum >= base){
                        result = "?";
                    }else{
                        result = add(leftNum, rightNum, base);
                    }
                }
            }else if(restQst.indexOf("-") > 0){ // 마이너스 문제
                String leftNum = restQst.split("-")[0].trim();
                String rightNum = restQst.split("-")[1].trim();
                if(!isConfusion){ //단일 케이스
                    result = subtract(leftNum, rightNum, base);
                }else{ //혼란 케이스
                    int ln = Integer.parseInt(leftNum);
                    int rn = Integer.parseInt(rightNum);
                    int onesub = (ln%10 - rn%10);
                    int tensub = (ln/10 - rn/10);
                    if(onesub >= 0 && tensub >= 0){
                        result = subtract(leftNum, rightNum, base);
                    }else{
                        result = "?";
                    }
                    
                }
            }
            result = restQst + " = " + result;
            answer[j++] = result;
        }
        return answer;
    }
    public String getOnePostionNum(String num){
        return num.substring(num.length()-1, num.length());
    }

    public Set<Integer> findPossibleBases(String a, String b, String result, boolean isAddition) {
        Set<Integer> possibleBases = new HashSet<>();
        
        // 사용된 최대 자릿수로 최소 base 결정
        int minBase = getMinimumBase(a, b, result);
        
        // 오직 숫자이므로 10진법까지만 체크 (0-9, A-Z)
        for (int base = minBase; base <= 10; base++) {
            try {
                String calculated;
                if (isAddition) {
                    calculated = add(a, b, base);
                } else {
                    calculated = subtract(a, b, base);
                }
                
                if (calculated.equals(result)) {
                    possibleBases.add(base);
                }
            } catch (Exception e) {
                // 해당 base에서 계산 불가능
                continue;
            }
        }
        
        return possibleBases;
    }

    public String subtract(String a, String b, int base) {
        if (a.equals(b)) {
            return "0";
        }
        
        StringBuilder result = new StringBuilder();
        int borrow = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;
        
        while (i >= 0) {
            int digitA = charToDigit(a.charAt(i)) - borrow;
            int digitB = (j >= 0) ? charToDigit(b.charAt(j)) : 0;
            
            if (digitA < digitB) {
                digitA += base;
                borrow = 1;
            } else {
                borrow = 0;
            }
            
            result.append(digitToChar(digitA - digitB));
            i--;
            j--;
        }
        
        // 앞의 0 제거
        String resultStr = result.reverse().toString();
        return resultStr.replaceFirst("^0+(?!$)", "");
    }

    private int getMinimumBase(String... numbers) {
        int maxDigit = 0;
        for (String number : numbers) {
            for (char c : number.toCharArray()) {
                int digit = charToDigit(c);
                maxDigit = Math.max(maxDigit, digit);
            }
        }
        return maxDigit + 1;  // 최대 자릿수 + 1이 최소 base
    }

    public String add(String a, String b, int base) {
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base must be between 2 and 10");
        }
        
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;
        
        while (i >= 0 || j >= 0 || carry > 0) {
            int digitA = (i >= 0) ? charToDigit(a.charAt(i)) : 0;
            int digitB = (j >= 0) ? charToDigit(b.charAt(j)) : 0;
            
            int sum = digitA + digitB + carry;
            result.append(digitToChar(sum % base));
            carry = sum / base;
            
            i--;
            j--;
        }
        
        return result.reverse().toString();
    }
    
    private int charToDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        return c - 'A' + 10;
    }
    
    private char digitToChar(int digit) {
        if (digit < 10) {
            return (char) ('0' + digit);
        }
        return (char) ('A' + digit - 10);
    }
}