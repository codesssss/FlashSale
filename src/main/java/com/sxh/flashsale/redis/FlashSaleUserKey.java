package com.sxh.flashsale.redis;

public class FlashSaleUserKey extends BasePrefix{

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private FlashSaleUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static FlashSaleUserKey token = new FlashSaleUserKey(TOKEN_EXPIRE, "tk");
	public static FlashSaleUserKey getById = new FlashSaleUserKey(0, "id");
}
