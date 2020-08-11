package org.jeecg.modules.jf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Data
@TableName("r_equipment")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="r_equipment对象", description="设备信息")
public class REquipment {
    
	/**所属流水线设备编号*/
	@Excel(name = "所属流水线设备编号", width = 15)
    @ApiModelProperty(value = "所属流水线设备编号")
	private String asequipid;
	/**所属类别编号*/
	@Excel(name = "所属类别编号", width = 15)
    @ApiModelProperty(value = "所属类别编号")
	private String categoryid;
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
	/**删除标识0-正常,1-已删除*/
	@Excel(name = "删除标识0-正常,1-已删除", width = 15)
    @ApiModelProperty(value = "删除标识0-正常,1-已删除")
	private Integer delFlag;
	/**所属部门编号*/
	@Excel(name = "所属部门编号", width = 15)
    @ApiModelProperty(value = "所属部门编号")
	private String deptid;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private String disp;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
	private String equipname;
	/**设备编号*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "设备编号")
	private String id;
	/**所属流水工序号*/
	@Excel(name = "所属流水工序号", width = 15)
    @ApiModelProperty(value = "所属流水工序号")
	private Integer location;
	/**制造厂家*/
	@Excel(name = "制造厂家", width = 15)
    @ApiModelProperty(value = "制造厂家")
	private String manufacturer;
	/**产出系数*/
	@Excel(name = "产出系数", width = 15)
    @ApiModelProperty(value = "产出系数")
	private java.math.BigDecimal outcoefficient;
	/**产出类型*/
	@Excel(name = "产出类型", width = 15)
    @ApiModelProperty(value = "产出类型")
	private String outtype;
	/**放置位置*/
	@Excel(name = "放置位置", width = 15)
    @ApiModelProperty(value = "放置位置")
	private String placement;
	/**功率*/
	@Excel(name = "功率", width = 15)
    @ApiModelProperty(value = "功率")
	private Integer power;
	/**额定电压*/
	@Excel(name = "额定电压", width = 15)
    @ApiModelProperty(value = "额定电压")
	private Integer ratedVoltage;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
	private String state;
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
	/**设备用途*/
	@Excel(name = "设备用途", width = 15)
    @ApiModelProperty(value = "设备用途")
	private String rusage;
	/**车间编号*/
	@Excel(name = "车间编号", width = 15)
    @ApiModelProperty(value = "车间编号")
	private String workshopid;

	/**车间名称*/
	@TableField(exist = false)
	private String wsname;
	/** 设备产能 （包含合格数）**/
	@TableField(exist = false)
	private BigDecimal rdata;
	/** 不合格数 **/
	@TableField(exist = false)
	private  BigDecimal runqualifieddata;
}
