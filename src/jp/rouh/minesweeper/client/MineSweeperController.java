package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.*;

public class MineSweeperController implements MineSweeperEventHandler{
    private MineSweeper model;
    private MineSweeperFrame view;
    public MineSweeperController(Difficulty difficulty){
        model = new SafeLaunchMineSweeper(difficulty);
        view = new MineSweeperFrame(model);
        model.setObserver(view);
        view.setHandler(this);
    }
    @Override
    public void doOpen(int x, int y){
        model.open(x, y);
    }
    @Override
    public void doFlag(int x, int y){
        model.toggleFlag(x, y);
    }
    @Override
    public void doStamp(int x, int y){
        model.stamp(x, y);
    }
    @Override
    public void restart(Difficulty difficulty, GenerationPolicy policy){

    }
    public static void main(String[] args){
        new MineSweeperController(BasicDifficulty.BEGINNER);
    }
}
