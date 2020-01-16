package jp.rouh.minesweeper.field;

import java.util.List;

public enum MineCellView{
    SAFE,
    MC_1,
    MC_2,
    MC_3,
    MC_4,
    MC_5,
    MC_6,
    MC_7,
    MC_8,
    MINE,
    COVERED,
    FLAGGED,
    UNREVEALED_MINE,
    INCORRECT_FLAGGED;
    private static final List<MineCellView> REMOVED_VIEWS =
            List.of(SAFE, MC_1, MC_2, MC_3, MC_4, MC_5, MC_6, MC_7, MC_8, MINE);
    public static MineCellView mineCountOf(int mineCount){
        return REMOVED_VIEWS.get(mineCount);
    }
}
