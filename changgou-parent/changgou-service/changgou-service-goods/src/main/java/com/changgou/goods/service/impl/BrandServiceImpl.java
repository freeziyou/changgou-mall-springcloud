package com.changgou.goods.service.impl;

import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
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
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据分类查询品牌集合
     * @param categoryId 分类 Id
     * @return
     */
    @Override
    public List<Brand> findByCategory(Integer categoryId) {
        return brandMapper.findByCategory(categoryId);
    }

    /**
     * Brand 条件分页查询
     *
     * @param brand 查询条件
     * @param page  页码
     * @param size  页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Brand> findPage(Brand brand, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(brand);
        // 执行搜索
        return new PageInfo<Brand>(brandMapper.selectByExample(example));
    }

    /**
     * Brand 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Brand> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Brand>(brandMapper.selectAll());
    }

    /**
     * Brand 条件查询
     *
     * @param brand
     * @return
     */
    @Override
    public List<Brand> findList(Brand brand) {
        // 构建查询条件
        Example example = createExample(brand);
        // 根据构建的条件查询数据
        return brandMapper.selectByExample(example);
    }


    /**
     * Brand  构建查询对象
     *
     * @param brand
     * @return
     */
    public Example createExample(Brand brand) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (brand != null) {
            // 品牌id
            if (!StringUtils.isEmpty(brand.getId())) {
                criteria.andEqualTo("id", brand.getId());
            }
            // 品牌名称
            if (!StringUtils.isEmpty(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            // 品牌图片地址
            if (!StringUtils.isEmpty(brand.getImage())) {
                criteria.andEqualTo("image", brand.getImage());
            }
            // 品牌的首字母
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
            // 排序
            if (!StringUtils.isEmpty(brand.getSeq())) {
                criteria.andEqualTo("seq", brand.getSeq());
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
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Brand
     *
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 增加 Brand
     *
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        brandMapper.insert(brand);
    }

    /**
     * 根据 ID 查询 Brand
     *
     * @param id
     * @return
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Brand 全部数据
     *
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }
}
