package com.cloud.order.mapper;

import com.cloud.order.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yangx
* @description 针对表【order】的数据库操作Mapper
* @createDate 2025-09-23 11:01:15
* @Entity com.cloud.order.domain.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




