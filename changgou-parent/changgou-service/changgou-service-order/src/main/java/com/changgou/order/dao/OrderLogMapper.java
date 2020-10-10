package com.changgou.order.dao;

import com.changgou.order.pojo.OrderLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Repository
public interface OrderLogMapper extends Mapper<OrderLog> {
}
