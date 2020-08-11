package org.jeecg.modules.jf.vo;

import lombok.Data;
import org.jeecg.modules.jf.entity.MMaterial;
import org.jeecg.modules.jf.entity.MProductMap;

import java.util.Map;

/**
 * @Description: 物料BOM
 * @Author: jeecg-boot
 * @Date:   2019-11-10
 * @Version: V1.0
 */
@Data
public class BomVO {

	/*bom组成*/
	/*private List<MBom> mBomList;*/
	/*产品*/
	private MProductMap mProductMap;
	/*部件*/
	private Map<String,MMaterial> mMaterialList;


	/*private MBom mbom;
	private List<MMaterial> parentList;
	private List<MMaterial> childList;*/
    
	/**物料清单编号*//*
	private String id;
	*//**物料编码*//*
	private String materialencode;


	*//**父项*//*
	private String parentid;
	private List<MMaterial> parentList;

	*//**版本号*//*
	private String version;


	*//**子项*//*
	private String childid;
	private List<MMaterial> childList;


	*//**子项版本号*//*
	private String childversion;
	*//**层次*//*
	private Integer level;
	*//**是否叶子节点*//*
	private Integer isleaf;
	*//**数量*//*
	private java.math.BigDecimal number;
	*//**位置*//*
	private String position;
	*//**备注*//*
	private Object disp;
	*//**创建人*//*
	private String createBy;
	*//**创建时间*//*
	private Date createTime;
	*//**修改人*//*
	private String updateBy;
	*//**修改时间*//*
	private Date updateTime;
	*//**删除标识0-正常,1-已删除*//*
	private Integer delFlag;
	*//*任务单编号*//*
	private String taskid;*/
}
