package com.sxh.flashsale.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.flashsale.dao.OrderDao;
import com.sxh.flashsale.domain.FlashSaleOrder;
import com.sxh.flashsale.domain.FlashSaleUser;
import com.sxh.flashsale.domain.OrderInfo;
import com.sxh.flashsale.redis.OrderKey;
import com.sxh.flashsale.redis.RedisService;
import com.sxh.flashsale.vo.GoodsVo;

@Service
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	RedisService redisService;
	
	public FlashSaleOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		//return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
		return redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId, FlashSaleOrder.class);
	}
	
	public OrderInfo getOrderById(long orderId) {
		return orderDao.getOrderById(orderId);
	}
	

	@Transactional
	public OrderInfo createOrder(FlashSaleUser user, GoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		orderDao.insert(orderInfo);
		FlashSaleOrder flashSaleOrder = new FlashSaleOrder();
		flashSaleOrder.setGoodsId(goods.getId());
		flashSaleOrder.setOrderId(orderInfo.getId());
		flashSaleOrder.setUserId(user.getId());
		orderDao.insertMiaoshaOrder(flashSaleOrder);
		
		redisService.set(OrderKey.getMiaoshaOrderByUidGid, ""+user.getId()+"_"+goods.getId(), flashSaleOrder);
		 
		return orderInfo;
	}

	public void deleteOrders() {
		orderDao.deleteOrders();
		orderDao.deleteMiaoshaOrders();
	}

}
