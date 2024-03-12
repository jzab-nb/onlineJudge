package xyz.jzab.oj.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginUserVo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色
     */
    private String role;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String name;

    /**
     * 头像链接
     */
    private String avatar;

    /**
     * 生成的token
     */
    private String token;
}
