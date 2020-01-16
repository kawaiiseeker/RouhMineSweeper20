package jp.rouh.minesweeper.client;

import jp.rouh.minesweeper.Difficulty;

interface MineSweeperEventHandler{
    void doOpen(int x, int y);
    void doFlag(int x, int y);
    void doStamp(int x, int y);
    void restart(Difficulty difficulty, GenerationPolicy policy);
}
