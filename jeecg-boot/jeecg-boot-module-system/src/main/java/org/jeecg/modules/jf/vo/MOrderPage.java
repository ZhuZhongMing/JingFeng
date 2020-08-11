package org.jeecg.modules.jf.vo;

import java.util.List;

import org.jeecg.modules.jf.entity.MOrderlist;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 销售订单
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Data
public class MOrderPage {
	
	/**合同编号**/
	@Excel(name = "删除标识", width = 15)
	private String contract;
	

	/**客户编号*/
	@Excel(name = "客户编号", width = 15)
	private String customerid;
	/**业务员*/
	@Excel(name = "业务员", width = 15)
	private String salesman;
	/**部门编号*/
	@Excel(name = "部门编号", width = 15)
	private String deptid;
	/**支付方式*/
	@Excel(name = "支付方式", width = 15)
	private String paymentid;
	/**支付时间*/
	@Excel(name = "支付时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date paymentdate;
	/**递送方式*/
	@Excel(name = "递送方式", width = 15)
	private String deliverymethod;
	/**递送费用*/
	@Excel(name = "递送费用", width = 15)
	private java.math.BigDecimal deliveryfee;
	/**审核人*/
	@Excel(name = "审核人", width = 15)
	private String auditperson;
	/**审核时间*/
	@Excel(name = "审核时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date audittime;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**结算货币*/
	@Excel(name = "结算货币", width = 15)
	private String currency;
	/**订单类别*/
	@Excel(name = "订单类别", width = 15)
	private String ordertype;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
	/**合同附件*/
	private String accessory;

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

	@ExcelCollection(name="订单明细")
	private List<MOrderlist> mOrderlistList;

	
}
