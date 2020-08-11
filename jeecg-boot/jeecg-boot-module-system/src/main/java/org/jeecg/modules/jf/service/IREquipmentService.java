package org.jeecg.modules.jf.service;

import org.jeecg.modules.jf.entity.REquipment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
public interface IREquipmentService extends IService<REquipment> {

    /* 根据设备编号查询所在车间 */
    Map<String, String> getWSNamesByIds(List<String> ids);

    /*根据设备编号统计今日设备产能*/
    BigDecimal sumByDPD(REquipment item);

    /*根据检测设备编号统计今日不合格数量*/
    BigDecimal sumUnqualifiedByCPD(REquipment item);
}
