class Solution {
    public int solution(String[] board) {
        int answer = 1;

        final String FIRST = "O";
        final String SECON = "X";

        int firstCnt = 0;
        int seconCnt = 0;

        for(int i=0; i<3; i++){
            String row = board[i];
            String[] col = row.split("");
            for(int j=0; j<col.length; j++){
                if(col[j].equals(".")){
                    continue;
                }else if(col[j].equals(FIRST)){
                    firstCnt++;
                }else if(col[j].equals(SECON)){
                    seconCnt++;
                }
            }
        }

        boolean firstIsWin = isWin(FIRST, board);
        boolean seconIsWin = isWin(SECON, board);

        if(seconCnt>firstCnt){//case 1
            return 0;
        }else if(firstCnt>seconCnt+1){// case 2
            return 0;
        }
        
        if(firstIsWin && seconIsWin){ // case 5
            return 0;
        }else if(firstIsWin && firstCnt-seconCnt != 1){ // case 3 1더 많아야 하는데 그렇지않음
            return 0;
        }else if(seconIsWin && firstCnt != seconCnt){ // case 4 착수가 같지않음 
            return 0; 
        }
        
        return answer;
    }
    //승리 여부. 첫 for문에 합칠까..분리가 보기나을듯
    public boolean isWin(String user, String[] board){
        String answer = user.repeat(3);
        for(int i=0; i<3; i++){ //가로 검증
            if(board[i].equals(answer)){ 
                return true;
            }
            String col = board[0].split("")[i] + board[1].split("")[i] + board[2].split("")[i];
            if(col.equals(answer)){//세로 검증
                return true;
            }
        }
        String cross = board[0].split("")[0] + board[1].split("")[1] + board[2].split("")[2];
        String cross2 = board[0].split("")[2] + board[1].split("")[1] + board[2].split("")[0];
        if(cross.equals(answer) || cross2.equals(answer)){
            return true;
        }
        return false;
    }
    /**
     * 
     * 불가능 case
     * 1. 후공이 착수가 더 많음
     * 2. 선공이 착수가 2개 이상 더 많음
     * 3. 선공이 착수하며 isWin, 후공의 남은 수가 있음
     * 4. 후공이 착수하며 isWin, 선공의 남은 수가 있음, 
     * 5. 선,후공이 둘다 isWin
     * 6. 여러번 이기는건? ㄱㅊ음 착수하면서 한번에 여럿을 걸칠 수도 있음.
     */
}