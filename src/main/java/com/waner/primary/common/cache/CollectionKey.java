package com.waner.primary.common.cache;

public class CollectionKey extends BasePrefix{

	protected CollectionKey(String prefix) {
		super(prefix);
	}

	protected CollectionKey(String prefix, int seconds) {
		super(prefix, seconds);
	}

  public static final CollectionKey ESSAY_KEY = new CollectionKey("type:essay");
  public static final CollectionKey QUESTION_KEY = new CollectionKey("type:question");
  public static final CollectionKey RECOMMEND_KEY = new CollectionKey("type:recommend");
}
