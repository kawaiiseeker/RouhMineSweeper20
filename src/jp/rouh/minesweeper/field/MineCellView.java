package jp.rouh.minesweeper.field;

/**
 * プレイヤーが得られる外見を表すクラス
 */
public enum MineCellView{
    /** 周囲地雷数0の掘削済みセル */
    EXPOSED_0(0),
    /** 周囲地雷数1の掘削済みセル */
    EXPOSED_1(1),
    /** 周囲地雷数2の掘削済みセル */
    EXPOSED_2(2),
    /** 周囲地雷数3の掘削済みセル */
    EXPOSED_3(3),
    /** 周囲地雷数4の掘削済みセル */
    EXPOSED_4(4),
    /** 周囲地雷数5の掘削済みセル */
    EXPOSED_5(5),
    /** 周囲地雷数6の掘削済みセル */
    EXPOSED_6(6),
    /** 周囲地雷数7の掘削済みセル */
    EXPOSED_7(7),
    /** 周囲地雷数8の掘削済みセル */
    EXPOSED_8(8),
    /** ゲーム終了後の外見。掘削された地雷セル */
    EXPOSED_MINE(-1),
    /** 未掘削のセル */
    COVERED(-1),
    /** 旗が設置されたセル */
    FLAGGED(-1),
    /** ゲーム終了後の外見。旗が設置されていない未採掘の地雷セル */
    RESULT_COVERED_MINE(-1),
    /** ゲーム終了後の外見。旗が設置された地雷でないセル */
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
