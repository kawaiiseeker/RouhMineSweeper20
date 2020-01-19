package jp.rouh.minesweeper.field;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class FullRandomFieldGenerator implements FieldGenerator{
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
