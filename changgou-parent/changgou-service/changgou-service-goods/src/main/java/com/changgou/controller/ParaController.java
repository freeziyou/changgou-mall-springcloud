package com.changgou.controller;

import com.changgou.goods.pojo.Para;
import com.changgou.service.ParaService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/18/2020 14:05
 * @description TODO
 */
@RestController
@RequestMapping("/para")
@CrossOrigin
public class ParaController {

    @Autowired
    private ParaService paraService;

    /**
     * Para 分页条件搜索实现
     *
     * @param para
     * @param page 当前页
     * @param size 每页显示多少条
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Para para, @PathVariable int page, @PathVariable int size) {
        //执行搜索
        PageInfo<Para> pageInfo = paraService.findPage(para, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Para 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return 分页结果
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //分页查询
        PageInfo<Para> pageInfo = paraService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param para 查询条件
     * @return 查询结果
     */
    @PostMapping("/search")
    public Result<List<Para>> findList(@RequestBody(required = false) Para para) {
        List<Para> list = paraService.findList(para);
        return new Result<List<Para>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id id
     * @return 结果信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        paraService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Para 数据
     *
     * @param para 修改的参数
     * @param id   id
     * @return 结果信息
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Para para, @PathVariable Integer id) {
        // 设置主键值
        para.setId(id);
        // 修改数据
        paraService.update(para);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Para 数据
     *
     * @param para
     * @return 结果信息
     */
    @PostMapping
    public Result add(@RequestBody Para para) {
        paraService.add(para);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Para 数据
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result<Para> findById(@PathVariable Integer id) {
        // 根据 ID 查询
        Para para = paraService.findById(id);
        return new Result<Para>(true, StatusCode.OK, "id 查询成功!", para);
    }

    /**
     * 查询 Para 全部数据
     *
     * @return 查询结果
     */
    @GetMapping
    public Result<List<Para>> findAll() {
        List<Para> list = paraService.findAll();
        return new Result<List<Para>>(true, StatusCode.OK, "查询成功!", list);
    }
}
