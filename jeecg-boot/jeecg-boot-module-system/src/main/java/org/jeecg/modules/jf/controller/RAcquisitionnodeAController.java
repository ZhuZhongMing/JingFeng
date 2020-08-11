package org.jeecg.modules.jf.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.RAcquisitionnodeA;
import org.jeecg.modules.jf.service.IRAcquisitionnodeAService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.jf.vo.NodeVO;
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
 * @Description: 节点信息
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/system/rAcquisitionnodeA")
@Slf4j
public class RAcquisitionnodeAController {
	@Autowired
	private IRAcquisitionnodeAService rAcquisitionnodeAService;

	 /*今日设备动作总数*/
	 @GetMapping(value = "/getTodayRdata")
	 public Result<Integer> getTodayRdata(HttpServletRequest req) {
		 Result<Integer> result = new Result<Integer>();
		 SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
		 Integer sum = rAcquisitionnodeAService.sumRdata(f.format(new Date()));
		 result.setSuccess(true);
		 result.setResult(sum);
		 return result;
	 }

	 /*设备动作总数*/
	 @GetMapping(value = "/getSumRdata")
	 public Result<Integer> getSumRdata(HttpServletRequest req) {
		 Result<Integer> result = new Result<Integer>();
		 Integer sum = rAcquisitionnodeAService.sumRdata();
		 result.setSuccess(true);
		 result.setResult(sum);
		 return result;
	 }

	 /*今日加工总数*/
	 /**
	  * 查询设备节点实时信息
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/getTodayNode")
	 public Result<List<NodeVO>> getTodayNode(HttpServletRequest req) {
		 Result<List<NodeVO>> result = new Result<List<NodeVO>>();
		 SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
		 String time = f.format(new Date());
		 NodeVO vo = new NodeVO();
		 List<NodeVO> pageList = rAcquisitionnodeAService.getnode(time);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 //log.info("长度："+pageList.size()+"  id:"+ids+"  time:"+time);
		 return result;
	 }

	 /*加工总数*/
	 /**
	  * 查询设备节点实时信息
	  * @param req
	  * @return
	  */
	 /*@GetMapping(value = "/getSumNode")
	 public Result<List<NodeVO>> getSumNode(HttpServletRequest req) {
		 Result<List<NodeVO>> result = new Result<List<NodeVO>>();
		 SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
		 String time = f.format(new Date());
		 NodeVO vo = new NodeVO();
		 List<NodeVO> pageList = rAcquisitionnodeAService.getnode();
		 result.setSuccess(true);
		 result.setResult(pageList);
		 //log.info("长度："+pageList.size()+"  id:"+ids+"  time:"+time);
		 return result;
	 }*/


	 /**
	  * 查询设备节点实时信息
	  * @param req
	  * @return
	  */
/*	 @GetMapping(value = "/queryNode")
	 public Result<List<NodeVO>> queryNode(String id,HttpServletRequest req) {
		 Result<List<NodeVO>> result = new Result<List<NodeVO>>();
		 SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
		 String time = f.format(new Date());
		 NodeVO vo = new NodeVO();
		 *//*for (String id:ids) {
			 List<NodeVO> ar = rAcquisitionnodeAService.queryNode(id,time);
			 for (NodeVO v : ar) {
			 	*//**//*设备编号*//**//*
			 	vo.setEquipid(v.getEquipid());
			 	*//**//*网关*//**//*
			 	vo.setGatewayid(v.getGatewayid());
			 	*//**//*动作数*//**//*
			 	vo.setRdata(BigDecimal.valueOf(vo.getRdata().intValue() + v.getActionamount()));
			 	*//**//*生产数*//**//*
				 vo.setProductamount(vo.getProductamount()+ (BigDecimal.valueOf(vo.getActionamount()) / vo.getRdata() ) );
			 }
		 }*//*
		 List<NodeVO> pageList = rAcquisitionnodeAService.queryNode(id,time);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 //log.info("长度："+pageList.size()+"  id:"+ids+"  time:"+time);
		 return result;
	 }*/
	
	/**
	  * 分页列表查询
	 * @param rAcquisitionnodeA
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<RAcquisitionnodeA>> queryPageList(RAcquisitionnodeA rAcquisitionnodeA,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<RAcquisitionnodeA>> result = new Result<IPage<RAcquisitionnodeA>>();
		QueryWrapper<RAcquisitionnodeA> queryWrapper = QueryGenerator.initQueryWrapper(rAcquisitionnodeA, req.getParameterMap());
		Page<RAcquisitionnodeA> page = new Page<RAcquisitionnodeA>(pageNo, pageSize);
		IPage<RAcquisitionnodeA> pageList = rAcquisitionnodeAService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param rAcquisitionnodeA
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<RAcquisitionnodeA> add(@RequestBody RAcquisitionnodeA rAcquisitionnodeA) {
		Result<RAcquisitionnodeA> result = new Result<RAcquisitionnodeA>();
		try {
			rAcquisitionnodeAService.save(rAcquisitionnodeA);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param rAcquisitionnodeA
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<RAcquisitionnodeA> edit(@RequestBody RAcquisitionnodeA rAcquisitionnodeA) {
		Result<RAcquisitionnodeA> result = new Result<RAcquisitionnodeA>();
		RAcquisitionnodeA rAcquisitionnodeAEntity = rAcquisitionnodeAService.getById(rAcquisitionnodeA.getId());
		if(rAcquisitionnodeAEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = rAcquisitionnodeAService.updateById(rAcquisitionnodeA);
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
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			rAcquisitionnodeAService.removeById(id);
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
	public Result<RAcquisitionnodeA> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<RAcquisitionnodeA> result = new Result<RAcquisitionnodeA>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.rAcquisitionnodeAService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<RAcquisitionnodeA> queryById(@RequestParam(name="id",required=true) String id) {
		Result<RAcquisitionnodeA> result = new Result<RAcquisitionnodeA>();
		RAcquisitionnodeA rAcquisitionnodeA = rAcquisitionnodeAService.getById(id);
		if(rAcquisitionnodeA==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(rAcquisitionnodeA);
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
  public ModelAndView exportXls(HttpServletRequest request, RAcquisitionnodeA rAcquisitionnodeA) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<RAcquisitionnodeA> queryWrapper = QueryGenerator.initQueryWrapper(rAcquisitionnodeA, request.getParameterMap());
      List<RAcquisitionnodeA> pageList = rAcquisitionnodeAService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<RAcquisitionnodeA> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "节点信息列表");
      mv.addObject(NormalExcelConstants.CLASS, RAcquisitionnodeA.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("节点信息列表数据", "导出人:Jeecg", "导出信息"));
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
              List<RAcquisitionnodeA> listRAcquisitionnodeAs = ExcelImportUtil.importExcel(file.getInputStream(), RAcquisitionnodeA.class, params);
              rAcquisitionnodeAService.saveBatch(listRAcquisitionnodeAs);
              return Result.ok("文件导入成功！数据行数:" + listRAcquisitionnodeAs.size());
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
