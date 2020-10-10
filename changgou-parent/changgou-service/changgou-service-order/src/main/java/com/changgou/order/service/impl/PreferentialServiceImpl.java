package com.changgou.order.service.impl;

import com.changgou.order.dao.PreferentialMapper;
import com.changgou.order.pojo.Preferential;
import com.changgou.order.service.PreferentialService;
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
public class PreferentialServiceImpl implements PreferentialService {

    @Autowired
    private PreferentialMapper preferentialMapper;

    /**
     * Preferential 条件分页查询
     *
     * @param preferential 查询条件
     * @param page         页码
     * @param size         页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Preferential> findPage(Preferential preferential, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(preferential);
        // 执行搜索
        return new PageInfo<Preferential>(preferentialMapper.selectByExample(example));
    }

    /**
     * Preferential 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Preferential> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Preferential>(preferentialMapper.selectAll());
    }

    /**
     * Preferential 条件查询
     *
     * @param preferential
     * @return
     */
    @Override
    public List<Preferential> findList(Preferential preferential) {
        // 构建查询条件
        Example example = createExample(preferential);
        // 根据构建的条件查询数据
        return preferentialMapper.selectByExample(example);
    }


    /**
     * Preferential  构建查询对象
     *
     * @param preferential
     * @return
     */
    public Example createExample(Preferential preferential) {
        Example example = new Example(Preferential.class);
        Example.Criteria criteria = example.createCriteria();
        if (preferential != null) {
            // ID
            if (!StringUtils.isEmpty(preferential.getId())) {
                criteria.andEqualTo("id", preferential.getId());
            }
            // 消费金额
            if (!StringUtils.isEmpty(preferential.getBuyMoney())) {
                criteria.andEqualTo("buyMoney", preferential.getBuyMoney());
            }
            // 优惠金额
            if (!StringUtils.isEmpty(preferential.getPreMoney())) {
                criteria.andEqualTo("preMoney", preferential.getPreMoney());
            }
            // 品类ID
            if (!StringUtils.isEmpty(preferential.getCategoryId())) {
                criteria.andEqualTo("categoryId", preferential.getCategoryId());
            }
            // 活动开始日期
            if (!StringUtils.isEmpty(preferential.getStartTime())) {
                criteria.andEqualTo("startTime", preferential.getStartTime());
            }
            // 活动截至日期
            if (!StringUtils.isEmpty(preferential.getEndTime())) {
                criteria.andEqualTo("endTime", preferential.getEndTime());
            }
            // 状态
            if (!StringUtils.isEmpty(preferential.getState())) {
                criteria.andEqualTo("state", preferential.getState());
            }
            // 类型1不翻倍 2翻倍
            if (!StringUtils.isEmpty(preferential.getType())) {
                criteria.andEqualTo("type", preferential.getType());
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
    public void delete(Integer id) {
        preferentialMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Preferential
     *
     * @param preferential
     */
    @Override
    public void update(Preferential preferential) {
        preferentialMapper.updateByPrimaryKey(preferential);
    }

    /**
     * 增加 Preferential
     *
     * @param preferential
     */
    @Override
    public void add(Preferential preferential) {
        preferentialMapper.insert(preferential);
    }

    /**
     * 根据 ID 查询 Preferential
     *
     * @param id
     * @return
     */
    @Override
    public Preferential findById(Integer id) {
        return preferentialMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Preferential 全部数据
     *
     * @return
     */
    @Override
    public List<Preferential> findAll() {
        return preferentialMapper.selectAll();
    }
}
