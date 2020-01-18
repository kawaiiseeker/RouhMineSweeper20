package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.field.MineCellView;

import javax.swing.*;

class MineCellLabel extends JLabel{
    private MineCellStyle style = MineCellStyle.COVERED;
    private boolean highlighted = false;
    MineCellLabel(){
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setSize(MineSweeperFrame.CELL_SIZE, MineSweeperFrame.CELL_SIZE);
    }
    public void updateView(MineCellView view, boolean secured){
        style = secured? MineCellStyle.ofSecured(view):MineCellStyle.of(view);
        applyStyle();
    }
    public void setHighlight(boolean flg){
        highlighted = flg;
        applyStyle();
    }
    private void applyStyle(){
        setText(style.getText());
        setBorder(style.getBorder());
        setForeground(style.getFontColor());
        setBackground(style.getBackgroundColor(highlighted));
    }
}
