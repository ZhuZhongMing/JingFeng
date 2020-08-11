package org.jeecg.modules.jf.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 生产任务单
 * @Author: jeecg-boot
 * @Date:   2019-11-08
 * @Version: V1.0
 */
@Data
@TableName("m_producttaskorder")
public class MProductTaskOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    
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
	/**生产物料编码*/
	@Excel(name = "生产物料编码", width = 15)
	private String materialencodeid;
	/**物料名称*/
	@TableField(exist=false)
	@Excel(name = "产品", width = 15)
	private String productname;

	/**生产车间编号*/
	@Excel(name = "生产车间编号", width = 15)
	private String workshopid;
	/**生产车间名称*/
	@TableField(exist=false)
	@Excel(name = "生产车间名称", width = 15)
	private String wsname;

	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
	private String equipmentid;
	/**设备名称*/
	@TableField(exist=false)
	@Excel(name = "设备名称", width = 15)
	private String equipname;

	/**工序编号*/
	@Excel(name = "工序编号", width = 15)
	private String workprocessid;
	/**工序名称*/
	@TableField(exist=false)
	@Excel(name = "工序名称", width = 15)
	private String wpname;


	/**数量*/
	@Excel(name = "数量", width = 15)
	private BigDecimal productnumber;
	/**业务员*/
	@Excel(name = "业务员", width = 15)
	private String salesman;

	/**已完成数量*/
	@Excel(name = "已完成数量", width = 15)
	private BigDecimal finishednumber;
	/**计划开始时间*/
	@Excel(name = "计划开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	/*@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date planstarttime;
	/**计划结束时间*/
	@Excel(name = "计划结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	/*@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date planendtime;
	/**实际开始时间*/
	@Excel(name = "实际开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	/*@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date realstarttime;
	/**实际结束时间*/
	@Excel(name = "实际结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	/*@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date realendtime;
	/**需要工时*/
	@Excel(name = "需要工时", width = 15)
	private BigDecimal needworkhour;
	/**是否外协*/
	@Excel(name = "是否外协", width = 15)
	private String isoutsource;
	/**状态*/
	@Excel(name = "状态", width = 15)
	private String state;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
	/**车间计划明细编号*/
	@Excel(name = "车间计划明细编号", width = 15)
	private String shopplanid;

	/*任务单优先级*/
	private Long priority;
}
