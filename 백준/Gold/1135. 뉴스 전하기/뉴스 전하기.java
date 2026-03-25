import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
            
        ////////////////////////////////////////////
        
        // str = "15\r\n" + //
        //                 "-1 0 0 0 0 2 2 3 3 6 7 7 4 12 13";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);

        String[] row = args[1].split(" ");
        int[] empl = new int[N];


        List<List<Integer>> relation = new ArrayList<>();
        List<List<Integer>> masterList = new ArrayList<>();
        int[] subSize = new int[N];

        for(int i=0; i<N; i++){
            relation.add(new ArrayList<>());
            masterList.add(new ArrayList<>());
        }

        for(int i=0; i<N; i++){
            int val = Integer.parseInt(row[i]);
            empl[i] = val;
            if(i>0){
                relation.get(val).add(i);
                masterList.get(i).add(val);
                // subSize[val]++;
                
            }
        }
        
        checkBoss(subSize, relation, 0);
        int max = Arrays.stream(subSize).max().getAsInt();
        System.out.println(max);
    }
    
    public static int checkBoss(int[] subSize, List<List<Integer>> relation, int startIdx){
        List<Integer> rel = relation.get(startIdx);
        if(rel.size()==0) return 0; //자기 자신이 마지막(부사수 없음)

        for(int child : rel) checkBoss(subSize, relation, child);
        int rSize = rel.size();

        rel.sort((a, b) -> subSize[b] - subSize[a]);

        int max = 0;
        for(int j=0; j<rel.size(); j++){
            max = Math.max(max, (j+1) + subSize[rel.get(j)]);
        }
        //rSize는 최소 이 idx가 해야하는 경우
        //그리고 자식 중의 최대 코스트(최대 깊이 or 최대 넓이)
        subSize[startIdx] = max;
        return max;
    }
    /**
     * 정보를 들었는가?
     * YES 
     * 자식이 있는가?
     * YES 
     * 자식들이 여럿인가?
     * YES 
     * 자식들 중 자식이 많은 순서로 정렬이 필요한가
     * YES 
     * 해당 자식부터 1초 소비. 
     */
}

