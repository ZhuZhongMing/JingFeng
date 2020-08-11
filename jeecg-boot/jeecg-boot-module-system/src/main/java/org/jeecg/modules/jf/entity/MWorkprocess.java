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
 * @Description: 工序
 * @Author: jeecg-boot
 * @Date:   2019-10-19
 * @Version: V1.0
 */
@Data
@TableName("m_workprocess")
public class MWorkprocess implements Serializable {
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
	/**编码*/
	@Excel(name = "编码", width = 15)
	private String wpencode;
	/**工序名称*/
	@Excel(name = "工序名称", width = 15)
	private String wpname;
	/**工序性质*/
	@Excel(name = "工序性质", width = 15)
	private String wpproperty;
	/**自动派工*/
	@Excel(name = "自动派工", width = 15)
	private String isautotaskassign;
	/**方向编号*/
	@Excel(name = "方向编号", width = 15)
	private String stepnumber;
	/**是否禁用进度*/
	@Excel(name = "是否禁用进度", width = 15)
	private String isstop;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
}
