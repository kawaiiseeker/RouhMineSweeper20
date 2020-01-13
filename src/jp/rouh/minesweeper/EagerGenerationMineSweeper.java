package jp.rouh.minesweeper;

public abstract class EagerGenerationMineSweeper extends BaseMineSweeper{
    public EagerGenerationMineSweeper(Difficulty difficulty){
        super(difficulty);
        generate();
    }
    abstract void generate();
}
