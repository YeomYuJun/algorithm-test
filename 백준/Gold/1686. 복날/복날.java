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

        // str = "3 1\r\n" + //
        //                 "0.000 0.000\r\n" + //
        //                 "150.000 0.000\r\n" + //
        //                 "179.000 0.000"; //

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        String FAIL = "No.";
        String SUCCESS = "Yes, visiting %d other holes.";

        int V = Integer.parseInt(args[0].split(" ")[0]);
        int m = Integer.parseInt(args[0].split(" ")[1]);

        double xs = Double.parseDouble(args[1].split(" ")[0]);
        double ys = Double.parseDouble(args[1].split(" ")[1]);

        double xt = Double.parseDouble(args[2].split(" ")[0]);
        double yt = Double.parseDouble(args[2].split(" ")[1]);

        //닭의 속도 = Vm/sec
        //m분간 이동가능 거리  3m/sec , 1분이면  180m, 그전이면 다 가능. ex) 179.999m 
        double limitLen = V*60*m;

        List<double[]> bunkers = new ArrayList<>();
        for(int i=2; i<args.length; i++){
            double xb = Double.parseDouble(args[i].split(" ")[0]);
            double yb = Double.parseDouble(args[i].split(" ")[1]);
            double[] bunker = {xb,yb};
            bunkers.add(bunker);
        }
        //bunkers.add(new double[]{xt,yt}); //도착점 추가 

        //x,y에서 정렬? 굳이..어차피 탐색해야할듯? 
        Queue<double[]> q = new LinkedList<>();
        double[] chicken = new double[]{xs,ys,0}; 
        boolean[] visitBunkers = new boolean[bunkers.size()];
        int finalCnt = -1;

        q.offer(chicken);
        while(!q.isEmpty()){
            double[] chic = q.poll();
            double xc = chic[0];
            double yc = chic[1];
            double visitBunkerCnt = chic[2];
            if(xc == xt && yc == yt){
                finalCnt = (int) visitBunkerCnt;
                finalCnt--;// 도착 벙커는 제외
                break;
            }

            for(int i=0; i<bunkers.size(); i++){
                if(visitBunkers[i]) continue;
                
                double[] b = bunkers.get(i);
                double xb = b[0];
                double yb = b[1];
                
                double len = getLength(xc, yc, xb, yb);
                if(len<limitLen){
                    visitBunkers[i] = true;
                    q.offer(new double[]{xb,yb,visitBunkerCnt+1});
                }
            }
        }

        if(finalCnt == -1){
            System.out.println(FAIL);
        }else{
            System.out.printf(SUCCESS, finalCnt);
        }
    }
    public static double getLength(double xs, double ys, double xt, double yt){
        return Math.sqrt(Math.pow(xt-xs,2) + Math.pow(yt-ys,2));
    }


}