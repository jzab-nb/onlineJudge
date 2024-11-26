package xyz.jzab.oj;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jzab.oj.mapper.UserMapper;
import xyz.jzab.oj.model.entity.User;

import java.util.Arrays;

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

    }
}
