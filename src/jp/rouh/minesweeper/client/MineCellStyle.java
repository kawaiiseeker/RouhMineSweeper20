package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.field.MineCellView;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

enum MineCellStyle{
    SAFE("　", Color.WHITE, true),
    MC_1("１", new Color(51, 102, 204), true),
    MC_2("２", new Color(0, 153, 51), true),
    MC_3("３", new Color(204, 51, 0), true),
    MC_4("４", new Color(0, 51, 153), true),
    MC_5("５", new Color(102, 0, 0), true),
    MC_6("６", new Color(31, 184, 133), true),
    MC_7("７", new Color(204, 0, 51), true),
    MC_8("８", new Color(30, 0, 0), true),
    MINE("※", Color.RED, true),
    COVERED("　", Color.WHITE, false),
    SECURED_COVERED_MINE("❌", Color.BLACK, false),
    EXPLODED_COVERED_MINE("❌", Color.WHITE, false),
    FLAGGED("🚩", Color.RED, false),
    SECURED_FLAGGED("🚩", Color.BLACK, false),
    INCORRECT_FLAGGED("🚩", Color.WHITE, false);
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
    static MineCellStyle valueOf(MineCellView view, boolean secured){
        switch(view){
            case SAFE: return MineCellStyle.SAFE;
            case MC_1: return MineCellStyle.MC_1;
            case MC_2: return MineCellStyle.MC_2;
            case MC_3: return MineCellStyle.MC_3;
            case MC_4: return MineCellStyle.MC_4;
            case MC_5: return MineCellStyle.MC_5;
            case MC_6: return MineCellStyle.MC_6;
            case MC_7: return MineCellStyle.MC_7;
            case MC_8: return MineCellStyle.MC_8;
            case MINE: return MineCellStyle.MINE;
            case COVERED: return MineCellStyle.COVERED;
            case FLAGGED: return MineCellStyle.FLAGGED;
            case UNREVEALED_MINE:
                return secured?
                        MineCellStyle.SECURED_COVERED_MINE:
                        MineCellStyle.EXPLODED_COVERED_MINE;
            case INCORRECT_FLAGGED:
                return secured?
                        MineCellStyle.SECURED_FLAGGED:
                        MineCellStyle.INCORRECT_FLAGGED;
        }
        throw new IllegalArgumentException("implementation error");
    }
}