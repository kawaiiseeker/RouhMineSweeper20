package jp.rouh.minesweeper;

import jp.rouh.minesweeper.field.SafeLaunchMineField;

public class SafeLaunchMineSweeper extends AbstractMineSweeper{
    public SafeLaunchMineSweeper(Difficulty difficulty){
        super(difficulty, SafeLaunchMineField::new);
    }
}
