package com.changgou.user.service.impl;

import com.changgou.user.dao.AreasMapper;
import com.changgou.user.pojo.Areas;
import com.changgou.user.service.AreasService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Service
public class AreasServiceImpl implements AreasService {

    @Autowired
    private AreasMapper areasMapper;

    /**
     * Areas 条件分页查询
     * @param areas 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Areas> findPage(Areas areas, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(areas);
        // 执行搜索
        return new PageInfo<Areas>(areasMapper.selectByExample(example));
    }

    /**
     * Areas 分页查询
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Areas> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Areas>(areasMapper.selectAll());
    }

    /**
     * Areas 条件查询
     * @param areas
     * @return
     */
    @Override
    public List<Areas> findList(Areas areas) {
        // 构建查询条件
        Example example = createExample(areas);
        // 根据构建的条件查询数据
        return areasMapper.selectByExample(example);
    }


    /**
     * Areas  构建查询对象
     * @param areas
     * @return
     */
    public Example createExample(Areas areas) {
        Example example = new Example(Areas.class);
        Example.Criteria criteria = example.createCriteria();
        if (areas != null){
            // 区域ID
            if (!StringUtils.isEmpty(areas.getAreaid())) {
                criteria.andEqualTo("areaid", areas.getAreaid());
            }
            // 区域名称
            if (!StringUtils.isEmpty(areas.getArea())) {
                criteria.andEqualTo("area", areas.getArea());
            }
            // 城市ID
            if (!StringUtils.isEmpty(areas.getCityid())) {
                criteria.andEqualTo("cityid", areas.getCityid());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        areasMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Areas
     * @param areas
     */
    @Override
    public void update(Areas areas) {
        areasMapper.updateByPrimaryKey(areas);
    }

    /**
     * 增加 Areas
     * @param areas
     */
    @Override
    public void add(Areas areas) {
        areasMapper.insert(areas);
    }

    /**
     * 根据 ID 查询 Areas
     * @param id
     * @return
     */
    @Override
    public Areas findById(String id) {
        return areasMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Areas 全部数据
     * @return
     */
    @Override
    public List<Areas> findAll() {
        return areasMapper.selectAll();
    }
}
