package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/16/2020 12:51
 * @description TODO
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页条件查询
     *
     * @param brand 搜索条件
     * @param page  当前页
     * @param size  每页显示条数
     * @return 结果
     */
    @Override
    public PageInfo<Brand> findPage(Brand brand, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 条件搜索
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        // 封装 PageInfo
        return new PageInfo<Brand>(brands);
    }

    /**
     * 分页查询
     *
     * @param page 当前页
     * @param size 每页显示条数
     * @return 结果
     */
    @Override
    public PageInfo<Brand> findPage(int page, int size) {
        // 分页实现, 后面的查询紧跟集合查询
        // page: 当前页, size: 每页显示多少条
        PageHelper.startPage(page, size);
        // 查询
        List<Brand> brands = brandMapper.selectAll();
        // 封装 PageInfo
        return new PageInfo<Brand>(brands);
    }

    /**
     * 条件查询
     *
     * @param brand 品牌
     * @return 符合条件品牌
     */
    @Override
    public List<Brand> findList(Brand brand) {
        // 构建查询条件
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    /**
     * 构建查询对象
     *
     * @param brand 品牌
     * @return 查询对象
     */
    public Example createExample(Brand brand) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (brand != null) {
            // 品牌名称
            if (!StringUtils.isEmpty(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            // 品牌图片地址
            if (!StringUtils.isEmpty(brand.getImage())) {
                criteria.andLike("image", "%" + brand.getImage() + "%");
            }
            // 品牌的首字母
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andLike("letter", "%" + brand.getLetter() + "%");
            }
            // 品牌id
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andEqualTo("id", brand.getId());
            }
            // 排序
            if (!StringUtils.isEmpty(brand.getSeq())) {
                criteria.andEqualTo("seq", brand.getSeq());
            }
        }
        return example;
    }

    /**
     * 删除品牌数据
     *
     * @param id id
     */
    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改品牌数据
     *
     * @param brand 品牌
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 增加品牌
     *
     * @param brand 品牌
     */
    @Override
    public void add(Brand brand) {
        // selective 会忽略空值
        brandMapper.insertSelective(brand);
    }

    /**
     * 根据 id 查询
     *
     * @param id id
     * @return 数据
     */
    @Override
    public Brand findById(Integer id) {
        // 根据 id 查询
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 全部数据
     *
     * @return 所有数据
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }
}


