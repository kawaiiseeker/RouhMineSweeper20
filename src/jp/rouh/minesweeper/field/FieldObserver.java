package jp.rouh.minesweeper.field;

public interface FieldObserver{
    void notifyCellUpdated(int x, int y);
    void notifyFieldSecured();
    void notifyFieldExploded();
}
