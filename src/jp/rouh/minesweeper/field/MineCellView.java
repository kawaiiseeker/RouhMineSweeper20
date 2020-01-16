package jp.rouh.minesweeper.field;

public enum MineCellView{
    EXPOSED_0(0),
    EXPOSED_1(1),
    EXPOSED_2(2),
    EXPOSED_3(3),
    EXPOSED_4(4),
    EXPOSED_5(5),
    EXPOSED_6(6),
    EXPOSED_7(7),
    EXPOSED_8(8),
    EXPOSED_MINE(-1),
    COVERED(-1),
    FLAGGED(-1),
    RESULT_COVERED_MINE(-1),
    RESULT_FLAGGED_SAFE(-1);
    private final int mineCount;
    MineCellView(int mineCount){
        this.mineCount = mineCount;
    }
    /* package */ static MineCellView mineCountOf(int mineCount){
        assert mineCount>=0 && mineCount<9;
        return valueOf(mineCount);
    }
    private static MineCellView valueOf(int mineCount){
        for(MineCellView mineCellView:values()){
            if(mineCellView.mineCount==mineCount){
                return mineCellView;
            }
        }
        throw new IllegalArgumentException("no such cell-view");
    }
}
