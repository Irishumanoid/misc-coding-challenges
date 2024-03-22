package cse143exercises.ConstructionPatterns;


//classType<impl extends classType<impl>>
public abstract class X {
    protected int foo;

    protected abstract static class Builder<T extends X, B extends Builder<T, B>> {
        private T obj;
        private B thisObj;

        public Builder() {
            obj = createObj();
            thisObj = getThis();
        }
        
        public B withFoo(int foo) {
            obj.foo = foo; return thisObj;
        }

        public T build() {return obj;}

        protected abstract B getThis();
        protected abstract T createObj();
    }

    protected X() {}

    public int getFoo() {return foo;}
    
}

