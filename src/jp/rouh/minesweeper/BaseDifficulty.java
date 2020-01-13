package jp.rouh.minesweeper;

public enum BaseDifficulty implements Difficulty{
    BEGINNER(9, 9, 10),
    INTERMEDIATE(16, 16, 30),
    ADVANCED(30, 16, 99);
    private final int width;
    private final int height;
    private final int totalMineCount;
    BaseDifficulty(int width, int height, int totalMineCount){
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
}
