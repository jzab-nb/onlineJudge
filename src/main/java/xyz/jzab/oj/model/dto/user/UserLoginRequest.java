package xyz.jzab.oj.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
public class UserLoginRequest implements Serializable {

    private String username;

    private String password;
}