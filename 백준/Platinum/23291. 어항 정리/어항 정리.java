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
            
        // ///////////////////////////////////////////
        
        // str = "16 0\r\n" + //
        //                 "1 1 10000 1 2 3 10000 9999 10 9 8 10000 5 4 3 1";// 

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }
    
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);
        int K = Integer.parseInt(args[0].split(" ")[1]);
        String[] row = args[1].split(" ");

        int[][] aquarium = new int[N][N];

        //초기화
        for(int i=0; i<N; i++){
            int val = Integer.parseInt(row[i]);
            aquarium[0][i] = val;
        }
        //조기종료조건
        if(getMinMaxGap(aquarium) == 0){
            System.out.println(0);
            return;
        }
        
        int count = 0;
        while(true){
            //1. 가장 작은 어항 +1
            addOneFishForMin(aquarium);
            // System.out.println("1. +1한 상태: ");
            // printAqua(aquarium);

            //2. 가장 왼쪽을 오른쪽 위로
            putLeftToTop(aquarium);
            // System.out.println("2. 가장 왼쪽을 오른쪽 위로 ");
            // printAqua(aquarium);

            //3. 2개 이상 어항을 공중부양 > 시계90도 회전 > 바닥에 있는거 위에 얹기 
            gripOverTwoAndTurn90(aquarium, N-1);
            // System.out.println("3. 2개 이상 어항을 공중부양 > 시계90도 회전 > 바닥에 있는거 위에 얹기 ");
            // printAqua(aquarium);

            //4. 회전 놓기 끝난 후 인접 물고기 수 조절
            fullScanAquariumForShare(aquarium);
            // System.out.println("4. 회전 놓기 끝난 후 인접 물고기 수 조절");
            // printAqua(aquarium);

            //5. 다시 일렬로 놓기
            putVerticalToHorizon(aquarium);
            // System.out.println("5. 다시 일렬로 놓기: ");
            // printAqua(aquarium);

            //6. N/2개 공중부양 이동
            //N/(2^m) = 1,N= 2^m,  Log2(N) = x번 가능 
            int x = N/2; 
            putHarfToTop(aquarium, x, N);
            // System.out.println("6. N/2개 공중부양 후 이동: ");
            // printAqua(aquarium);
            //아니 미친 딱 2번만 수행하는거네

            //7. 다 끝나면 재조절 작업 
            fullScanAquariumForShare(aquarium);
            // System.out.println("7. 재조절 작업 수행: ");
            // printAqua(aquarium);

            //8. 다시 일렬로 놓기
            putVerticalToHorizon(aquarium);
            count++; //8번까지 하면 다 한 거임
            // System.out.println("8. 일렬로 수행: ");
            // printAqua(aquarium);

            int gap = getMinMaxGap(aquarium);
            if(gap <=  K){
                System.out.println(count);
                return;
            }
            // System.out.println(count+ "번째 ===============================================================");
            // return;
        }   

    }
    public static void putHarfToTop(int[][] aqua, int x, int N){
        

        Stack<List<Integer>> stacks = new Stack<>();

        int y = aqua.length;
        for(int j=0; j<x; j++){   
            List<Integer> vertical = new ArrayList<>();
            for(int i=0; i<y; i++){
                if(aqua[i][j] == 0) break;
                vertical.add(aqua[i][j]);        
            }
            stacks.add(vertical);
        }
        int height = stacks.peek().size();
        int xIdx = 0;
        while(!stacks.isEmpty()){
            List<Integer> list = stacks.pop();
            
            int yIdx = list.size();
            for(int li=list.size()-1; li>=0; li--){
                aqua[yIdx++][xIdx] = list.get(li);
            }
            xIdx++;
            //5 5    
            //6 3 =>    stack({6,5}, {3,5}) => 5 3 y가 증가하는 방향
        }
        

        //당기기
        for(int i=0; i<height; i++){
            for(int j=0; j<x; j++){
                aqua[i][j] = aqua[i][j+x];
                aqua[i][j+x] = 0;
            }
        }
        if(x==(N/4)) return;
        // System.out.println("N/2 도중: x=" + x + "일 때 : ");
        // printAqua(aqua);
        putHarfToTop(aqua, x/2, N);
    }

    public static void putVerticalToHorizon(int[][] aqua){
        Stack<List<Integer>> verticalStacks = new Stack<>();
        int idx = 0;

        while(idx < (aqua[0].length/2)){
            List<Integer> result = getVerticalArr(aqua, idx);
            if(result == null || result.size() == 0){
                break;
            }
            verticalStacks.add(result);
            idx++;
        }

        
        List<Integer> floor = Arrays.stream(aqua[0]).filter(v -> v!=0).boxed().collect(Collectors.toList());
        int fullSize = aqua[0].length;
        //8 - 
        int fSize = floor.size();
        int fIdx = floor.size()-1;

        
        for(int i=fullSize-1; i>fullSize-1-(fSize-idx); i--){
            aqua[0][i] = floor.get(fIdx--);
        }
        
        int listIdx = fullSize-1-(fSize-idx);
        while(!verticalStacks.isEmpty()){
            List<Integer> list = verticalStacks.pop();
            for(int i=list.size()-1; i>=0; i--){
                aqua[0][listIdx--] = list.get(i);
            }
        }
        for(int i=1; i<aqua.length; i++){
            aqua[i] = new int[aqua[0].length];
        }
        // 4 4
        // 7 ~  5 =  // 4-2만큼 
    }

    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    public static void fullScanAquariumForShare(int[][] aqua){
        int N = aqua.length;
        int M = aqua[0].length;
        
        int[][] plmaMap = new int[N][M]; //+1, -1 누적해놓고 aquq랑 +=으로 합치기        
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(aqua[i][j] == 0) break; //좌하단에 붙어있으니 그냥 break해도 될듯

                int cur = aqua[i][j];
                for(int d=0;d<4;d++){
                    int ny = i+dy[d];
                    int nx = j+dx[d];
                    if(ny < 0 || nx < 0 || ny >=N || nx>=M ) continue;
                    if(aqua[ny][nx] == 0) continue;
                    
                    int gap = cur - aqua[ny][nx];
                    //중복 계산 방지용으로 양수 gap만 체크 (나눠주는 것만)
                    if(gap>0 && (gap/5)>0){
                        plmaMap[i][j] -= (gap/5);// 몫 나눠주기
                        plmaMap[ny][nx] += (gap/5); //몫 받기
                    }
                }
            }
        }

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                aqua[i][j]+=plmaMap[i][j];
            }
        }
        // System.out.println("");
        // System.out.println("나눠주기 이후: ");
        // printAqua(aquarium);
    }
    public static int getMinMaxGap(int[][] aqua){
        int y = aqua.length;

        int MAX = -1;
        int MIN = 10001;
        for(int i=0; i<y; i++){
            MAX = Math.max(MAX, Arrays.stream(aqua[i]).max().getAsInt());
            MIN = Math.min(MIN, Arrays.stream(aqua[i]).filter(v -> v!=0).min().orElse(Integer.MAX_VALUE));
        }
        return MAX-MIN;
    }

    public static void addOneFishForMin(int[][] aqua){
        int y = aqua.length;
        int x = aqua[0].length;

        int MIN = 10001;
        for(int i=0; i<y; i++){
            MIN = Math.min(MIN, Arrays.stream(aqua[i]).filter(v -> v!=0).min().orElse(Integer.MAX_VALUE));
        }
        for(int i=0; i<y; i++){
            for(int j=0; j<x; j++){
                if(aqua[i][j] == MIN) aqua[i][j]++;
            }
        }
    }

    public static void putLeftToTop(int[][] aqua){
        //무조건 [0]번째 ROW가 그렇게 되는건가..
        aqua[1][0] = aqua[0][0];
        
        int x = aqua[0].length;
        for(int i=0; i<x-1; i++){
            aqua[0][i] = aqua[0][i+1];
            if(aqua[0][i+1] == 0) break;
        }
        aqua[0][x-1] = 0;
    }

    public static void pullFirstLineToLeft(int[][] aqua){
        int x = aqua[0].length;
        
        for(int i=0; i<x-1; i++){
            aqua[0][i] = aqua[0][i+1];
            if(aqua[0][i+1] == 0) return;
        }
        aqua[0][x-1] = 0;
    }

    public static void gripOverTwoAndTurn90(int[][] aqua, int width){
        
        Stack<List<Integer>> verticalStacks = new Stack<>();
        int idx = 0;

        while(idx < (aqua[0].length/2)){
            List<Integer> result = getVerticalArr(aqua, idx);
            if(result == null || result.size() == 0){
                break;
            }
            verticalStacks.add(result);
            idx++;
        }
        if(verticalStacks.size() == 0){
            System.out.println("종료");
            return;
        } 
        int remainWidth = width-idx;
        int height = verticalStacks.peek().size();
        // 한 번 더 어항을 공중 부양시키는 것은 불가능하다. 그 이유는 <그림 6>
        // 과 같이 공중 부양시킨 어항 중 가장 오른쪽에 있는 어항의 아래에 바닥에 있는 어항이 없기 때문이다.
        if(remainWidth < height){
            return;
        }
        
        int vIdx = 1;
        while(!verticalStacks.isEmpty()){
            List<Integer> vlist = verticalStacks.pop();
            for(int i=0; i<vlist.size(); i++){
                aqua[vIdx][i] = vlist.get(i);
            }
            pullFirstLineToLeft(aqua);
            vIdx++;
        }
        // printAqua(aqua);

        gripOverTwoAndTurn90(aqua, remainWidth);
        
    }
    public static List<Integer> getVerticalArr(int[][] aqua, int verIdx){
        int y = aqua.length;
        if(aqua[1][verIdx] == 0) return null;

        List<Integer> vertical = new ArrayList<>();
        for(int i=0; i<y; i++){
            if(aqua[i][verIdx] == 0) break;
            vertical.add(aqua[i][verIdx]);
        }
        
        return vertical;
    }

    public static void printAqua(int[][] aqua){
        StringBuilder sb = new StringBuilder();

        for(int i=aqua.length-1; i>=0; i--){
            for(int j=0; j<aqua[0].length; j++){
                sb.append(aqua[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
