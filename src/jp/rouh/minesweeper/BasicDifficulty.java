package jp.rouh.minesweeper;

/**
 * マインスイーパの基本難易度を表すクラス。
 */
public enum BasicDifficulty implements Difficulty{
    /** 初級(9x9マス、地雷数10) */
    BEGINNER(9, 9, 10),
    /** 中級(16x16マス、地雷数30) */
    INTERMEDIATE(16, 16, 30),
    /** 上級(16x30マス、地雷数99) */
    ADVANCED(30, 16, 99);
    private final int width;
    private final int height;
    private final int totalMineCount;
    BasicDifficulty(int width, int height, int totalMineCount){
        this.width = width;
        this.height = height;
        this.totalMineCount = totalMineCount;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth(){
        return width;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight(){
        return height;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalMineCount(){
        return totalMineCount;
    }

}
