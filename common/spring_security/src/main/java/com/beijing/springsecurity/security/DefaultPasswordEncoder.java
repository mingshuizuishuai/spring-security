package com.beijing.springsecurity.security;

import com.beijing.servicebase.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordEncoder implements PasswordEncoder {
    @Override//md5加密操作
    public String encode(CharSequence charSequence) {
        //对传来的密码进行加密
        return MD5.encrypt(charSequence.toString());
    }

    @Override//进行密码比对操作
    public boolean matches(CharSequence charSequence, String dataPassword) {
        //charSequence传进来的密码 dataPassword数据库中存的密码
        return dataPassword.equals(MD5.encrypt(charSequence.toString()));
    }
}
