package org.jeecg.modules.jf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MProductTaskOrder;
import org.jeecg.modules.jf.mapper.MProductTaskOrderMapper;
import org.jeecg.modules.jf.service.IMProductTaskOrderService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 生产任务单
 * @Author: jeecg-boot
 * @Date:   2019-11-08
 * @Version: V1.0
 */
@Service
public class MProductTaskOrderServiceImpl extends ServiceImpl<MProductTaskOrderMapper, MProductTaskOrder> implements IMProductTaskOrderService {

    @Resource
    private MProductTaskOrderMapper mProductTaskOrderMapper;

    @Override
    public IPage<MProductTaskOrder> queryList(Page<MProductTaskOrder> page, QueryWrapper<MProductTaskOrder> queryWrapper) {
        return this.baseMapper.queryList(page,queryWrapper);
    }

    @Override
    public MProductTaskOrder findMax() {
        return mProductTaskOrderMapper.findMax();
    }
}
