import java.io.*;
import java.util.*;

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

        // str = "3\r\n" + //
        //                 "-5 1 5 1\r\n" + //
        //                 "3\r\n" + //
        //                 "0 0 2\r\n" + //
        //                 "-6 1 2\r\n" + //
        //                 "6 2 2\r\n" + //
        //                 "2 3 13 2\r\n" + //
        //                 "8\r\n" + //
        //                 "-3 -1 1\r\n" + //
        //                 "2 2 3\r\n" + //
        //                 "2 3 1\r\n" + //
        //                 "0 1 7\r\n" + //
        //                 "-4 5 1\r\n" + //
        //                 "12 1 1\r\n" + //
        //                 "12 1 2\r\n" + //
        //                 "12 1 3\r\n" + //
        //                 "102 16 19 -108\r\n" + //
        //                 "12\r\n" + //
        //                 "-107 175 135\r\n" + //
        //                 "-38 -115 42\r\n" + //
        //                 "140 23 70\r\n" + //
        //                 "148 -2 39\r\n" + //
        //                 "-198 -49 89\r\n" + //
        //                 "172 -151 39\r\n" + //
        //                 "-179 -52 43\r\n" + //
        //                 "148 42 150\r\n" + //
        //                 "176 0 10\r\n" + //
        //                 "153 68 120\r\n" + //
        //                 "-56 109 16\r\n" + //
        //                 "-187 -174 8";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int i=0;
        int T = Integer.parseInt(args[i]); // T
        i++;
        for(int t=0; t<T; t++){
            String[] pos = args[i].split(" ");
            int sX = Integer.parseInt(pos[0]);
            int sY = Integer.parseInt(pos[1]);

            int eX = Integer.parseInt(pos[2]);
            int eY = Integer.parseInt(pos[3]);
            i++;
            int[] s = {sX,sY};
            int[] e = {eX,eY};

            int N = Integer.parseInt(args[i]);
            i++;
            int count = 0;
            for(int n=0; n<N; n++){
                String[] starStr = args[i].split(" ");
                i++;
                int starX = Integer.parseInt(starStr[0]);
                int starY = Integer.parseInt(starStr[1]);
                int starR = Integer.parseInt(starStr[2]);
                int[] star = {starX, starY, starR};

                boolean isStartIn = isIn(star, s);
                boolean isEndIn = isIn(star, e);

                if(isStartIn != isEndIn){ //안and박 or 박or안
                    count++;
                }else{
                    //안and안 or 박and박
                }
            }
            System.out.println(count);
        }
    }

    public static boolean isIn(int[] star, int[] point){
        int xGap = Math.abs(star[0] - point[0]);
        int yGap = Math.abs(star[1] - point[1]);

        double posGap = Math.sqrt((Math.pow(xGap,2) + Math.pow(yGap,2)));
        double range = (double) star[2];
        //행성계의 경계가 맞닿거나 서로 교차하는 경우는 없다
        return range > posGap;
    }


    /**
     * 행성이 시작점을 감싸고, 도착점은 바깥인가? + 1
     * or 도착점을 감싸고 시작점 바깥인가? + 1
     * or 둘다 바깥인가? continue
     * or 둘다 안인가? contibue
     */
}