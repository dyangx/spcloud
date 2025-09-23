package com.cloud.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.order.domain.Order;
import com.cloud.order.service.OrderService;
import com.cloud.order.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author yangx
* @description 针对表【order】的数据库操作Service实现
* @createDate 2025-09-23 11:01:15
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




