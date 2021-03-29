package com.beijing.springsecurity.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
//token操作工具类
@Component
public class TokenManager {
    //设置token的过期时间
    private long tokenEcpiration=24*60*60*1000;
    //设置秘钥
    private String tokenSignKey="12345";

    //1 使用jet根据用户名生成token
    public String createToken(String username){
        String token= Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenEcpiration))
                .signWith(SignatureAlgorithm.ES256, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    //2 根据token字符串，获取用户信息
    public String getUserInfoFromToken(String token){
        String userInfo=Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userInfo;
    }

    //3 移除token
    public void removeToken(String token){}
}
