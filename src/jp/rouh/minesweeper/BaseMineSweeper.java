package jp.rouh.minesweeper;

public abstract class BaseMineSweeper extends MineField implements MineSweeper{
    private final int width;
    private final int height;
    private final int totalMineCount;
    private int coveredCount;
    private int flaggedCount;
    private int timeCount = 0;
    protected MineSweeperObserver observer;
    private Status status = Status.READY;
    private enum Status{
        READY, RUNNING, EXPLODED, SECURED
    }
    private BaseMineSweeper(int width, int height, int totalMineCount){
        super(width, height);
        this.width = width;
        this.height = height;
        this.totalMineCount = totalMineCount;
        this.coveredCount = width*height;
        this.flaggedCount = 0;
    }
    public BaseMineSweeper(Difficulty difficulty){
        this(difficulty.width(), difficulty.height(), difficulty.totalMineCount());
    }
    @Override
    void incrementFlaggedCount(){
        flaggedCount++;
        observer.updateRemainingMineCount();
    }
    @Override
    void decrementFlaggedCount(){
        flaggedCount--;
        observer.updateRemainingMineCount();
    }
    @Override
    void decrementCoveredCount(){
        coveredCount--;
        if(coveredCount==totalMineCount){
            status = Status.SECURED;
            observer.updateStatus();
        }
    }
    @Override
    void explode(){
        status = Status.EXPLODED;
        observer.updateStatus();
    }
    @Override
    void update(int x, int y){
        observer.updateCell(x, y);
    }
    @Override
    public void setObserver(MineSweeperObserver observer){
        this.observer = observer;
    }
    @Override
    public int getHeight(){
        return height;
    }
    @Override
    public int getWidth(){
        return width;
    }
    @Override
    public int getTotalMineCount(){
        return totalMineCount;
    }
    @Override
    public int getRemainingMineCount(){
        return totalMineCount - flaggedCount;
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
        if(status==Status.READY){
            status = Status.RUNNING;
            firstOpen(x, y);
        }
        if(status==Status.RUNNING){
            super.get(x, y).open();
        }
    }
    protected void firstOpen(int x, int y){
        startCounting();
    }
    @Override
    public void stamp(int x, int y){
        requireWithinRange(x, y);
        if(status==Status.RUNNING){
            super.get(x, y).stampOpen();
        }
    }
    @Override
    public void toggleFlag(int x, int y){
        requireWithinRange(x, y);
        if(status==Status.RUNNING){
            super.get(x, y).toggleFlag();
        }
    }
    @Override
    public MineCellView getView(int x, int y){
        requireWithinRange(x, y);
        return isFinished()?
                super.get(x, y).getResultView():
                super.get(x, y).getView();
    }
    private void startCounting(){
        new Thread(()->{
            try{
                while(!isFinished()){
                    timeCount++;
                    observer.updateTimeCount();
                    Thread.sleep(1000);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }
}
