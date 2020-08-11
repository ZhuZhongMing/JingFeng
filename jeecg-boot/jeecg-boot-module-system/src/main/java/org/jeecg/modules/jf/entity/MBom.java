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
 * @Description: 物料BOM
 * @Author: jeecg-boot
 * @Date:   2019-11-10
 * @Version: V1.0
 */
@Data
@TableName("m_bom")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="m_bom对象", description="物料BOM")
public class MBom {
    
	/**物料清单编号*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "物料清单编号")
	private String id;
	/**物料编码*/
	@Excel(name = "物料编码", width = 15)
    @ApiModelProperty(value = "物料编码")
	private String materialencode;
	/**物料名称*/
	@TableField(exist = false)
	/*@Excel(name = "物料名称", width = 15)*/
	private String materialname;
	/**父项*/
	@Excel(name = "父项", width = 15)
    @ApiModelProperty(value = "父项")
	private String parentid;
	/**版本号*/
	@Excel(name = "版本号", width = 15)
    @ApiModelProperty(value = "版本号")
	private String version;
	/**子项*/
	@Excel(name = "子项", width = 15)
    @ApiModelProperty(value = "子项")
	private String childid;
	/**子项版本号*/
	@Excel(name = "子项版本号", width = 15)
    @ApiModelProperty(value = "子项版本号")
	private String childversion;
	/**层次*/
	@Excel(name = "层次", width = 15)
    @ApiModelProperty(value = "层次")
	private Integer level;
	/**是否叶子节点*/
	@Excel(name = "是否叶子节点", width = 15)
    @ApiModelProperty(value = "是否叶子节点")
	private Integer isleaf;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
	private java.math.BigDecimal number;
	/**位置*/
	@Excel(name = "位置", width = 15)
    @ApiModelProperty(value = "位置")
	private String position;
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
	private Integer delFlag;

	/*任务单编号*/
	private String taskid;
}
