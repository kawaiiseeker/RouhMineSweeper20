package jp.rouh.minesweeper.field;

import jp.rouh.minesweeper.Difficulty;
import jp.rouh.minesweeper.util.GridField;

/**
 * マインスイーパの盤面を表すクラス
 */
public abstract class MineField extends GridField<MineCell, MineField>{
    private final GenerationPolicy policy;
    private final int totalMineCount;
    private final int openQuota;
    private int flagCount = 0;
    private int openCount = 0;
    /**
     * コンストラクタ
     * @param difficulty 盤面の難易度
     * @param policy 地雷生成戦略
     */
    public MineField(Difficulty difficulty, GenerationPolicy policy){
        super(difficulty.getWidth(), difficulty.getHeight(), MineCell::new);
        this.totalMineCount = difficulty.getTotalMineCount();
        this.openQuota = difficulty.getTotalCellCount() - totalMineCount;
        this.policy = policy;
    }
    public int getTotalMineCount(){
        return totalMineCount;
    }
    public int getRemainingMineCount(){
        return totalMineCount - flagCount;
    }
    /* package */ void flagAdded(int x, int y){
        flagCount++;
        cellUpdated(x, y);
        remainingMineCountUpdated();
    }
    /* package */ void flagRemoved(int x, int y){
        flagCount--;
        cellUpdated(x, y);
        remainingMineCountUpdated();
    }
    /* package */ void cellOpened(int x, int y){
        openCount++;
        cellUpdated(x, y);
        if(openCount==openQuota){
            fieldSecured();
        }
    }
    /**
     * 推定残り地雷数が変化した際の処理
     */
    protected abstract void remainingMineCountUpdated();
    /**
     * セルが変更された場合の処理
     * @param x セルのx座標
     * @param y セルのy座標
     */
    protected abstract void cellUpdated(int x, int y);
    /**
     * 地雷セルが掘削された場合の処理
     */
    protected abstract void fieldExploded();
    /**
     * 全ての地雷でないセルが掘削された場合の処理
     */
    protected abstract void fieldSecured();
    protected void open(int x, int y){
        get(x, y).open();
    }
    protected void toggleFlag(int x, int y){
        get(x, y).toggleFlag();
    }
    protected void stamp(int x, int y){
        get(x, y).stamp();
    }
    protected MineCellView getView(int x, int y){
        return get(x, y).getView();
    }
    protected MineCellView getResultView(int x, int y){
        return get(x, y).getResultView();
    }
    /**
     * コンストラクタで指定された生成器をもとに地雷を生成します。
     * 生成の際に引数として初手の採掘位置を渡します。
     * @param x 掘削位置x座標
     * @param y 掘削位置y座標
     */
    protected void generate(int x, int y){
        policy.getGenerator().generate(this, x, y);

    }
}
