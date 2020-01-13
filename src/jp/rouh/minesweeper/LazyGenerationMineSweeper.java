package jp.rouh.minesweeper;

public abstract class LazyGenerationMineSweeper extends BaseMineSweeper{
    private boolean generated = false;
    public LazyGenerationMineSweeper(Difficulty difficulty){
        super(difficulty);
    }
    @Override
    protected void firstOpen(int x, int y){
        generate(x, y);
        super.firstOpen(x, y);
    }
    abstract void generate(int x, int y);
}
