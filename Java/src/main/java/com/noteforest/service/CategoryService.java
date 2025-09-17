package com.noteforest.service;

import com.noteforest.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> get();

    Category findById(Integer id);

    void update(Category category);
}
