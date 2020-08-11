package org.jeecg.modules.jf.service.impl;

import org.jeecg.modules.jf.entity.MBom;
import org.jeecg.modules.jf.mapper.MBomMapper;
import org.jeecg.modules.jf.service.IMBomService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 物料BOM
 * @Author: jeecg-boot
 * @Date:   2019-11-10
 * @Version: V1.0
 */
@Service
public class MBomServiceImpl extends ServiceImpl<MBomMapper, MBom> implements IMBomService {

    @Resource
    private MBomMapper mapper;

    @Override
    public List<MBom> List(String taskid) {
        return this.mapper.List(taskid);
    }
}
