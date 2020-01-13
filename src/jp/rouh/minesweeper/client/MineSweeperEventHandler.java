package jp.rouh.minesweeper.client;

interface MineSweeperEventHandler{
    void doOpen(int x, int y);
    void doFlag(int x, int y);
    void doStamp(int x, int y);
}
