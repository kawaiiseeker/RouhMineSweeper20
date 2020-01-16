package jp.rouh.minesweeper.field;

import jp.rouh.minesweeper.Difficulty;
import jp.rouh.minesweeper.util.GridField;

public abstract class AbstractMineField extends GridField<MineCell, AbstractMineField> implements MineField{
    private FieldObserver observer;
    protected final int totalMineCount;
    private final int openQuota;
    private int flagCount = 0;
    private int openCount = 0;
    /* package */ AbstractMineField(Difficulty difficulty){
        super(difficulty.getWidth(), difficulty.getHeight(), MineCell::new);
        this.totalMineCount = difficulty.getTotalMineCount();
        this.openQuota = difficulty.getTotalCellCount() - totalMineCount;
    }
    /* package */ void detectFlagAdded(int x, int y){
        flagCount++;
        if(observer!=null){
            observer.notifyCellUpdated(x, y);
        }
    }
    /* package */ void detectFlagRemoved(int x, int y){
        flagCount--;
        if(observer!=null){
            observer.notifyCellUpdated(x, y);
        }
    }
    /* package */ void detectCellOpened(int x, int y){
        openCount++;
        if(observer!=null){
            observer.notifyCellUpdated(x, y);
            if(openCount==openQuota){
                observer.notifyFieldSecured();
            }
        }
    }
    /* package */ void detectMineExposed(){
        if(observer!=null){
            observer.notifyFieldExploded();
        }
    }
    @Override
    public void setObserver(FieldObserver observer){
        this.observer = observer;
    }
    @Override
    public void open(int x, int y){
        if(openCount==0){
            generate(x, y);
        }
        get(x, y).open();
    }
    @Override
    public void stamp(int x, int y){
        get(x, y).stamp();
    }
    @Override
    public void toggleFlag(int x, int y){
        get(x, y).toggleFlag();
    }
    @Override
    public MineCellView getView(int x, int y){
        return get(x, y).getView();
    }
    @Override
    public MineCellView getResultView(int x, int y){
        return get(x, y).getResultView();
    }
    @Override
    public int getWidth(){
        return width;
    }
    @Override
    public int getHeight(){
        return height;
    }
    @Override
    public int getTotalMineCount(){
        return totalMineCount;
    }
    @Override
    public int getEstimatedRemainingMineCount(){
        return totalMineCount - flagCount;
    }
    public abstract void generate(int x, int y);
}
