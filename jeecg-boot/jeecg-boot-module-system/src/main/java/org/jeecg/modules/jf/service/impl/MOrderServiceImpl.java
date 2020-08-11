package org.jeecg.modules.jf.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.jf.entity.MOrder;
import org.jeecg.modules.jf.entity.MOrderlist;
import org.jeecg.modules.jf.mapper.MOrderlistMapper;
import org.jeecg.modules.jf.mapper.MOrderMapper;
import org.jeecg.modules.jf.service.IMOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 销售订单
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Service
@Slf4j
public class MOrderServiceImpl extends ServiceImpl<MOrderMapper, MOrder> implements IMOrderService {

	@Resource
	private MOrderMapper mOrderMapper;
	@Resource
	private MOrderlistMapper mOrderlistMapper;

	@Override
	public MOrder findMaxID() {
		return mOrderMapper.findMaxID();
	}

	@Override
	@Transactional
	public void saveMain(MOrder mOrder, List<MOrderlist> mOrderlistList) {
		mOrderMapper.insert(mOrder);
		log.info("morder:"+mOrder);
		if(mOrderlistList!=null && mOrderlistList.size()>0) {
			for(MOrderlist entity:mOrderlistList) {
				log.info("entity:"+entity);
				//外键设置
				entity.setOrderid(mOrder.getId());
				mOrderlistMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(MOrder mOrder,List<MOrderlist> mOrderlistList) {
		mOrderMapper.updateById(mOrder);
		
		//1.先删除子表数据
		mOrderlistMapper.deleteByMainId(mOrder.getId());
		
		//2.子表数据重新插入
		if(mOrderlistList!=null && mOrderlistList.size()>0) {
			for(MOrderlist entity:mOrderlistList) {
				//外键设置
				entity.setOrderid(mOrder.getId());
				mOrderlistMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		mOrderlistMapper.deleteByMainId(id);
		mOrderMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			mOrderlistMapper.deleteByMainId(id.toString());
			mOrderMapper.deleteById(id);
		}
	}

	@Override
	public MOrder getOneById(String id) {
		return mOrderMapper.getOneById(id);
	}


}
