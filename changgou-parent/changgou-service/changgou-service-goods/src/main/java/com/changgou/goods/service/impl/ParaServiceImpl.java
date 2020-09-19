package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.ParaMapper;
import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.ParaService;
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
public class ParaServiceImpl implements ParaService {

    @Autowired
    private ParaMapper paraMapper;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据分类 ID 查询参数列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Para> findByCategoryId(Integer id) {
        // 查询分类信息
        Category category = categoryMapper.selectByPrimaryKey(id);
        // 根据分类的模板 ID 查询参数列表
        Para para = new Para();
        para.setTemplateId(category.getTemplateId());
        return paraMapper.select(para);
    }

    /**
     * Para 条件分页查询
     *
     * @param para 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Para> findPage(Para para, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(para);
        // 执行搜索
        return new PageInfo<Para>(paraMapper.selectByExample(example));
    }

    /**
     * Para 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Para> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<Para>(paraMapper.selectAll());
    }

    /**
     * Para 条件查询
     *
     * @param para
     * @return
     */
    @Override
    public List<Para> findList(Para para) {
        // 构建查询条件
        Example example = createExample(para);
        // 根据构建的条件查询数据
        return paraMapper.selectByExample(example);
    }


    /**
     * Para  构建查询对象
     *
     * @param para
     * @return
     */
    public Example createExample(Para para) {
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        if (para != null) {
            // id
            if (!StringUtils.isEmpty(para.getId())) {
                criteria.andEqualTo("id", para.getId());
            }
            // 名称
            if (!StringUtils.isEmpty(para.getName())) {
                criteria.andLike("name", "%" + para.getName() + "%");
            }
            // 选项
            if (!StringUtils.isEmpty(para.getOptions())) {
                criteria.andEqualTo("options", para.getOptions());
            }
            // 排序
            if (!StringUtils.isEmpty(para.getSeq())) {
                criteria.andEqualTo("seq", para.getSeq());
            }
            // 模板ID
            if (!StringUtils.isEmpty(para.getTemplateId())) {
                criteria.andEqualTo("templateId", para.getTemplateId());
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
        // 根据 ID 查询
        Para para = paraMapper.selectByPrimaryKey(id);
        // 修改模板统计数据
        updateParaNum(para, -1);
        paraMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改 Para
     *
     * @param para
     */
    @Override
    public void update(Para para) {
        paraMapper.updateByPrimaryKey(para);
    }

    /**
     * 增加 Para
     *
     * @param para
     */
    @Override
    public void add(Para para) {
        paraMapper.insert(para);

        // 修改模板统计数据
        updateParaNum(para, 1);
    }

    /**
     * 根据 ID 查询 Para
     *
     * @param id
     * @return
     */
    @Override
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 Para 全部数据
     *
     * @return
     */
    @Override
    public List<Para> findAll() {
        return paraMapper.selectAll();
    }

    /**
     * 修改模板统计数据
     *
     * @param para  操作的参数
     * @param count 变更的数量
     */
    public void updateParaNum(Para para, int count) {
        //修改模板数量统计
        Template template = templateMapper.selectByPrimaryKey(para.getTemplateId());
        template.setParaNum(template.getParaNum() + count);
        templateMapper.updateByPrimaryKeySelective(template);
    }
}
