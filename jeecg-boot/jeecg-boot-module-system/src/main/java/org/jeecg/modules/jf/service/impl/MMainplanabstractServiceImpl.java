package org.jeecg.modules.jf.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MMainplanabstract;
import org.jeecg.modules.jf.entity.MMainplan;
import org.jeecg.modules.jf.mapper.MMainplanMapper;
import org.jeecg.modules.jf.mapper.MMainplanabstractMapper;
import org.jeecg.modules.jf.service.IMMainplanabstractService;
import org.jeecg.modules.jf.vo.MMyPlanPage;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Service
public class MMainplanabstractServiceImpl extends ServiceImpl<MMainplanabstractMapper, MMainplanabstract> implements IMMainplanabstractService {

	@Autowired
	private MMainplanabstractMapper mMainplanabstractMapper;
	@Autowired
	private MMainplanMapper mMainplanMapper;

	@Override
	public MMainplanabstract findMaxID() {
		return mMainplanabstractMapper.findMaxID();
	}

	@Override
	@Transactional
	public void saveMain(MMainplanabstract mMainplanabstract, List<MMainplan> mMainplanList) {
		mMainplanabstractMapper.insert(mMainplanabstract);
		if(mMainplanList!=null && mMainplanList.size()>0) {
			for(MMainplan entity:mMainplanList) {
				//外键设置
				entity.setMainplanabstractid(mMainplanabstract.getId());
				mMainplanMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(MMainplanabstract mMainplanabstract,List<MMainplan> mMainplanList) {
		mMainplanabstractMapper.updateById(mMainplanabstract);
		
		//1.先删除子表数据
		mMainplanMapper.deleteByMainId(mMainplanabstract.getId());
		
		//2.子表数据重新插入
		if(mMainplanList!=null && mMainplanList.size()>0) {
			for(MMainplan entity:mMainplanList) {
				//外键设置
				entity.setMainplanabstractid(mMainplanabstract.getId());
				mMainplanMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		mMainplanMapper.deleteByMainId(id);
		mMainplanabstractMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			mMainplanMapper.deleteByMainId(id.toString());
			mMainplanabstractMapper.deleteById(id);
		}
	}

	@Override
	public IPage<MMyPlanPage> getList(Page<MMyPlanPage> page,String id) {
		return this.baseMapper.getList(page, id);
	}

	@Override
	public MMyPlanPage getByMainId(String id) {
		return mMainplanabstractMapper.getByMainId(id);
	}

	@Override
	public List<MMyPlanPage> countByid(String id) {
		return mMainplanabstractMapper.countByid(id);
	}

}
