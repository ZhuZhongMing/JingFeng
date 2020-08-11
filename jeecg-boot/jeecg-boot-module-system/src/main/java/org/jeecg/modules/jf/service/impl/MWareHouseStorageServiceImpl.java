package org.jeecg.modules.jf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MWareHouseStorage;
import org.jeecg.modules.jf.mapper.MWareHouseStorageMapper;
import org.jeecg.modules.jf.service.IMWareHouseStorageService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 库存信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Service
public class MWareHouseStorageServiceImpl extends ServiceImpl<MWareHouseStorageMapper, MWareHouseStorage> implements IMWareHouseStorageService {
    @Resource
    private MWareHouseStorageMapper mWareHouseStorageMapper;

    @Override
    public IPage<MWareHouseStorage> List(Page<MWareHouseStorage> page, QueryWrapper<MWareHouseStorage> queryWrapper) {
        return this.baseMapper.List(page,queryWrapper);
    }
}
