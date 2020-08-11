package org.jeecg.modules.jf.service;

import org.jeecg.modules.jf.entity.MMainplan;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 计划详情
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface IMMainplanService extends IService<MMainplan> {

	public List<MMainplan> selectByMainId(String mainId);
}
