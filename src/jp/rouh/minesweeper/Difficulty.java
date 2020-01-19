package jp.rouh.minesweeper;

/**
 * マインスイーパの難易度を定義するインターフェース。
 * 盤面の縦幅・横幅・地雷数を保持します。
 */
public interface Difficulty{
    /**
     * 盤面の横幅を返します。
     * @return 横幅
     */
    int getWidth();
    /**
     * 盤面の縦幅を返します。
     * @return 縦幅
     */
    int getHeight();
    /**
     * 地雷数を返します。
     * @return 地雷数
     */
    int getTotalMineCount();
    /**
     * 盤面のマス数、すなわち縦幅*横幅の値を返します。
     * @return マス数
     */
    default int getTotalCellCount(){
        return getWidth()*getHeight();
    }
}
