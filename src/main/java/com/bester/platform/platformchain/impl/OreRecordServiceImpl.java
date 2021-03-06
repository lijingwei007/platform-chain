package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.constant.OreRecordStatus;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.bester.platform.platformchain.entity.OreRecordEntity;
import com.bester.platform.platformchain.service.OreRecordService;
import com.bester.platform.platformchain.util.BeansListUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
@Service
public class OreRecordServiceImpl implements OreRecordService {
    @Resource
    private OreRecordDao oreRecordDao;

    @Override
    public BigDecimal queryOreNumbByUserId(Integer userId) {
        return oreRecordDao.queryOreNumbByUserId(userId);
    }

    @Override
    public PageInfo<OreRecordDTO> queryOreRecordByUserId(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OreRecordEntity> oreRecordEntities = oreRecordDao.queryAllOreRecordByUserId(userId, OreRecordStatus.RECEIVED);
        return BeansListUtils.copyListPageInfo(oreRecordEntities, OreRecordDTO.class);
    }

    @Override
    public Integer receiveOre(Integer id) {
        return oreRecordDao.receiveOre(id);
    }

    @Override
    public OreRecordDTO showOreById(Integer id) {
        OreRecordEntity oreRecordEntity = oreRecordDao.showOreById(id);
        if (oreRecordEntity == null) {
            return null;
        }
        OreRecordDTO oreRecordDTO = new OreRecordDTO();
        BeanUtils.copyProperties(oreRecordEntity, oreRecordDTO);
        return oreRecordDTO;
    }

    @Override
    public List<OreRecordDTO> showOreByUserId(Integer userId) {
        List<OreRecordEntity> oreRecordEntities = oreRecordDao.queryAllOreRecordByUserId(userId, OreRecordStatus.PENDING);
        if (CollectionUtils.isEmpty(oreRecordEntities)) {
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(oreRecordEntities, OreRecordDTO.class);
    }
}
