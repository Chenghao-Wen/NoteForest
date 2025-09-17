package com.noteforest.service;

import com.noteforest.pojo.note;
import com.noteforest.pojo.PageBean;

public interface noteService {
    /**
     * 新增文章
     * @param note
     */
    void add(note note);

    /**
     * 查看所有文章
     * @return
     */
    PageBean<note> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    note detail(Integer id);

    void delete(Integer note);

    void update(note note);
}
