package com.changgou.order.controller;

import com.changgou.order.pojo.CategoryReport;
import com.changgou.order.service.CategoryReportService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */

@RestController
@RequestMapping("/categoryReport")
@CrossOrigin
public class CategoryReportController {

    @Autowired
    private CategoryReportService categoryReportService;

    /**
     * CategoryReport 分页条件搜索实现
     *
     * @param categoryReport
     * @param page           当前页
     * @param size           每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) CategoryReport categoryReport, @PathVariable int page, @PathVariable int size) {
        // 调用 CategoryReportService 实现分页条件查询 CategoryReport
        PageInfo<CategoryReport> pageInfo = categoryReportService.findPage(categoryReport, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * CategoryReport 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 CategoryReportService 实现分页查询 CategoryReport
        PageInfo<CategoryReport> pageInfo = categoryReportService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param categoryReport
     * @return
     */
    @PostMapping("/search")
    public Result<List<CategoryReport>> findList(@RequestBody(required = false) CategoryReport categoryReport) {
        //调用 CategoryReportService 实现条件查询 CategoryReport
        List<CategoryReport> list = categoryReportService.findList(categoryReport);
        return new Result<List<CategoryReport>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Date id) {
        // 调用 CategoryReportService 实现根据主键删除
        categoryReportService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 CategoryReport数据
     *
     * @param categoryReport
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody CategoryReport categoryReport, @PathVariable Date id) {
        // 设置主键值
        categoryReport.setCountDate(id);
        // 调用 CategoryReportService 实现修改 CategoryReport
        categoryReportService.update(categoryReport);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 CategoryReport 数据
     *
     * @param categoryReport
     * @return
     */
    @PostMapping
    public Result add(@RequestBody CategoryReport categoryReport) {
        // 调用 CategoryReportService 实现添加 CategoryReport
        categoryReportService.add(categoryReport);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 CategoryReport 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<CategoryReport> findById(@PathVariable Date id) {
        // 调用 CategoryReportService 实现根据主键查询 CategoryReport
        CategoryReport categoryReport = categoryReportService.findById(id);
        return new Result<CategoryReport>(true, StatusCode.OK, "ID 查询成功!", categoryReport);
    }

    /**
     * 查询 CategoryReport 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<CategoryReport>> findAll() {
        // 调用 CategoryReportService 实现查询所有 CategoryReport
        List<CategoryReport> list = categoryReportService.findAll();
        return new Result<List<CategoryReport>>(true, StatusCode.OK, "查询成功!", list);
    }
}
