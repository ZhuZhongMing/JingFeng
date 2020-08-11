package org.jeecg.modules.jf.service.impl;

import org.jeecg.modules.jf.entity.RAcquisitionnodeA;
import org.jeecg.modules.jf.mapper.RAcquisitionnodeAMapper;
import org.jeecg.modules.jf.service.IRAcquisitionnodeAService;
import org.jeecg.modules.jf.vo.NodeVO;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 节点信息
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Service
public class RAcquisitionnodeAServiceImpl extends ServiceImpl<RAcquisitionnodeAMapper, RAcquisitionnodeA> implements IRAcquisitionnodeAService {
    @Resource
    private RAcquisitionnodeAMapper mapper;

    /*@Override
    public List<NodeVO> queryNode(String id,String time) {
        return mapper.queryNode(id,time);
    }*/

    @Override
    public Integer sumRdata(String time) {
        return mapper.sumRdata(time);
    }

    @Override
    public Integer sumRdata() {
        return mapper.getsumRdata();
    }

    @Override
    public List<NodeVO> getnode(String time) {
        return mapper.gettodaynode(time);
    }

    /*@Override
    public List<NodeVO> getnode() {
        return mapper.getsumnode();
    }*/

    @Override
    public Integer sumData(RAcquisitionnodeA s) {
        return mapper.sumData(s);
    }
}
