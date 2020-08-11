package org.jeecg.modules.jf.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.jf.entity.MShopplan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 车间计划
 * @Author: jeecg-boot
 * @Date:   2019-11-11
 * @Version: V1.0
 */
public interface MShopplanMapper extends BaseMapper<MShopplan> {

    /*分页查询*/
    IPage<MShopplan> getListPage(Page<MShopplan> page, QueryWrapper<MShopplan> queryWrapper);

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<MShopplan> selectByMainId(@Param("mainId") String mainId);

    /*根据车间计划编号分页列表查询车间计划明细*/
    IPage<MShopplan> queryPageShopplanList(Page<MShopplan> page, String shopplanid);

}
