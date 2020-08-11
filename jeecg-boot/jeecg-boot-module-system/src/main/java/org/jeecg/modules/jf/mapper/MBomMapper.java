package org.jeecg.modules.jf.mapper;

import java.util.List;

import org.jeecg.modules.jf.entity.MBom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 物料BOM
 * @Author: jeecg-boot
 * @Date:   2019-11-10
 * @Version: V1.0
 */
public interface MBomMapper extends BaseMapper<MBom> {

    /*列表查询*/
    List<MBom> List(String taskid);

}
