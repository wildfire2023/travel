package com.waner.primary.common.cache;

public class TestKey extends BasePrefix {
    private TestKey(String prefix, int seconds) {
        super(prefix, seconds);
    }

    public static TestKey TEST = new TestKey("prefix", 60);


}
