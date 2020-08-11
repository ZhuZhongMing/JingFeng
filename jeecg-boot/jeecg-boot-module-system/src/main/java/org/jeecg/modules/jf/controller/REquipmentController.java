package org.jeecg.modules.jf.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.REquipment;
import org.jeecg.modules.jf.service.IREquipmentService;
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
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="设备信息")
@RestController
@RequestMapping("/system/rEquipment")
public class REquipmentController {
	@Autowired
	private IREquipmentService rEquipmentService;


	 /**
	  * 模糊查询设备信息
	  * @param rEquipment 设备信息
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/queryEquipmentByLike")
	 public Result<List<REquipment>> queryEquipmentByLike(REquipment rEquipment,
											HttpServletRequest req) {
		 Result<List<REquipment>> result = new Result<List<REquipment>>();
		 //QueryWrapper<REquipment> queryWrapper = QueryGenerator.initQueryWrapper(rEquipment, req.getParameterMap());
		 QueryWrapper<REquipment> queryWrapper = new QueryWrapper<REquipment>();
		 //queryWrapper.orderByAsc("create_time");
		 //queryWrapper.like()
		 queryWrapper.like("id",rEquipment.getId());
		 List<REquipment> pageList = rEquipmentService.list(queryWrapper);
		 //log.info(pageList.toString()+ "     "+pageList.size());
		 SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

		 /** 统计生产设备产能 和 检测设备合格数 **/
		 pageList.forEach(item -> {

			 // TODO 临时借用这个字段存放今日时间
			 item.setWsname("%"+ sdf.format(new Date()) + "%");
			item.setRdata(rEquipmentService.sumByDPD(item));
		 });

		 /** 为检测设备时,统计每日不合格数 **/
		 if ("%CPD%".equals(rEquipment.getId()) && rEquipment.getId() != null) {
			 pageList.forEach(item -> {
				 // TODO 临时借用这个字段存放今日时间
				 item.setWsname("%"+ sdf.format(new Date()) + "%");
				 item.setRunqualifieddata(rEquipmentService.sumUnqualifiedByCPD(item));
			 });
		 }

		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }

	 /**
	  * 全查询
	  * @param mWareHouse
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/getAll")
	 public Result<List<REquipment>> getAll(REquipment mWareHouse,
											HttpServletRequest req) {
		 Result<List<REquipment>> result = new Result<List<REquipment>>();
		 QueryWrapper<REquipment> queryWrapper = QueryGenerator.initQueryWrapper(mWareHouse, req.getParameterMap());
		 queryWrapper.orderByAsc("create_time");
		 List<REquipment> pageList = rEquipmentService.list(queryWrapper);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }


	 /**
	  * 根据车间查询
	  * @param mWareHouse
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/getListByWid")
	 public Result<List<REquipment>> getListByWid(REquipment mWareHouse,
											HttpServletRequest req) {
		 Result<List<REquipment>> result = new Result<List<REquipment>>();
		 QueryWrapper<REquipment> queryWrapper = QueryGenerator.initQueryWrapper(mWareHouse, req.getParameterMap());
		 queryWrapper.eq("workshopid",mWareHouse.getWorkshopid());
		 queryWrapper.orderByAsc("create_time");
		 List<REquipment> pageList = rEquipmentService.list(queryWrapper);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }

	
	/**
	  * 分页列表查询
	 * @param rEquipment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "设备信息-分页列表查询")
	@ApiOperation(value="设备信息-分页列表查询", notes="设备信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<REquipment>> queryPageList(REquipment rEquipment,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<REquipment>> result = new Result<IPage<REquipment>>();
		QueryWrapper<REquipment> queryWrapper = QueryGenerator.initQueryWrapper(rEquipment, req.getParameterMap());
		Page<REquipment> page = new Page<REquipment>(pageNo, pageSize);
		IPage<REquipment> pageList = rEquipmentService.page(page, queryWrapper);

		//批量查询设备的所在车间
		//step.1 先拿到全部的 设备ids
		//step.2 通过 设备ids，一次性查询设备的所在车间名称
		//List<String> ids = pageList.getRecords().stream().map(REquipment::getId).collect(Collectors.toList());
		List<String> ids = pageList.getRecords().stream().map(REquipment::getId).collect(Collectors.toList());
		if(ids!=null && ids.size()>0){
			Map<String,String>  wsNames = rEquipmentService.getWSNamesByIds(ids);
			pageList.getRecords().forEach(item->{
				//TODO 临时借用这个字段用于页面展示
				item.setWsname(wsNames.get(item.getId()));
			});
		}

		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param rEquipment
	 * @return
	 */
	@AutoLog(value = "设备信息-添加")
	@ApiOperation(value="设备信息-添加", notes="设备信息-添加")
	@PostMapping(value = "/add")
	public Result<REquipment> add(@RequestBody REquipment rEquipment) {
		Result<REquipment> result = new Result<REquipment>();
		try {
			rEquipmentService.save(rEquipment);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param rEquipment
	 * @return
	 */
	@AutoLog(value = "设备信息-编辑")
	@ApiOperation(value="设备信息-编辑", notes="设备信息-编辑")
	@PutMapping(value = "/edit")
	public Result<REquipment> edit(@RequestBody REquipment rEquipment) {
		Result<REquipment> result = new Result<REquipment>();
		REquipment rEquipmentEntity = rEquipmentService.getById(rEquipment.getId());
		if(rEquipmentEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = rEquipmentService.updateById(rEquipment);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备信息-通过id删除")
	@ApiOperation(value="设备信息-通过id删除", notes="设备信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			rEquipmentService.removeById(id);
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
	@AutoLog(value = "设备信息-批量删除")
	@ApiOperation(value="设备信息-批量删除", notes="设备信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<REquipment> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<REquipment> result = new Result<REquipment>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.rEquipmentService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备信息-通过id查询")
	@ApiOperation(value="设备信息-通过id查询", notes="设备信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<REquipment> queryById(@RequestParam(name="id",required=true) String id) {
		Result<REquipment> result = new Result<REquipment>();
		REquipment rEquipment = rEquipmentService.getById(id);
		if(rEquipment==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(rEquipment);
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
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<REquipment> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              REquipment rEquipment = JSON.parseObject(deString, REquipment.class);
              queryWrapper = QueryGenerator.initQueryWrapper(rEquipment, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<REquipment> pageList = rEquipmentService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "设备信息列表");
      mv.addObject(NormalExcelConstants.CLASS, REquipment.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("设备信息列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
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
              List<REquipment> listREquipments = ExcelImportUtil.importExcel(file.getInputStream(), REquipment.class, params);
              rEquipmentService.saveBatch(listREquipments);
              return Result.ok("文件导入成功！数据行数:" + listREquipments.size());
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
