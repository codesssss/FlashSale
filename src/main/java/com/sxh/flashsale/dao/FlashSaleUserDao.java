package com.sxh.flashsale.dao;

import com.sxh.flashsale.domain.FlashSaleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FlashSaleUserDao {
	
	@Select("select * from miaosha_user where id = #{id}")
	public FlashSaleUser getById(@Param("id")long id);

	@Update("update miaosha_user set password = #{password} where id = #{id}")
	public void update(FlashSaleUser toBeUpdate);
}
