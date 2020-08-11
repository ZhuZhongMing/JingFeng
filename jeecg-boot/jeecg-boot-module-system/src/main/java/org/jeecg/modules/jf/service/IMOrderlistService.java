package org.jeecg.modules.jf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MOrderlist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface IMOrderlistService extends IService<MOrderlist> {


	/*根据订单统计分配情况*/
	Integer sumAllotByid(String orderid);

	public List<MOrderlist> selectByMainId(String mainId);

	/*订单总数*/
	Integer getSumMaterial();
	/*今日订单总数*/
	Integer getSumMaterial(String time);

	/**
	 * 根据订单号分页列表查询订单明细
	 */
	IPage<MOrderlist> queryPageOrderList(Page<MOrderlist> page, String orderid);

	/**
	 * 根据订单号查询订单明细
	 */
	List<MOrderlist> queryOrderListByMainId(String orderid);
}
