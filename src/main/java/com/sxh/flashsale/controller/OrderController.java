package com.sxh.flashsale.controller;

import com.sxh.flashsale.domain.FlashSaleUser;
import com.sxh.flashsale.domain.OrderInfo;
import com.sxh.flashsale.redis.RedisService;
import com.sxh.flashsale.result.CodeMsg;
import com.sxh.flashsale.result.Result;
import com.sxh.flashsale.service.GoodsService;
import com.sxh.flashsale.service.FlashSaleUserService;
import com.sxh.flashsale.service.OrderService;
import com.sxh.flashsale.vo.GoodsVo;
import com.sxh.flashsale.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	FlashSaleUserService userService;
	
	@Autowired
    RedisService redisService;
	
	@Autowired
    OrderService orderService;
	
	@Autowired
    GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, FlashSaleUser user,
                                      @RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
