package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.MineSweeper;
import jp.rouh.minesweeper.MineSweeperObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MineSweeperFrame extends MouseAdapter implements MineSweeperObserver{
    private static final String TIME_LABEL_TEXT = "time: ";
    private static final String MINE_LABEL_TEXT = "mine: ";
    public static final int CELL_SIZE = 30;
    private final int width;
    private final int height;
    private JFrame frame = new JFrame();
    private JPanel top = new JPanel();
    private JLabel timeLabel = new JLabel();
    private JLabel mineLabel = new JLabel();
    private JPanel field = new JPanel();
    private MineCellLabel[][] labels;
    private MineSweeperEventHandler handler;
    private MineSweeper model;
    private int mx = -1;
    private int my = -1;
    private boolean leftClick = false;
    private boolean rightClick = false;
    MineSweeperFrame(MineSweeper model){
        this.width = model.getWidth();
        this.height = model.getHeight();
        this.model = model;
        this.labels = new MineCellLabel[height][width];
        frame.setTitle("MineSweeper");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(
                new Dimension(CELL_SIZE*width, CELL_SIZE*(height + 1)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.setSize(CELL_SIZE*width, CELL_SIZE);
        top.setLocation(0, 0);
        mineLabel.setSize(CELL_SIZE*width/2, CELL_SIZE);
        timeLabel.setSize(CELL_SIZE*width/2, CELL_SIZE);
        mineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mineLabel.setText("mine: ");
        timeLabel.setText("time: ");
        top.add(mineLabel);
        top.add(timeLabel);
        field.setLayout(new GridLayout(width, height));
        field.setSize(new Dimension(CELL_SIZE*width, CELL_SIZE*height));
        field.setLocation(0, CELL_SIZE);
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                labels[y][x] = new MineCellLabel(this);
                field.add(labels[y][x]);
                updateCell(x, y);
            }
        }
        frame.add(top);
        frame.add(field);
        frame.setVisible(true);
        frame.repaint();
        updateTimeCount();
        updateRemainingMineCount();
    }
    public void setHandler(MineSweeperEventHandler handler){
        this.handler = handler;
    }
    /* override methods from MouseAdaptor */
    @Override
    public void mouseEntered(MouseEvent e){
        if(!model.isFinished()){
            Point p = findSourcePoint(e);
            mx = p.x;
            my = p.y;
            updateCellHighlight(p.x, p.y, true);
        }
    }
    @Override
    public void mouseExited(MouseEvent e){
        if(!model.isFinished()){
            Point p = findSourcePoint(e);
            updateCellHighlight(p.x, p.y, false);
        }
    }
    @Override
    public void mousePressed(MouseEvent e){
        if(SwingUtilities.isRightMouseButton(e)){
            rightClick = true;
        }else if(SwingUtilities.isLeftMouseButton(e)){
            leftClick = true;
        }
        boolean doubleClick = e.getClickCount()==2;
        Point p = findSourcePoint(e);
        if((leftClick && rightClick) || (leftClick && doubleClick) ){
            handler.doStamp(p.x, p.y);
        }else if(rightClick){
            handler.doFlag(p.x, p.y);
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)){
            handler.doOpen(mx, my);
            leftClick = false;
        }else if(SwingUtilities.isRightMouseButton(e)){
            rightClick = false;
        }
    }
    private Point findSourcePoint(MouseEvent e){
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(e.getSource().equals(labels[y][x])){
                    return new Point(x, y);
                }
            }
        }
        throw new IllegalArgumentException("source not found");
    }

    @Override
    public void updateRemainingMineCount(){
        mineLabel.setText(MINE_LABEL_TEXT+model.getRemainingMineCount());
        top.repaint();
    }
    @Override
    public void updateTimeCount(){
        timeLabel.setText(TIME_LABEL_TEXT+model.getTimeCount());
        top.repaint();
    }
    @Override
    public void updateStatus(){
        if(model.isFinished()){
            for(int x = 0; x<width; x++){
                for(int y = 0; y<height; y++){
                    labels[y][x].update(model.getView(x, y), model.isSecured(), false);
                }
            }
        }
    }
    @Override
    public void updateCell(int x, int y){
        labels[y][x].update(model.getView(x, y), model.isSecured(), false);
    }
    private void updateCellHighlight(int x, int y, boolean onMouse){
        labels[y][x].update(model.getView(x, y), model.isSecured(), onMouse);
    }
}
