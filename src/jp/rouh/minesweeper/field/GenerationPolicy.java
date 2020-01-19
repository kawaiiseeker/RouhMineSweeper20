package jp.rouh.minesweeper.field;

/**
 * 盤面の地雷生成戦略を表すクラス
 */
public enum GenerationPolicy{
    /** 完全ランダム地雷生成 */
    FULL_RANDOM(new FullRandomFieldGenerator()),
    /** 初手が必ず周囲地雷数0となる地雷生成 */
    SAFE_LAUNCH(new SafeLaunchFieldGenerator());
    private final FieldGenerator generator;
    GenerationPolicy(FieldGenerator generator){
        this.generator = generator;
    }
    FieldGenerator getGenerator(){
        return generator;
    }
    @Override
    public String toString(){
        return super.toString().toLowerCase().replace("_", " ");
    }
}
