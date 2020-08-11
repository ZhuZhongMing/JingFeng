package org.jeecg.modules.jf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MProductMap;
import org.jeecg.modules.jf.mapper.MProductMapMapper;
import org.jeecg.modules.jf.service.IMProductMapService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 产品信息
 * @Author: jeecg-boot
 * @Date:   2019-12-09
 * @Version: V1.0
 */
@Service
public class MProductMapServiceImpl extends ServiceImpl<MProductMapMapper, MProductMap> implements IMProductMapService {

    @Resource
    private MProductMapMapper mapper;

    @Override
    public Page<MProductMap> pageList(Page<MProductMap> page, QueryWrapper<MProductMap> queryWrapper) {
        return this.baseMapper.pageList(page,queryWrapper);
    }
}
