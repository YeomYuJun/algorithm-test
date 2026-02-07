    import java.io.*;
    import java.util.*;
    import java.util.stream.Collectors;
    import java.util.stream.IntStream;

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
                
            // ///////////////////////////////////////////
            
            // str = "4\r\n" + //
            //                     "1000 1000 1000 1000";// 

            ////////////////////////////////////////////
            
            String[] list = str.split("\r\n");  
            solution(list);
        }
        
        public static void solution(String[] args) throws IOException {
            int N = Integer.parseInt(args[0]);
            String[] aStr = args[1].split(" ");
            int[] arr = Arrays.stream(aStr).mapToInt(Integer::parseInt).toArray();
            
            Set<Integer> set = new HashSet<>();

            int[] nums = new int[1001];
            int MIN = 1001; 
            boolean hasNext = false;
            boolean hasAnother = false;

            for(int val : arr){
                ++nums[val];
                MIN = Math.min(MIN, val);
                set.add(val);
            }
            int totalCnt = set.size();
            //3개 이상의 숫자가 존재하는가
            hasAnother = totalCnt > 2;
            //다음 숫자가 존재하는가? (1000이면 다음 숫자가 있을 수 없음);
            hasNext = (MIN < 1000) && (nums[MIN+1] > 0);
            
            int[] ans = new int[N];
            if(hasNext && !hasAnother) {
                // MIN과 MIN+1만 존재하는 경우에는 MIN+1을 먼저
                ans[0] = MIN + 1;
                nums[MIN + 1]--;
            } else {
                // 그 외의 경우에는 사전순 최소 = MIN
                ans[0] = MIN;
                nums[MIN]--;
            }
            List<Integer> list = set.stream().collect(Collectors.toList());
            Collections.sort(list);

            boolean[] isFree = new boolean[1001];

            for(int num : set) {
                // num+1이 존재하지 않으면 자유로움
                isFree[num] = (num == 1000) || (nums[num+1] == 0);
            }

            for(int i=1; i<N; i++) {
                int prev = ans[i-1];
                
                // prev+1이 아닌 숫자 중에서 선택
                for(int candidate : list) {
                    if(nums[candidate] <= 0 ) continue;
                    if(candidate == prev + 1) continue;  // 연속 값 스킵
                    if(!isFree[candidate]){
                        boolean hasOtherNumber = IntStream.rangeClosed(0, 1000)
                            .anyMatch(value -> nums[value] > 0 
                                            && value != candidate 
                                            && value != candidate + 1);

                        if(!hasOtherNumber) continue;
                    }
                    ans[i] = candidate;
                    break;
                }
                
                
                nums[ans[i]]--;
                
                // isFree 상태 업데이트
                if(nums[ans[i]] == 0 && ans[i] > 0) {
                    if(ans[i] - 1 >= 0) {  // 추가 체크, 여기서 OOB 뜬...건가?
                        isFree[ans[i] - 1] = true; // 내가 사라지면 앞 숫자가 자유로워짐
                    }
                    isFree[ans[i] - 1] = true;  
                }
            }
            // System.out.println(Arrays.toString(ans));
            StringBuilder sb = new StringBuilder();
            for(int a : ans){
                sb.append(a).append(" ");
            }
            System.out.println(sb.toString().trim());

            
        }
        /**
         * 그리디하게 되는가?  sorting만으로 되는가?
         * ..dp인가>?  
         * sort하고, ai+1 = a(i+1) 이면 교체 후 고정? 
         * 아니다 
         * 1이 있고, 2가 있을 때 => 1. 이외의 숫자가 있으면 0번쨰
         *                    => 2. 이외의 숫자가 없으면 2가 0번째임       
         * 1이 있고, 2가 없을 때 => 걍 0번째임
         * 여기서 1은 MIN이고, 2는 MIN+1번째, 3은 another이 있는가. 정도 
         */

    }