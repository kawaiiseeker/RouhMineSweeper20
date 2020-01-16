package jp.rouh.minesweeper.field;

import jp.rouh.minesweeper.Difficulty;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SafeLaunchMineField extends AbstractMineField{
    public SafeLaunchMineField(Difficulty difficulty){
        super(difficulty);
    }
    @Override
    public void generate(int x, int y){
        Random random = ThreadLocalRandom.current();
        int mineCount = 0;
        while(mineCount<totalMineCount){
            int rx = random.nextInt(width);
            int ry = random.nextInt(height);
            if(Math.abs(rx - x)>2 || Math.abs(ry - y)>2){
                if(!super.get(rx, ry).isMine()){
                    super.get(rx, ry).setMine();
                    mineCount++;
                }
            }
        }
        for(int ax = 0; ax<width; ax++){
            for(int ay = 0; ay<height; ay++){
                if(!super.get(ax, ay).isMine()){
                    super.get(ax, ay).setMineCount();
                }
            }
        }
    }
}
