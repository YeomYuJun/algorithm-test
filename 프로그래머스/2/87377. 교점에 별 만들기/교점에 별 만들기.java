import java.util.*;
class Solution {
    public String[] solution(int[][] line) {
        String[] answer = {};
        //정수로 표현되는 교점 찾기.
        List<List<Long>> crossLines = new ArrayList<>();
        long maxX = Long.MIN_VALUE, minX = Long.MAX_VALUE;
        long maxY = Long.MIN_VALUE, minY = Long.MAX_VALUE;
        /*
        *
        *      bf - ed         ec - af
        * x = --------- , y = ---------
        *      ad - bc         ad - bc 
        *  ad - bc = 0 인 경우 교점 없음.
        * 
        */
        for(int i = 0; i < line.length-1; i++){ 
            //ax+by+e=0;
            int aX = line[i][0];
            int bY = line[i][1];
            int eC = line[i][2];

            for(int j=i+1; j< line.length; j++){
                //cx+dy+f=0
                int cX = line[j][0];
                int dY = line[j][1];
                int fC = line[j][2];

                //최대 10만 * 10만이라 long타입 필요
                double denominato = ((long)aX*(long)dY) - ((long)bY*(long)cX); // 분모
                if(denominato == 0) continue;
                double Lnumerator = ((long)bY*(long)fC) - ((long)eC*(long)dY); // x분자
                double Rnumerator = ((long)eC*(long)cX) - ((long)aX*(long)fC); // y분자
                
                double crossX = Lnumerator / denominato;
                double crossY = Rnumerator / denominato;
                if(crossX % 1.0 == 0.0 && crossY % 1.0 == 0.0){//정수 교점이라는 뜻
                    crossLines.add(Arrays.asList((long)crossX,(long)crossY));
                    maxX = Math.max(maxX, (long)crossX);
                    minX = Math.min(minX, (long)crossX);
                    maxY = Math.max(maxY, (long)crossY);
                    minY = Math.min(minY, (long)crossY);
                }
            }
        }
        //교점들의 집합 crossLines에서 모든 별을 포함하는 최소한의 크기 => List의  x축(최고x - 최저x), y축(최고y-최저y)
        answer = new String[(int)(maxY - minY +1)];
        
        int g = 0;
        for(long y = maxY; y>=minY; y--){ //위에서 아래로 
            StringBuilder sb = new StringBuilder();
            for(long x = minX; x<=maxX; x++){
                //해당 포인트가 교점 목록에 포함되어 있는가?
                if(crossLines.contains(Arrays.asList(x,y))){
                    sb.append("*");
                }else{
                    sb.append(".");
                }
            }
            String str = sb.toString();
            answer[g++] = str;
        }
        
        return answer;        
    }
}