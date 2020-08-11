package org.jeecg.modules.jf.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.MShopplan;
import org.jeecg.modules.jf.service.IMShopplanService;
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
 * @Description: 车间计划
 * @Author: jeecg-boot
 * @Date:   2019-11-11
 * @Version: V1.0
 */
@Slf4j
@Api(tags="车间计划")
@RestController
@RequestMapping("/system/mShopplan")
public class MShopplanController {
	@Autowired
	private IMShopplanService mShopplanService;

	 /**
	  * 根据车间计划查询明细
	  * @param mShopplan  shopplanid  计划编号
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "车间计划-分页列表查询")
	 @ApiOperation(value="车间计划-分页列表查询", notes="车间计划-分页列表查询")
	 @GetMapping(value = "/queryListByMainId")
	 public Result<List<MShopplan>> queryListByMainId(MShopplan mShopplan, HttpServletRequest req) {
		 Result<List<MShopplan>> result = new Result<List<MShopplan>>();
		 QueryWrapper<MShopplan> queryWrapper = QueryGenerator.initQueryWrapper(mShopplan, req.getParameterMap());

		 //根据车间计划查询明细
		 queryWrapper.eq("shopplanid",mShopplan.getShopplanid());
		 queryWrapper.eq("del_flag",0);

		 //IPage<MShopplan> pageList = mShopplanService.page(page, queryWrapper);
		 List<MShopplan> pageList = mShopplanService.list(queryWrapper);

		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }

	
	/**
	  * 分页列表查询
	 * @param mShopplan
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "车间计划-分页列表查询")
	@ApiOperation(value="车间计划-分页列表查询", notes="车间计划-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<MShopplan>> queryPageList(MShopplan mShopplan,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MShopplan>> result = new Result<IPage<MShopplan>>();
		QueryWrapper<MShopplan> queryWrapper = QueryGenerator.initQueryWrapper(mShopplan, req.getParameterMap());
		Page<MShopplan> page = new Page<MShopplan>(pageNo, pageSize);

		//IPage<MShopplan> pageList = mShopplanService.page(page, queryWrapper);
		IPage<MShopplan> pageList = mShopplanService.getListPage(page, queryWrapper);

		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param mShopplan
	 * @return
	 */
	@AutoLog(value = "车间计划-添加")
	@ApiOperation(value="车间计划-添加", notes="车间计划-添加")
	@PostMapping(value = "/add")
	public Result<MShopplan> add(@RequestBody MShopplan mShopplan) {
		Result<MShopplan> result = new Result<MShopplan>();
		try {
			mShopplanService.save(mShopplan);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param mShopplan
	 * @return
	 */
	@AutoLog(value = "车间计划-编辑")
	@ApiOperation(value="车间计划-编辑", notes="车间计划-编辑")
	@PutMapping(value = "/edit")
	public Result<MShopplan> edit(@RequestBody MShopplan mShopplan) {
		Result<MShopplan> result = new Result<MShopplan>();
		MShopplan mShopplanEntity = mShopplanService.getById(mShopplan.getId());
		if(mShopplanEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = mShopplanService.updateById(mShopplan);
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
	@AutoLog(value = "车间计划-通过id删除")
	@ApiOperation(value="车间计划-通过id删除", notes="车间计划-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			mShopplanService.removeById(id);
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
	@AutoLog(value = "车间计划-批量删除")
	@ApiOperation(value="车间计划-批量删除", notes="车间计划-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<MShopplan> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MShopplan> result = new Result<MShopplan>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.mShopplanService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "车间计划-通过id查询")
	@ApiOperation(value="车间计划-通过id查询", notes="车间计划-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<MShopplan> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MShopplan> result = new Result<MShopplan>();
		MShopplan mShopplan = mShopplanService.getById(id);
		if(mShopplan==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(mShopplan);
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
      QueryWrapper<MShopplan> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              MShopplan mShopplan = JSON.parseObject(deString, MShopplan.class);
              queryWrapper = QueryGenerator.initQueryWrapper(mShopplan, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<MShopplan> pageList = mShopplanService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "车间计划列表");
      mv.addObject(NormalExcelConstants.CLASS, MShopplan.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("车间计划列表数据", "导出人:Jeecg", "导出信息"));
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
              List<MShopplan> listMShopplans = ExcelImportUtil.importExcel(file.getInputStream(), MShopplan.class, params);
              mShopplanService.saveBatch(listMShopplans);
              return Result.ok("文件导入成功！数据行数:" + listMShopplans.size());
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
