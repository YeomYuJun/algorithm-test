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
        
        // str = "4 3\r\n" + //
        //                 "2 1 2 1\r\n" + //
        //                 "1 1 1 1\r\n" + //
        //                 "1 1 1 1\r\n" + //
        //                 "1 1 1 1";

        ////////////////////////////////////////////
        
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0].split(" ")[0]);  // 2~100
        int L = Integer.parseInt(args[0].split(" ")[1]);  // 1~2,100

        int[][] map = new int[N][N];

        for(int i=0; i<N; i++){
            String[] row = args[i+1].split(" ");
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(row[j]);
            }
        }

        int roadCnt = 0;
        for(int i=0; i<N; i++){
            int startH = map[i][0];

            int horizonStraightLen = 1; //현재 row에서 얼마나 연속되어있는지.
            boolean horizonPathable = true;
            
            for(int j=1; j<N; j++){
                int curH = map[i][j-1];
                int nextH = map[i][j];
                if(nextH == curH){
                    horizonStraightLen++;
                    continue;
                }else if(curH == nextH-1 && horizonStraightLen >= L){//경사로를 놓을 수 있는 유일한 경우
                    //올라가는 경우 = 1
                    horizonStraightLen=1;
                    continue;
                }else if(curH == nextH+1){//경사로를 내려가야 하는 경우
                    if(L==1){
                        horizonStraightLen=-L+1;
                        continue;
                    }else{
                        int newJ = j+1;
                        int newLen = 1;
                        boolean isAble = false;
                        while(newJ < N){ //L이 2 이상일 때 newJ가 처음부터 장외라면 불가능임
                            if(map[i][newJ] == nextH){ //다음 높이에서 연속인지 체크
                                newLen++;
                                if(newLen >= L){
                                    isAble = true;
                                    break;
                                }
                            }else{
                                break;
                            }
                            newJ++;
                        }
                        if(isAble){
                            horizonStraightLen=-L+1;
                            continue;
                        }else{
                            horizonPathable = false;
                            break;
                        }
                    }
                }else if(curH > nextH){ //내려가는 경우는 없음.
                    horizonPathable = false;
                    break;
                }else{ //너무 높거나, 길이가 맞지 않는 경우.
                    horizonPathable = false;
                    break;
                }
            }
            if(horizonPathable){
                roadCnt++;
            }

            int verticalStraightLen = 1; //현재 col에서 얼마나 연속되어있는지.
            boolean verticalPathable = true;

            for(int j=1; j<N; j++){
                int curH = map[j-1][i];
                int nextH = map[j][i];
                if(nextH == curH){
                    verticalStraightLen++;
                    continue;
                }else if(curH == nextH-1 && verticalStraightLen >= L){//경사로를 놓을 수 있는 유일한 경우
                    verticalStraightLen=1; 
                    continue;
                }else if(curH == nextH+1){//경사로를 내려가야 하는 경우
                    if(L==1){
                        verticalStraightLen=-L+1;//해당 경사로를 놓은 위치에 놓을 수 없으므로 길이-1로 음수처리
                        continue;
                    }else{
                        int newJ = j+1;
                        int newLen = 1;
                        boolean isAble = false;
                        while(newJ < N){ //L이 2 이상일 때 newJ가 처음부터 장외라면 불가능임
                            if(map[newJ][i] == nextH){ //다음 높이에서 연속인지 체크
                                newLen++;
                                if(newLen >= L){
                                    isAble = true;
                                    break;
                                }
                            }else{
                                break;
                            }
                            newJ++;
                        }
                        if(isAble){
                            verticalStraightLen=-L+1;
                            continue;
                        }else{
                            verticalPathable = false;
                            break;
                        }
                    }
                }else if(curH > nextH){ //내려가는 경우는 없음.
                    verticalPathable = false;
                    break;
                }else{ //너무 높거나, 길이가 맞지 않는 경우.
                    verticalPathable = false;
                    break;
                }
            }
            if(verticalPathable){
                roadCnt++;
            }
        }
        System.out.println(roadCnt);

    }
    /**
     * 경사로는 1개인가? 개수는 매우 많아 부족할 일이 없다 <- 무제한
     * 
     */


}

