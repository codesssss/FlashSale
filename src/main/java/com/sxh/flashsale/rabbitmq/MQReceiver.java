package com.sxh.flashsale.rabbitmq;

import com.sxh.flashsale.domain.FlashSaleOrder;
import com.sxh.flashsale.domain.FlashSaleUser;
import com.sxh.flashsale.redis.RedisService;
import com.sxh.flashsale.service.GoodsService;
import com.sxh.flashsale.service.FlashSaleService;
import com.sxh.flashsale.service.OrderService;
import com.sxh.flashsale.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
		
		@Autowired
        RedisService redisService;
		
		@Autowired
        GoodsService goodsService;
		
		@Autowired
        OrderService orderService;
		
		@Autowired
		FlashSaleService flashSaleService;
		
		@RabbitListener(queues=MQConfig.FLASHSALE_QUEUE)
		public void receive(String message) {
			log.info("receive message:"+message);
			FlashSaleMessage mm  = RedisService.stringToBean(message, FlashSaleMessage.class);
			FlashSaleUser user = mm.getUser();
			long goodsId = mm.getGoodsId();
			
			GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
	    	int stock = goods.getStockCount();
	    	if(stock <= 0) {
	    		return;
	    	}
	    	//判断是否已经秒杀到了
	    	FlashSaleOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
	    	if(order != null) {
	    		return;
	    	}
	    	//减库存 下订单 写入秒杀订单
	    	flashSaleService.miaosha(user, goods);
		}
	
//		@RabbitListener(queues=MQConfig.QUEUE)
//		public void receive(String message) {
//			log.info("receive message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
//		public void receiveTopic1(String message) {
//			log.info(" topic  queue1 message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
//		public void receiveTopic2(String message) {
//			log.info(" topic  queue2 message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.HEADER_QUEUE)
//		public void receiveHeaderQueue(byte[] message) {
//			log.info(" header  queue message:"+new String(message));
//		}
//		
		
}
