package org.jeecg.modules.jf.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 销售订单
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Data
@TableName("m_order")
public class MOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**创建人*/
	private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**客户编号*/
	private String customerid;
	/*客户名称*/
	@TableField(exist = false)
	private String cname;

	/**业务员*/
	private String salesman;

	/**部门编号*/
	private String deptid;
	/*部门名称*/
	@TableField(exist = false)
	private String departName;

	/**支付方式*/
	private String paymentid;
	/**支付时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date paymentdate;
	/**递送方式*/
	private String deliverymethod;
	/**递送费用*/
	private java.math.BigDecimal deliveryfee;
	/**审核人*/
	private String auditperson;
	/**审核时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date audittime;
	/**备注*/
	private String disp;
	/**结算货币*/
	private String currency;
	/**订单类别*/
	private String ordertype;
	/**删除标识*/
	private String delFlag;
	/**订单分配状态*/
	private String orderstate;
	/**合同附件*/
	private String accessory;
	/**合同编号**/
	private String contract;
}
