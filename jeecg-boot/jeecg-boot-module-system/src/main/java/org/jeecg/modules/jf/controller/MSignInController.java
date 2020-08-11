package org.jeecg.modules.jf.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.MSignIn;
import org.jeecg.modules.jf.service.IMSignInService;
import java.util.Date;
import java.util.stream.Collectors;

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
 * @Description: 签到记录
 * @Author: jeecg-boot
 * @Date:   2020-01-09
 * @Version: V1.0
 */
@Slf4j
@Api(tags="签到记录")
@RestController
@RequestMapping("/system/mSignIn")
public class MSignInController {
	@Autowired
	private IMSignInService mSignInService;

	 /**
	  * 判断当天是否已经签到
	  */
	 @GetMapping(value = "/checkSign")
	 public Result<Integer> checkSign(HttpServletRequest req) {
		 Result<Integer> result = new Result<Integer>();
		 QueryWrapper<MSignIn> queryWrapper = new QueryWrapper<MSignIn>();
		 /*当天时间*/
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 String time = sdf.format(new Date());
		 String begintime = (time + " 00:00:00"); // 当天第一秒
		 String endtime = (time + " 23:59:59"); // 当天最后一秒

		 String id = "";
		 // 获取登录用户信息
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 Integer count = 0;
		 if (sysUser != null) {
			 // 登录账号
			 id = sysUser.getUsername();

			 queryWrapper.eq("create_by",id);
			 queryWrapper.ge("create_time",begintime);
			 queryWrapper.le("create_time",endtime);

			 queryWrapper.eq("create_by", id)
					 .and(wrapper -> wrapper.ge("create_time", begintime))
					 .and(wrapper -> wrapper.le("create_time", endtime));

			 count = mSignInService.count(queryWrapper);
		 }

		 result.setSuccess(true);
		 result.setResult(count);
		 return result;
	 }

	
	/**
	  * 分页列表查询
	 * @param mSignIn
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<MSignIn>> queryPageList(MSignIn mSignIn,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MSignIn>> result = new Result<IPage<MSignIn>>();
		QueryWrapper<MSignIn> queryWrapper = QueryGenerator.initQueryWrapper(mSignIn, req.getParameterMap());
		Page<MSignIn> page = new Page<MSignIn>(pageNo, pageSize);
		IPage<MSignIn> pageList = mSignInService.page(page, queryWrapper);

		//批量查询设备的所在车间
		//step.1 先拿到全部的 设备ids
		//step.2 通过 设备ids，一次性查询设备的所在车间名称
		//List<String> ids = pageList.getRecords().stream().map(REquipment::getId).collect(Collectors.toList());
		List<String> ids = pageList.getRecords().stream().map(MSignIn::getId).collect(Collectors.toList());
		if(ids!=null && ids.size()>0){
			Map<String,String>  enames = mSignInService.getEquipNamesByIds(ids);
			pageList.getRecords().forEach(item->{
				//TODO 临时借用这个字段用于页面展示
				item.setEquipname(enames.get(item.getId()));
			});
		}

		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param mSignIn
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<MSignIn> add(@RequestBody MSignIn mSignIn) {
		Result<MSignIn> result = new Result<MSignIn>();
		try {
			mSignInService.save(mSignIn);
			result.success("签到成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param mSignIn
	 * @return
	 */
	@PutMapping(value = "/edit")
	@AutoLog(value = "签到记录-修改")
	@ApiOperation(value = "签到记录-修改", notes = "签到记录-修改")
	public Result<MSignIn> edit(@RequestBody MSignIn mSignIn) {
		Result<MSignIn> result = new Result<MSignIn>();
		MSignIn mSignInEntity = mSignInService.getById(mSignIn.getId());
		if(mSignInEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = mSignInService.updateById(mSignIn);
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
			mSignInService.removeById(id);
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
	public Result<MSignIn> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MSignIn> result = new Result<MSignIn>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.mSignInService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<MSignIn> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MSignIn> result = new Result<MSignIn>();
		MSignIn mSignIn = mSignInService.getById(id);
		if(mSignIn==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(mSignIn);
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
      QueryWrapper<MSignIn> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              MSignIn mSignIn = JSON.parseObject(deString, MSignIn.class);
              queryWrapper = QueryGenerator.initQueryWrapper(mSignIn, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<MSignIn> pageList = mSignInService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "签到记录列表");
      mv.addObject(NormalExcelConstants.CLASS, MSignIn.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("签到记录列表数据", "导出人:Jeecg", "导出信息"));
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
              List<MSignIn> listMSignIns = ExcelImportUtil.importExcel(file.getInputStream(), MSignIn.class, params);
              mSignInService.saveBatch(listMSignIns);
              return Result.ok("文件导入成功！数据行数:" + listMSignIns.size());
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
