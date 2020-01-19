package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.*;
import jp.rouh.minesweeper.field.GenerationPolicy;

import java.util.function.BiFunction;

public class MineSweeperController implements MineSweeperEventHandler{
    private MineSweeper model;
    private MineSweeperFrame view;
    private BiFunction<Difficulty, GenerationPolicy, MineSweeper> modelFactory;
    public MineSweeperController(BiFunction<Difficulty, GenerationPolicy, MineSweeper> modelFactory){
        this.modelFactory = modelFactory;
        this.view = new MineSweeperFrame(this);
        startNewGame();
    }
    private void startNewGame(){
        var difficulty = view.getSelectedDifficulty();
        var policy = view.getSelectedGenerationPolicy();
        model = modelFactory.apply(difficulty, policy);
        model.setObserver(view);
        view.updateModel(model);
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
        startNewGame();
    }
    public static void main(String[] args){
        new MineSweeperController(StandardMineSweeper::new);
    }
}
