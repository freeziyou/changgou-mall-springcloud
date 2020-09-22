package com.changgou.content.service;
import com.changgou.content.pojo.ContentCategory;
import com.github.pagehelper.PageInfo;
import java.util.List;
/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface ContentCategoryService {

    /**
     * ContentCategory 多条件分页查询
     * @param contentCategory
     * @param page
     * @param size
     * @return
     */
    PageInfo<ContentCategory> findPage(ContentCategory contentCategory, int page, int size);

    /**
     * ContentCategory 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<ContentCategory> findPage(int page, int size);

    /**
     * ContentCategory 多条件搜索方法
     * @param contentCategory
     * @return
     */
    List<ContentCategory> findList(ContentCategory contentCategory);

    /**
     * 删除 ContentCategory
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 ContentCategory 数据
     * @param contentCategory
     */
    void update(ContentCategory contentCategory);

    /**
     * 新增 ContentCategory
     * @param contentCategory
     */
    void add(ContentCategory contentCategory);

    /**
     * 根据 ID 查询 ContentCategory
     * @param id
     * @return
     */
     ContentCategory findById(Long id);

    /**
     * 查询所有 ContentCategory
     * @return
     */
    List<ContentCategory> findAll();
}
