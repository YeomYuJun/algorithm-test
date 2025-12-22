import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder mainSb = new StringBuilder();
        String str = "";
        String tmp = "";
        /////////////////////////////////////////////
        
        while((tmp=br.readLine()) != null){
            mainSb.append(tmp).append("\r\n");
        }
        str = mainSb.toString();
        
        ///////////////////////////////////////////

        // str = "11\r\n" + //
        //                 "9 9 9 9 9 10 10 10 10 10 11";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }
    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        String[] buildings = args[1].split(" ");
        
        double[] db = new double[N];
        for(int i=0; i<N; i++){
            db[i] = Double.parseDouble(buildings[i]);
        }

        //n-m의 연결관계 양방향임 = j=i+1부터 모든 경우
        int[] countList = new int[N];
        for(int i=0; i<N-1; i++){
            double left = db[i];
            double max = -Double.MAX_VALUE;
            
            for(int j=i+1; j<N; j++){
                double right = db[j];

                //기울기는 x증가량분의 y증가량
                double xAxisGap = j-i;
                double yAxisGap = right - left;
                double inclination = yAxisGap/xAxisGap; 

                if(right < left){ //우측 빌딩이 더 작으면 그 사이 기울기 판별 , -2 -1.5
                    if(max < inclination && max < 0){  // 음수로 내려갈 때 양수의 영역이 있으면 안됨.
                        //연결 양방향 추가
                        countList[i]++;
                        countList[j]++;
                        max = inclination;
                    }
                }else{ //우측 빌딩이 더 크면=> 기울기가 더 크면
                    if(max < inclination){ 
                        //연결 양방향 추가
                        countList[i]++;
                        countList[j]++;
                        max = inclination;
                    }
                }
            }
        }
        int answer = 0;
        for(int i=0; i<N; i++){
            answer = Math.max(answer, countList[i]);
        }
        System.out.println(answer);
    }
    /**
     * . N은 50보다 작거나 같은 자연수이다. 둘째 줄에 1번 빌딩부터 그 높이가 주어진다. 높이는 1,000,000,000보다 작거나 같은 자연수
     * 10억은 좀..
     */
    
}