package org.jeecg.modules.jf.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import org.jeecg.modules.jf.entity.REquipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
public interface REquipmentMapper extends BaseMapper<REquipment> {

    /* 根据设备编号查询所在车间 */
    List<REquipment> getWSNamesByIds(@Param("idList")List<String> idList);

    /*根据设备编号统计今日设备产能*/
    BigDecimal sumByDPD(@Param("item")REquipment item);

    /*根据检测设备编号统计今日不合格数量*/
    BigDecimal sumUnqualifiedByCPD(@Param("item")REquipment item);

}
