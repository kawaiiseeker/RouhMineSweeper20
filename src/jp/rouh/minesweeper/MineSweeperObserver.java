package jp.rouh.minesweeper;

/**
 * マインスイーパのモデルから変更通知を受け取るオブザーバのインターフェース
 */
public interface MineSweeperObserver{
    /**
     * 旗の設置または除去による推定残り地雷数の変化を通知します。
     */
    void updateRemainingMineCount();
    /**
     * カウントの変化を通知します。
     */
    void updateTimeCount();
    /**
     * ゲームの状態変化を通知します。
     */
    void updateStatus();
    /**
     * マスの状態変化を通知します。
     * @param x x座標
     * @param y y座標
     * @throws IllegalArgumentException 座標が範囲外の場合
     */
    void updateCell(int x, int y);
}
