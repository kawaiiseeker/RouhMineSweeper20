package jp.rouh.minesweeper.util;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 2次元の固定長の要素を保持するクラス
 * @param <E> 要素の型
 */
public interface Square<E>{
    /**
     * 指定した位置に指定した要素を挿入します
     * @param x x座標
     * @param y y座標
     * @param element 保持する要素
     */
    void set(int x, int y, E element);
    /**
     * 全ての位置に指定した関数で要素を初期化します
     * @param factory 生成関数
     */
    void init(Supplier<? extends E> factory);
    /**
     * 術tの位置に指定した関数で要素を初期化します
     * @param factory 引数に座標を持つ生成関数
     */
    void init(BiFunction<Integer, Integer, ? extends E> factory);
    /**
     * 指定した位置に保持された要素を取得します
     * @param x x座標
     * @param y y座標
     * @return 指定した位置に保持されている要素
     */
    E get(int x, int y);
    /**
     * 指定した列の全ての要素をリストとして取得します
     * @param x x座標
     * @return 列のリスト
     */
    List<E> col(int x);
    /**
     * 指定した行のすべての要素をリストとして取得します
     * @param y y座標
     * @return 行のリスト
     */
    List<E> row(int y);
    /**
     * 全ての要素を一次元のリストとして取得します
     * @return 全要素のリスト
     */
    List<E> unfold();
    /**
     * 全ての要素を一次元のストリームとして取得します
     * @return 全要素のストリーム
     */
    default Stream<E> stream(){
        return unfold().stream();
    }
}
