import com.cloud.order.OrderApplication;
import com.cloud.order.controller.OrderController;
import com.cloud.order.domain.Order;
import com.cloud.order.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * JUnit test class for testing Order functionality
 * This class contains test cases to verify the behavior of Order class
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class OrderTest {

    @Autowired
    private OrderController orderController;
    
    private Order testOrder;
    
    @Before
    public void setUp() {
        // 在每个测试方法执行前准备测试数据
        testOrder = new Order();
        testOrder.setOrderNo("TEST-" + System.currentTimeMillis());
        testOrder.setName("测试用户");
        testOrder.setAmount(100.0);
        log.info("测试开始，准备测试数据: {}", testOrder);
    }
    
    @Test
    public void testCreateOrder() {
        // 测试创建订单
        log.info("测试创建订单");
        Result result = orderController.createOrder(testOrder);
        Assert.assertNotNull("创建订单结果不应为空", result);
        Assert.assertEquals("创建订单应该成功", 200, result.getCode());
        log.info("创建订单测试完成: {}", result);
    }
    
    @Test
    public void testGetOrderById() {
        // 测试根据ID获取订单
        log.info("测试根据ID获取订单");
        // 先创建一个订单
        Result createResult = orderController.createOrder(testOrder);
        Assert.assertEquals("创建订单应该成功", 200, createResult.getCode());
        
        // 获取订单ID
        Integer orderId = 1; // 假设ID为1，实际应从createResult中获取
        
        // 根据ID查询
        Result getResult = orderController.getOrderById(orderId);
        Assert.assertNotNull("查询结果不应为空", getResult);
        Assert.assertEquals("查询订单应该成功", 200, getResult.getCode());
        log.info("根据ID获取订单测试完成: {}", getResult);
    }
    
    @Test
    public void testGetAllOrders() {
        // 测试获取所有订单
        log.info("测试获取所有订单");
        Result result = orderController.getAllOrders();
        Assert.assertNotNull("查询结果不应为空", result);
        Assert.assertEquals("查询所有订单应该成功", 200, result.getCode());
        log.info("获取所有订单测试完成: {}", result);
    }
}
