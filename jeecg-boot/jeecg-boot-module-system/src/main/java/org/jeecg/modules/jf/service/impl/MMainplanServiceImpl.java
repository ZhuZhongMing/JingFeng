package org.jeecg.modules.jf.service.impl;

import org.jeecg.modules.jf.entity.MMainplan;
import org.jeecg.modules.jf.mapper.MMainplanMapper;
import org.jeecg.modules.jf.service.IMMainplanService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 计划详情
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Service
public class MMainplanServiceImpl extends ServiceImpl<MMainplanMapper, MMainplan> implements IMMainplanService {
	
	@Autowired
	private MMainplanMapper mMainplanMapper;
	
	@Override
	public List<MMainplan> selectByMainId(String mainId) {
		return mMainplanMapper.selectByMainId(mainId);
	}
}
