package org.jeecg.modules.jf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MProductMap;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 产品信息
 * @Author: jeecg-boot
 * @Date:   2019-12-09
 * @Version: V1.0
 */
public interface IMProductMapService extends IService<MProductMap> {

    /*分页查询*/
    Page<MProductMap> pageList(Page<MProductMap> page, QueryWrapper<MProductMap> queryWrapper);
}
