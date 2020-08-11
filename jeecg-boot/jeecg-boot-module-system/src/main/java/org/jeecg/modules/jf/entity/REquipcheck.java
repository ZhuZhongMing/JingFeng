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
 * @Description: 设备点检
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@Data
@TableName("r_equipcheck")
public class REquipcheck implements Serializable {
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
	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
	private String equipid;
	/**保养类型*/
	@Excel(name = "保养类型", width = 15)
	private String maintaintype;
	/**保养结果*/
	@Excel(name = "保养结果", width = 15)
	private String maintainresult;
	/**工时数*/
	@Excel(name = "工时数", width = 15)
	private BigDecimal workinghours;
	/**隐患处理提醒*/
	@Excel(name = "隐患处理提醒", width = 15)
	private String troubleremind;
	/**隐患修复预计日期*/
	@Excel(name = "隐患修复预计日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date predictdate;
	/**保养部门*/
	@Excel(name = "保养部门", width = 15)
	private String maintaindept;
	/**保养人*/
	@Excel(name = "保养人", width = 15)
	private String maintainoperator;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
}
