package jp.rouh.minesweeper.client;

interface MineSweeperEventHandler{
    void cellRightClicked(int x, int y);
    void cellLeftClicked(int x, int y);
    void cellDoubleClicked(int x, int y);
    void restartButtonPressed();
}
