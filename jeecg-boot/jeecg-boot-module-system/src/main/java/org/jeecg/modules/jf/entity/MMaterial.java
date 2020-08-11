package org.jeecg.modules.jf.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 物料信息
 * @Author: jeecg-boot
 * @Date:   2019-10-19
 * @Version: V1.0
 */
@Data
@TableName("m_material")
public class MMaterial implements Serializable {
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
	/**物料编号*/
	@Excel(name = "物料编号", width = 15)
	private String materialencode;
	/**物料名称*/
	@Excel(name = "物料名称", width = 15)
	private String materialname;
	/**别名*/
	@Excel(name = "别名", width = 15)
	private String alias;
	/**英文名*/
	@Excel(name = "英文名", width = 15)
	private String englishname;
	/**检索码*/
	@Excel(name = "检索码", width = 15)
	private String indexcode;
	/**等级*/
	@Excel(name = "等级", width = 15)
	private String level;
	/**型号*/
	@Excel(name = "型号", width = 15)
	private String model;
	/**规格*/
	@Excel(name = "规格", width = 15)
	private String special;
	/**通用规格*/
	@Excel(name = "通用规格", width = 15)
	private String gspecial;
	/**商标*/
	@Excel(name = "商标", width = 15)
	private String brand;
	/**ABC类*/
	@Excel(name = "ABC类", width = 15)
	private String abccategory;
	/**颜色*/
	@Excel(name = "颜色", width = 15)
	private String color;
	/**计算特性1*/
	@Excel(name = "计算特性1", width = 15)
	private BigDecimal spe1;
	/**计算特性2*/
	@Excel(name = "计算特性2", width = 15)
	private BigDecimal spe2;
	/**条形码*/
	@Excel(name = "条形码", width = 15)
	private String barcode;


	/**物料分类编码*/
	@Excel(name = "物料分类编码", width = 15)
	private String materialtypeencode;
	/**分类名称*/
	@TableField(exist = false)
	@Excel(name = "分类名称", width = 15)
	private String mcategoryname;


	/**单位Id*/
	@Excel(name = "单位Id", width = 15)
	private String unitid;
	/**是否虚拟件*/
	@Excel(name = "是否虚拟件", width = 15)
	private String isvirtalpart;
	/**是否外购件*/
	@Excel(name = "是否外购件", width = 15)
	private String ispurchasepart;
	/**是否通用件*/
	@Excel(name = "是否通用件", width = 15)
	private String isgeneralpart;
	/**安全库存*/
	@Excel(name = "安全库存", width = 15)
	private BigDecimal minwhstorge;
	/**最高库存*/
	@Excel(name = "最高库存", width = 15)
	private BigDecimal maxwhstorge;
	/**当前库存*/
	@Excel(name = "当前库存", width = 15)
	private BigDecimal currentwhstogre;
	/**允许销售*/
	@Excel(name = "允许销售", width = 15)
	private String issell;
	/**序列号*/
	@Excel(name = "序列号", width = 15)
	private String serialnumber;
	/**采购单位*/
	@Excel(name = "采购单位", width = 15)
	private String purchaseorganize;
	/**产地*/
	@Excel(name = "产地", width = 15)
	private String productaddress;
	/**是否入库检验*/
	@Excel(name = "是否入库检验", width = 15)
	private String iswhinspec;
	/**成本日期*/
	@Excel(name = "成本日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date costdate;
	/**标准成本价*/
	@Excel(name = "标准成本价", width = 15)
	private BigDecimal standardcost;
	/**销售价*/
	@Excel(name = "销售价", width = 15)
	private BigDecimal salescost;
	/**采购价*/
	@Excel(name = "采购价", width = 15)
	private BigDecimal purchasecost;
	/**采购提前天数*/
	@Excel(name = "采购提前天数", width = 15)
	private Integer preparedays;
	/**工序编号*/
	@Excel(name = "工序编号", width = 15)
	private String processid;
	/**是否停用*/
	@Excel(name = "是否停用", width = 15)
	private String isstop;
	/**起初数值*/
	@Excel(name = "起初数值", width = 15)
	private BigDecimal startnum;
	/**图片*/
	@Excel(name = "图片", width = 15)
	private String figurepath;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String disp;
	/**删除标识0-正常,1-已删除*/
	@Excel(name = "删除标识0-正常,1-已删除", width = 15)
	private String delFlag;
	/*数量*/
	@TableField(exist = false)
	private BigDecimal number;
}
