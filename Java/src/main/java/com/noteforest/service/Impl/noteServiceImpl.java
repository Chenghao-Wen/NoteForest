package com.noteforest.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.noteforest.mapper.noteMapper;
import com.noteforest.pojo.note;
import com.noteforest.pojo.PageBean;
import com.noteforest.service.noteService;
import com.noteforest.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class noteServiceImpl implements noteService {
    @Autowired
    private noteMapper noteMapper;

    /**
     * 新增文章
     *
     * @param note
     */
    @Override
    public void add(note note) {
        // 获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        note.setCreateTime(LocalDateTime.now());
        note.setUpdateTime(LocalDateTime.now());
        note.setCreateUser(id);
        noteMapper.insert(note);
    }

    /**
     * 查看所有文章
     *
     * @return
     */
    @Override
    public PageBean<note> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 创建pageBean对象
        PageBean<note> notePageBean = new PageBean<>();

        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        // 调用mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<note> as = noteMapper.list(userId, categoryId, state);

        // 转为page对象，使其可以使用page的方法
        Page<note> p = (Page<note>) as;

        // 数据填充到pageBean对象中
        notePageBean.setTotal(p.getTotal());
        notePageBean.setItems(p.getResult());
        return notePageBean;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public note detail(Integer id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return noteMapper.selectById(userId, id);
    }

    /**
     * @param note
     */
    @Override
    public void delete(Integer note) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        noteMapper.delete(userId, note);
    }

    /**
     * @param note
     */
    @Override
    public void update(note note) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        note.setCreateUser(userId);
        note.setUpdateTime(LocalDateTime.now());
        noteMapper.update(note);
    }
}
