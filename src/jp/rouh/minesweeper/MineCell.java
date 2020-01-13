package jp.rouh.minesweeper;

import jp.rouh.minesweeper.util.GridCell;

public class MineCell extends GridCell<MineCell, MineField>{
    private static final int MINE = 9;
    private boolean covered = true;
    private boolean flagged = false;
    private int value = 0;
    public void toggleFlag(){
        if(flagged){
            removeFlag();
        }else{
            buildFlag();
        }
    }
    public void removeFlag(){
        if(flagged){
            flagged = false;
            getParent().decrementFlaggedCount();
            getParent().update(getX(), getY());
        }
    }
    public void buildFlag(){
        if(covered && !flagged){
            flagged = true;
            getParent().incrementFlaggedCount();
            getParent().update(getX(), getY());
        }
    }
    public void open(){
        if(covered && !flagged){
            covered = false;
            getParent().decrementCoveredCount();
            getParent().update(getX(), getY());
            if(this.isMine()){
                getParent().explode();
            }else if(this.isSafe()){
                aroundCells().forEach(MineCell::open);
            }
        }
    }
    void setMine(){
        value = MINE;
    }
    void setMineCount(){
        value = aroundMineCount();
    }
    void stampOpen(){
        if(!covered){
            int aroundMineCount = value;
            if(aroundMineCount==aroundFlaggedCount()){
                aroundCells().forEach(MineCell::open);
            }
        }
    }
    public MineCellView getView(){
        if(flagged) return MineCellView.FLAGGED;
        if(covered) return MineCellView.COVERED;
        return MineCellView.mineCountOf(value);
    }
    public MineCellView getResultView(){
        if(!covered && isMine()) return MineCellView.MINE;
        if(!flagged && isMine()) return MineCellView.UNREVEALED_MINE;
        if(flagged && !isMine()) return MineCellView.INCORRECT_FLAGGED;
        return getView();
    }
    private boolean isSafe(){
        return value==0;
    }
    boolean isMine(){
        return value==MINE;
    }
    boolean isCovered(){
        return covered;
    }
    boolean isFlagged(){
        return flagged;
    }
    private int aroundMineCount(){
        return (int)aroundCells().stream().filter(MineCell::isMine).count();
    }
    private int aroundFlaggedCount(){
        return (int)aroundCells().stream().filter(MineCell::isFlagged).count();
    }
    /* for debug */
    @Override
    public String toString(){
        return "MineCell("+getX()+","+getY()+")["+value+"|"+(covered?(flagged?"F":"C"):"R")+"]";
    }
    /* for debug */
    public String toDebugViewString(){
        if(isSafe()) return " ";
        if(isMine()) return "X";
        return Integer.toString(value);
    }
    /* for debug */
    public String toViewString(){
        if(flagged) return "▼";
        if(covered) return "■";
        return toDebugViewString();
    }
}
