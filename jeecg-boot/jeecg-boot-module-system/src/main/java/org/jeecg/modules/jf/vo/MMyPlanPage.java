package org.jeecg.modules.jf.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Data
public class MMyPlanPage {
	
	/**主键*/
	private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**物料编号*/
	@Excel(name = "物料编号", width = 15)
	private String materialencode;
	/**物料名称*/
	private String materialname;
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
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**计划状态编号*/
	@Excel(name = "计划状态编号", width = 15)
	private String planstatusid;

	/**部门编号*/
	@Excel(name = "部门编号", width = 15)
	private String deptid;
	/**业务员编号*/
	@Excel(name = "业务员编号", width = 15)
	private String salesmanid;
	/**计划种类编号*/
	@Excel(name = "计划种类编号", width = 15)
	private String plantypeid;
	/**业务日期*/
	@Excel(name = "业务日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date saledate;
	/**审核人编号*/
	@Excel(name = "审核人编号", width = 15)
	private String auditorid;
	/**审核日期*/
	@Excel(name = "审核日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date auditdate;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15)
	private String auditstatus;




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
	/**是否关闭*/
	@Excel(name = "是否关闭", width = 15)
	private String isclosed;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;

	

	
}
