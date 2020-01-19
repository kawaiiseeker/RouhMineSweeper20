package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.field.MineCellView;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

enum MineCellStyle{
    EXPOSED_0("　", Color.WHITE, true),
    EXPOSED_1("１", new Color(51, 102, 204), true),
    EXPOSED_2("２", new Color(0, 153, 51), true),
    EXPOSED_3("３", new Color(204, 51, 0), true),
    EXPOSED_4("４", new Color(0, 51, 153), true),
    EXPOSED_5("５", new Color(102, 0, 0), true),
    EXPOSED_6("６", new Color(31, 184, 133), true),
    EXPOSED_7("７", new Color(204, 0, 51), true),
    EXPOSED_8("８", new Color(30, 0, 0), true),
    EXPOSED_MINE("※", Color.RED, true),
    COVERED("　", Color.WHITE, false),
    SECURED_COVERED_MINE("❌", Color.BLACK, false),
    EXPLODED_COVERED_MINE("❌", Color.WHITE, false),
    FLAGGED("🚩", Color.RED, false),
    SECURED_FLAGGED("🚩", Color.BLACK, false),
    EXPLODED_FLAGGED_SAFE("🚩", Color.WHITE, false);
    private static final Color REMOVED_COLOR = new Color(140, 200, 140);
    private static final Color TARGETED_COLOR = new Color(70, 160, 70);
    private static final Color COVERED_COLOR = new Color(30, 120, 30);
    private static final Border RAISED_BORDER = new BevelBorder(BevelBorder.RAISED);
    private static final Border LOWERED_BORDER = new BevelBorder(BevelBorder.LOWERED);
    private final String text;
    private final Color fontColor;
    private final boolean removed;
    MineCellStyle(String text, Color fontColor, boolean removed){
        this.text = text;
        this.fontColor = fontColor;
        this.removed = removed;
    }
    Color getBackgroundColor(boolean onMouse){
        return removed? REMOVED_COLOR: onMouse? TARGETED_COLOR: COVERED_COLOR;
    }
    Color getFontColor(){
        return fontColor;
    }
    String getText(){
        return text;
    }
    Border getBorder(){
        return removed? LOWERED_BORDER:RAISED_BORDER;
    }
    static MineCellStyle of(MineCellView view){
        return valueOf(view, false);
    }
    static MineCellStyle ofSecured(MineCellView view){
        return valueOf(view, true);
    }
    private static MineCellStyle valueOf(MineCellView view, boolean secured){
        switch(view){
            case EXPOSED_0: return MineCellStyle.EXPOSED_0;
            case EXPOSED_1: return MineCellStyle.EXPOSED_1;
            case EXPOSED_2: return MineCellStyle.EXPOSED_2;
            case EXPOSED_3: return MineCellStyle.EXPOSED_3;
            case EXPOSED_4: return MineCellStyle.EXPOSED_4;
            case EXPOSED_5: return MineCellStyle.EXPOSED_5;
            case EXPOSED_6: return MineCellStyle.EXPOSED_6;
            case EXPOSED_7: return MineCellStyle.EXPOSED_7;
            case EXPOSED_8: return MineCellStyle.EXPOSED_8;
            case EXPOSED_MINE: return MineCellStyle.EXPOSED_MINE;
            case COVERED: return MineCellStyle.COVERED;
            case FLAGGED: return MineCellStyle.FLAGGED;
            case RESULT_COVERED_MINE:
                return secured?
                        MineCellStyle.SECURED_COVERED_MINE:
                        MineCellStyle.EXPLODED_COVERED_MINE;
            case RESULT_FLAGGED_SAFE:
                return secured?
                        MineCellStyle.SECURED_FLAGGED:
                        MineCellStyle.EXPLODED_FLAGGED_SAFE;
        }
        throw new IllegalArgumentException("unexpected out of switch statement");
    }
}