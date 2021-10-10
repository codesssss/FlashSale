package com.sxh.flashsale.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.sxh.flashsale.result.CodeMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxh.flashsale.dao.FlashSaleUserDao;
import com.sxh.flashsale.domain.FlashSaleUser;
import com.sxh.flashsale.exception.GlobalException;
import com.sxh.flashsale.redis.FlashSaleUserKey;
import com.sxh.flashsale.redis.RedisService;
import com.sxh.flashsale.util.MD5Util;
import com.sxh.flashsale.util.UUIDUtil;
import com.sxh.flashsale.vo.LoginVo;

@Service
public class FlashSaleUserService {
	
	
	public static final String COOKI_NAME_TOKEN = "token";
	
	@Autowired
    FlashSaleUserDao flashSaleUserDao;
	
	@Autowired
	RedisService redisService;
	
	public FlashSaleUser getById(long id) {
		//取缓存
		FlashSaleUser user = redisService.get(FlashSaleUserKey.getById, ""+id, FlashSaleUser.class);
		if(user != null) {
			return user;
		}
		//取数据库
		user = flashSaleUserDao.getById(id);
		if(user != null) {
			redisService.set(FlashSaleUserKey.getById, ""+id, user);
		}
		return user;
	}
	// http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
	public boolean updatePassword(String token, long id, String formPass) {
		//取user
		FlashSaleUser user = getById(id);
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//更新数据库
		FlashSaleUser toBeUpdate = new FlashSaleUser();
		toBeUpdate.setId(id);
		toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
		flashSaleUserDao.update(toBeUpdate);
		//处理缓存
		redisService.delete(FlashSaleUserKey.getById, ""+id);
		user.setPassword(toBeUpdate.getPassword());
		redisService.set(FlashSaleUserKey.token, token, user);
		return true;
	}


	public FlashSaleUser getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		FlashSaleUser user = redisService.get(FlashSaleUserKey.token, token, FlashSaleUser.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}
	

	public String login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		FlashSaleUser user = getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token	 = UUIDUtil.uuid();
		addCookie(response, token, user);
		return token;
	}
	
	private void addCookie(HttpServletResponse response, String token, FlashSaleUser user) {
		redisService.set(FlashSaleUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(FlashSaleUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
