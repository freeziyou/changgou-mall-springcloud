package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * Category 条件分页查询
     *
     * @param category 查询条件
     * @param page     页码
     * @param size     页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Category> findPage(Category category, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(category);
        // 执行搜索
        return new PageInfo<Category>(categoryMapper.selectByExample(example));
    }

    /**
     * Category 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Category> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Category>(categoryMapper.selectAll());
    }

    /**
     * Category 条件查询
     *
     * @param category
     * @return
     */
    @Override
    public List<Category> findList(Category category) {
        // 构建查询条件
        Example example = createExample(category);
        // 根据构建的条件查询数据
        return categoryMapper.selectByExample(example);
    }


    /**
     * Category  构建查询对象
     *
     * @param category
     * @return
     */
    public Example createExample(Category category) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (category != null) {
            // 分类ID
            if (!StringUtils.isEmpty(category.getId())) {
                criteria.andEqualTo("id", category.getId());
            }
            // 分类名称
            if (!StringUtils.isEmpty(category.getName())) {
                criteria.andLike("name", "%" + category.getName() + "%");
            }
            // 商品数量
            if (!StringUtils.isEmpty(category.getGoodsNum())) {
                criteria.andEqualTo("goodsNum", category.getGoodsNum());
            }
            // 是否显示
            if (!StringUtils.isEmpty(category.getIsShow())) {
                criteria.andEqualTo("isShow", category.getIsShow());
            }
            // 是否导航
            if (!StringUtils.isEmpty(category.getIsMenu())) {
                criteria.andEqualTo("isMenu", category.getIsMenu());
            }
            // 排序
            if (!StringUtils.isEmpty(category.getSeq())) {
                criteria.andEqualTo("seq", category.getSeq());
            }
            // 上级ID
            if (!StringUtils.isEmpty(category.getParentId())) {
                criteria.andEqualTo("parentId", category.getParentId());
            }
            // 模板ID
            if (!StringUtils.isEmpty(category.getTemplateId())) {
                criteria.andEqualTo("templateId", category.getTemplateId());
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
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Category
     *
     * @param category
     */
    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 增加 Category
     *
     * @param category
     */
    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    /**
     * 根据 ID 查询 Category
     *
     * @param id
     * @return
     */
    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Category 全部数据
     *
     * @return
     */
    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    /**
     * 根据父节点 ID 查询子分类
     *
     * @param pid 父节点 ID
     */
    @Override
    public List<Category> findByParentId(Integer pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }
}
