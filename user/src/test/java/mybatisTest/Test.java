package mybatisTest;

import com.cloud.user.service.UerService;
import com.cloud.user.vo.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private UerService uerService;

    @org.junit.Test
    public void test(){
        List<User> list = uerService.selectUser();
        System.out.println(list);

    }
}
