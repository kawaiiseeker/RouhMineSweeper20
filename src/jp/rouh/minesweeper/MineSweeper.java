package jp.rouh.minesweeper;

import jp.rouh.minesweeper.field.MineCellView;

/**
 * マインスイーパアプリケーションのモデルを定義するインターフェース
 */
public interface MineSweeper{
    /**
     * このモデルの状態変更を通知する宛先を登録する。
     * 変更通知の形式は{@link MineSweeperObserver MineSweeperObserver}
     * インターフェースで規定する。
     * @param observer 通知先オブザーバ
     */
    void setObserver(MineSweeperObserver observer);
    /**
     * 盤面の縦幅を返します。
     * @return 縦幅
     */
    int getHeight();
    /**
     * 盤面の横幅を返します。
     * @return 横幅
     */
    int getWidth();
    /**
     * 総地雷数を返します。
     * @return 地雷数
     */
    int getTotalMineCount();
    /**
     * 総地雷数から旗設置数を引いて求められる推定残り地雷数を返します。
     * @return 推定残り地雷数
     */
    int getRemainingMineCount();
    /**
     * 最初の掘削から現在までのカウントを返します。
     * カウントは1から1秒ごとにインクリメントします。
     * @return カウント
     */
    int getTimeCount();
    /**
     * ゲームが終了状態かどうかを返します。
     * @return true  ゲームが終了状態の場合
     *         false ゲームが継続中の場合
     */
    boolean isFinished();
    /**
     * 盤面の地雷でないマスが全て掘削され、
     * 勝利としてゲームが終了状態かどうかを返します。
     * @return true  ゲームに勝利した場合
     *         false ゲームに敗北したか、継続中の場合
     */
    boolean isSecured();
    /**
     * 地雷が掘削され、敗北としてゲームが終了状態かどうかを返します。
     * @return true  ゲームに敗北した場合
     *         false ゲームに勝利したか、継続中の場合
     */
    boolean isExploded();
    /**
     * 指定した座標(x, y)にあるマスを掘削します。
     * 掘削したマスの周囲に地雷がない場合は、周囲のマスも連鎖的に掘削します。
     * 掘削したマスが地雷の場合、敗北としてゲームを終了します。
     * 全ての地雷でないマスを掘削した場合、勝利としてゲームを終了します。
     * ゲームが終了状態の場合、処理をスキップします。
     * マスが既に掘削されている場合、処理をスキップします。
     * マスに旗が設置されている場合、処理をスキップします。
     * @param x x座標
     * @param y y座標
     * @throws IllegalArgumentException 座標が範囲外の場合
     */
    void open(int x, int y);
    /**
     * 指定した座標(x, y)の周囲のマスを自動的に掘削します。
     * 周囲のマスに設置された旗の数と、指定したマスの周囲地雷数が一致した場合、
     * 周囲のマスの旗が設置されていない未掘削のマスを全て掘削します。
     * ゲームが終了状態の場合、処理をスキップします。
     * マスが未掘削の場合、処理をスキップします。
     * マスの周囲の旗設置数と、周囲地雷数が一致しない場合、処理をスキップします。
     * @param x x座標
     * @param y y座標
     * @throws IllegalArgumentException 座標が範囲外の場合
     */
    void stamp(int x, int y);
    /**
     * 指定した座標(x, y)のマスに旗を設置します。
     * マスに既に旗が設置されていた場合、旗を除去します。
     * ゲームが終了状態の場合、処理をスキップします。
     * マスが既に掘削されている場合、処理をスキップします。
     * @param x x座標
     * @param y y座標
     * @throws IllegalArgumentException 座標が範囲外の場合
     */
    void toggleFlag(int x, int y);
    /**
     * 指定した座標(x, y)のプレイヤーが得られる外見を返します。
     * @param x x座標
     * @param y y座標
     * @return view マスの外見
     * @throws IllegalArgumentException 座標が範囲外の場合
     */
    MineCellView getView(int x, int y);
}
