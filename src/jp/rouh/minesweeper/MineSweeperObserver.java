package jp.rouh.minesweeper;

public interface MineSweeperObserver{
    void updateRemainingMineCount();
    void updateTimeCount();
    void updateStatus();
    void updateCell(int x, int y);
}
