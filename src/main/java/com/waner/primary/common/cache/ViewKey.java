package com.waner.primary.common.cache;

/**
 * 子类默认继承父类无参构造器
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
public class ViewKey extends BasePrefix {
	protected ViewKey(String prefix) {
		super(prefix);
	}

	protected ViewKey(String prefix, int seconds) {
		super(prefix, seconds);
	}

  public static final ViewKey ESSAY_KEY = new ViewKey("type:essay");
	public static final ViewKey RECOMMEND_KEY = new ViewKey("type:recommend");
	public static final ViewKey QUESTION_KEY = new ViewKey("type:question");
}
