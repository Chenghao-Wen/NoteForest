package com.noteforest.controller;

import com.noteforest.pojo.note;
import com.noteforest.pojo.PageBean;
import com.noteforest.pojo.Result;
import com.noteforest.service.noteService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class noteController {
    @Autowired
    private noteService noteService;

    @GetMapping("list")
    public Result<String> list(@RequestHeader(name = "Authorization") String token, HttpServletResponse response) {
        return Result.success("获取文章成功");
    }

    @PostMapping
    public Result<String> category(@RequestBody @Validated note note) {
        System.out.println(note);
        noteService.add(note);
        return Result.success("新增文章成功");
    }

    @GetMapping
    public Result<PageBean<note>> list(Integer pageNum, Integer pageSize,
                                          @RequestParam(required = false) Integer categoryId,
                                          @RequestParam(required = false) String state) {
        return Result.success(noteService.list(pageNum, pageSize, categoryId, state));
    }

    @GetMapping("/detail")
    public Result<note> detail(Integer id) {
        return Result.success(noteService.detail(id));
    }

    @DeleteMapping
    public Result delete(Integer id) {
        noteService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody note note) {
        noteService.update(note);
        return Result.success();
    }
}
