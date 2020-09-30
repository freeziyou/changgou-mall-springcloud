package com.changgou.user.service;

import com.changgou.user.pojo.UndoLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
public interface UndoLogService {

    /**
     * UndoLog 多条件分页查询
     * @param undoLog
     * @param page
     * @param size
     * @return
     */
    PageInfo<UndoLog> findPage(UndoLog undoLog, int page, int size);

    /**
     * UndoLog 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<UndoLog> findPage(int page, int size);

    /**
     * UndoLog 多条件搜索方法
     * @param undoLog
     * @return
     */
    List<UndoLog> findList(UndoLog undoLog);

    /**
     * 删除 UndoLog
     * @param id
     */
    void delete(Long id);

    /**
     * 修改 UndoLog 数据
     * @param undoLog
     */
    void update(UndoLog undoLog);

    /**
     * 新增 UndoLog
     * @param undoLog
     */
    void add(UndoLog undoLog);

    /**
     * 根据 ID 查询 UndoLog
     * @param id
     * @return
     */
     UndoLog findById(Long id);

    /**
     * 查询所有 UndoLog
     * @return
     */
    List<UndoLog> findAll();
}
