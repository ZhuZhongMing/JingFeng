package org.jeecg.modules.jf.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Data
public class NodeVO {

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**车间编号*/
	@Excel(name = "车间编号", width = 15)
	private String workshopid;
	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
	private String equipid;
	/**部署位置（工序）*/
	@Excel(name = "部署位置（工序）", width = 15)
	private String location;
	/**位置描述*/
	@Excel(name = "位置描述", width = 15)
	private String locationdis;
	/**部署日期*/
	@Excel(name = "部署日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date datetime;
	/**是否采集节点*/
	@Excel(name = "是否采集节点", width = 15)
	private String isacquisition;
	/**节点数据类型*/
	@Excel(name = "节点数据类型", width = 15)
	private String nodedatatype;
	/**数据保留天数*/
	@Excel(name = "数据保留天数", width = 15)
	private Integer storageperiod;
	/**网关编号*/
	@Excel(name = "网关编号", width = 15)
	private String gatewayid;
	/**节点状态*/
	@Excel(name = "节点状态", width = 15)
	private String nodestatus;
	/**动作数*/
	@Excel(name = "动作数", width = 15)
	private Integer actionamount;
	/**生产数*/
	@Excel(name = "生产数", width = 15)
	private BigDecimal productamount;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
	private String operator;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
	/**生产数*/
	@Excel(name = "数据", width = 15)
	private BigDecimal rdata;

	
}
