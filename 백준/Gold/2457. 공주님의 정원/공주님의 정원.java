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

        // str = "4\r\n" + //
        //                 "1 1 5 31\r\n" + //
        //                 "1 1 6 30\r\n" + //
        //                 "5 15 8 31\r\n" + //
        //                 "6 10 11 10";

        ////////////////////////////////////////////
        String[] list = str.split("\r\n");  
        solution(list);
    }

    public static void solution(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        
        //3월 1일부터 11월 30일 3 1 ~ 12 1
        List<int[]> flowerList = new ArrayList<>();
        for(int i=1; i<=N; i++){
            String[] n = args[i].split(" ");
            int sm = Integer.parseInt(n[0]);
            int sd = Integer.parseInt(n[1]);
            int em = Integer.parseInt(n[2]);
            int ed = Integer.parseInt(n[3]);

            flowerList.add(new int[]{sm,sd,em,ed});
        }
        flowerList.sort((o1, o2) -> {
            int st1 = o1[0]*100 + o1[1];
            int st2 = o2[0]*100 + o2[1]; 
            int et1 = o1[2]*100 + o1[3];
            int et2 = o2[2]*100 + o2[3];
            if(st1 != st2){
                return Integer.compare(st1, st2);
            }
            return Integer.compare(et1, et2);
        });

        int flowerCnt = 0;

        int[] mmdd = new int[]{0,0};// st는 최소, et는 max가 되게 설정. 
        int[] mmdd2 = new int[]{0,0};
        for(int i=0; i<N; i++){
            int[] f = flowerList.get(i);
            int st = f[0]*100 + f[1];
            int et = f[2]*100 + f[3];

            if(mmdd[0] == 0 && mmdd[1] == 0){// 첫번째/
                if(et > 301 && st <= 301){
                    flowerCnt++;
                    mmdd[0] = st;
                    mmdd[1] = et;
                }
            }

            //n번째 꽃
            //st = 101, et = 531;
            //st = 101, et = 601을 만난다면?
            if(st <= 301){ //시작시간이 어쨋든 원하는 조건에 맞음  //아직 st가 301을 넘기 전. = et 최대값으로 갱신
                if(et > mmdd[1]){
                    mmdd[0] = st;
                    mmdd[1] = et;
                }
            }else{//3월1일 이후 선택 = 현재 et보다 이르고, new et가 현재보다 길 것.
                if(mmdd[0] == 0 ){ //301일 이후인데 선택 없음은 공백 생김 = 실패
                    System.out.println(0);
                    return;
                }
                if(et  > mmdd[1]){//다음 꽃이 현재 max보다 길 때 

                    if(st <= mmdd[1]){ //첫번째보다 시작지점이 작으면
                        if(mmdd2[0] == 0 & mmdd2[1] == 0){ //첫 연결
                            mmdd2[0] = st;
                            mmdd2[1] = et;
                            flowerCnt++;
                        }else{
                            if(et > mmdd2[1]){//신규보다도 길면 최적선택
                                mmdd2[0] = st;
                                mmdd2[1] = et;
                            }
                        }
                    }else if(st <= mmdd2[1]){ //1번째보다 이후지만, 2번째보다 이내일 경우
                        if(et > mmdd[1]){ //끝이 길어야 선택, 아니면 할 필요 X 
                            mmdd[0] = mmdd2[0];
                            mmdd[1] = mmdd2[1];
                            mmdd2[0] = st;
                            mmdd2[1] = et;
                            flowerCnt++;
                        }

                    }else{//불가능 케이스
                        System.out.println(0);
                        return;
                    }
                }
            }
            if(et >= 1201){ //넘는 순간 종료
                break;
            }else if(i==N-1){ //끝까지 못넘김.
                flowerCnt = 0;
            }
            
        }
        System.out.println(flowerCnt);

    }
    /**
     * 졍렬은 0,1,2,3 순서로 하면 되나?  200+28 > 300+1
     * 꽃들의 최소 개수
     * = 3 1 보다 빠르고 em ed가 가장 긴 꽃 선택 1
     * and 다음 sm,sd가 em ed보다 빠르며, new em ed가 가장 긴 것으로 선택
     * 반복
     */

}