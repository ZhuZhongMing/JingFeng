package org.jeecg.modules.jf.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.jf.vo.MMyPlanPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.MMainplan;
import org.jeecg.modules.jf.entity.MMainplanabstract;
import org.jeecg.modules.jf.vo.MMainplanabstractPage;
import org.jeecg.modules.jf.service.IMMainplanabstractService;
import org.jeecg.modules.jf.service.IMMainplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/system/mMainplanabstract")
@Slf4j
public class MMainplanabstractController {
	@Autowired
	private IMMainplanabstractService mMainplanabstractService;
	@Autowired
	private IMMainplanService mMainplanService;

	 /**
	  * 查看主计划详情
	  * @param id 主计划id
	  * @return
	  */
	 @GetMapping(value = "/getByMainId")
	 public Result<MMyPlanPage> getByMainId(@RequestParam(name="id") String id) {
		 Result<MMyPlanPage> result = new Result<MMyPlanPage>();
		 MMyPlanPage pageList = mMainplanabstractService.getByMainId(id);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 log.info("结果："+result);
		 return result;
	 }

	 /**
	  *  确认计划
	  * @param mMainplan
	  * @return
	  */
	 @PutMapping(value = "/okPlan")
	 public Result<MMainplan> okPlan(@RequestBody MMainplan mMainplan) {
		 Result<MMainplan> result = new Result<MMainplan>();
		 MMainplan mMainplanEntity = mMainplanService.getById(mMainplan.getId());
		 if(mMainplanEntity==null) {
			 result.error500("未找到对应实体");
		 }else {
			 boolean ok = mMainplanService.updateById(mMainplan);
			 //TODO 返回false说明什么？
			 if(ok) {
				 result.success("操作成功!");
			 }
		 }

		 return result;
	 }

	 /**
	  * 统计个人等待确认计划
	  * @param
	  * @return
	  */
	 @GetMapping(value = "/countByid")
	 public Result<List<MMyPlanPage>> countByid() {
		 Result<List<MMyPlanPage>> result = new Result<List<MMyPlanPage>>();

		 String id = "";
		 // 获取登录用户信息
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (sysUser != null) {
			 // 登录账号
			 id = sysUser.getRealname();
		 }

		 List<MMyPlanPage> pageList = mMainplanabstractService.countByid(id);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 //log.info("结果："+result.getResult());
		 return result;
	 }



	 /**
	  * 分页列表查询个人计划
	  * @param
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/listByid")
	 public Result<IPage<MMyPlanPage>> listByid(MMyPlanPage mMyPlanPage,
													  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													  HttpServletRequest req) {
		 Result<IPage<MMyPlanPage>> result = new Result<IPage<MMyPlanPage>>();
		 Page<MMyPlanPage> page = new Page<MMyPlanPage>(pageNo, pageSize);

		 String id = "";
		 // 获取登录用户信息
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (sysUser != null) {
			 // 登录账号
			 id = sysUser.getRealname();
		 }

		 IPage<MMyPlanPage> pageList = mMainplanabstractService.getList(page,id);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 //log.info("结果："+result.getResult());
		 return result;
	 }
	
	/**
	  * 分页列表查询
	 * @param mMainplanabstract
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<MMainplanabstract>> queryPageList(MMainplanabstract mMainplanabstract,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MMainplanabstract>> result = new Result<IPage<MMainplanabstract>>();
		QueryWrapper<MMainplanabstract> queryWrapper = QueryGenerator.initQueryWrapper(mMainplanabstract, req.getParameterMap());
		Page<MMainplanabstract> page = new Page<MMainplanabstract>(pageNo, pageSize);
		IPage<MMainplanabstract> pageList = mMainplanabstractService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param mMainplanabstractPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<MMainplanabstract> add(@RequestBody MMainplanabstractPage mMainplanabstractPage) {
		Result<MMainplanabstract> result = new Result<MMainplanabstract>();
		try {
			MMainplanabstract mMainplanabstract = new MMainplanabstract();
			BeanUtils.copyProperties(mMainplanabstractPage, mMainplanabstract);
			
			mMainplanabstractService.saveMain(mMainplanabstract, mMainplanabstractPage.getMMainplanList());
			MMainplanabstract m = mMainplanabstractService.findMaxID();
			result.setResult(m);
			//result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	 /**
	  *   添加或修改计划
	  * @param
	  * @return
	  */
	 @PostMapping(value = "/saveOrUpdate")
	 public Result<MMainplanabstract> addList(@RequestBody MMainplan mMainplan) {
		 Result<MMainplanabstract> result = new Result<MMainplanabstract>();
		 try {
			 if (mMainplan.getId() == null) {
				 mMainplan.setPlanstatusid("0");
			 }
		 } catch (NullPointerException e) {
		 	log.info("添加主计划发生空指针异常");
		 } finally {
			 mMainplanService.saveOrUpdate(mMainplan);
			 result.success("操作成功！");
			 return result;
		 }
	 }
	
