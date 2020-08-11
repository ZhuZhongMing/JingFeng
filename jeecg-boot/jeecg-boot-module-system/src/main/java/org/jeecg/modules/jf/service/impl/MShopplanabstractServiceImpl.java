package org.jeecg.modules.jf.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.jf.entity.MOrder;
import org.jeecg.modules.jf.entity.MOrderlist;
import org.jeecg.modules.jf.entity.MShopplanabstract;
import org.jeecg.modules.jf.entity.MShopplan;
import org.jeecg.modules.jf.mapper.MShopplanMapper;
import org.jeecg.modules.jf.mapper.MShopplanabstractMapper;
import org.jeecg.modules.jf.service.IMOrderService;
import org.jeecg.modules.jf.service.IMOrderlistService;
import org.jeecg.modules.jf.service.IMShopplanabstractService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 车间计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-11-19
 * @Version: V1.0
 */
@Service
@Slf4j
public class MShopplanabstractServiceImpl extends ServiceImpl<MShopplanabstractMapper, MShopplanabstract> implements IMShopplanabstractService {

	@Autowired
	private IMOrderlistService iMOrderlistService;
	@Autowired
	private IMOrderService iMOrderService;

	/*@Autowired
	private  IMShopplanabstractService iMShopplanabstractService;*/

	@Resource
	private MShopplanabstractMapper mShopplanabstractMapper;
	@Resource
	private MShopplanMapper mShopplanMapper;

	
	@Override
	@Transactional
	public void saveMain(MShopplanabstract mShopplanabstract, List<MShopplan> mShopplanList) {
		mShopplanabstractMapper.insert(mShopplanabstract);
		if(mShopplanList!=null && mShopplanList.size()>0) {
			for(MShopplan entity:mShopplanList) {
				//外键设置
				entity.setShopplanid(mShopplanabstract.getId());

				mShopplanMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(MShopplanabstract mShopplanabstract,List<MShopplan> mShopplanList) {
		mShopplanabstractMapper.updateById(mShopplanabstract);
		
		//1.先删除子表数据
		mShopplanMapper.deleteByMainId(mShopplanabstract.getId());
		
		//2.子表数据重新插入
		if(mShopplanList!=null && mShopplanList.size()>0) {
			for(MShopplan entity:mShopplanList) {
				//外键设置
				entity.setShopplanid(mShopplanabstract.getId());
				mShopplanMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {

		/*删除明细时，将订单改变*/

		//MShopplanabstract mShopplanabstract = mShopplanabstractMapper.selectOne(new QueryWrapper<MShopplanabstract>().eq("id",id));
		MShopplanabstract mShopplanabstract = mShopplanabstractMapper.getOneById(id);

		//log.info("计划id："+id);
		//log.info("计划信息："+mShopplanabstract);
		try {
			if (mShopplanabstract.getOrderid() != null && mShopplanabstract.getOrderid() != "") {
				List<MShopplan> list = mShopplanMapper.selectByMainId(id);
				for (MShopplan n:list) {
					MOrderlist mOrderlist = iMOrderlistService.getById(n.getOrderid());
					//log.info("订单信息："+mOrderlist);
					if (n.getNumber() > mOrderlist.getAllotednumber()) {
						/*计划数量比订单已分配数量多    ??????*/
						mOrderlist.setAllotnumber(mOrderlist.getOrdernum());
						mOrderlist.setAllotednumber(0);
						/*mOrderlist.setAllotnumber(n.getNumber() + mOrderlist.getAllotnumber());
						mOrderlist.setAllotednumber(mOrderlist.getAllotednumber() - n.getNumber());*/
					} else if (n.getNumber() < mOrderlist.getAllotednumber()) {
						/*计划数量比订单已分配数量少*/
						mOrderlist.setAllotnumber(n.getNumber() + mOrderlist.getAllotnumber());
						mOrderlist.setAllotednumber(mOrderlist.getAllotednumber() - n.getNumber());
					} else {
						/*相等*/
						mOrderlist.setAllotnumber(n.getNumber() + mOrderlist.getAllotnumber());
						mOrderlist.setAllotednumber(mOrderlist.getAllotednumber() - n.getNumber());
					}
					iMOrderlistService.updateById(mOrderlist);
					/*判断订单状态*/
					Integer sum = iMOrderlistService.sumAllotByid(mOrderlist.getOrderid());
					if (sum <= 0) {
						MOrder mOrder = new MOrder();
						mOrder.setId(mOrderlist.getOrderid());
						mOrder.setOrderstate("1");
						iMOrderService.updateById(mOrder);
					} else {
						MOrder mOrder = new MOrder();
						mOrder.setId(mOrderlist.getOrderid());
						mOrder.setOrderstate("0");
						iMOrderService.updateById(mOrder);
					}

					/*删除*/
					mShopplanMapper.deleteByMainId(id);
					mShopplanabstractMapper.deleteById(id);
				}
			} else {
				mShopplanabstractMapper.deleteById(id);
			}
		} catch (Exception e) {
			log.info("删除计划时没查到订单信息");
		}

	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			mShopplanMapper.deleteByMainId(id.toString());
			mShopplanabstractMapper.deleteById(id);
		}
	}

	@Override
	public MShopplanabstract findMaxID() {
		return mShopplanabstractMapper.findMaxID();
	}

}
