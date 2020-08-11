package org.jeecg.modules.jf.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.MWorkShop;
import org.jeecg.modules.jf.service.IMWorkShopService;

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
 * @Description: 车间信息
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@RestController
@RequestMapping("/system/mWorkShop")
@Slf4j
public class MWorkShopController {
	@Autowired
	private IMWorkShopService mWorkShopService;

	 /**
	  * 全查询
	  * @param mWareHouse
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/getAll")
	 public Result<List<MWorkShop>> getAll(MWorkShop mWareHouse,
												  HttpServletRequest req) {
		 Result<List<MWorkShop>> result = new Result<List<MWorkShop>>();
		 QueryWrapper<MWorkShop> queryWrapper = QueryGenerator.initQueryWrapper(mWareHouse, req.getParameterMap());
		 try {
		 	if (null != mWareHouse.getDeptid() && "" != mWareHouse.getDeptid()) {
				queryWrapper.eq("deptid",mWareHouse.getDeptid());
			}
		 } catch (Exception e) {
		 	log.info("全查询车间信息");
		 } finally {
			 List<MWorkShop> pageList = mWorkShopService.list(queryWrapper);
			 result.setSuccess(true);
			 result.setResult(pageList);
			 return result;
		 }
	 }
	
	/**
	  * 分页列表查询
	 * @param mWorkShop
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<MWorkShop>> queryPageList(MWorkShop mWorkShop,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MWorkShop>> result = new Result<IPage<MWorkShop>>();
		QueryWrapper<MWorkShop> queryWrapper = QueryGenerator.initQueryWrapper(mWorkShop, req.getParameterMap());
		Page<MWorkShop> page = new Page<MWorkShop>(pageNo, pageSize);
		IPage<MWorkShop> pageList = mWorkShopService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param mWorkShop
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<MWorkShop> add(@RequestBody MWorkShop mWorkShop) {
		Result<MWorkShop> result = new Result<MWorkShop>();
		try {
			mWorkShopService.save(mWorkShop);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param mWorkShop
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<MWorkShop> edit(@RequestBody MWorkShop mWorkShop) {
		Result<MWorkShop> result = new Result<MWorkShop>();
		MWorkShop mWorkShopEntity = mWorkShopService.getById(mWorkShop.getId());
		if(mWorkShopEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = mWorkShopService.updateById(mWorkShop);
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
			mWorkShopService.removeById(id);
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
	public Result<MWorkShop> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MWorkShop> result = new Result<MWorkShop>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.mWorkShopService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<MWorkShop> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MWorkShop> result = new Result<MWorkShop>();
		MWorkShop mWorkShop = mWorkShopService.getById(id);
		if(mWorkShop==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(mWorkShop);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, MWorkShop mWorkShop) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<MWorkShop> queryWrapper = QueryGenerator.initQueryWrapper(mWorkShop, request.getParameterMap());
      List<MWorkShop> pageList = mWorkShopService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<MWorkShop> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "车间信息列表");
      mv.addObject(NormalExcelConstants.CLASS, MWorkShop.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("车间信息列表数据", "导出人:Jeecg", "导出信息"));
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
              List<MWorkShop> listMWorkShops = ExcelImportUtil.importExcel(file.getInputStream(), MWorkShop.class, params);
              mWorkShopService.saveBatch(listMWorkShops);
              return Result.ok("文件导入成功！数据行数:" + listMWorkShops.size());
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