	/**
	  *  编辑
	 * @param mMainplanabstractPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<MMainplanabstract> edit(@RequestBody MMainplanabstractPage mMainplanabstractPage) {
		Result<MMainplanabstract> result = new Result<MMainplanabstract>();
		MMainplanabstract mMainplanabstract = new MMainplanabstract();
		BeanUtils.copyProperties(mMainplanabstractPage, mMainplanabstract);
		MMainplanabstract mMainplanabstractEntity = mMainplanabstractService.getById(mMainplanabstract.getId());
		if(mMainplanabstractEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = mMainplanabstractService.updateById(mMainplanabstract);
			mMainplanabstractService.updateMain(mMainplanabstract, mMainplanabstractPage.getMMainplanList());
			result.setResult(mMainplanabstract);
			//result.success("修改成功!");
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
			mMainplanabstractService.delMain(id);
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
	public Result<MMainplanabstract> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MMainplanabstract> result = new Result<MMainplanabstract>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.mMainplanabstractService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public Result<MMainplanabstract> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MMainplanabstract> result = new Result<MMainplanabstract>();
		MMainplanabstract mMainplanabstract = mMainplanabstractService.getById(id);
		if(mMainplanabstract==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(mMainplanabstract);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryMMainplanByMainId")
	public Result<List<MMainplan>> queryMMainplanListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<MMainplan>> result = new Result<List<MMainplan>>();
		List<MMainplan> mMainplanList = mMainplanService.selectByMainId(id);
		result.setResult(mMainplanList);
		result.setSuccess(true);
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, MMainplanabstract mMainplanabstract) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<MMainplanabstract> queryWrapper = QueryGenerator.initQueryWrapper(mMainplanabstract, request.getParameterMap());
      List<MMainplanabstract> queryList = mMainplanabstractService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<MMainplanabstract> mMainplanabstractList = new ArrayList<MMainplanabstract>();
      if(oConvertUtils.isEmpty(selections)) {
    	  mMainplanabstractList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  mMainplanabstractList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<MMainplanabstractPage> pageList = new ArrayList<MMainplanabstractPage>();
      for (MMainplanabstract main : mMainplanabstractList) {
          MMainplanabstractPage vo = new MMainplanabstractPage();
          BeanUtils.copyProperties(main, vo);
          List<MMainplan> mMainplanList = mMainplanService.selectByMainId(main.getId());
          vo.setMMainplanList(mMainplanList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "计划摘要列表");
      mv.addObject(NormalExcelConstants.CLASS, MMainplanabstractPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("计划摘要列表数据", "导出人:Jeecg", "导出信息"));
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
              List<MMainplanabstractPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MMainplanabstractPage.class, params);
              for (MMainplanabstractPage page : list) {
                  MMainplanabstract po = new MMainplanabstract();
                  BeanUtils.copyProperties(page, po);
                  mMainplanabstractService.saveMain(po, page.getMMainplanList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
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
