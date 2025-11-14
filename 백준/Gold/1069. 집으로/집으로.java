import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String str = "";
        String tmp = "";
        while((tmp=br.readLine()) != null){
            str += tmp+ "\r\n";
        }
        String[] list = str.split("\r\n");
        
        System.out.println(solution(list));
    }

    public static String solution(String[] args){
        String[] pos = args[0].split(" ");
        double[] eunjin = new double[]{Double.parseDouble(pos[0]),Double.parseDouble(pos[1])};
        double d = Double.parseDouble(pos[2]);
        double t = Double.parseDouble(pos[3]);;

        double walkTime = squareABroot(eunjin[0], eunjin[1]);
        //1. d/t 가 1 보다 낮으면 할 필요 없지않나? 전부 걷기 선에서 끝날 거 같은데 
        double jumpTT = d/t; //5를 3초만에 간다 = 초당 1.666
        
        if(jumpTT < 1.0){
            return String.valueOf(walkTime);
        }else{   
            double remainDistance = walkTime;
            double tik = 0.0;
            
            while(remainDistance!=0){
                if(remainDistance>0 && remainDistance >= d){ //거리가 양수이며, d보다 클 때는 점프
                    remainDistance-=d;
                    tik+=t;
                }else if(remainDistance > 0 && remainDistance < d) { //거리가 양수이며, d보다 작을 때
                    double willWalk = d-remainDistance; //음수가 된 만큼 걸어야하는 거리
                    if(t+willWalk < remainDistance){//점프 후 걷는 것이 여기서 걷는 것보다 빠를 경우
                        tik += t + willWalk;
                        remainDistance = 0;
                    }else{
                        tik += remainDistance;
                        remainDistance = 0;
                    }
                }else{ //음수로 넘어간다면 점프로 앞으로 돌아올 이유가 없음
                    remainDistance+=1;
                    tik+=1;
                }
            }
            int maxJump = (Math.max((int)walkTime/(int)d,1))+1; 
            //maxJump의 횟수로 두개의 원을 그려서 이동해야함 1d+ (maxJump-1)d or (2d + maxJump-2d) 느낌
            //즉 최대 점프로 만들 수 있는 두개의 원의 종류가 항상 있다고 가정하면 
            //d가 walkTime을 앞지르는 순간의 횟수+1임.  
            //maxJump * t 가 직선 tik보다 작으면 가능하다?
            tik = Math.min((maxJump*t), tik);
            return String.valueOf(tik);
            
            //1. 원점을 지나 음수의 좌표로 갈 수 있음.
            //2. 점프는 수직수평으로 가는 것이 아님.
            //즉 경우의 수는 다음과 같음
            /*
             * 0. 점프가 더 효율적일 경우
             * 1. 점프로 도달
             * 2. 점프 + 걷기로 도달
             */
            //3. 10 10 1000 5에서 10.0이 왜나오는지?
            //(10,10)에서 1000의 거리를 점프할 이유가 없는데, 벽이 있나?
            //아 삼각형을 그리는 방법 = 원점과 은진의 점에서 반지름이 d의 배수인 원을 그려서 도달할 수 있는 경우와
            //그 도달할 수 있는 경우의 t를 구해서 min이구나
        }
    }
    public static double squareABroot(double a, double b){
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b,2));
    }
}

