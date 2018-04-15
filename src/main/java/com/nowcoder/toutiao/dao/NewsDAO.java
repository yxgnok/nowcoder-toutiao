package com.nowcoder.toutiao.dao;

import com.nowcoder.toutiao.model.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by kongxy on 2018/4/7 0007.
 */
@Mapper
public interface NewsDAO {
    String TABLE_NAME = "news";
    String INSET_FIELDS = "title,link,image,like_count,comment_count,created_date,user_id";
    String SELECT_FIELDS = "id,"+INSET_FIELDS;

    @Insert({"insert into ", TABLE_NAME,"(",INSET_FIELDS,") values (#{title},#{link},#{image},#{likeCount}," +
            "#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    News selectById(int id);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);

}
