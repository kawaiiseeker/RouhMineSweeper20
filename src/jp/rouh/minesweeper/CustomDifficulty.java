package jp.rouh.minesweeper;

public class CustomDifficulty implements Difficulty{
    public static final int MIN_WIDTH = 9;
    public static final int MIN_HEIGHT = 9;
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 30;
    private final int width;
    private final int height;
    private final int totalMineCount;
    public CustomDifficulty(int width, int height, int totalMineCount){
        if(width<MIN_WIDTH || width>MAX_WIDTH){
            throw new IllegalArgumentException("invalid width");
        }
        if(height<MIN_HEIGHT || height>MAX_HEIGHT){
            throw new IllegalArgumentException("invalid height");
        }
        if(totalMineCount<=0 || totalMineCount>=width*height){
            throw new IllegalArgumentException("invalid mine-count");
        }
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
}
