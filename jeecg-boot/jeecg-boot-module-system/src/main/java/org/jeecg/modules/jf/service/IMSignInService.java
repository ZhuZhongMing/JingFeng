package org.jeecg.modules.jf.service;

import org.jeecg.modules.jf.entity.MSignIn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 签到记录
 * @Author: jeecg-boot
 * @Date:   2020-01-09
 * @Version: V1.0
 */
public interface IMSignInService extends IService<MSignIn> {

    /*根据签到记录编号批量查询设备名称*/
    Map<String, String> getEquipNamesByIds(List<String> ids);
}
