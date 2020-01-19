package jp.rouh.minesweeper.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * セルとリンクしたフィールド
 * {@link GridCell GridCell}クラスと合わせて使用します。
 * 型パラメータに、GridCellの実装であるサブクラスと、
 * このセルの実装であるサブクラスを設定する必要があります。
 * 以下はその実装例です。
 * {@code class ServiceCell extends GridCell<ServiceCell, ServiceField>}
 * {@code class ServiceField extends GridField<ServiceCell, ServiceField>}
 */
public abstract class GridField<C extends GridCell<C, F>, F extends GridField<C, F>>{
    private final int width;
    private final int height;
    private final Square<C> cells;
    /**
     * コンストラクタ
     * @param width フィールドの横幅
     * @param height フィールドの縦幅
     * @param supplier セルの生成関数
     */
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
    /**
     * 指定した座標にあるセルを返します。
     * @param x X座標
     * @param y Y座標
     * @return セル
     */
    public C get(int x, int y){
        requireWithinRange(x, y);
        return cells.get(x, y);
    }
    /**
     * 指定した座標の周囲の8座標にあるセルをリストで返します。
     * ただし範囲外の座標は無視されます。
     * @param x X座標
     * @param y Y座標
     * @return 周囲のセルのリスト
     */
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
    /**
     * フィールド上の全てのセルを一次元のリスト形式で返します。
     * @return 全てのセルのリスト
     */
    public List<C> allCells(){
        List<C> cells = new ArrayList<>(8);
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                cells.add(get(x, y));
            }
        }
        return cells;
    }
    /**
     * 座標(x, y)が範囲内かどうか判定します。
     * @param x X座標
     * @param y Y座標
     * @return true  範囲内の場合
     *         false 範囲外の場合
     */
    public boolean hasRange(int x, int y){
        return x>=0 && x<width && y>=0 && y<height;
    }
    /**
     * 座標(x, y)が範囲内であることを要求します。
     * 範囲外の場合、例外をスローします。
     * @param x X座標
     * @param y Y座標
     * @throws IllegalArgumentException 座標が範囲外の場合
     */
    protected void requireWithinRange(int x, int y){
        if(!hasRange(x, y)){
            throw new IllegalArgumentException("out of range: ("+x+", "+y+")");
        }
    }
    /**
     * フィールドの縦幅を返します。
     * @return 縦幅
     */
    public int getHeight(){
        return height;
    }
    /**
     * フィールドの横幅を返します。
     * @return 横幅
     */
    public int getWidth(){
        return width;
    }
}
