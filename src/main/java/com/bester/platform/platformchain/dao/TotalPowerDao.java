package com.bester.platform.platformchain.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface TotalPowerDao {
    // 在这里新增接口

    /**
     * 添加当天所有用户总算力
     *
     * @param day
     * @param totalPower
     * @return
     */
    int insertTotalPower(@Param("day") String day, @Param("totalPower") int totalPower);
}
