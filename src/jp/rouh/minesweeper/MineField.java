package jp.rouh.minesweeper;

import jp.rouh.minesweeper.util.GridField;

public abstract class MineField extends GridField<MineCell, MineField>{
    MineField(int width, int height){
        super(width, height, MineCell::new);
    }
    abstract void incrementFlaggedCount();
    abstract void decrementFlaggedCount();
    abstract void decrementCoveredCount();
    abstract void explode();
    abstract void update(int x, int y);
}
