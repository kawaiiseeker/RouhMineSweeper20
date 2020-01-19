package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.*;
import jp.rouh.minesweeper.field.GenerationPolicy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.BiConsumer;

public class MineSweeperFrame extends JFrame implements MouseListener, MineSweeperObserver{
    public static final Difficulty DEFAULT_DIFFICULTY = BasicDifficulty.BEGINNER;
    public static final GenerationPolicy DEFAULT_GENERATION_POLICY = GenerationPolicy.SAFE_LAUNCH;
    private static final String TIME_LABEL_TEXT = "TIME: ";
    private static final String MINE_LABEL_TEXT = "MINE: ";
    public static final int CELL_SIZE = 30;
    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JPanel fieldPanel = new JPanel();
    private MineCellLabel[][] labels;
    private JComboBox<DifficultyOption> difficultyBox;
    private JLabel timeLabel = new JLabel();
    private JLabel mineLabel = new JLabel();
    private JLabel messageLabel = new JLabel();
    private GenerationPolicy generationPolicy = DEFAULT_GENERATION_POLICY;
    private Difficulty customDifficulty = DEFAULT_DIFFICULTY;
    private MineSweeperEventHandler handler;
    private MineSweeper model;
    private int width;
    private int height;
    private int currentMouseX = -1;
    private int currentMouseY = -1;
    private boolean leftClick = false;
    private boolean rightClick = false;
    MineSweeperFrame(MineSweeperEventHandler handler){
        this.handler = handler;
        setTitle("MineSweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        topPanel.setLocation(0, 0);
        difficultyBox = new JComboBox<>(DifficultyOption.values());
        difficultyBox.setSelectedItem(DEFAULT_DIFFICULTY);
        JButton restartButton = new JButton("RESTART");
        JButton settingButton = new JButton("SETTING");
        restartButton.setMargin(new Insets(0, 5, 0, 5));
        settingButton.setMargin(new Insets(0, 5, 0, 5));
        restartButton.addActionListener(e->handler.restartButtonPressed());
        settingButton.addActionListener(e->new SettingDialog());
        topPanel.add(settingButton);
        topPanel.add(difficultyBox);
        topPanel.add(restartButton);
        mineLabel.setText(MINE_LABEL_TEXT);
        timeLabel.setText(TIME_LABEL_TEXT);
        bottomPanel.add(mineLabel);
        bottomPanel.add(timeLabel);
        bottomPanel.add(messageLabel);
        add(topPanel);
        add(fieldPanel);
        add(bottomPanel);
    }
    /* package */ void updateModel(MineSweeper model){
        this.model = model;
        width = model.getWidth();
        height = model.getHeight();
        messageLabel.setText("");
        resize();
    }
    private void resize(){
        getContentPane().setPreferredSize(new Dimension(width*CELL_SIZE, (height + 2)*CELL_SIZE));
        pack();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        fieldPanel.removeAll();
        topPanel.setPreferredSize(new Dimension(width*CELL_SIZE, CELL_SIZE));
        fieldPanel.setPreferredSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
        bottomPanel.setPreferredSize(new Dimension(width*CELL_SIZE, CELL_SIZE));
        fieldPanel.setLayout(new GridLayout(height, width));
        labels = new MineCellLabel[height][width];
        for(int y = 0; y<height; y++){
            for(int x = 0; x<width; x++){
                labels[y][x] = new MineCellLabel();
                labels[y][x].addMouseListener(this);
                fieldPanel.add(labels[y][x]);
                updateCell(x, y);
            }
        }
        setVisible(true);
        pack();
        repaint();
    }
    private enum DifficultyOption{
        BEGINNER,
        INTERMEDIATE,
        ADVANCED,
        CUSTOM
    }
    // implement interface MineSweeperObserver
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
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                updateCell(x, y);
            }
        }
        if(model.isSecured()){
            messageLabel.setText("you win");
        }else if(model.isExploded()){
            messageLabel.setText("you lose");
        }
    }
    @Override
    public void updateCell(int x, int y){
        labels[y][x].updateView(model.getView(x, y), model.isSecured());
    }
    // implement interface MouseAdaptor
    @Override
    public void mouseClicked(MouseEvent mouseEvent){
        //ignored
    }
    @Override
    public void mouseEntered(MouseEvent e){
        findSource(e).execute((x, y)->{
            labels[y][x].setHighlight(true);
            currentMouseX = x;
            currentMouseY = y;
        });
    }
    @Override
    public void mouseExited(MouseEvent e){
        findSource(e).execute((x, y)->
            labels[y][x].setHighlight(false));
    }
    @Override
    public void mousePressed(MouseEvent e){
        boolean doubleClick = e.getClickCount()==2;
        if(SwingUtilities.isRightMouseButton(e)){
            rightClick = true;
        }else if(SwingUtilities.isLeftMouseButton(e)){
            leftClick = true;
        }
        findSource(e).execute((x, y)->{
            if((leftClick && rightClick) || (leftClick && doubleClick)){
                handler.cellDoubleClicked(x, y);
            }else if(rightClick){
                handler.cellRightClicked(x, y);
            }
        });
    }
    @Override
    public void mouseReleased(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)){
            handler.cellLeftClicked(currentMouseX, currentMouseY);
            leftClick = false;
        }else if(SwingUtilities.isRightMouseButton(e)){
            rightClick = false;
        }
    }
    private static class SourcePoint extends Point{
        private SourcePoint(int x, int y){
            super(x, y);
        }
        private void execute(BiConsumer<Integer, Integer> consumer){
            consumer.accept(x, y);
        }
    }
    private SourcePoint findSource(MouseEvent e){
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(e.getSource().equals(labels[y][x])){
                    return new SourcePoint(x, y);
                }
            }
        }
        throw new IllegalArgumentException("source not found");
    }
    private class SettingDialog extends JDialog{
        private JTextField hInputField;
        private JTextField wInputField;
        private JTextField mInputField;
        private JComboBox<GenerationPolicy> generationPolicyBox;
        private SettingDialog(){
            setTitle("Settings");
            setModal(true);
            setLocationRelativeTo(null);
            setResizable(false);
            var containerPanel = new JPanel();
            containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
            var generationPolicyPanel = new JPanel();
            generationPolicyPanel.setSize(CELL_SIZE*8, CELL_SIZE);
            generationPolicyPanel.setBorder(BorderFactory.createTitledBorder("generation policy"));
            generationPolicyPanel.add(new JLabel("policy"));
            generationPolicyBox = new JComboBox<>(GenerationPolicy.values());
            generationPolicyBox.setSelectedItem(DEFAULT_GENERATION_POLICY);
            generationPolicyPanel.add(generationPolicyBox);
            JPanel customDifficultyPanel = new JPanel();
            customDifficultyPanel.setSize(CELL_SIZE*8, CELL_SIZE);
            customDifficultyPanel.setBorder(BorderFactory.createTitledBorder("custom difficulty"));
            hInputField = new JTextField(Integer.toString(customDifficulty.getHeight()));
            wInputField = new JTextField(Integer.toString(customDifficulty.getWidth()));
            mInputField = new JTextField(Integer.toString(customDifficulty.getTotalMineCount()));
            hInputField.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
            wInputField.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
            mInputField.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
            customDifficultyPanel.add(new JLabel("height"));
            customDifficultyPanel.add(hInputField);
            customDifficultyPanel.add(new JLabel("width"));
            customDifficultyPanel.add(wInputField);
            customDifficultyPanel.add(new JLabel("mine"));
            customDifficultyPanel.add(mInputField);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setSize(CELL_SIZE*8, CELL_SIZE);
            JButton cancelButton = new JButton("cancel");
            JButton applyButton = new JButton("apply");
            cancelButton.addActionListener(this::cancel);
            applyButton.addActionListener(this::apply);
            buttonPanel.add(cancelButton);
            buttonPanel.add(applyButton);
            containerPanel.add(generationPolicyPanel);
            containerPanel.add(customDifficultyPanel);
            containerPanel.add(buttonPanel);
            add(containerPanel);
            pack();
            setVisible(true);
        }
        private void cancel(ActionEvent e){
            this.dispose();
        }
        private void apply(ActionEvent e){
            int heightValue = -1;
            int widthValue = -1;
            int mineValue = -1;
            boolean heightValid = false;
            boolean widthValid = false;
            boolean mineValid = false;
            String message = "";
            if(hInputField.getText().isEmpty()){
                message += "invalid height: empty\n";
            }else{
                try{
                    heightValue = Integer.parseInt(hInputField.getText());
                    if(heightValue<0){
                        message += "invalid height: not positive number";
                    }else{
                        int min = CustomDifficulty.MIN_HEIGHT;
                        int max = CustomDifficulty.MAX_HEIGHT;
                        if(min<=heightValue && heightValue<=max){
                            heightValid = true;
                        }else{
                            message += "invalid height: out of " + min + "..." + max + "\n";
                        }
                    }
                }catch(NumberFormatException ex){
                    message += "invalid height: not number\n";
                }
            }
            if(wInputField.getText().isEmpty()){
                message += "invalid width: empty\n";
            }else{
                try{
                    widthValue = Integer.parseInt(wInputField.getText());
                    if(widthValue<0){
                        message += "invalid width: not positive number";
                    }else{
                        int min = CustomDifficulty.MIN_WIDTH;
                        int max = CustomDifficulty.MAX_WIDTH;
                        if(min<=widthValue && widthValue<=max){
                            widthValid = true;
                        }else{
                            message += "invalid width: out of " + min + "..." + max + "\n";
                        }
                    }
                }catch(NumberFormatException ex){
                    message += "invalid width: not number\n";
                }
            }
            if(mInputField.getText().isEmpty()){
                message += "invalid mine: empty\n";
            }else{
                try{
                    mineValue = Integer.parseInt(mInputField.getText());
                    if(mineValue<0){
                        message += "invalid mine: not positive number";
                    }else{
                        if(heightValid && widthValid){
                            if(mineValue<widthValue*heightValue){
                                mineValid = true;
                            }else{
                                message += "invalid mine: out of height*width\n";
                            }
                        }
                    }
                }catch(NumberFormatException ex){
                    message += "invalid mine: not number\n";
                }
            }
            if(heightValid && widthValid && mineValid){
                customDifficulty = new CustomDifficulty(widthValue, heightValue, mineValue);
                generationPolicy = (GenerationPolicy)generationPolicyBox.getSelectedItem();
                dispose();
            }else{
                JOptionPane.showMessageDialog(this, message);
            }
        }
    }
    /* package */ Difficulty getSelectedDifficulty(){
        DifficultyOption option = (DifficultyOption)difficultyBox.getSelectedItem();
        if(option!=null){
            switch(option){
                case BEGINNER: return BasicDifficulty.BEGINNER;
                case INTERMEDIATE: return BasicDifficulty.INTERMEDIATE;
                case ADVANCED: return BasicDifficulty.ADVANCED;
                case CUSTOM: return customDifficulty;
            }
        }
        return DEFAULT_DIFFICULTY;
    }
    /* package */ GenerationPolicy getSelectedGenerationPolicy(){
        return generationPolicy;
    }
}
