package com.nowcoder.toutiao;

import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
public class ToutiaoApplicationTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Test
    public void contextLoads() {
        News news = newsDAO.selectById(9);
        System.out.println("id:"+news.getId()+" title:"+news.getTitle()+" link:"+news.getLink()
        +" image:"+news.getImage()+" likecount:"+news.getLikeCount()+" commentCount:"+news.getCommentCount()
        +" createdDate:"+ news.getCreatedDate()+ " userId:"+news.getUserId());

        User user = userDAO.selectById(9);
        System.out.println("id:"+user.getId()+" name:"+user.getName()+" password:"+user.getPassword()
        +" salt:"+user.getSalt()+" headUrl:"+user.getHeadUrl());
    }

}
