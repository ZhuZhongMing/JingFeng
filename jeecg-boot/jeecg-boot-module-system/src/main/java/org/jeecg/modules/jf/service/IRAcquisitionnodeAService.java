package org.jeecg.modules.jf.service;

import org.jeecg.modules.jf.entity.RAcquisitionnodeA;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.jf.vo.NodeVO;

import java.util.List;

/**
 * @Description: 节点信息
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
public interface IRAcquisitionnodeAService extends IService<RAcquisitionnodeA> {

    /*查询节点实时数据*/
    /*List<NodeVO> queryNode(String id,String time);*/

    /*今日动作总数*/
    Integer sumRdata(String time);
    /*设备动作总数*/
    Integer sumRdata();

    /*今日加工总数*/
    List<NodeVO> getnode(String time);

    /*加工总数*/
    /*List<NodeVO> getnode();*/

    /*统计时间段内生产数量*/
    Integer sumData(RAcquisitionnodeA s);
}
