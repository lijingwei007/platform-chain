package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordService {
    /**
     * 矿量查询
     *
     * @param userId
     * @return
     */
    BigDecimal queryOreNumbByUserId(Integer userId);

    /**
     * 矿石纪录分页查询
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<OreRecordDTO> queryOreRecordByUserId(Integer userId, int pageNum, int pageSize);
}
