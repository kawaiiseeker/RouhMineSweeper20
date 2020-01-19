package jp.rouh.minesweeper.field;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 完全ランダム地雷生成器
 */
class SafeLaunchFieldGenerator implements FieldGenerator{
    /**
     * 地雷の生成処理
     * 初手が必ず周囲地雷数0となるよう地雷を生成します。
     * @param field 対象盤面
     * @param x 採掘位置x座標
     * @param y 採掘位置y座標
     */
    @Override
    public void generate(MineField field, int x, int y){
        Random random = ThreadLocalRandom.current();
        int mineCount = 0;
        while(mineCount<field.getTotalMineCount()){
            int rx = random.nextInt(field.getWidth());
            int ry = random.nextInt(field.getHeight());
            if(Math.abs(rx - x)>=2 || Math.abs(ry - y)>=2){
                if(!field.get(rx, ry).isMine()){
                    field.get(rx, ry).setMine();
                    mineCount++;
                }
            }
        }
        apply(field);
    }
}
