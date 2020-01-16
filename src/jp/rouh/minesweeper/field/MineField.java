package jp.rouh.minesweeper.field;

public interface MineField{
    void setObserver(FieldObserver observer);
    void open(int x, int y);
    void stamp(int x, int y);
    void toggleFlag(int x, int y);
    MineCellView getView(int x, int y);
    MineCellView getResultView(int x, int y);
    int getWidth();
    int getHeight();
    int getTotalMineCount();
    int getEstimatedRemainingMineCount();
    boolean hasRange(int x, int y);
}
