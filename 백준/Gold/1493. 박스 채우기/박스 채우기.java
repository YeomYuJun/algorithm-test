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
            
        ///////////////////////////////////////////
        
        // str = "1000000 1000000 1000000\r\n" + //
        //                 "20\r\n" + //
        //                 "0 1000000\r\n" + //
        //                 "1 1000000\r\n" + //
        //                 "2 1000000\r\n" + //
        //                 "3 1000000\r\n" + //
        //                 "4 1000000\r\n" + //
        //                 "5 1000000\r\n" + //
        //                 "6 1000000\r\n" + //
        //                 "7 1000000\r\n" + //
        //                 "8 1000000\r\n" + //
        //                 "9 1000000\r\n" + //
        //                 "10 1000000\r\n" + //
        //                 "11 1000000\r\n" + //
        //                 "12 1000000\r\n" + //
        //                 "13 1000000\r\n" + //
        //                 "14 1000000\r\n" + //
        //                 "15 1000000\r\n" + //
        //                 "16 1000000\r\n" + //
        //                 "17 1000000\r\n" + //
        //                 "18 1000000\r\n" + //
        //                 "19 1000000"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        long L = Long.parseLong(args[0].split(" ")[0]);
        long W = Long.parseLong(args[0].split(" ")[1]);
        long H = Long.parseLong(args[0].split(" ")[2]);

        long N = Long.parseLong(args[1]);

        int[] cnts = new int[20]; //큐브 개수 , 0~N-1까지의 size 큐브를 확신할 수 있는가? X


        int[] cubeSet = new int[20]; //실제 크기
        cubeSet[0] = 1;
        for(int i=1;i<20; i++){
            cubeSet[i] = cubeSet[i-1]*2;
        }

        for(int i=0;i<N; i++){
            String[] row = args[i+2].split(" ");

            int power = Integer.parseInt(row[0]);
            int count = Integer.parseInt(row[1]);
            cnts[power] = count;
        }

        long totalUsed = 0; // 사용한 총 큐브 개수
        long filledVolume = 0; // 현재까지 채워진 '부피' 개념의 수치 (가장 작은 1x1x1 기준)

        // 가장 큰 큐브부터 채워나감
        for (int i = 19; i >= 0; i--) {
            // 현재 단계에서 더 큰 큐브들에 의해 이미 채워진 공간을 8배 확장 (비트 연산 << 3)
            filledVolume <<= 3;

            // 현재 크기(2^i)의 큐브가 박스 전체에 들어갈 수 있는 최대 개수
            long maxCanFit = (L >> i) * (W >> i) * (H >> i);
            
            // 박스 전체 공간 중 아직 비어있는 공간
            long need = maxCanFit - filledVolume;
            
            long used = Math.min(cnts[i], need);            
            totalUsed += used;
            filledVolume += used;
        }

        // 최종적으로 채워진 부피가 전체 부피와 같다면 성공
        if (filledVolume == L * W * H) {
            System.out.println(totalUsed);
        } else {
            System.out.println(-1);
        }
    }
    /**
     *  L x W x H 의 박스를 2의 n승 정육면체로 가득 채우는 방법. 최소로
     *  그리드인가?
     *  최대 524288넓이 
     */
}
