package jp.rouh.minesweeper.field;

import jp.rouh.minesweeper.util.GridCell;

public class MineCell extends GridCell<MineCell, MineField>{
    private static final int MINE = 9;
    private boolean covered = true;
    private boolean flagged = false;
    private int value = 0;
    /* package */ void toggleFlag(){
        if(covered){
            if(flagged){
                flagged = false;
                parent.flagRemoved(x, y);
            }else{
                flagged = true;
                parent.flagAdded(x, y);
            }
        }
    }
    /* package */ void open(){
        if(covered && !flagged){
            covered = false;
            parent.cellOpened(x, y);
            if(this.isMine()){
                parent.fieldExploded();
            }else if(value==0){
                aroundCells().forEach(MineCell::open);
            }
        }
    }
    /* package */ void stamp(){
        if(!covered){
            int aroundMineCount = value;
            if(aroundMineCount==aroundFlaggedCount()){
                aroundCells().forEach(MineCell::open);
            }
        }
    }
    /* package */ boolean isMine(){
        return value==MINE;
    }
    /* package */ void setMine(){
        value = MINE;
    }
    /* package */ void setMineCount(){
        value = aroundMineCount();
    }
    public MineCellView getView(){
        if(flagged) return MineCellView.FLAGGED;
        if(covered) return MineCellView.COVERED;
        if(isMine()) return MineCellView.EXPOSED_MINE;
        return MineCellView.mineCountOf(value);
    }
    public MineCellView getResultView(){
        if(!isMine() && flagged) return MineCellView.RESULT_FLAGGED_SAFE;
        if(isMine() && !flagged && covered) return MineCellView.RESULT_COVERED_MINE;
        return getView();
    }
    private boolean isFlagged(){
        return flagged;
    }
    private int aroundMineCount(){
        return (int)aroundCells().stream().filter(MineCell::isMine).count();
    }
    private int aroundFlaggedCount(){
        return (int)aroundCells().stream().filter(MineCell::isFlagged).count();
    }
}
