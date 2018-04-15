package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by kongxy on 2018/4/16 0016.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/reg"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody       //当返回的页面不是html，而是其他数据格式如json、xml等时使用@ResponseBody标签
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "remember", defaultValue = "0") int remember) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.isEmpty()) {
                return ToutiaoUtil.getJSONString(0, "注册成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("注册异常",e.getMessage());
            return ToutiaoUtil.getJSONString(1,"注册异常");
        }
    }

}
