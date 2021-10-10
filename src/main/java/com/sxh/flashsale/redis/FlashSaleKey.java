package com.sxh.flashsale.redis;

public class FlashSaleKey extends BasePrefix{

	private FlashSaleKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static FlashSaleKey isGoodsOver = new FlashSaleKey(0, "go");
	public static FlashSaleKey getMiaoshaPath = new FlashSaleKey(60, "mp");
	public static FlashSaleKey getMiaoshaVerifyCode = new FlashSaleKey(300, "vc");
}
