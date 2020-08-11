package org.jeecg.modules.jf.service;

import org.jeecg.modules.jf.entity.MShopplan;
import org.jeecg.modules.jf.entity.MShopplanabstract;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 车间计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-11-19
 * @Version: V1.0
 */

public interface IMShopplanabstractService extends IService<MShopplanabstract> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(MShopplanabstract mShopplanabstract, List<MShopplan> mShopplanList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MShopplanabstract mShopplanabstract, List<MShopplan> mShopplanList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);

	/*查找最新插入的记录*/
    MShopplanabstract findMaxID();
}
