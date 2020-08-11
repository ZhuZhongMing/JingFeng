package org.jeecg.modules.jf.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.*;
import org.jeecg.modules.jf.service.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 生产任务单
 * @Author: jeecg-boot
 * @Date:   2019-11-08
 * @Version: V1.0
 */
@RestController
@RequestMapping("/system/mProductTaskOrder")
@Slf4j
public class MProductTaskOrderController {
	@Autowired
	private IMProductTaskOrderService mProductTaskOrderService;
	/*物料信息*/
	@Autowired
	private IMMaterialService iMMaterialService;
	/*物料分类信息*/
	@Autowired
	private IMMaterialcategoryService iMMaterialcategoryService;
	 /*物料BOM*/
	 @Autowired
	 private IMBomService iMBomService;
	 /*设备采集节点配置*/
	 @Autowired
	 private IRAcquisitionnodeAService iRAcquisitionnodeAService;
	 /*设备信息*/
	 @Autowired
	 private IREquipmentService iREquipmentService;
	 @Autowired
	 private IMShopplanService iMShopplanService;
	 @Autowired
	 private IMShopplanabstractService iMShopplanabstractService;

	 /**
	  * 分页列表查询
	  * @param mProductTaskOrder
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/queryList")
	 public Result<IPage<MProductTaskOrder>> queryList(MProductTaskOrder mProductTaskOrder,
													   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													   HttpServletRequest req) {
		 Result<IPage<MProductTaskOrder>> result = new Result<IPage<MProductTaskOrder>>();
		 QueryWrapper<MProductTaskOrder> queryWrapper = QueryGenerator.initQueryWrapper(mProductTaskOrder, req.getParameterMap());
		 Page<MProductTaskOrder> page = new Page<MProductTaskOrder>(pageNo, pageSize);
		 IPage<MProductTaskOrder> pageList = mProductTaskOrderService.queryList(page, queryWrapper);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }

	 /**
	  * 统计个人等待确认生产任务
	  * @param mProductTaskOrder
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/count")
	 public Result<Integer> count(MProductTaskOrder mProductTaskOrder,
													   HttpServletRequest req) {
		 Result<Integer> result = new Result<Integer>();
		 QueryWrapper<MProductTaskOrder> queryWrapper = QueryGenerator.initQueryWrapper(mProductTaskOrder, req.getParameterMap());
		 String id = "";
		 // 获取登录用户信息
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (sysUser != null) {
			 // 登录账号
			 id = sysUser.getRealname();
			 queryWrapper.like("salesman",("%"+id+"%"));
			 //log.info("salesman:"+id);
			 queryWrapper.eq("state","0");
		 }

		 Integer count = mProductTaskOrderService.count(queryWrapper);
		 result.setSuccess(true);
		 result.setResult(count);
		 return result;
	 }

	 /**
	  * 统计个人正在生产生产任务
	  * @param mProductTaskOrder
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/countStart")
	 public Result<Integer> countStart(MProductTaskOrder mProductTaskOrder,
								  HttpServletRequest req) {
		 Result<Integer> result = new Result<Integer>();
		 QueryWrapper<MProductTaskOrder> queryWrapper = QueryGenerator.initQueryWrapper(mProductTaskOrder, req.getParameterMap());
		 String id = "";
		 // 获取登录用户信息
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (sysUser != null) {
			 // 登录账号
			 id = sysUser.getRealname();
			 queryWrapper.like("salesman",("%"+id+"%"));
			 //log.info("salesman:"+id);
			 queryWrapper.eq("state","1");
		 }

		 Integer count = mProductTaskOrderService.count(queryWrapper);
		 result.setSuccess(true);
		 result.setResult(count);
		 return result;
	 }

	 /**
	  * 个人生产任务分页列表查询
	  * @param mProductTaskOrder
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/queryListBySalesman")
	 public Result<IPage<MProductTaskOrder>> queryListBySalesman(MProductTaskOrder mProductTaskOrder,
													   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													   HttpServletRequest req) {
	 	/*salesman*/
		 Result<IPage<MProductTaskOrder>> result = new Result<IPage<MProductTaskOrder>>();
		 //QueryWrapper<MProductTaskOrder> queryWrapper = QueryGenerator.initQueryWrapper(mProductTaskOrder, req.getParameterMap());
		 QueryWrapper<MProductTaskOrder> queryWrapper = new QueryWrapper<MProductTaskOrder>();
		 Page<MProductTaskOrder> page = new Page<MProductTaskOrder>(pageNo, pageSize);

