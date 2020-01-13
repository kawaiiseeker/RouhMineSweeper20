package jp.rouh.minesweeper;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleMineSweeper extends EagerGenerationMineSweeper{
    public SimpleMineSweeper(Difficulty difficulty){
        super(difficulty);
    }
    @Override
    void generate(){
        Random random = ThreadLocalRandom.current();
        int width = getWidth();
        int height = getHeight();
        int totalMineCount = getTotalMineCount();
        int mineCount = 0;
        while(mineCount<totalMineCount){
            int rx = random.nextInt(width);
            int ry = random.nextInt(height);
            if(!super.get(rx, ry).isMine()){
                super.get(rx, ry).setMine();
                mineCount++;
            }
        }
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(!super.get(x, y).isMine()){
                    super.get(x, y).setMineCount();
                }
            }
        }
    }
}
