package jp.rouh.minesweeper;

import jp.rouh.minesweeper.field.FieldObserver;
import jp.rouh.minesweeper.field.MineCellView;
import jp.rouh.minesweeper.field.MineField;

import java.util.function.Function;

abstract class AbstractMineSweeper implements MineSweeper, FieldObserver{
    private MineSweeperObserver observer;
    private final String difficultyName;
    private final MineField field;
    private int timeCount = 0;
    private Status status = Status.RUNNING;
    private enum Status{
        RUNNING, EXPLODED, SECURED
    }
    AbstractMineSweeper(Difficulty difficulty, Function<Difficulty, MineField> mineFieldFactory){
        this.difficultyName = difficulty.getName();
        this.field = mineFieldFactory.apply(difficulty);
        this.field.setObserver(this);
    }
    @Override
    public void setObserver(MineSweeperObserver observer){
        this.observer = observer;
    }
    @Override
    public int getHeight(){
        return field.getHeight();
    }
    @Override
    public int getWidth(){
        return field.getWidth();
    }
    @Override
    public int getTotalMineCount(){
        return field.getTotalMineCount();
    }
    @Override
    public int getEstimatedRemainingMineCount(){
        return field.getEstimatedRemainingMineCount();
    }
    @Override
    public int getTimeCount(){
        return timeCount;
    }
    @Override
    public boolean isFinished(){
        return isSecured() || isExploded();
    }
    @Override
    public boolean isSecured(){
        return status==Status.SECURED;
    }
    @Override
    public boolean isExploded(){
        return status==Status.EXPLODED;
    }
    @Override
    public void open(int x, int y){
        requireWithinRange(x, y);
        if(isFinished()) return;
        if(timeCount==0){
            startCounting();
        }
        field.open(x, y);
    }
    @Override
    public void stamp(int x, int y){
        requireWithinRange(x, y);
        if(isFinished()) return;
        field.stamp(x, y);
    }
    @Override
    public void toggleFlag(int x, int y){
        requireWithinRange(x, y);
        if(isFinished()) return;
        field.toggleFlag(x, y);
    }
    @Override
    public MineCellView getView(int x, int y){
        requireWithinRange(x, y);
        return isFinished()?
                field.getResultView(x, y):
                field.getView(x, y);
    }
    @Override
    public String getDifficultyName(){
        return difficultyName;
    }
    private void startCounting(){
        new Thread(()->{
            try{
                while(!isFinished()){
                    timeCount++;
                    if(observer!=null){
                        observer.updateTimeCount();
                    }
                    Thread.sleep(1000);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }
    private void requireWithinRange(int x, int y){
        if(!field.hasRange(x, y)){
            throw new IllegalArgumentException("out of range: ("+x+","+y+")");
        }
    }
    @Override
    public void notifyCellUpdated(int x, int y){
        if(observer!=null){
            observer.updateCell(x, y);
        }
    }
    @Override
    public void notifyFieldSecured(){
        this.status = Status.SECURED;
        if(observer!=null){
            observer.updateStatus();
        }
    }
    @Override
    public void notifyFieldExploded(){
        this.status = Status.EXPLODED;
        if(observer!=null){
            observer.updateStatus();
        }
    }
}
