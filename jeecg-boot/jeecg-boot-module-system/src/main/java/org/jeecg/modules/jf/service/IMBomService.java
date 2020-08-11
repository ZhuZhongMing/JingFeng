package org.jeecg.modules.jf.service;

import org.jeecg.modules.jf.entity.MBom;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 物料BOM
 * @Author: jeecg-boot
 * @Date:   2019-11-10
 * @Version: V1.0
 */
public interface IMBomService extends IService<MBom> {

    /*列表查询*/
    List<MBom> List(String taskid);
}
