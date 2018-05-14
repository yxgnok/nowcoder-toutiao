package com.nowcoder.toutiao;

import com.nowcoder.toutiao.dao.CommentDAO;
import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.Comment;
import com.nowcoder.toutiao.model.LoginTicket;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private NewsDAO newsDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Test
    public void initDatabase() {
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
            user.setName(String.format("user%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
            user.setPassword("newPassword");
            userDAO.updatePassword(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime()+1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/news/%dm.png",random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setUserId(i + 1);
            news.setTitle(String.format("title[%d]",i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
            newsDAO.addNews(news);
            System.out.println(news.getId());



            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i + 1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d", i + 1));
            loginTicketDAO.addTicket(ticket);

            loginTicketDAO.updateStatus(ticket.getTicket(),2);
        }

        Assert.assertEquals(1,loginTicketDAO.selectByTicket("TICKET1").getUserId());
        Assert.assertEquals(2,loginTicketDAO.selectByTicket("TICKET1").getStatus());

    }

}
