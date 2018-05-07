package com.nowcoder.toutiao.service;

import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Created by kongxy on 2018/4/7 0007.
 */
@Service
public class NewsService {
    public String say() {
        return "this is from newsService";
    }

    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    public News getById(int id) {
        return newsDAO.selectById(id);
    }

    public int addNews(News news) {
        newsDAO.addNews(news);
        return news.getId();
    }

    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }

    public String saveImage(MultipartFile file) throws IOException {
        //图片上传前置条件，判断上传文件是不是一张图片
        int doPos = file.getOriginalFilename().lastIndexOf(".");
        if (doPos < 0) {
            return null;
        }
        //判断是不是允许的格式
        String fileExt = file.getOriginalFilename().substring(doPos + 1).toLowerCase();
        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return  null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return ToutiaoUtil.TOUTIAO_DOMAIN+"image?name="+fileName;
    }
}
