package org.jeecg.modules.jf.service.impl;

import org.jeecg.modules.jf.entity.MSignIn;
import org.jeecg.modules.jf.mapper.MSignInMapper;
import org.jeecg.modules.jf.service.IMSignInService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 签到记录
 * @Author: jeecg-boot
 * @Date:   2020-01-09
 * @Version: V1.0
 */
@Service
public class MSignInServiceImpl extends ServiceImpl<MSignInMapper, MSignIn> implements IMSignInService {
    @Resource
    private  MSignInMapper mSignInMapper;

    @Override
    public Map<String, String> getEquipNamesByIds(List<String> ids) {
        List<MSignIn> list = this.baseMapper.getEquipNamesByIds(ids);
        Map<String, String> res = new HashMap<String, String>();
        list.forEach(item -> {
                    if (res.get(item.getId()) == null) {
                        res.put(item.getId(), item.getEquipname());
                    }
                }
        );
        return res;
    }
}
