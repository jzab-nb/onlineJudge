package xyz.jzab.oj.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Configuration
@Profile({"dev", "test"})
public class Knife4jConfig {

}
