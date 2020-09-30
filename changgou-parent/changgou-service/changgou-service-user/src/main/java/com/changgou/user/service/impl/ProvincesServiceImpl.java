package com.changgou.user.service.impl;

import com.changgou.user.dao.ProvincesMapper;
import com.changgou.user.pojo.Provinces;
import com.changgou.user.service.ProvincesService;
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
public class ProvincesServiceImpl implements ProvincesService {

    @Autowired
    private ProvincesMapper provincesMapper;

    /**
     * Provinces 条件分页查询
     * @param provinces 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Provinces> findPage(Provinces provinces, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(provinces);
        // 执行搜索
        return new PageInfo<Provinces>(provincesMapper.selectByExample(example));
    }

    /**
     * Provinces 分页查询
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Provinces> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Provinces>(provincesMapper.selectAll());
    }

    /**
     * Provinces 条件查询
     * @param provinces
     * @return
     */
    @Override
    public List<Provinces> findList(Provinces provinces) {
        // 构建查询条件
        Example example = createExample(provinces);
        // 根据构建的条件查询数据
        return provincesMapper.selectByExample(example);
    }


    /**
     * Provinces  构建查询对象
     * @param provinces
     * @return
     */
    public Example createExample(Provinces provinces) {
        Example example = new Example(Provinces.class);
        Example.Criteria criteria = example.createCriteria();
        if (provinces != null){
            // 省份ID
            if (!StringUtils.isEmpty(provinces.getProvinceid())) {
                criteria.andEqualTo("provinceid", provinces.getProvinceid());
            }
            // 省份名称
            if (!StringUtils.isEmpty(provinces.getProvince())) {
                criteria.andEqualTo("province", provinces.getProvince());
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
        provincesMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Provinces
     * @param provinces
     */
    @Override
    public void update(Provinces provinces) {
        provincesMapper.updateByPrimaryKey(provinces);
    }

    /**
     * 增加 Provinces
     * @param provinces
     */
    @Override
    public void add(Provinces provinces) {
        provincesMapper.insert(provinces);
    }

    /**
     * 根据 ID 查询 Provinces
     * @param id
     * @return
     */
    @Override
    public Provinces findById(String id) {
        return provincesMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Provinces 全部数据
     * @return
     */
    @Override
    public List<Provinces> findAll() {
        return provincesMapper.selectAll();
    }
}
