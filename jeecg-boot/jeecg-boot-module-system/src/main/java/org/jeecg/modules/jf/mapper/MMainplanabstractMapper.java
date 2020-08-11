package org.jeecg.modules.jf.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MMainplanabstract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.jf.vo.MMyPlanPage;

/**
 * @Description: 计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface MMainplanabstractMapper extends BaseMapper<MMainplanabstract> {

    /*查询 最大id*/
    public MMainplanabstract findMaxID();

    /*分页查询*/
    IPage<MMyPlanPage> getList(Page<MMyPlanPage> page,String id);

    /**
     * 查看主计划详情
     */
    MMyPlanPage getByMainId(String id);

    /**
     * 查询个人等待确认计划
     */
    List<MMyPlanPage> countByid(String id);

}
