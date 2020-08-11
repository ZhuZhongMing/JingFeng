package org.jeecg.modules.jf.service;

import org.jeecg.modules.jf.entity.MOrderlist;
import org.jeecg.modules.jf.entity.MOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 销售订单
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface IMOrderService extends IService<MOrder> {

	/*查询 最大id*/
	public MOrder findMaxID();

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(MOrder mOrder, List<MOrderlist> mOrderlistList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MOrder mOrder, List<MOrderlist> mOrderlistList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);


	/*根据id查新订单摘要*/
    MOrder getOneById(String id);
}
