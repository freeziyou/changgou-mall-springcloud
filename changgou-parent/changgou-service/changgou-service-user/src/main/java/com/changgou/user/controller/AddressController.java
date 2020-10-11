package com.changgou.user.controller;

import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 根据用户名查询用户收件地址列表
     *
     * @return
     */
    @GetMapping("/user/list")
    public Result<List<Address>> list() {
        // 获取用户登录名
        String username = TokenDecode.getUserInfo().get("username");
        // 查询用户的收件列表
        List<Address> addresses = addressService.list(username);
        return new Result<List<Address>>(true, StatusCode.OK, "查询成功!", addresses);
    }

    /**
     * Address 分页条件搜索实现
     *
     * @param address
     * @param page    当前页
     * @param size    每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Address address, @PathVariable int page, @PathVariable int size) {
        // 调用 AddressService 实现分页条件查询 Address
        PageInfo<Address> pageInfo = addressService.findPage(address, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * Address 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 AddressService 实现分页查询 Address
        PageInfo<Address> pageInfo = addressService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param address
     * @return
     */
    @PostMapping("/search")
    public Result<List<Address>> findList(@RequestBody(required = false) Address address) {
        //调用 AddressService 实现条件查询 Address
        List<Address> list = addressService.findList(address);
        return new Result<List<Address>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 AddressService 实现根据主键删除
        addressService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 Address数据
     *
     * @param address
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Address address, @PathVariable Integer id) {
        // 设置主键值
        address.setId(id);
        // 调用 AddressService 实现修改 Address
        addressService.update(address);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 Address 数据
     *
     * @param address
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Address address) {
        // 调用 AddressService 实现添加 Address
        addressService.add(address);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 Address 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Address> findById(@PathVariable Integer id) {
        // 调用 AddressService 实现根据主键查询 Address
        Address address = addressService.findById(id);
        return new Result<Address>(true, StatusCode.OK, "ID 查询成功!", address);
    }

    /**
     * 查询 Address 全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Address>> findAll() {
        // 调用 AddressService 实现查询所有 Address
        List<Address> list = addressService.findAll();
        return new Result<List<Address>>(true, StatusCode.OK, "查询成功!", list);
    }
}
