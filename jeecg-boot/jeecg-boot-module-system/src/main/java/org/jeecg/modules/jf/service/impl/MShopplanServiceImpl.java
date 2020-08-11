package org.jeecg.modules.jf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MShopplan;
import org.jeecg.modules.jf.mapper.MShopplanMapper;
import org.jeecg.modules.jf.service.IMShopplanService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 车间计划
 * @Author: jeecg-boot
 * @Date:   2019-11-11
 * @Version: V1.0
 */
@Service
public class MShopplanServiceImpl extends ServiceImpl<MShopplanMapper, MShopplan> implements IMShopplanService {

    @Resource
    private MShopplanMapper mShopplanMapper;

    @Override
    public IPage<MShopplan> getListPage(Page<MShopplan> page, QueryWrapper<MShopplan> queryWrapper) {
        return this.baseMapper.getListPage(page,queryWrapper);
    }

    @Override
    public List<MShopplan> selectByMainId(String mainId) {
        return mShopplanMapper.selectByMainId(mainId);
    }

    @Override
    public IPage<MShopplan> queryPageShopplanList(Page<MShopplan> page, String shopplanid) {
        return this.baseMapper.queryPageShopplanList(page,shopplanid);
    }
}
