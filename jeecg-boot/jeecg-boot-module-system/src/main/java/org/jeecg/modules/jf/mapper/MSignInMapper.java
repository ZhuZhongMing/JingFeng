package org.jeecg.modules.jf.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import org.jeecg.modules.jf.entity.MSignIn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: 签到记录
 * @Author: jeecg-boot
 * @Date:   2020-01-09
 * @Version: V1.0
 */
public interface MSignInMapper extends BaseMapper<MSignIn> {

    /*根据签到记录编号批量查询设备名称*/
    List<MSignIn> getEquipNamesByIds(@Param("idList")List<String> idList);

}
