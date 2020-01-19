package jp.rouh.minesweeper;

import jp.rouh.minesweeper.field.GenerationPolicy;
import jp.rouh.minesweeper.field.MineCellView;
import jp.rouh.minesweeper.field.MineField;

/**
 * マインスイーパアプリケーションのモデルを担う実装クラス
 */
public class StandardMineSweeper extends MineField implements MineSweeper{
    private MineSweeperObserver observer;
    private Status status = Status.RUNNING;
    private int timeCount = 0;
    private enum Status{
        RUNNING, SECURED, EXPLODED
    }
    /**
     * コンストラクタ
     * @param difficulty 難易度
     * @param policy 地雷生成戦略
     */
    public StandardMineSweeper(Difficulty difficulty, GenerationPolicy policy){
        super(difficulty, policy);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void remainingMineCountUpdated(){
        if(observer!=null){
            observer.updateRemainingMineCount();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void cellUpdated(int x, int y){
        if(observer!=null){
            observer.updateCell(x, y);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void fieldExploded(){
        status = Status.EXPLODED;
        if(observer!=null){
            observer.updateStatus();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void fieldSecured(){
        status = Status.SECURED;
        if(observer!=null){
            observer.updateStatus();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setObserver(MineSweeperObserver observer){
        this.observer = observer;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getTimeCount(){
        return timeCount;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinished(){
        return isSecured() || isExploded();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSecured(){
        return status==Status.SECURED;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExploded(){
        return status==Status.EXPLODED;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void open(int x, int y){
        if(status==Status.RUNNING){
            if(timeCount==0){
                startCounting();
                generate(x, y);
            }
            super.open(x, y);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void stamp(int x, int y){
        if(status==Status.RUNNING){
            super.stamp(x, y);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleFlag(int x, int y){
        if(status==Status.RUNNING){
            super.toggleFlag(x, y);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public MineCellView getView(int x, int y){
        if(status==Status.RUNNING){
            return super.getView(x, y);
        }else{
            return super.getResultView(x, y);
        }
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
}
