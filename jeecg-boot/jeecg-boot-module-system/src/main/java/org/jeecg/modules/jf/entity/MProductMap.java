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
 * @Description: 产品信息
 * @Author: jeecg-boot
 * @Date:   2019-12-09
 * @Version: V1.0
 */
@Data
@TableName("m_product_map")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="m_product_map对象", description="产品信息")
public class MProductMap {
    
	/**编号*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "编号")
	private String id;
	/**产品名称*/
	@Excel(name = "产品名称", width = 15)
    @ApiModelProperty(value = "产品名称")
	private String productname;
	/**产品型号*/
	@Excel(name = "产品型号", width = 15)
    @ApiModelProperty(value = "产品型号")
	private String producttype;
	/**产品规格*/
	@Excel(name = "产品规格", width = 15)
    @ApiModelProperty(value = "产品规格")
	private String productsize;
	/**产品颜色*/
	@Excel(name = "产品颜色", width = 15)
    @ApiModelProperty(value = "产品颜色")
	private String productcolor;
	/**产品长度（节）*/
	@Excel(name = "产品长度（节）", width = 15)
    @ApiModelProperty(value = "产品长度（节）")
	private Integer productlength;

	/**上刀*/
	@Excel(name = "上刀", width = 15)
    @ApiModelProperty(value = "上刀")
	private String topblade;
	@TableField(exist = false)
	private String topbladename;

	/**下刀*/
	@Excel(name = "下刀", width = 15)
    @ApiModelProperty(value = "下刀")
	private String bottomblade;
	@TableField(exist = false)
	private String bottombladename;

	/**连接片*/
	@Excel(name = "连接片", width = 15)
    @ApiModelProperty(value = "连接片")
	private String connectionblade;
	@TableField(exist = false)
	private String connectionbladename;

	/**传动片*/
	@Excel(name = "传动片", width = 15)
    @ApiModelProperty(value = "传动片")
	private String driverblade;
	@TableField(exist = false)
	private String driverbladename;

	/**铆钉*/
	@Excel(name = "铆钉", width = 15)
    @ApiModelProperty(value = "铆钉")
	private String rivet;
	@TableField(exist = false)
	private String rivetname;

	/**是否带反弹传动片0-否,1-是*/
	@Excel(name = "是否带反弹传动片0-否,1-是", width = 15)
    @ApiModelProperty(value = "是否带反弹传动片0-否,1-是")
	private Integer isresilient;
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

	/** 数量 **/
	@TableField(exist = false)
    private Integer number;
}
