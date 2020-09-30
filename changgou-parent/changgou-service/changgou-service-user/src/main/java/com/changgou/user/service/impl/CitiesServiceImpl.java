package com.changgou.user.service.impl;

import com.changgou.user.dao.CitiesMapper;
import com.changgou.user.pojo.Cities;
import com.changgou.user.service.CitiesService;
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
public class CitiesServiceImpl implements CitiesService {

    @Autowired
    private CitiesMapper citiesMapper;

    /**
     * Cities 条件分页查询
     *
     * @param cities 查询条件
     * @param page   页码
     * @param size   页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Cities> findPage(Cities cities, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(cities);
        // 执行搜索
        return new PageInfo<Cities>(citiesMapper.selectByExample(example));
    }

    /**
     * Cities 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Cities> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Cities>(citiesMapper.selectAll());
    }

    /**
     * Cities 条件查询
     *
     * @param cities
     * @return
     */
    @Override
    public List<Cities> findList(Cities cities) {
        // 构建查询条件
        Example example = createExample(cities);
        // 根据构建的条件查询数据
        return citiesMapper.selectByExample(example);
    }


    /**
     * Cities  构建查询对象
     *
     * @param cities
     * @return
     */
    public Example createExample(Cities cities) {
        Example example = new Example(Cities.class);
        Example.Criteria criteria = example.createCriteria();
        if (cities != null) {
            // 城市ID
            if (!StringUtils.isEmpty(cities.getCityid())) {
                criteria.andEqualTo("cityid", cities.getCityid());
            }
            // 城市名称
            if (!StringUtils.isEmpty(cities.getCity())) {
                criteria.andEqualTo("city", cities.getCity());
            }
            // 省份ID
            if (!StringUtils.isEmpty(cities.getProvinceid())) {
                criteria.andEqualTo("provinceid", cities.getProvinceid());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        citiesMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Cities
     *
     * @param cities
     */
    @Override
    public void update(Cities cities) {
        citiesMapper.updateByPrimaryKey(cities);
    }

    /**
     * 增加 Cities
     *
     * @param cities
     */
    @Override
    public void add(Cities cities) {
        citiesMapper.insert(cities);
    }

    /**
     * 根据 ID 查询 Cities
     *
     * @param id
     * @return
     */
    @Override
    public Cities findById(String id) {
        return citiesMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Cities 全部数据
     *
     * @return
     */
    @Override
    public List<Cities> findAll() {
        return citiesMapper.selectAll();
    }
}
