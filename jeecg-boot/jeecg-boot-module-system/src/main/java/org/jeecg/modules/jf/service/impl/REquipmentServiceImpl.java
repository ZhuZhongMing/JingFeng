package org.jeecg.modules.jf.service.impl;

import org.jeecg.modules.jf.entity.REquipment;
import org.jeecg.modules.jf.mapper.REquipmentMapper;
import org.jeecg.modules.jf.service.IREquipmentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Service
public class REquipmentServiceImpl extends ServiceImpl<REquipmentMapper, REquipment> implements IREquipmentService {
    @Resource
    private REquipmentMapper rEquipmentMapper;

    @Override
    public Map<String, String> getWSNamesByIds(List<String> ids) {
        List<REquipment> list = this.baseMapper.getWSNamesByIds(ids);

        Map<String, String> res = new HashMap<String, String>();
        list.forEach(item -> {
                    if (res.get(item.getId()) == null) {
                        res.put(item.getId(), item.getWsname());
                    }
                }
        );
        return res;
    }

    @Override
    public BigDecimal sumByDPD(REquipment item) {
        return this.rEquipmentMapper.sumByDPD(item);
    }

    @Override
    public BigDecimal sumUnqualifiedByCPD(REquipment item) {
        return this.rEquipmentMapper.sumUnqualifiedByCPD(item);
    }
}
