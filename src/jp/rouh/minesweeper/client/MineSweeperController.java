package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.*;

import java.util.function.Function;

public class MineSweeperController implements MineSweeperEventHandler{
    private MineSweeper model;
    private MineSweeperFrame view;
    private Function<Difficulty, MineSweeper> modelFactory;
    public MineSweeperController(Function<Difficulty, MineSweeper> modelFactory, Difficulty difficulty){
        this.modelFactory = modelFactory;
        model = modelFactory.apply(difficulty);
        view = new MineSweeperFrame(model, this);
        model.setObserver(view);
    }
    @Override
    public void cellRightClicked(int x, int y){
        model.toggleFlag(x, y);
    }
    @Override
    public void cellLeftClicked(int x, int y){
        model.open(x, y);
    }
    @Override
    public void cellDoubleClicked(int x, int y){
        model.stamp(x, y);
    }
    @Override
    public void restartButtonPressed(){
        model = modelFactory.apply(view.getSelectedDifficulty());
        model.setObserver(view);
        view.updateModel(model);
    }
    public static void main(String[] args){
        new MineSweeperController(SafeLaunchMineSweeper::new, BasicDifficulty.BEGINNER);
    }
}
