package org.jeecg.modules.jf.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Data
@TableName("m_customer")
public class MCustomer implements Serializable {
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
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
	private String cname;
	/**简称*/
	@Excel(name = "简称", width = 15)
	private String abcname;
	/**检索码*/
	@Excel(name = "检索码", width = 15)
	private String indexcode;
	/**是否供应商*/
	@Excel(name = "是否供应商", width = 15)
	private String issupplier;
	/**是否客户*/
	@Excel(name = "是否客户", width = 15)
	private String isclient;
	/**客户类型*/
	@Excel(name = "客户类型", width = 15)
	private String ctype;
	/**客户分类1*/
	@Excel(name = "客户分类1", width = 15)
	private String categorytype;
	/**所属区域*/
	@Excel(name = "所属区域", width = 15)
	private String carea;
	/**首次合作时间*/
	@Excel(name = "首次合作时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date firstcodate;
	/**客户分类2*/
	@Excel(name = "客户分类2", width = 15)
	private String categorytype2;
	/**是否外协*/
	@Excel(name = "是否外协", width = 15)
	private String isassist;
	/**客户地址*/
	@Excel(name = "客户地址", width = 15)
	private String caddress;
	/**邮政编码*/
	@Excel(name = "邮政编码", width = 15)
	private String postcode;
	/**法人*/
	@Excel(name = "法人", width = 15)
	private String cmanager;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
	private String contactperson;
	/**联系人电话*/
	@Excel(name = "联系人电话", width = 15)
	private String telephone;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
	private String email;
	/**传真*/
	@Excel(name = "传真", width = 15)
	private String fax;
	/**银行*/
	@Excel(name = "银行", width = 15)
	private String bank;
	/**开户账号*/
	@Excel(name = "开户账号", width = 15)
	private String account;
	/**付款方式*/
	@Excel(name = "付款方式", width = 15)
	private String paymentkind;
	/**付款周期*/
	@Excel(name = "付款周期", width = 15)
	private Integer paymentcircle;
	/**结算货币*/
	@Excel(name = "结算货币", width = 15)
	private String currency;
	/**税号*/
	@Excel(name = "税号", width = 15)
	private String taxno;
	/**营业执照*/
	@Excel(name = "营业执照", width = 15)
	private String license;
	/**信用等级*/
	@Excel(name = "信用等级", width = 15)
	private String creditgrade;
	/**信用额度*/
	@Excel(name = "信用额度", width = 15)
	private BigDecimal creditlimit;
	/**业务员*/
	@Excel(name = "业务员", width = 15)
	private String salesman;
	/**启用状态*/
	@Excel(name = "启用状态", width = 15)
	private String cstatus;
	/**描述*/
	@Excel(name = "描述", width = 15)
	private String description;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**企业网址*/
	@Excel(name = "企业网址", width = 15)
	private String website;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
}
