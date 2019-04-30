package com.gbicc.test;

public class SingletonInstance {

    private static class singleToInstancceget {
        private static final SingletonInstance s = new SingletonInstance();
    }
    private SingletonInstance(){

    }
    public static SingletonInstance getInstance(){
        return null;
    }

}
