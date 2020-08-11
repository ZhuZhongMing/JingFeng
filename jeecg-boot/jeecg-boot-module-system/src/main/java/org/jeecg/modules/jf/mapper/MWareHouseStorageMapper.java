package org.jeecg.modules.jf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.jf.entity.MWareHouseStorage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 库存信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
public interface MWareHouseStorageMapper extends BaseMapper<MWareHouseStorage> {

    /*分页查询*/
    IPage<MWareHouseStorage> List(Page<MWareHouseStorage> page, @Param("ew") QueryWrapper<MWareHouseStorage> queryWrapper);



}
