package com.changgou.user.controller;

import com.changgou.user.pojo.OauthClientDetails;
import com.changgou.user.service.OauthClientDetailsService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */

@RestController
@RequestMapping("/oauthClientDetails")
@CrossOrigin
public class OauthClientDetailsController {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    /**
     * OauthClientDetails 分页条件搜索实现
     * @param oauthClientDetails
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) OauthClientDetails oauthClientDetails, @PathVariable int page, @PathVariable int size) {
        // 调用 OauthClientDetailsService 实现分页条件查询 OauthClientDetails
        PageInfo<OauthClientDetails> pageInfo = oauthClientDetailsService.findPage(oauthClientDetails, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * OauthClientDetails 分页搜索实现
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 OauthClientDetailsService 实现分页查询 OauthClientDetails
        PageInfo<OauthClientDetails> pageInfo = oauthClientDetailsService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     * @param oauthClientDetails
     * @return
     */
    @PostMapping("/search")
    public Result<List<OauthClientDetails>> findList(@RequestBody(required = false) OauthClientDetails oauthClientDetails) {
        //调用 OauthClientDetailsService 实现条件查询 OauthClientDetails
        List<OauthClientDetails> list = oauthClientDetailsService.findList(oauthClientDetails);
        return new Result<List<OauthClientDetails>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // 调用 OauthClientDetailsService 实现根据主键删除
        oauthClientDetailsService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 OauthClientDetails数据
     * @param oauthClientDetails
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody OauthClientDetails oauthClientDetails, @PathVariable String id) {
        // 设置主键值
        oauthClientDetails.setClientId(id);
        // 调用 OauthClientDetailsService 实现修改 OauthClientDetails
        oauthClientDetailsService.update(oauthClientDetails);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 OauthClientDetails 数据
     * @param oauthClientDetails
     * @return
     */
    @PostMapping
    public Result add(@RequestBody OauthClientDetails oauthClientDetails) {
        // 调用 OauthClientDetailsService 实现添加 OauthClientDetails
        oauthClientDetailsService.add(oauthClientDetails);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 OauthClientDetails 数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<OauthClientDetails> findById(@PathVariable String id){
        // 调用 OauthClientDetailsService 实现根据主键查询 OauthClientDetails
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findById(id);
        return new Result<OauthClientDetails>(true, StatusCode.OK, "ID 查询成功!", oauthClientDetails);
    }

    /**
     * 查询 OauthClientDetails 全部数据
     * @return
     */
    @GetMapping
    public Result<List<OauthClientDetails>> findAll() {
        // 调用 OauthClientDetailsService 实现查询所有 OauthClientDetails
        List<OauthClientDetails> list = oauthClientDetailsService.findAll();
        return new Result<List<OauthClientDetails>>(true, StatusCode.OK, "查询成功!", list) ;
    }
}
