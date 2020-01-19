package jp.rouh.minesweeper.util;

import java.util.List;

/**
 * フィールドとリンクしたセル
 * {@link GridField GridField}クラスと合わせて使用します。
 * 型パラメータに、このセルの実装であるサブクラスと、
 * GridFieldの実装であるサブクラスを設定する必要があります。
 * 以下はその実装例です。
 * {@code class ServiceCell extends GridCell<ServiceCell, ServiceField>}
 * {@code class ServiceField extends GridField<ServiceCell, ServiceField>}
 */
public abstract class GridCell<C extends GridCell<C, F>, F extends GridField<C, F>>{
    /** このセルのX座標 */
    protected int x;
    /** このセルのY座標 */
    protected int y;
    /** フィールドへの参照 */
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
    /**
     * フィールド場の周囲の8座標にあるセルをリストで返します。
     * ただし範囲外の座標は無視されます。
     * @return 周囲のセルのリスト
     */
    public List<C> aroundCells(){
        return parent.aroundCellsFrom(x, y);
    }
    /**
     * このセルのX座標を返します。
     * @return x座標
     */
    public int getX(){
        return x;
    }
    /**
     * このセルのY座標を返します。
     * @return y座標
     */
    public int getY(){
        return y;
    }
}
