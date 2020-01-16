package jp.rouh.minesweeper.util;

import java.util.List;

public abstract class GridCell<C extends GridCell<C, F>, F extends GridField<C, F>>{
    protected int x;
    protected int y;
    protected F parent;
    /* package */ void setX(int x){
        this.x = x;
    }
    /* package */ void setY(int y){
        this.y = y;
    }
    /* package */ void setParent(F parent){
        this.parent = parent;
    }
    public List<C> aroundCells(){
        return parent.aroundCellsFrom(x, y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
