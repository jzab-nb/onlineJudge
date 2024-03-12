package xyz.jzab.oj.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.constant.JwtConstant;
import xyz.jzab.oj.exception.BusinessException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
public class JwtUtils {
    public static String createToken(Integer userId){
        // 指定加密算法
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        // 过期时间
        long expMillis = System.currentTimeMillis()+1000L*JwtConstant.EXP;

        Date exp = new Date(expMillis);
        // 密钥实例
        SecretKey key = Keys.hmacShaKeyFor(JwtConstant.KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                // 加密算法和密钥
                .signWith(key, algorithm)
                .expiration(exp)
                .claim("userId", userId) // 自定义负载
                .compact( );
    }

    public static Integer getUserIdFromToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(JwtConstant.KEY.getBytes(StandardCharsets.UTF_8));
        Integer result;
        try{
            // 解析token
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            // 获取返回值
            result = claimsJws.getPayload().get("userId", Integer.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorCode.TOKEN_ERROR);
        }
        return result;
    }
}
