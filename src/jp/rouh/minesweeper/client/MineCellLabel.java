package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.MineCellView;

import javax.swing.*;
import java.awt.event.MouseListener;

class MineCellLabel extends JLabel{
    MineCellLabel(MouseListener listener){
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setSize(MineSweeperFrame.CELL_SIZE, MineSweeperFrame.CELL_SIZE);
        addMouseListener(listener);
    }
    public void update(MineCellView view, boolean secured, boolean onMouse){
        MineCellStyle style = MineCellStyle.valueOf(view, secured);
        setText(style.getText());
        setBorder(style.getBorder());
        setForeground(style.getFontColor());
        setBackground(style.getBackgroundColor(onMouse));
    }
}
