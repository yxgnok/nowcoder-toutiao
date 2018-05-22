package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.model.HostHolder;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.service.AliyunService;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

/**
 * Created by kongxy on 2018/5/6 0006.
 */
@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @Autowired
    AliyunService aliyunService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR + imageName)), response.getOutputStream());
        } catch (Exception e) {
            logger.error("读取图片错误" + imageName + e.getMessage());
        }
    }

    //图片上传
    @RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
//            String fileUrl = newsService.saveImage(file);
//            String fileUrl = qiniuService.saveImage(file);
            String fileUrl = aliyunService.saveImage(file);
            if (fileUrl == null) {
                return ToutiaoUtil.getJSONString(1, "上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0, fileUrl);
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "上传失败");
        }
    }

    @RequestMapping(path = {"/user/addNews/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        try {
            News news = new News();
            if (hostHolder.getUser() != null) {
                news.setUserId(hostHolder.getUser().getId());
            } else {
                //匿名id自定义
                news.setUserId(0);
            }
            news.setCreatedDate(new Date());
            news.setTitle(title);
            news.setImage(image);
            news.setLink(link);
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("添加咨询错误" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "发布失败");

        }
    }

    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetiail(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.getById(newsId);
        if (news != null) {
            //评论
        }
        model.addAttribute("news",news);
        model.addAttribute("owner",userService.getUser(news.getUserId()));
        return "detail";
    }
}