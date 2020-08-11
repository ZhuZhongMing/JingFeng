package org.jeecg.modules.jf.mapper;

import java.util.List;

import org.jeecg.modules.jf.entity.RAcquisitionnodeA;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.jf.vo.NodeVO;

/**
 * @Description: 节点信息
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface RAcquisitionnodeAMapper extends BaseMapper<RAcquisitionnodeA> {

    /*统计时间段内生产数量*/
    Integer sumData(RAcquisitionnodeA s);

    /*查询节点实时数据*/
    /*List<NodeVO> queryNode(String id,String time);*/

    /*今日动作总数*/
    Integer sumRdata(String time);
    /*设备动作总数*/
    Integer getsumRdata();

    /*今日加工总数*/
    List<NodeVO> gettodaynode(String time);

    /*加工总数*/
   /* List<NodeVO> getsumnode();*/
}

