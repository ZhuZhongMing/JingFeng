package org.jeecg.modules.jf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.jf.entity.MProductTaskOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 生产任务单
 * @Author: jeecg-boot
 * @Date:   2019-11-08
 * @Version: V1.0
 */
public interface MProductTaskOrderMapper extends BaseMapper<MProductTaskOrder> {

    /*分页查询*/
    IPage<MProductTaskOrder> queryList(Page<MProductTaskOrder> page,@Param(Constants.WRAPPER)  QueryWrapper<MProductTaskOrder> queryWrapper);

    /*
     *查找最新添加的一项
     */
    MProductTaskOrder findMax();

}
