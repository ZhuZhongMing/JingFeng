package org.jeecg.modules.jf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MMaterial;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 物料信息
 * @Author: jeecg-boot
 * @Date:   2019-10-19
 * @Version: V1.0
 */
public interface IMMaterialService extends IService<MMaterial> {

    /*分页列表查询*/
    IPage<MMaterial> queryListPage(Page<MMaterial> page, QueryWrapper<MMaterial> queryWrapper);

}
