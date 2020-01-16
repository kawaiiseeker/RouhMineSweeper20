package jp.rouh.minesweeper;

public class CustomDifficulty implements Difficulty{
    private final int width;
    private final int height;
    private final int totalMineCount;
    CustomDifficulty(int width, int height, int totalMineCount){
        this.width = width;
        this.height = height;
        this.totalMineCount = totalMineCount;
    }
    @Override
    public int width(){
        return width;
    }
    @Override
    public int height(){
        return height;
    }
    @Override
    public int totalMineCount(){
        return totalMineCount;
    }
    @Override
    public String difficultyName(){
        return "CUSTOM";
    }
}
