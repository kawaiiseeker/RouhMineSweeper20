package jp.rouh.minesweeper.field;

public enum GenerationPolicy{
    FULL_RANDOM(new FullRandomFieldGenerator()),
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
