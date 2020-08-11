package org.jeecg.modules.jf.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MOrderlist;
import org.jeecg.modules.jf.mapper.MOrderlistMapper;
import org.jeecg.modules.jf.service.IMOrderlistService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Service
public class MOrderlistServiceImpl extends ServiceImpl<MOrderlistMapper, MOrderlist> implements IMOrderlistService {
	
	@Resource
	private MOrderlistMapper mOrderlistMapper;

	@Override
	public Integer sumAllotByid(String orderid) {
		return mOrderlistMapper.sumAllotByid(orderid);
	}

	@Override
	public List<MOrderlist> selectByMainId(String mainId) {
		return mOrderlistMapper.selectByMainId(mainId);
	}

	@Override
	public Integer getSumMaterial() {
		return mOrderlistMapper.getSumMaterial();
	}

	@Override
	public Integer getSumMaterial(String time) {
		return mOrderlistMapper.getSumMaterialBytime(time);
	}

	@Override
	public IPage<MOrderlist> queryPageOrderList(Page<MOrderlist> page, String orderid) {
		return this.baseMapper.queryPageOrderList(page,orderid);
	}

	@Override
	public List<MOrderlist> queryOrderListByMainId(String orderid) {
		return mOrderlistMapper.queryOrderListByMainId(orderid);
	}
}
