package xyz.jzab.oj.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Component
@ConfigurationProperties(prefix = "file")
@Data
public class FileConfig {
    private Long maxSize;
    private String path;
}
