package jp.rouh.minesweeper;

import jp.rouh.minesweeper.field.FullRandomMineField;

public class SimpleMineSweeper extends AbstractMineSweeper{
    public SimpleMineSweeper(Difficulty difficulty){
        super(difficulty, FullRandomMineField::new);
    }
}
