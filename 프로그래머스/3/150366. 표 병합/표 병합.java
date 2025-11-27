import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public String[] solution(String[] commands) {
        String[] answer = {};
        List<String> answers = new ArrayList<>();
        Sheet sh = new Sheet();

        for(String cmd : commands){
            String[] cmdArr = cmd.split(" ");
            String type = cmdArr[0];
            if(cmdArr.length == 3){ //unmerge, update val1 val2, print
                switch (type) {
                    case "UPDATE":
                        String val1 = cmdArr[1];
                        String val2 = cmdArr[2];
                        // 전체 50×50 순회하며 val1을 가진 셀 찾기
                        for(int r = 0; r < 50; r++){
                            for(int c = 0; c < 50; c++){
                                Cell cell1 = sh.getRow(r).getCell(c);
                                Cell root = cell1.findGrandma(cell1);
                                if(val1.equals(root.value)){
                                    root.setValue(val2);
                                }
                            }
                        }
                        break;
                    case "UNMERGE":// 정확히 뭐하라는거지..그냥 순회하면 2500번인가?
                        int x1 = Integer.parseInt(cmdArr[1]) - 1;
                        int y1 = Integer.parseInt(cmdArr[2]) - 1;
                        Cell targetCell = sh.getRow(y1).getCell(x1);
                        Cell targetRoot = targetCell.findGrandma(targetCell);
                        String savedValue = targetRoot.getValue();  // 값 저장

                        // 1단계: 같은 루트를 가진 모든 셀을 수집
                        List<Cell> cellsToUnmerge = new ArrayList<>();
                        for(int r = 0; r < 50; r++){
                            for(int c = 0; c < 50; c++){
                                Cell cell1 = sh.getRow(r).getCell(c);
                                Cell root = cell1.findGrandma(cell1);

                                if(root == targetRoot){  // 같은 루트면
                                    cellsToUnmerge.add(cell1);
                                }
                            }
                        }

                        // 2단계: 수집한 셀들을 일괄 초기화
                        for(Cell cell1 : cellsToUnmerge){
                            cell1.parent = cell1;  // 자기 자신으로 초기화
                            cell1.setValue(null); // 값 초기화
                        }

                        // (x1, y1) 위치만 값 복원
                        targetCell.setValue(savedValue);

                        break;
                    case "PRINT":
                        int x = Integer.parseInt(cmdArr[1]) - 1;
                        int y = Integer.parseInt(cmdArr[2]) - 1;
                        String val = sh.getRow(y).getCell(x).getValue();
                        answers.add(val == null ? "EMPTY" : val);
                        break;
                }
            }else{ //update r c val, merge r1 c1 r2 c2
                switch (type) {
                    case "UPDATE":
                        int x = Integer.parseInt(cmdArr[1]) - 1;
                        int y = Integer.parseInt(cmdArr[2]) - 1;
                        String val = cmdArr[3];

                        Cell targetCell = sh.getRow(y).getCell(x);
                        Cell root = targetCell.findGrandma(targetCell);
                        root.setValue(val);
                        break;
                    case "MERGE":
                        int x1 = Integer.parseInt(cmdArr[1]) - 1;
                        int y1 = Integer.parseInt(cmdArr[2]) - 1;
                        int x2 = Integer.parseInt(cmdArr[3]) - 1;
                        int y2 = Integer.parseInt(cmdArr[4]) - 1;
                        Cell cell1 = sh.getRow(y1).getCell(x1);
                        Cell cell2 = sh.getRow(y2).getCell(x2);
                        Cell root1 = cell1.findGrandma(cell1);
                        Cell root2 = cell2.findGrandma(cell2);

                        if (root1 != root2) {  // 같은 그룹이 아니면
                            // 값 처리 (root1에 값 있으면 유지, 없으면 root2 값 가져옴)
                            if (root1.getValue() == null && root2.getValue() != null) {
                                root1.setValue(root2.getValue());
                            }
                            root2.addParent(root1); 
                        }
                        break;
                }
            }
        }
        

        answer = answers.toArray(new String[0]);
        return answer;
    }
    
    public class Cell {
        private int x;
        private int y;
        private Cell parent;
        private String value; 

        public Cell(int x, int y){
            this.x = x;
            this.y = y;
            this.parent = this;
        }

        //1과 2 연결, 2와 3 연결, 1과 3연결이라는 것을 인식하는 방법?
        //=> new가 아닌 실제 객체?
        public void addParent(Cell cell){
            this.parent = (cell);
        }
        public Cell getParent(){
            return this.parent;
        }

        public String toString(){
            return "[ x: "+this.x+", y: " +this.y+", value: " + this.value + "]";
        }
        public String getValue(){
            Cell root = findGrandma(this);
            return root.value;
        }
        public void setValue(String value){
            this.value = value;
        }

        public Cell findGrandma(Cell cell){
            int depth = 0;
            while(cell != cell.getParent()){
                cell = cell.getParent();
                depth++;
                if(depth > 2500) break; // 무한 루프 방지
            }
            return cell;
        }
    }

    public class Row {
        private List<Cell> row;

        public Row(int index, int rowCount){
            this.row = new ArrayList<>();
            List<Cell> tmp  = new ArrayList<Cell>();
            for(int i=0;i<rowCount;i++){
                tmp.add(new Cell(i, index)); //x축 증가
            }
            this.row.addAll(tmp);
        }

        public Cell getCell(int index){
            return this.row.get(index);
        }
    }

    public class Sheet {
        private List<Row> sheet;
        
        public Sheet(){
            this.sheet = new ArrayList<>();

            int colCount = 50;
            int rowCount = 50;
            List<Row> tmp  = new ArrayList<Row>();
            for(int i=0;i<colCount;i++){
                tmp.add(new Row(i, rowCount)); //y축 증가
            }
            this.sheet.addAll(tmp);
        }

        public Row getRow(int index){
            return this.sheet.get(index);
        }
    }
}