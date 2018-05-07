package com.nowcoder.toutiao.service;

import com.nowcoder.toutiao.dao.CommentDAO;
import com.nowcoder.toutiao.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by kongxy on 2018/5/6 0006.
 */
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDAO.selectByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }
}
