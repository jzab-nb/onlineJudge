package xyz.jzab.oj.model.vo;

import java.util.Date;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
public class UserVo     {
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
     * 密码
     */
    private String password;

    /**
     * 头像链接
     */
    private String avatar;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建者
     */
    private Integer createUser;

    /**
     * 更新者
     */
    private Integer updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
