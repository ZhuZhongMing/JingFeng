package org.jeecg.modules.jf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MShopplan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 车间计划
 * @Author: jeecg-boot
 * @Date:   2019-11-11
 * @Version: V1.0
 */
public interface IMShopplanService extends IService<MShopplan> {

    /*分页查询*/
    IPage<MShopplan> getListPage(Page<MShopplan> page, QueryWrapper<MShopplan> queryWrapper);

    public List<MShopplan> selectByMainId(String mainId);

    /*根据车间计划编号分页列表查询车间计划明细*/
    IPage<MShopplan> queryPageShopplanList(Page<MShopplan> page, String shopplanid);
}
