package com.cloud.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.order.domain.Order;
import com.cloud.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 订单控制器 - 提供订单的增删查改功能
 * @author yangx
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param order 订单信息
     * @return 创建结果
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        boolean success = orderService.save(order);
        if (success) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 查询所有订单
     * @return 订单列表
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.list();
        return ResponseEntity.ok(orders);
    }

    /**
     * 分页查询订单
     * @param current 当前页码
     * @param size 每页大小
     * @return 分页结果
     */
    @GetMapping("/page")
    public ResponseEntity<Page<Order>> getOrdersByPage(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        Page<Order> page = new Page<>(current, size);
        Page<Order> result = orderService.page(page);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return 订单信息
     */
    @GetMapping("/orderNo/{orderNo}")
    public ResponseEntity<Order> getOrderByOrderNo(@PathVariable String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 根据名称模糊查询订单
     * @param name 订单名称
     * @return 订单列表
     */
    @GetMapping("/search")
    public ResponseEntity<List<Order>> searchOrdersByName(@RequestParam String name) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Order> orders = orderService.list(queryWrapper);
        return ResponseEntity.ok(orders);
    }

    /**
     * 更新订单
     * @param id 订单ID
     * @param order 更新的订单信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order existingOrder = orderService.getById(id);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        
        order.setId(id);
        order.setUpdateTime(new Date());
        // 保持创建时间不变
        order.setCreateTime(existingOrder.getCreateTime());
        
        boolean success = orderService.updateById(order);
        if (success) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 部分更新订单
     * @param id 订单ID
     * @param order 部分更新的订单信息
     * @return 更新结果
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Order> partialUpdateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order existingOrder = orderService.getById(id);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 只更新非空字段
        if (order.getOrderNo() != null) {
            existingOrder.setOrderNo(order.getOrderNo());
        }
        if (order.getName() != null) {
            existingOrder.setName(order.getName());
        }
        existingOrder.setUpdateTime(new Date());
        
        boolean success = orderService.updateById(existingOrder);
        if (success) {
            return ResponseEntity.ok(existingOrder);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 删除订单
     * @param id 订单ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Order existingOrder = orderService.getById(id);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        
        boolean success = orderService.removeById(id);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 批量删除订单
     * @param ids 订单ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batch")
    public ResponseEntity<Void> batchDeleteOrders(@RequestBody List<Long> ids) {
        boolean success = orderService.removeByIds(ids);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 获取订单总数
     * @return 订单总数
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getOrderCount() {
        long count = orderService.count();
        return ResponseEntity.ok(count);
    }
}
