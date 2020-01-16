package jp.rouh.minesweeper.field;

import jp.rouh.minesweeper.util.GridCell;

public class MineCell extends GridCell<MineCell, AbstractMineField>{
    private static final int MINE = 9;
    private boolean covered = true;
    private boolean flagged = false;
    private int value = 0;
    /* package */ void toggleFlag(){
        if(covered){
            if(flagged){
                flagged = false;
                parent.detectFlagRemoved(x, y);
            }else{
                flagged = true;
                parent.detectFlagAdded(x, y);
            }
        }
    }
    /* package */ void open(){
        if(covered && !flagged){
            covered = false;
            parent.detectCellOpened(x, y);
            if(this.isMine()){
                parent.detectMineExposed();
            }else if(this.isSafe()){
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
        if(covered && isMine()) return MineCellView.RESULT_COVERED_MINE;
        if(flagged && !isMine()) return MineCellView.RESULT_FLAGGED_SAFE;
        return getView();
    }
    /* package */ boolean isSafe(){
        return value==0;
    }
    /* package */ boolean isMine(){
        return value==MINE;
    }
    /* package */ boolean isCovered(){
        return covered;
    }
    /* package */ boolean isFlagged(){
        return flagged;
    }
    private int aroundMineCount(){
        return (int)aroundCells().stream().filter(MineCell::isMine).count();
    }
    private int aroundFlaggedCount(){
        return (int)aroundCells().stream().filter(MineCell::isFlagged).count();
    }
}
