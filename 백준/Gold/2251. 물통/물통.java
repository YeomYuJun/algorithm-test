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
            
            // str = "8 9 10";// 

            ////////////////////////////////////////////
            
            String[] list = str.split("\r\n");  
            solution(list);
        }
        
        public static void solution(String[] args) throws IOException {
            int a = Integer.parseInt(args[0].split(" ")[0]);
            int b = Integer.parseInt(args[0].split(" ")[1]);
            int c = Integer.parseInt(args[0].split(" ")[2]);

            Set<Integer> answer = new HashSet<>();
 
            // C>A C>B A>C A>B B>A B>C 6개의 이동 경로
            // System.out.println("C의 경우: "+  answer);
            //CA CB .. cur = B.. BA BC..상태를 공유해야함.
            //ABC의 현 용량을 가지고 {a,b,c} 상태
            //{A,B,C}의 용량 비트마스킹 200 200 200..너무 큰데? 그냥 boolean우로

            // boolean[][][] visit = new boolean[200][200][200]; //최대 800만?
            boolean[][][] visit = new boolean[a+1][b+1][c+1]; 
            visit[0][0][c] = true;
            
            Queue<int[]> q = new LinkedList<>();

            q.add(new int[]{0,0,c});
            while(!q.isEmpty()){
                int[] abc = q.poll();
                
                int curA = abc[0];
                int curB = abc[1];
                int curC = abc[2];
                if(curA == 0){
                    answer.add(curC);
                }

                if(curA > 0){
                    int remainB = b - curB;
                    int remainC = c - curC;
                    //a to b 
                    if(remainB > 0){
                        int aL = Math.max(0, curA - remainB); // 다 붓거나, remainB만큼 빠지거나
                        int bL = Math.min(curB + curA , b);// B가 꽉 차거나, A가 다 털리거나
                        if(!visit[aL][bL][curC]){    //해당 조합 넣어본 적 있음
                            visit[aL][bL][curC] = true;
                            q.add(new int[]{aL,bL, curC});
                        }
                    }
                    //a to c
                    if(remainC > 0){
                        int aL = Math.max(0, curA - remainC); 
                        int cL = Math.min(curC + curA , c);
                        if(!visit[aL][curB][cL]){   
                            visit[aL][curB][cL] = true;
                            q.add(new int[]{aL,curB, cL});
                        }
                    }
                }
                if(curB > 0){
                    int remainA = a - curA;
                    int remainC = c - curC;
                    //b to a
                    if(remainA > 0){
                        int bL = Math.max(0, curB - remainA); 
                        int aL = Math.min(curA + curB , a);
                        if(!visit[aL][bL][curC]){   
                            visit[aL][bL][curC]= true;
                            q.add(new int[]{aL, bL, curC});
                        }
                    }
                    //b to c
                    if(remainC > 0){
                        int bL = Math.max(0, curB - remainC); 
                        int cL = Math.min(curC + curB , c);
                        if(!visit[curA][bL][cL]){   
                            visit[curA][bL][cL] = true;
                            q.add(new int[]{curA, bL, cL});
                        }
                    }
                }
                if(curC > 0){
                    int remainA = a - curA;
                    int remainB = b - curB;

                    //c to a
                    if(remainA > 0){
                        int cL = Math.max(0, curC - remainA);
                        int aL = Math.min(curA + curC , a);
                        if(!visit[aL][curB][cL]){   
                            visit[aL][curB][cL] = true;
                            q.add(new int[]{aL, curB, cL});
                        }
                    }
                    //c to b
                    if(remainB > 0){
                        int cL = Math.max(0, curC - remainB); 
                        int bL = Math.min(curB + curC , b);
                        if(!visit[curA][bL][cL]){   
                            visit[curA][bL][cL] = true;
                            q.add(new int[]{curA, bL, cL});
                        }
                    }
                }   
            }
            List<Integer> ansList = answer.stream().sorted().collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for(int cc : ansList){
                sb.append(cc).append(" ");
            }
            System.out.println(sb.toString().trim());
            
        }

        /**
         * 여기서 ABC 가지고 만들 수 있는 조합. 
         * CA | CB | CACB | CA BC | CB AC 
         * -A   -B    -A-B   -A+B   -B+A
         *  x    o  
         * CA AB(A<B)가능
         * 
         * DP인가???
         */
        
    }