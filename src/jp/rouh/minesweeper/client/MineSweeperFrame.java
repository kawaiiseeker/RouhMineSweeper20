package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.BasicDifficulty;
import jp.rouh.minesweeper.Difficulty;
import jp.rouh.minesweeper.MineSweeper;
import jp.rouh.minesweeper.MineSweeperObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MineSweeperFrame extends MouseAdapter implements MineSweeperObserver, ActionListener{
    private static final String TIME_LABEL_TEXT = "time: ";
    private static final String MINE_LABEL_TEXT = "mine: ";
    public static final int CELL_SIZE = 30;
    private final int width;
    private final int height;
    private JComboBox<String> difficultyBox;
    private JButton restartButton = new JButton("RESTART");
    private JButton settingButton = new JButton("SETTING");
    private JLabel timeLabel = new JLabel();
    private JLabel mineLabel = new JLabel();
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
        JFrame frame = new JFrame();
        frame.setTitle("MineSweeper");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(
                new Dimension(CELL_SIZE*width, CELL_SIZE*(height + 2)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JPanel top = new JPanel();
        top.setSize(CELL_SIZE*width, CELL_SIZE);
        top.setLocation(0, 0);
        difficultyBox = new JComboBox<>();
        for(Difficulty difficulty: BasicDifficulty.values()){
            difficultyBox.addItem(difficulty.toString());
        }
        difficultyBox.addItem("CUSTOM");
        difficultyBox.setSelectedItem(BasicDifficulty.BEGINNER); //TODO
        restartButton.setMargin(new Insets(0, 5, 0, 5));
        settingButton.setMargin(new Insets(0, 5, 0, 5));
        restartButton.addActionListener(this);
        settingButton.addActionListener(this);
        top.add(settingButton);
        top.add(difficultyBox);
        top.add(restartButton);
        JPanel field = new JPanel();
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
        JPanel bottom = new JPanel();
        bottom.setSize(CELL_SIZE*width, CELL_SIZE);
        bottom.setLocation(0, CELL_SIZE*(height + 1));
        mineLabel.setSize(CELL_SIZE*width/2, CELL_SIZE);
        timeLabel.setSize(CELL_SIZE*width/2, CELL_SIZE);
        mineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mineLabel.setText("MINE: ");
        timeLabel.setText("TIME: ");
        bottom.add(mineLabel);
        bottom.add(timeLabel);
        frame.add(top);
        frame.add(field);
        frame.add(bottom);
        frame.setVisible(true);
        frame.repaint();

        updateTimeCount();
        updateRemainingMineCount();
    }
    void initialize(MineSweeper model){
        //
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
    }
    @Override
    public void updateTimeCount(){
        timeLabel.setText(TIME_LABEL_TEXT+model.getTimeCount());
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
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(actionEvent.getSource()==settingButton){
            new SettingDialog();
        }
    }
    private JTextField heightField = new JTextField();
    private JTextField widthField = new JTextField();
    private JTextField mineField = new JTextField();
    private class SettingDialog extends JDialog{
        private SettingDialog(){
            setTitle("Settings");
            setModal(true);
            setLocationRelativeTo(null);
            setResizable(false);
            JPanel containerPanel = new JPanel();
            containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

            JPanel generationPolicyPanel = new JPanel();
            generationPolicyPanel.setSize(CELL_SIZE*8, CELL_SIZE);
            generationPolicyPanel.setBorder(BorderFactory.createTitledBorder("generation policy"));
            generationPolicyPanel.add(new JLabel("policy"));
            JComboBox<String> generationPolicyBox = new JComboBox<>();
            generationPolicyBox.addItem("eager random");
            generationPolicyBox.addItem("lazy random");
            generationPolicyBox.addItem("solvable");
            generationPolicyPanel.add(generationPolicyBox);

            JPanel customDifficultyPanel = new JPanel();
            customDifficultyPanel.setSize(CELL_SIZE*8, CELL_SIZE);
            customDifficultyPanel.setBorder(BorderFactory.createTitledBorder("custom difficulty"));
            heightField.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
            widthField.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
            mineField.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
            customDifficultyPanel.add(new JLabel("height"));
            customDifficultyPanel.add(heightField);
            customDifficultyPanel.add(new JLabel("width"));
            customDifficultyPanel.add(widthField);
            customDifficultyPanel.add(new JLabel("mine"));
            customDifficultyPanel.add(mineField);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setSize(CELL_SIZE*8, CELL_SIZE);
            JButton cancelButton = new JButton("cancel");
            JButton applyButton = new JButton("apply");
            buttonPanel.add(cancelButton);
            buttonPanel.add(applyButton);

            containerPanel.add(generationPolicyPanel);
            containerPanel.add(customDifficultyPanel);
            containerPanel.add(buttonPanel);
            add(containerPanel);

            pack();

            setVisible(true);
        }
    }

}
