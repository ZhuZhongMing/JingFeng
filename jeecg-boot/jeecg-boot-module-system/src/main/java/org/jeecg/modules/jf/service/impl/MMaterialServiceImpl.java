package org.jeecg.modules.jf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MMaterial;
import org.jeecg.modules.jf.mapper.MMaterialMapper;
import org.jeecg.modules.jf.service.IMMaterialService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 物料信息
 * @Author: jeecg-boot
 * @Date:   2019-10-19
 * @Version: V1.0
 */
@Service
public class MMaterialServiceImpl extends ServiceImpl<MMaterialMapper, MMaterial> implements IMMaterialService {

    @Resource
    private MMaterialMapper mMaterialMapper;

    @Override
    public IPage<MMaterial> queryListPage(Page<MMaterial> page, QueryWrapper<MMaterial> queryWrapper) {
        return this.baseMapper.queryListPage(page,queryWrapper);
    }
}
