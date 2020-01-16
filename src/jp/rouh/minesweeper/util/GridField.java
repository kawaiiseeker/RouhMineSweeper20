package jp.rouh.minesweeper.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class GridField<C extends GridCell<C, F>, F extends GridField<C, F>>{
    protected final int width;
    protected final int height;
    private final Square<C> cells;
    public GridField(int width, int height, Supplier<C> supplier){
        this.width = width;
        this.height = height;
        this.cells = new ArraySquare<>(width, height);
        @SuppressWarnings("unchecked")
        F concreteField = (F)this;
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                C cell = supplier.get();
                cell.setX(x);
                cell.setY(y);
                cell.setParent(concreteField);
                cells.set(x, y, cell);
            }
        }
    }
    public C get(int x, int y){
        requireWithinRange(x, y);
        return cells.get(x, y);
    }
    public List<C> aroundCellsFrom(int x, int y){
        requireWithinRange(x, y);
        List<C> cells = new ArrayList<>(8);
        for(int dx: new int[]{-1, 0, 1}){
            for(int dy: new int[]{-1, 0, 1}){
                if(dx==0 && dy==0) continue;
                int ax = x + dx;
                int ay = y + dy;
                if(hasRange(ax, ay)){
                    cells.add(get(ax, ay));
                }
            }
        }
        return cells;
    }
    public List<C> allCells(){
        List<C> cells = new ArrayList<>(8);
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                cells.add(get(x, y));
            }
        }
        return cells;
    }
    public boolean hasRange(int x, int y){
        return x>=0 && x<width && y>=0 && y<height;
    }
    protected void requireWithinRange(int x, int y){
        if(!hasRange(x, y)){
            throw new IllegalArgumentException("out of range: ("+x+", "+y+")");
        }
    }
}
