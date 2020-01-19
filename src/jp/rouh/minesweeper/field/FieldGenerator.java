package jp.rouh.minesweeper.field;

/**
 * マインスイーパ盤面の地雷生成器のインターフェース
 */
@FunctionalInterface
interface FieldGenerator{
    /**
     * 地雷の生成処理
     * ゲームが開始後、最初に掘削が行われた場合に呼び出される。
     * @param field 対象盤面
     * @param x 掘削位置x座標
     * @param y 掘削位置y座標
     */
    void generate(MineField field, int x, int y);
    /**
     * 盤面の全ての地雷でないマスに対して、周囲地雷数を設定する。
     * @param field 対象盤面
     */
    default void apply(MineField field){
        for(int ax = 0; ax<field.getWidth(); ax++){
            for(int ay = 0; ay<field.getHeight(); ay++){
                if(!field.get(ax, ay).isMine()){
                    field.get(ax, ay).setMineCount();
                }
            }
        }
    }
}
