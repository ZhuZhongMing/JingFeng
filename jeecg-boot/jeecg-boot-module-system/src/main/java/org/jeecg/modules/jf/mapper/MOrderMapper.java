package org.jeecg.modules.jf.mapper;

import org.jeecg.modules.jf.entity.MOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 销售订单
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface MOrderMapper extends BaseMapper<MOrder> {

    /*查询 最大id*/
    public MOrder findMaxID();

    /*根据id查新订单摘要*/
    MOrder getOneById(String id);

}
