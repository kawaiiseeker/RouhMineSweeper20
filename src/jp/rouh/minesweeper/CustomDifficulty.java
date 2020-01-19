package jp.rouh.minesweeper;

/**
 * マインスイーパのユーザ定義の難易度を表すクラス。
 */
public class CustomDifficulty implements Difficulty{
    /** 横幅の最小値 */
    public static final int MIN_WIDTH = 9;
    /** 縦幅の最小値 */
    public static final int MIN_HEIGHT = 9;
    /** 横幅の最大値 */
    public static final int MAX_WIDTH = 50;
    /** 縦幅の最大値 */
    public static final int MAX_HEIGHT = 30;
    private final int width;
    private final int height;
    private final int totalMineCount;
    /**
     * コンストラクタ
     * @param width 横幅
     * @param height 縦幅
     * @param totalMineCount 地雷数
     * @throws IllegalArgumentException 横幅が範囲外の場合
     *                                  縦幅が範囲外の場合
     *                                  地雷数が1未満またはマス数以上の場合
     */
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
