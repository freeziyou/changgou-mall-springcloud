package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
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
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据分类 ID 查询模板信息
     * @param id
     * @return
     */
    @Override
    public Template findByCategoryId(Integer id) {
        // 查询分类信息
        Category category = categoryMapper.selectByPrimaryKey(id);
        // 根据模板 Id 查询模板信息
        return templateMapper.selectByPrimaryKey(category.getTemplateId());
    }

    /**
     * Template 条件分页查询
     *
     * @param template 查询条件
     * @param page     页码
     * @param size     页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Template> findPage(Template template, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(template);
        // 执行搜索
        return new PageInfo<Template>(templateMapper.selectByExample(example));
    }

    /**
     * Template 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Template> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Template>(templateMapper.selectAll());
    }

    /**
     * Template 条件查询
     *
     * @param template
     * @return
     */
    @Override
    public List<Template> findList(Template template) {
        // 构建查询条件
        Example example = createExample(template);
        // 根据构建的条件查询数据
        return templateMapper.selectByExample(example);
    }


    /**
     * Template  构建查询对象
     *
     * @param template
     * @return
     */
    public Example createExample(Template template) {
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        if (template != null) {
            // ID
            if (!StringUtils.isEmpty(template.getId())) {
                criteria.andEqualTo("id", template.getId());
            }
            // 模板名称
            if (!StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name", "%" + template.getName() + "%");
            }
            // 规格数量
            if (!StringUtils.isEmpty(template.getSpecNum())) {
                criteria.andEqualTo("specNum", template.getSpecNum());
            }
            // 参数数量
            if (!StringUtils.isEmpty(template.getParaNum())) {
                criteria.andEqualTo("paraNum", template.getParaNum());
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
        templateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Template
     *
     * @param template
     */
    @Override
    public void update(Template template) {
        templateMapper.updateByPrimaryKey(template);
    }

    /**
     * 增加 Template
     *
     * @param template
     */
    @Override
    public void add(Template template) {
        templateMapper.insert(template);
    }

    /**
     * 根据 ID 查询 Template
     *
     * @param id
     * @return
     */
    @Override
    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Template 全部数据
     *
     * @return
     */
    @Override
    public List<Template> findAll() {
        return templateMapper.selectAll();
    }
}
