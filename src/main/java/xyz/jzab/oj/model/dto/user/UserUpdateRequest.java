package xyz.jzab.oj.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色
     */
    private String role;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像链接
     */
    private String avatar;
}
