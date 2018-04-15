package com.nowcoder.toutiao.service;

import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.User;
import com.nowcoder.toutiao.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * Created by kongxy on 2018/4/7 0007.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username,String password) {
        //后台检测，前端检测可能被越过

        Map<String,Object> map = new HashMap<String,Object>();

        //检测用户名是否为空
        if (StringUtils.isBlank(username)) {
            map.put("msgname","用户名不能为空");
            return map;
        }

        //检测密码是否为空
        if (StringUtils.isBlank(password)) {
            map.put("msgpwd","密码不能为空");
            return map;
        }

        //检测用户名是否已经被注册
        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msgname", "用户名已被注册");
            return map;
        }


        user = new User();
        user.setName(username);
        //密码保存之前要加密
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        return map;
    }
}
