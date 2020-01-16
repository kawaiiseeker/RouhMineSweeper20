package jp.rouh.minesweeper;

public interface Difficulty{
    int getWidth();
    int getHeight();
    int getTotalMineCount();
    default int getTotalCellCount(){
        return getWidth()*getHeight();
    }
    String getName();
}
