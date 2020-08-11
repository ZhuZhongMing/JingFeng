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
 * @Description: 模具信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Data
@TableName("r_mold")
public class RMold implements Serializable {
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
	/**模具名称*/
	@Excel(name = "模具名称", width = 15)
	private String moldname;
	/**模具数量*/
	@Excel(name = "模具数量", width = 15)
	private Integer moldnum;
	/**说明*/
	@Excel(name = "说明", width = 15)
	private String disp;
	/**模具列数*/
	@Excel(name = "模具列数", width = 15)
	private String moldcolumn;
	/**宽度*/
	@Excel(name = "宽度", width = 15)
	private Integer width;
	/**步骤*/
	@Excel(name = "步骤", width = 15)
	private Integer step;
	/**生产厂家*/
	@Excel(name = "生产厂家", width = 15)
	private String manufacturer;
	/**应用产品*/
	@Excel(name = "应用产品", width = 15)
	private String applicable;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
}
