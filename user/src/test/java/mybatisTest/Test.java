package mybatisTest;

import com.cloud.user.UserApplication;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.service.UerService;
import com.cloud.user.vo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes ={UserApplication.class})
public class Test {

    @Autowired
    private UerService uerService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    SqlSession sqlSession;

    @Autowired
    SqlSessionFactory factory;

    @org.junit.Test
    public void test(){
        UserMapper userMapper1 = sqlSession.getMapper(UserMapper.class);
        userMapper1.getOneUser();
        userMapper1.getOneUser();
    }

    @org.junit.Test
    public void test1(){
        SqlSession session = factory.openSession();
        UserMapper userMapper1 = session.getMapper(UserMapper.class);
        User u1 = userMapper1.getOneUser();
        System.out.println(1111111111);
        User u2 = userMapper1.getOneUser();
        System.out.println(u1);
        System.out.println(u2);
    }

    @org.junit.Test
    public void test2(){
        User u = new User();
        u.setId("1");
        System.out.println(u.selectById());
    }
}
