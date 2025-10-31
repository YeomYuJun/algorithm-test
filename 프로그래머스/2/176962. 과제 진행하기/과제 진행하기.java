import java.util.*;
class Solution {
    public String[] solution(String[][] plans) {
        String[] answer = new String[plans.length];
        List<String> ansList = new ArrayList<>();
        //정렬
        Arrays.sort(plans, (o1,o2) -> {
            return strToMinutes(o1[1]) - strToMinutes(o2[1]);
        });

        Stack<String[]> remainStack = new Stack<>();
        
        for(int i=1; i<=plans.length; i++) {
            String[] currPlan = plans[i-1];
            String startName = currPlan[0];
            String startTime = currPlan[1];
            String remainTime = currPlan[2];
            if(i == plans.length){ //마지막에 숙제 (일단 계획 순서상)
                remainStack.add(new String[]{currPlan[0], currPlan[2]});
                break;
            }

            String[] nextPlan = plans[i];
            
            int needTime = strToMinutes(startTime) + Integer.parseInt(remainTime);
            int curTime = strToMinutes(nextPlan[1]);
            int leftTime = curTime - needTime;
            if(curTime < needTime){ //다음이 종료보다 가까우면

                remainStack.add(new String[]{startName, String.valueOf(Math.abs(leftTime))}); //남은 걸로 저장 
            }else{ //진행 중 과제를 종료해버리면

                ansList.add(startName);

                while(!remainStack.isEmpty() && leftTime > 0){ //남는 시간동안 멈춘 과제 진행 
                    int ll = doLeftPlanPlay(leftTime, remainStack, ansList);
                    leftTime = ll;
                }
                //남은 과제까지 끝남 = 허송세월 보내야함
                continue;
            }

        }
        //다 끝나고 남은 숙제 = 다음날로 넘어가는 경우...도 있나? 
        while(!remainStack.isEmpty()){ //남는 시간동안 멈춘 과제 진행 
            ansList.add(remainStack.pop()[0]);
        }

        answer = ansList.toArray(new String[ansList.size()]);
        
        return answer;
    }

    public int strToMinutes(String minute){
        String hour = minute.split(":")[0];
        String mm = minute.split(":")[1];
        int m = Integer.parseInt(hour) * 60 + Integer.parseInt(mm);
        return m;
    }

    public int doLeftPlanPlay(int rt, Stack<String[]> remainStack, List<String> ansList){
        String[] lastPlan = remainStack.pop();
        
        int left = Integer.parseInt(lastPlan[1]) - rt;
        if(left > 0){
            remainStack.add(new String[]{lastPlan[0], String.valueOf(left)});
            return 0; //더 쓸 수 있는 시간은
        }else{ //숙제완료
            ansList.add(lastPlan[0]);
            return -left; //더 쓸 수 있는 시간 
        }
        
    }
    /**
     * plans[0] = name
     * plans[1] = startTime 
     * plans[2] = spendTime
     * 
     * spend로 증가 = 다음 시작이면 해당 과제 끝남.
     * 숙제 중에 다음 숙제를 만다면 남은 시간을 (remain 숙제에 넣고) 다시 시작
     * 
     * 1. 숙제 시작 시각으로 정렬 
     */
}