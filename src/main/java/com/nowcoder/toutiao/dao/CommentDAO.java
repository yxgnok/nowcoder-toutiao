package com.nowcoder.toutiao.dao;

import com.nowcoder.toutiao.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by kongxy on 2018/4/15 0015.
 */
@Mapper
public interface CommentDAO {
    String TABLE_NAME = "Comment";
    String INSERT_FIELDS = "fromid,toid,content,conversation_id,created_date";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    int addComment(Comment comment);

    Comment selectById(int id);

}
