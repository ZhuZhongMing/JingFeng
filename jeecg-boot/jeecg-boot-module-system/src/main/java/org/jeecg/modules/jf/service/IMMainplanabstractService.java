package org.jeecg.modules.jf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MMainplan;
import org.jeecg.modules.jf.entity.MMainplanabstract;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.jf.vo.MMyPlanPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface IMMainplanabstractService extends IService<MMainplanabstract> {

	/*查询 最大id*/
	public MMainplanabstract findMaxID();

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(MMainplanabstract mMainplanabstract, List<MMainplan> mMainplanList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MMainplanabstract mMainplanabstract, List<MMainplan> mMainplanList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);


	/*分页查询*/
	IPage<MMyPlanPage> getList(Page<MMyPlanPage> page,String id);

	/**
	 * 查看主计划详情
	 */
    MMyPlanPage getByMainId(String id);

	/**
	 * 查询个人等待确认计划
	 */
    List<MMyPlanPage> countByid(String id);
}
