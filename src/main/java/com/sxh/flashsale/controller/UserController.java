package com.sxh.flashsale.controller;

import com.sxh.flashsale.domain.FlashSaleUser;
import com.sxh.flashsale.redis.RedisService;
import com.sxh.flashsale.result.Result;
import com.sxh.flashsale.service.FlashSaleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    FlashSaleUserService userService;
	
	@Autowired
    RedisService redisService;
	
    @RequestMapping("/info")
    @ResponseBody
    public Result<FlashSaleUser> info(Model model, FlashSaleUser user) {
        return Result.success(user);
    }
    
}
