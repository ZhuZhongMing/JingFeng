package org.jeecg.modules.jf.mapper;

import org.jeecg.modules.jf.entity.MShopplanabstract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 车间计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-11-19
 * @Version: V1.0
 */
@Repository
public interface MShopplanabstractMapper extends BaseMapper<MShopplanabstract> {

    /*查找最新插入的记录*/
    MShopplanabstract findMaxID();

    /*根据id查询一个*/
    MShopplanabstract getOneById(String id);

}
