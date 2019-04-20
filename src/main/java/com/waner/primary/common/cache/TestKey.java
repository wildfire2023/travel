package com.waner.primary.common.cache;

public class TestKey extends BasePrefix {
    private TestKey(String prefix, int seconds) {
        super(prefix, seconds);
    }

    /**
     * 静态变量访问使用类名进行访问
     */
    public static TestKey TEST = new TestKey("prefix", 60);
}
