package jp.rouh.minesweeper.field;

@FunctionalInterface
interface FieldGenerator{
    void generate(MineField field, int x, int y);
    default void apply(MineField field){
        for(int ax = 0; ax<field.getWidth(); ax++){
            for(int ay = 0; ay<field.getHeight(); ay++){
                if(!field.get(ax, ay).isMine()){
                    field.get(ax, ay).setMineCount();
                }
            }
        }
    }
}
