package org.jeecg.modules.jf.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.jf.entity.MOrderlist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface MOrderlistMapper extends BaseMapper<MOrderlist> {

	/*根据订单统计分配情况*/
	Integer sumAllotByid(String orderid);

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<MOrderlist> selectByMainId(@Param("mainId") String mainId);

	/*订单总数*/
	Integer getSumMaterial();
	/*今日订单总数*/
	Integer getSumMaterialBytime(String time);

	/**
	 * 根据订单号分页列表查询订单明细
	 */
	IPage<MOrderlist> queryPageOrderList(Page<MOrderlist> page, String orderid);

	/**
	 * 根据订单号查询订单明细
	 */
	List<MOrderlist> queryOrderListByMainId(String orderid);
}
