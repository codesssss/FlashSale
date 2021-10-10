package com.sxh.flashsale.rabbitmq;

import com.sxh.flashsale.domain.FlashSaleUser;

public class FlashSaleMessage {
	private FlashSaleUser user;
	private long goodsId;
	public FlashSaleUser getUser() {
		return user;
	}
	public void setUser(FlashSaleUser user) {
		this.user = user;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
}
