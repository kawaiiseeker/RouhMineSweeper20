package jp.rouh.minesweeper.field;

import jp.rouh.minesweeper.Difficulty;
import jp.rouh.minesweeper.util.GridField;

public abstract class MineField extends GridField<MineCell, MineField>{
    private final GenerationPolicy policy;
    private final int totalMineCount;
    private final int openQuota;
    private int flagCount = 0;
    private int openCount = 0;
    public MineField(Difficulty difficulty, GenerationPolicy policy){
        super(difficulty.getWidth(), difficulty.getHeight(), MineCell::new);
        this.totalMineCount = difficulty.getTotalMineCount();
        this.openQuota = difficulty.getTotalCellCount() - totalMineCount;
        this.policy = policy;
    }
    public int getTotalMineCount(){
        return totalMineCount;
    }
    public int getRemainingMineCount(){
        return totalMineCount - flagCount;
    }
    /* package */ void flagAdded(int x, int y){
        flagCount++;
        cellUpdated(x, y);
        remainingMineCountUpdated();
    }
    /* package */ void flagRemoved(int x, int y){
        flagCount--;
        cellUpdated(x, y);
        remainingMineCountUpdated();
    }
    /* package */ void cellOpened(int x, int y){
        openCount++;
        cellUpdated(x, y);
        if(openCount==openQuota){
            fieldSecured();
        }
    }
    protected abstract void remainingMineCountUpdated();
    protected abstract void cellUpdated(int x, int y);
    protected abstract void fieldExploded();
    protected abstract void fieldSecured();
    protected void open(int x, int y){
        get(x, y).open();
    }
    protected void toggleFlag(int x, int y){
        get(x, y).toggleFlag();
    }
    protected void stamp(int x, int y){
        get(x, y).stamp();
    }
    protected MineCellView getView(int x, int y){
        return get(x, y).getView();
    }
    protected MineCellView getResultView(int x, int y){
        return get(x, y).getResultView();
    }
    protected void generate(int x, int y){
        policy.getGenerator().generate(this, x, y);

    }
}
