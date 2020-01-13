package jp.rouh.minesweeper.util;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArraySquare<E> implements Square<E>{
    private final int width;
    private final int height;
    private final Object[][] values;
    @Override
    public void init(Supplier<? extends E> factory){
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                values[j][i] = factory.get();
            }
        }
    }
    @Override
    public void init(BiFunction<Integer, Integer, ? extends E> factory){
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                values[j][i] = factory.apply(j, i);
            }
        }
    }
    public ArraySquare(int width, int height){
        this.width = width;
        this.height = height;
        this.values = new Object[height][width];
    }
    private E get(int index){
        return get(index%width, index/width);
    }
    @Override
    @SuppressWarnings("unchecked")
    public E get(int x, int y){
        requireWithinRange(x, y);
        return (E) values[y][x];
    }
    @Override
    public List<E> col(int x){
        requireWithinRange(x, 0);
        return IntStream.range(0, height)
                .mapToObj(y->get(x, y))
                .collect(Collectors.toList());
    }
    @Override
    public List<E> row(int y){
        requireWithinRange(0, y);
        return IntStream.range(0, width)
                .mapToObj(x->get(x, y))
                .collect(Collectors.toList());
    }
    @Override
    public void set(int x, int y, E element){
        requireWithinRange(x, y);
        values[y][x] = element;
    }
    @Override
    public List<E> unfold(){
        return IntStream.range(0, width*height)
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }
    private void requireWithinRange(int x, int y){
        if(!hasRange(x, y))
            throw new IllegalArgumentException("out of range");
    }
    private boolean hasRange(int x, int y){
        return x>=0 && x<width && y>=0 && y<height;
    }
}
