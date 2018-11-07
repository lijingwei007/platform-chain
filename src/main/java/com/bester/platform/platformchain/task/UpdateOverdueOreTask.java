package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.constant.ListLimitNumber;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.util.ListAverageAssignUtil;
import com.bester.platform.platformchain.util.TemporaryPowerUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanrui
 */
@Component
public class UpdateOverdueOreTask {

    @Resource
    private OreRecordDao oreRecordDao;

    @Scheduled(cron = BlockChainParameters.OVERDUE_INTERVAL)
    public void updateOverdueOre() {
        List<Integer> userIdLists = oreRecordDao.selectMaxUpdateTime(TemporaryPowerUtil.overdueOreTime());
        List<List<Integer>> userIdAverageAssignList = ListAverageAssignUtil.dealBySubList(userIdLists,ListLimitNumber.LIST_LIMIT);
        if (userIdLists.size() >= ListLimitNumber.LIST_LIMIT) {
            userIdAverageAssignList.forEach(userIdList -> {
                oreRecordDao.updateOverduePower(userIdList);
            });
        } else {
            oreRecordDao.updateOverduePower(userIdLists);
        }
    }
}