		 String id = "";
		 // 获取登录用户信息
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (sysUser != null) {
			 // 登录账号
			 id = sysUser.getRealname();
			 queryWrapper.like("salesman",("%"+id+"%"));
			 queryWrapper.orderByAsc("state");
			 queryWrapper.orderByAsc("create_time");
		 }

		 IPage<MProductTaskOrder> pageList = mProductTaskOrderService.queryList(page, queryWrapper);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }

	 /**
	  * 分页列表查询
	 * @param mProductTaskOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<MProductTaskOrder>> queryPageList(MProductTaskOrder mProductTaskOrder,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MProductTaskOrder>> result = new Result<IPage<MProductTaskOrder>>();
		QueryWrapper<MProductTaskOrder> queryWrapper = QueryGenerator.initQueryWrapper(mProductTaskOrder, req.getParameterMap());
		Page<MProductTaskOrder> page = new Page<MProductTaskOrder>(pageNo, pageSize);
		IPage<MProductTaskOrder> pageList = mProductTaskOrderService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param mProductTaskOrder
	 * @return
	 */
	@PostMapping(value = "/add")
	@AutoLog(value = "添加任务单")
	public Result<MProductTaskOrder> add(@RequestBody MProductTaskOrder mProductTaskOrder) {
		Result<MProductTaskOrder> result = new Result<MProductTaskOrder>();
		try {
			//mProductTaskOrder.setState("0"); //默认的等待确认状态
			//mProductTaskOrder.setFinishednumber(0.00);//默认已完成0个
			mProductTaskOrderService.save(mProductTaskOrder);

			if (StringUtils.isNotBlank(mProductTaskOrder.getShopplanid())) {
				MShopplan plan = iMShopplanService.getById(mProductTaskOrder.getShopplanid());//计划分配状态
				plan.setDelFlag("1");//表示已分配任务
				iMShopplanService.updateById(plan);
				upp(plan.getShopplanid());
			}

			//添加任务单完成后，生成bom
			/*根据物料id物料信息*/
			/*MMaterial material = iMMaterialService.getById(mProductTaskOrder.getMaterialencodeid());
			*//*根据物料信息查出物料分类信息，上级关系*//*
			MMaterialcategory mMaterialcategory = iMMaterialcategoryService.getById(material.getMaterialtypeencode());
			*//*根据物料分类信息查出下级关系（可能是多个）*//*
			QueryWrapper<MMaterialcategory> queryWrapper = new QueryWrapper<MMaterialcategory>();
			queryWrapper.eq("parentencode",mMaterialcategory.getId());
			List<MMaterialcategory> list = iMMaterialcategoryService.list(queryWrapper);

			MBom bom = new MBom();
			MProductTaskOrder task = mProductTaskOrderService.findMax();
			//log.info("id"+task.getId());
			bom.setTaskid(task.getId());//任务单编号
			bom.setParentid(mMaterialcategory.getParentencode());//上级id
			bom.setMaterialencode(material.getId());//物料
			bom.setNumber(mProductTaskOrder.getProductnumber());//数量
			if (list.size() == 0) {
				iMBomService.save(bom);
			} else {
				for (MMaterialcategory m : list) {
					bom.setChildid(m.getId());
					iMBomService.save(bom);
				}
			}*/

			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/*检索计划明细分配状态修改计划分配状态*/
	public void upp (String id) {
		/*统计计划分配状态*/
		QueryWrapper<MShopplan> wapper = new QueryWrapper<MShopplan>();
		wapper.eq("shopplanid",id);
		wapper.eq("del_flag","0");
		Integer count = iMShopplanService.count(wapper);

		if (count <= 0) {
			MShopplanabstract a = iMShopplanabstractService.getById(id);
			a.setDelFlag("1");
			iMShopplanabstractService.updateById(a);
		} else {
			MShopplanabstract a = iMShopplanabstractService.getById(id);
			a.setDelFlag("0");
			iMShopplanabstractService.updateById(a);
		}
	}
	
	/**
	  *  编辑
	 * @param mProductTaskOrder
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<MProductTaskOrder> edit(@RequestBody MProductTaskOrder mProductTaskOrder) {
		Result<MProductTaskOrder> result = new Result<MProductTaskOrder>();
		MProductTaskOrder mProductTaskOrderEntity = mProductTaskOrderService.getById(mProductTaskOrder.getId());
		if(mProductTaskOrderEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = mProductTaskOrderService.updateById(mProductTaskOrder);
			//TODO 返回false说明什么？
			if(ok) {
				if (StringUtils.isNotBlank(mProductTaskOrder.getShopplanid())) {
					MShopplan plan = iMShopplanService.getById(mProductTaskOrder.getShopplanid());//计划分配状态
					plan.setDelFlag("1");//表示已分配任务
					iMShopplanService.updateById(plan);
					upp(plan.getShopplanid());
				}
				result.success("修改成功!");
			}
		}
		
		return result;
	}

	 /**
	  * 统计个人等待确认生产任务
	  * @param mProductTaskOrder
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/countTaskPriority")
	 public Result<Integer> countTaskPriority(MProductTaskOrder mProductTaskOrder,
								  HttpServletRequest req) {
		 Result<Integer> result = new Result<Integer>();
		 /*QueryWrapper<MProductTaskOrder> queryWrapper = QueryGenerator.initQueryWrapper(mProductTaskOrder, req.getParameterMap());*/
		 QueryWrapper<MProductTaskOrder> queryWrapper = new QueryWrapper<MProductTaskOrder>();
		 try {
		 	queryWrapper.eq("equipmentid",mProductTaskOrder.getEquipmentid());//设备编号
			queryWrapper.eq("state",1);//正在生产状态
			//queryWrapper.le("priority",mProductTaskOrder.getPriority());//任务单优先级
		 } catch (Exception e) {
			 queryWrapper.eq("state",1);//正在生产状态
			 //queryWrapper.le("priority",0);//任务单优先级
		 	log.info("判断任务优先级时发生空指针异常");
		 } finally {
			 Integer count = mProductTaskOrderService.count(queryWrapper);
			 result.setSuccess(true);
			 result.setResult(count);
		 }
		 return result;
	 }

	 /**
	  * 统计正在正在生产任务单的数量
	  * @param mProductTaskOrder
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/countFinshNumber")
	 public Result<Integer> countFinshNumber(MProductTaskOrder mProductTaskOrder,
											  HttpServletRequest req) {
		 Result<Integer> result = new Result<Integer>();
		 /*QueryWrapper<MProductTaskOrder> queryWrapper = QueryGenerator.initQueryWrapper(mProductTaskOrder, req.getParameterMap());*/
		 QueryWrapper<MProductTaskOrder> queryWrapper = new QueryWrapper<MProductTaskOrder>();
		 queryWrapper.eq("state",1);//正在生产状态
		 List<MProductTaskOrder> list = mProductTaskOrderService.list(queryWrapper);

		 //log.info(JSONObject.toJSONString(list));

		 for (MProductTaskOrder o : list) {
		 	/*判断设备类别（检测，生产）*/
			 REquipment e = iREquipmentService.getById(o.getEquipmentid());

			 RAcquisitionnodeA s = new RAcquisitionnodeA();
			 s.setCreateTime(o.getRealstarttime());//任务实际开始时间
			 if ("生产".equals(e.getRusage())) {
			 	  /*生产设备*/
				 if("DPD201901A".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_1");
					 s.setId("NumOfWorkpieces_1");
				 } else if("DPD201901B".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_2");
					 s.setId("NumOfWorkpieces_2");
				 } else if("DPD201901C".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_3");
					 s.setId("NumOfWorkpieces_3");
				 } else if("DPD201901D".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_4");
					 s.setId("NumOfWorkpieces_4");
				 } else if("DPD201901E".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_5");
					 s.setId("NumOfWorkpieces_5");
				 } else if("DPD201901F".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_6");
					 s.setId("NumOfWorkpieces_6");
				 }
			 } else {
			 	/*检测设备*/
				 if("CPD201901A".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_1");
					 s.setId("Qualified_1");
				 } else if("CPD201901B".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_2");
					 s.setId("Qualified_2");
				 } else if("CPD201901C".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_2");
					 s.setId("Qualified_3");
				 } else if("CPD201901D".equals(o.getEquipmentid())) {
					 //wrapper.eq("id","NumOfWorkpieces_2");
					 s.setId("Qualified_4");
				 }
			 }
			 //统计结果
			 java.math.BigDecimal sum = new BigDecimal(0);

			 //log.info("统计：{},任务数量：{}",sum);
			 try {
				 //log.info("节点：{},真实开始时间：{},设备用途:{}",s.getId(),s.getCreateTime(),e.getRusage());
				 sum = new BigDecimal (iRAcquisitionnodeAService.sumData(s));
				 //log.info("统计：{},任务数量：{}",sum,o.getProductnumber());
				 if(sum.compareTo(o.getProductnumber()) > -1){
					 /*已完成*/
					 o.setFinishednumber(o.getProductnumber());
					 o.setState("2");
					 //mProductTaskOrderService.updateById(o);
				 } else {
					 /*未完成*/
					 o.setFinishednumber(o.getProductnumber());
				 }
			 } catch (Exception exception) {
				 o.setFinishednumber(new BigDecimal(0));
			 } finally {
				 mProductTaskOrderService.updateById(o);
			 }


		 }
		 result.setSuccess(true);
		 result.setResult(list.size());
		 return result;
	 }
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {

			MProductTaskOrder mProductTaskOrder = mProductTaskOrderService.getById(id);
			if (StringUtils.isNotBlank(mProductTaskOrder.getShopplanid())) {
				MShopplan plan = iMShopplanService.getById(mProductTaskOrder.getShopplanid());//计划分配状态
				plan.setDelFlag("0");//表示分配任务
				iMShopplanService.updateById(plan);
				upp(plan.getShopplanid());
			}

			mProductTaskOrderService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<MProductTaskOrder> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MProductTaskOrder> result = new Result<MProductTaskOrder>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.mProductTaskOrderService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<MProductTaskOrder> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MProductTaskOrder> result = new Result<MProductTaskOrder>();
		MProductTaskOrder mProductTaskOrder = mProductTaskOrderService.getById(id);
		if(mProductTaskOrder==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(mProductTaskOrder);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, MProductTaskOrder mProductTaskOrder) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<MProductTaskOrder> queryWrapper = QueryGenerator.initQueryWrapper(mProductTaskOrder, request.getParameterMap());
      List<MProductTaskOrder> pageList = mProductTaskOrderService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<MProductTaskOrder> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "生产任务单列表");
      mv.addObject(NormalExcelConstants.CLASS, MProductTaskOrder.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("生产任务单列表数据", "导出人:Jeecg", "导出信息"));
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<MProductTaskOrder> listMProductTaskOrders = ExcelImportUtil.importExcel(file.getInputStream(), MProductTaskOrder.class, params);
              mProductTaskOrderService.saveBatch(listMProductTaskOrders);
              return Result.ok("文件导入成功！数据行数:" + listMProductTaskOrders.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
