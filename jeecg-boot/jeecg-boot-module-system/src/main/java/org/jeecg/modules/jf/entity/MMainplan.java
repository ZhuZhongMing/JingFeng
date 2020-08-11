package org.jeecg.modules.jf.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;

/**
 * @Description: 计划详情
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Data
@TableName("m_mainplan")
public class MMainplan implements Serializable {
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
	/**计划摘要编号*/
	private String mainplanabstractid;
	/**物料编号*/
	@Excel(name = "物料编号", width = 15)
	private String materialencode;
	/**数量*/
	@Excel(name = "数量", width = 15)
	private java.math.BigDecimal number;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date starttime;
	/**完成时间*/
	@Excel(name = "完成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date finishtime;
	/**计划类型编号*/
	@Excel(name = "计划类型编号", width = 15)
	private Integer plantypeid;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15)
	private String orderid;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
	private String cname;
	/**加工顺序*/
	@Excel(name = "加工顺序", width = 15)
	private Integer processorder;
	/**版本号*/
	@Excel(name = "版本号", width = 15)
	private String versionno;
	/**图号*/
	@Excel(name = "图号", width = 15)
	private String figureno;
	/**优先级编号*/
	@Excel(name = "优先级编号", width = 15)
	private String priorityid;
	/**计划状态编号*/
	@Excel(name = "计划状态编号", width = 15)
	private String planstatusid;
	/**是否关闭*/
	@Excel(name = "是否关闭", width = 15)
	private String isclosed;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
}
