package com.sxh.flashsale.access;

import com.sxh.flashsale.domain.FlashSaleUser;

public class UserContext {
	
	private static ThreadLocal<FlashSaleUser> userHolder = new ThreadLocal<FlashSaleUser>();
	
	public static void setUser(FlashSaleUser user) {
		userHolder.set(user);
	}
	
	public static FlashSaleUser getUser() {
		return userHolder.get();
	}

}
