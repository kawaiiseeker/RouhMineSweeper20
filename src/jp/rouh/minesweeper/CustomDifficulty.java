package jp.rouh.minesweeper;

public class CustomDifficulty implements Difficulty{
    private final int width;
    private final int height;
    private final int totalMineCount;
    public CustomDifficulty(int width, int height, int totalMineCount){
        this.width = width;
        this.height = height;
        this.totalMineCount = totalMineCount;
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
    public String getName(){
        return "CUSTOM";
    }
}
