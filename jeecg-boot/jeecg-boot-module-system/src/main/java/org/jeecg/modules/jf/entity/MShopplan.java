package org.jeecg.modules.jf.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 车间计划
 * @Author: jeecg-boot
 * @Date:   2019-11-11
 * @Version: V1.0
 */
@Data
@TableName("m_shopplan")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="m_shopplan对象", description="车间计划")
public class MShopplan {
    
	/**编号*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "编号")
	private String id;
	/**车间计划Id*/
	@Excel(name = "车间计划Id", width = 15)
    @ApiModelProperty(value = "车间计划Id")
	private String shopplanid;
	/**车间编号*/
	@Excel(name = "车间编号", width = 15)
    @ApiModelProperty(value = "车间编号")
	private String workshopid;
	/*车间名称*/
	@TableField(exist = false)
	private String wsname;
	/**待加工物料编号*/
	@Excel(name = "待加工物料编号", width = 15)
    @ApiModelProperty(value = "待加工物料编号")
	private String materialencode;
	/**物料名称*/
	@TableField(exist = false)
	/*@Excel(name = "物料名称", width = 15)*/
	private String productname;

	/**加工类型*/
	@Excel(name = "加工类型", width = 15)
    @ApiModelProperty(value = "加工类型")
	private String stype;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
	private Integer number;
	/**安排时间*/
	@Excel(name = "安排时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "安排时间")
	private Date plantime;
	/**开始时间*/
	@Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
	private Date starttime;
	/**完成时间*/
	@Excel(name = "完成时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "完成时间")
	private Date finishtime;
	/**主计划流水号*/
	@Excel(name = "主计划流水号", width = 15)
    @ApiModelProperty(value = "主计划流水号")
	private String mainplanid;
	/**订单明细编号*/
	/*@Excel(name = "订单编号", width = 15)*/
    @ApiModelProperty(value = "订单明细编号")
	private String orderid;
	/**计划状态*/
	@Excel(name = "计划状态", width = 15)
    @ApiModelProperty(value = "计划状态")
	private String planstatus;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private Object disp;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
    @ApiModelProperty(value = "修改人")
	private String updateBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**删除标识0-正常,1-已删除*/
	@Excel(name = "删除标识0-正常,1-已删除", width = 15)
    @ApiModelProperty(value = "删除标识0-正常,1-已删除")
	private String delFlag;
}
