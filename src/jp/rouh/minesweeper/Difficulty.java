package jp.rouh.minesweeper;

public interface Difficulty{
    int width();
    int height();
    int totalMineCount();
    String difficultyName();
}
