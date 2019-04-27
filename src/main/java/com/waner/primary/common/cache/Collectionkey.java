package com.waner.primary.common.cache;

public class Collectionkey extends BasePrefix{

	protected Collectionkey(String prefix) {
		super(prefix);
	}

	protected Collectionkey(String prefix, int seconds) {
		super(prefix, seconds);
	}

  public static final Collectionkey ESSAY_KEY = new Collectionkey("type:essay");
  public static final Collectionkey QUESTION_KEY = new Collectionkey("type:question");
  public static final Collectionkey RECOMMEND_KEY = new Collectionkey("type:recommend");
}
