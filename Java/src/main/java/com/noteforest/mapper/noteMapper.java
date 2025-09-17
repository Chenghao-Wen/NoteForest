package com.noteforest.mapper;

import com.noteforest.pojo.note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface noteMapper {
    @Insert("insert into note(title,cover_img,content,state,create_time,update_time,category_id,create_user) values(#{title},#{coverImg},#{content},#{state},#{createTime},#{updateTime},#{categoryId},#{createUser})")
    void insert(note note);

    /**
     * 分页查询文章列表
     * @param userId
     * @param categoryId
     * @param state
     * @return
     */
    List<note> list(Integer userId, Integer categoryId, String state);
    
    @Select("SELECT * FROM note WHERE id = #{id} and create_user = #{userId}")
    note selectById(Integer userId, Integer id);

    @Delete("DELETE FROM note WHERE id = #{id} and create_user = #{userId}")
    void delete(Integer userId, Integer note);

    @Update("UPDATE note SET title=#{title},cover_img=#{coverImg},content=#{content},state=#{state},update_time=#{updateTime},category_id=#{categoryId},create_user=#{createUser} WHERE id = #{id}")
    void update(note note);
}
