package com.beijing.aclservice.service.impl;

import com.beijing.aclservice.entity.User;
import com.beijing.aclservice.service.PermissionService;
import com.beijing.aclservice.service.UserService;
import com.beijing.springsecurity.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override//通过用户名加载用户
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        com.beijing.springsecurity.entity.User curUser = new com.beijing.springsecurity.entity.User();
        BeanUtils.copyProperties(user,curUser);

        //根据用户查询用户权限列表
        List<String> permissionList = permissionService.selectPermissionValueByUserId(user.getId());
        //securityUser是UserDetails的实现类，这里直接返回它即可
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(permissionList);
        return securityUser;
    }
}
