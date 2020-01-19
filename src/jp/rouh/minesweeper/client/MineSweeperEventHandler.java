package jp.rouh.minesweeper.client;

/**
 * マインスイーパアプリケーションのビューからイベントを受け取るクラス
 */
interface MineSweeperEventHandler{
    /**
     * 座標(x, y)のマスに対し右クリックがなされた場合の処理
     * @param x X座標
     * @param y Y座標
     */
    void cellRightClicked(int x, int y);
    /**
     * 座標(x, y)のマスに対し左クリックがなされた場合の処理
     * @param x X座標
     * @param y Y座標
     */
    void cellLeftClicked(int x, int y);
    /**
     * 座標(x, y)のマスに対しダブルクリックがなされた場合の処理
     * @param x X座標
     * @param y Y座標
     */
    void cellDoubleClicked(int x, int y);
    /**
     * リスタートボタンが押下された時の処理
     */
    void restartButtonPressed();
}
