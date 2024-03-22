package cse143exercises.ConstructionPatterns;

public class Y extends X {
    private int build;

    private static class Builder extends X.Builder<Y, Y.Builder> {

        @Override
        protected Y createObj() {
            return new Y();
        }

        @Override
        protected Builder getThis() {  
            return this;
        }

    }

    protected Y() {}

    public int getBuild() {
        return build;
    }
}
