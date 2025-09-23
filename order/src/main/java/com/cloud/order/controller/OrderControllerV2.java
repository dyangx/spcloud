package com.cloud.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.order.annotation.ApiLog;
import com.cloud.order.common.Result;
import com.cloud.order.domain.Order;
import com.cloud.order.exception.BusinessException;
import com.cloud.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 订单控制器V2 - 使用统一响应格式和统一异常处理
 * @author yangx
 */
@ApiLog
@RestController
@RequestMapping("/api/v2/orders")
public class OrderControllerV2 {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping
    public Result<Order> createOrder(@RequestBody Order order) {
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        boolean success = orderService.save(order);
        if (!success) {
            throw new BusinessException("订单创建失败");
        }
        return Result.success("订单创建成功", order);
    }

    /**
     * 根据ID查询订单
     */
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order == null) {
            throw new BusinessException(404, "订单不存在");
        }
        return Result.success(order);
    }

    /**
     * 查询所有订单
     */
    @GetMapping
    public Result<List<Order>> getAllOrders() {
        List<Order> orders = orderService.list();
        return Result.success(orders);
    }

    /**
     * 分页查询订单
     */
    @GetMapping("/page")
    public Result<Page<Order>> getOrdersByPage(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        Page<Order> page = new Page<>(current, size);
        Page<Order> result = orderService.page(page);
        return Result.success(result);
    }

    /**
     * 根据订单号查询订单
     */
    @GetMapping("/orderNo/{orderNo}")
    public Result<Order> getOrderByOrderNo(@PathVariable String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException(404, "订单不存在");
        }
        return Result.success(order);
    }

    /**
     * 根据名称模糊查询订单
     */
    @GetMapping("/search")
    public Result<List<Order>> searchOrdersByName(@RequestParam String name) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Order> orders = orderService.list(queryWrapper);
        return Result.success(orders);
    }

    /**
     * 更新订单
     */
    @PutMapping("/{id}")
    public Result<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order existingOrder = orderService.getById(id);
        if (existingOrder == null) {
            throw new BusinessException(404, "订单不存在");
        }
        
        order.setId(id);
        order.setUpdateTime(new Date());
        order.setCreateTime(existingOrder.getCreateTime());
        
        boolean success = orderService.updateById(order);
        if (!success) {
            throw new BusinessException("订单更新失败");
        }
        return Result.success("订单更新成功", order);
    }

    /**
     * 部分更新订单
     */
    @PatchMapping("/{id}")
    public Result<Order> partialUpdateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order existingOrder = orderService.getById(id);
        if (existingOrder == null) {
            throw new BusinessException(404, "订单不存在");
        }
        
        if (order.getOrderNo() != null) {
            existingOrder.setOrderNo(order.getOrderNo());
        }
        if (order.getName() != null) {
            existingOrder.setName(order.getName());
        }
        existingOrder.setUpdateTime(new Date());
        
        boolean success = orderService.updateById(existingOrder);
        if (!success) {
            throw new BusinessException("订单更新失败");
        }
        return Result.success("订单更新成功", existingOrder);
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteOrder(@PathVariable Long id) {
        Order existingOrder = orderService.getById(id);
        if (existingOrder == null) {
            throw new BusinessException(404, "订单不存在");
        }
        
        boolean success = orderService.removeById(id);
        if (!success) {
            throw new BusinessException("订单删除失败");
        }
        return Result.success("订单删除成功");
    }

    /**
     * 批量删除订单
     */
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteOrders(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "删除ID列表不能为空");
        }
        
        boolean success = orderService.removeByIds(ids);
        if (!success) {
            throw new BusinessException("批量删除失败");
        }
        return Result.success("批量删除成功");
    }

    /**
     * 获取订单总数
     */
    @GetMapping("/count")
    public Result<Long> getOrderCount() {
        long count = orderService.count();
        return Result.success(count);
    }
}