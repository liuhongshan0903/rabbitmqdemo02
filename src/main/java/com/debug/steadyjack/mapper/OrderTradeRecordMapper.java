package com.debug.steadyjack.mapper;


import com.debug.steadyjack.entity.OrderTradeRecord;

public interface OrderTradeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderTradeRecord record);

    int insertSelective(OrderTradeRecord record);

    OrderTradeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderTradeRecord record);

    int updateByPrimaryKey(OrderTradeRecord record);
}