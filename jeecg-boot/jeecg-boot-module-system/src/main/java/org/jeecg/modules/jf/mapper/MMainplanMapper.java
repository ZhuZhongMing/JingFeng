package org.jeecg.modules.jf.mapper;

import java.util.List;
import org.jeecg.modules.jf.entity.MMainplan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 计划详情
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface MMainplanMapper extends BaseMapper<MMainplan> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<MMainplan> selectByMainId(@Param("mainId") String mainId);
}
