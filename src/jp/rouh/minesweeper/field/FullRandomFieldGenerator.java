package jp.rouh.minesweeper.field;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 完全ランダム地雷生成器
 */
class FullRandomFieldGenerator implements FieldGenerator{
    /**
     * 地雷の生成処理
     * 初手採掘位置に関わらず、完全ランダムで盤面に地雷を生成する。
     * @param field 対象盤面
     * @param xIgnored 採掘位置x座標(生成処理では無視される)
     * @param yIgnored 採掘位置y座標(生成処理では無視される)
     */
    @Override
    public void generate(MineField field, int xIgnored, int yIgnored){
        Random random = ThreadLocalRandom.current();
        int mineCount = 0;
        while(mineCount<field.getTotalMineCount()){
            int rx = random.nextInt(field.getWidth());
            int ry = random.nextInt(field.getHeight());
            if(!field.get(rx, ry).isMine()){
                field.get(rx, ry).setMine();
                mineCount++;
            }
        }
        apply(field);
    }
}
