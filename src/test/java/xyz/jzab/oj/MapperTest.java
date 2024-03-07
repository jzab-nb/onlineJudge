package xyz.jzab.oj;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jzab.oj.mapper.UserMapper;
import xyz.jzab.oj.model.entity.User;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@SpringBootTest
public class MapperTest {
    @Autowired
    UserMapper userMapper;
    @Test
    void baseTest() {
        User user = new User(0, "root", "jzab", "王永霖", "aaa", "D://test.img", 0, -1, -1, null, null);

        userMapper.insert(user);
        System.out.println(userMapper.selectById(0));
        System.out.println("1" );
    }
}
