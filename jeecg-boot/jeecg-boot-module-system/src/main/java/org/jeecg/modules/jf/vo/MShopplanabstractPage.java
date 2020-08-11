package org.jeecg.modules.jf.vo;

import java.util.List;

import org.jeecg.modules.jf.entity.MShopplan;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 车间计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-11-19
 * @Version: V1.0
 */
@Data
public class MShopplanabstractPage {
	
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
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**部门编号*/
	@Excel(name = "部门编号", width = 15)
	private String deptid;

	/**车间编号*/
	@Excel(name = "部门编号", width = 15)
	private String workshopid;


	/**批准人*/
	@Excel(name = "批准人", width = 15)
	private String personincharge;
	/**批准日期*/
	@Excel(name = "批准日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date inchargedate;
	/**审核人*/
	@Excel(name = "审核人", width = 15)
	private String auditor;
	/**审核日期*/
	@Excel(name = "审核日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date auditdate;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15)
	private String auditstatus;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15)
	private String orderid;
	/**合同附件*/
	private String accessory;
	@ExcelCollection(name="车间计划明细")
	private List<MShopplan> mShopplanList;	
	
}
