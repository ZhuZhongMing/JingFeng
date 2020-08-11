package org.jeecg.modules.jf.controller;

import java.math.BigDecimal;
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
import org.jeecg.modules.jf.entity.MBom;
import org.jeecg.modules.jf.entity.MProductMap;
import org.jeecg.modules.jf.service.IMBomService;
import org.jeecg.modules.jf.service.IMProductMapService;
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
 * @Description: 产品信息
 * @Author: jeecg-boot
 * @Date:   2019-12-09
 * @Version: V1.0
 */
@Slf4j
@Api(tags="产品信息")
@RestController
@RequestMapping("/system/mProductMap")
public class MProductMapController {

	@Autowired
	private IMProductMapService mProductMapService;
	@Autowired
	private IMBomService iMBomService;



	 /**
	  * 全查询
	  * @param mProductMap
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "产品信息-分页列表查询")
	 @ApiOperation(value="产品信息-分页列表查询", notes="产品信息-分页列表查询")
	 @GetMapping(value = "/getAllList")
	 public Result<List<MProductMap>> getAllList(MProductMap mProductMap,
													  HttpServletRequest req) {
		 Result<List<MProductMap>> result = new Result<List<MProductMap>>();
		 QueryWrapper<MProductMap> queryWrapper = QueryGenerator.initQueryWrapper(mProductMap, req.getParameterMap());
		 List<MProductMap> pageList = mProductMapService.list( queryWrapper);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }
	
	/**
	  * 分页列表查询
	 * @param mProductMap
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "产品信息-分页列表查询")
	@ApiOperation(value="产品信息-分页列表查询", notes="产品信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<MProductMap>> queryPageList(MProductMap mProductMap,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MProductMap>> result = new Result<IPage<MProductMap>>();
		QueryWrapper<MProductMap> queryWrapper = QueryGenerator.initQueryWrapper(mProductMap, req.getParameterMap());
		Page<MProductMap> page = new Page<MProductMap>(pageNo, pageSize);
		IPage<MProductMap> pageList = mProductMapService.pageList(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param mProductMap
	 * @return
	 */
	@AutoLog(value = "产品信息-添加")
	@ApiOperation(value="产品信息-添加", notes="产品信息-添加")
	@PostMapping(value = "/add")
	public Result<MProductMap> add(@RequestBody MProductMap mProductMap) {
		Result<MProductMap> result = new Result<MProductMap>();
		try {
			mProductMapService.save(mProductMap);

			/*添加产品BOM*/
			addBom(mProductMap);

			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/*添加产品BOM*/
	 public void addBom(MProductMap mProductMap){
		 if (mProductMap.getIsresilient() == 0) {
			 /*不带反弹传动片*/
			 MBom b1 = new MBom();
			 b1.setMaterialencode(mProductMap.getId());//产品编号
			 b1.setNumber(new BigDecimal("1"));//用量
			 b1.setIsleaf(0);//非子节点
			 b1.setLevel(0);//最高级，根节点
			 b1.setParentid("null");
			 iMBomService.save(b1);
			 MBom b2 = new MBom();
			 b2.setMaterialencode(mProductMap.getTopblade());//上刀
			 b2.setNumber(new BigDecimal(mProductMap.getProductlength() / 4));//用量
			 b2.setIsleaf(1);//子节点
			 b2.setLevel(1);//1-子节点
			 b2.setParentid(mProductMap.getId());
			 iMBomService.save(b2);
			 MBom b3 = new MBom();
			 b3.setMaterialencode(mProductMap.getBottomblade());//下刀
			 b3.setNumber(new BigDecimal(mProductMap.getProductlength() / 4));//用量
			 b3.setIsleaf(1);//子节点
			 b3.setLevel(1);//1-子节点
			 b3.setParentid(mProductMap.getId());
			 iMBomService.save(b3);
			 MBom b4 = new MBom();
			 b4.setMaterialencode(mProductMap.getConnectionblade());//连接片
			 b4.setNumber(new BigDecimal(((mProductMap.getProductlength()  - (mProductMap.getProductlength() / 4))* 2)));//用量
			 b4.setIsleaf(1);//子节点
			 b4.setLevel(1);//1-子节点
			 b4.setParentid(mProductMap.getId());
			 iMBomService.save(b4);
			 MBom b5 = new MBom();
			 b5.setMaterialencode(mProductMap.getDriverblade());//传动片
			 b5.setNumber(new BigDecimal(mProductMap.getProductlength()));//用量
			 b5.setIsleaf(1);//子节点
			 b5.setLevel(1);//1-子节点
			 b5.setParentid(mProductMap.getId());
			 iMBomService.save(b5);
			 MBom b6 = new MBom();
			 b6.setMaterialencode(mProductMap.getRivet());//铆钉
			 b6.setNumber(new BigDecimal(mProductMap.getProductlength() * 2));//用量
			 b6.setIsleaf(1);//子节点
			 b6.setLevel(1);//1-子节点
			 b6.setParentid(mProductMap.getId());
			 iMBomService.save(b6);
		 } else if (mProductMap.getIsresilient() == 1) {
			 /*带反弹传动片*/
			 MBom b1 = new MBom();
			 b1.setMaterialencode(mProductMap.getId());//产品编号
			 b1.setNumber(new BigDecimal("1"));//用量
			 b1.setIsleaf(0);//非子节点
			 b1.setLevel(0);//最高级，根节点
			 b1.setParentid("null");
			 iMBomService.save(b1);
			 MBom b2 = new MBom();
			 b2.setMaterialencode(mProductMap.getTopblade());//上刀
			 b2.setNumber(new BigDecimal(mProductMap.getProductlength() / 4));//用量
			 b2.setIsleaf(1);//子节点
			 b2.setLevel(1);//1-子节点
			 b2.setParentid(mProductMap.getId());
			 iMBomService.save(b2);
			 MBom b3 = new MBom();
			 b3.setMaterialencode(mProductMap.getBottomblade());//下刀
			 b3.setNumber(new BigDecimal(mProductMap.getProductlength() / 4));//用量
			 b3.setIsleaf(1);//子节点
			 b3.setLevel(1);//1-子节点
			 b3.setParentid(mProductMap.getId());
			 iMBomService.save(b3);
			 MBom b4 = new MBom();
			 b4.setMaterialencode(mProductMap.getConnectionblade());//连接片
			 b4.setNumber(new BigDecimal(((mProductMap.getProductlength()  - (mProductMap.getProductlength() / 4))* 2)));//用量
			 b4.setIsleaf(1);//子节点
			 b4.setLevel(1);//1-子节点
			 b4.setParentid(mProductMap.getId());
			 iMBomService.save(b4);
			 MBom b5 = new MBom();
			 b5.setMaterialencode(mProductMap.getDriverblade());//传动片
			 b5.setNumber(new BigDecimal(mProductMap.getProductlength() / 2));//用量
			 b5.setIsleaf(1);//子节点
			 b5.setLevel(1);//1-子节点
			 b5.setParentid(mProductMap.getId());
			 iMBomService.save(b5);
			 MBom b6 = new MBom();
			 b6.setMaterialencode(mProductMap.getRivet());//铆钉
			 b6.setNumber(new BigDecimal(mProductMap.getProductlength() * 2));//用量
			 b6.setIsleaf(1);//子节点
			 b6.setLevel(1);//1-子节点
			 b6.setParentid(mProductMap.getId());
			 iMBomService.save(b6);
			 MBom b7 = new MBom();
			 b7.setMaterialencode(mProductMap.getDriverblade());//传动片
			 b7.setNumber(new BigDecimal(mProductMap.getProductlength() / 2));//用量
			 b7.setIsleaf(1);//子节点
			 b7.setLevel(1);//1-子节点
			 b7.setParentid(mProductMap.getId());
			 iMBomService.save(b7);

		 }
	 }
	 /*删除产品BOM*/
	 public void delBom(String id){
		 QueryWrapper<MBom> queryWrapper = new QueryWrapper<MBom>();
		 queryWrapper.eq("materialencode",id).or().eq("parentid",id);
		 iMBomService.remove(queryWrapper);
	 }
	
	/**
	  *  编辑
	 * @param mProductMap
	 * @return
	 */
	@AutoLog(value = "产品信息-编辑")
	@ApiOperation(value="产品信息-编辑", notes="产品信息-编辑")
	@PutMapping(value = "/edit")
	public Result<MProductMap> edit(@RequestBody MProductMap mProductMap) {
		Result<MProductMap> result = new Result<MProductMap>();
		MProductMap mProductMapEntity = mProductMapService.getById(mProductMap.getId());
		if(mProductMapEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = mProductMapService.updateById(mProductMap);
			//TODO 返回false说明什么？
			if(ok) {
				/*删除产品BOM*/
				delBom(mProductMap.getId());
				/*重新添加BOM*/
				addBom(mProductMap);
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
	@AutoLog(value = "产品信息-通过id删除")
	@ApiOperation(value="产品信息-通过id删除", notes="产品信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			mProductMapService.removeById(id);
			/*删除产品BOM*/
			delBom(id);
			//System.out.println("id："+id);
			/*QueryWrapper<MBom> queryWrapper1 = new QueryWrapper<MBom>();
			queryWrapper1.eq("parentid",id);
			iMBomService.remove(queryWrapper1);*/

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
	@AutoLog(value = "产品信息-批量删除")
	@ApiOperation(value="产品信息-批量删除", notes="产品信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<MProductMap> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MProductMap> result = new Result<MProductMap>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.mProductMapService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品信息-通过id查询")
	@ApiOperation(value="产品信息-通过id查询", notes="产品信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<MProductMap> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MProductMap> result = new Result<MProductMap>();
		MProductMap mProductMap = mProductMapService.getById(id);
		if(mProductMap==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(mProductMap);
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
      QueryWrapper<MProductMap> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              MProductMap mProductMap = JSON.parseObject(deString, MProductMap.class);
              queryWrapper = QueryGenerator.initQueryWrapper(mProductMap, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<MProductMap> pageList = mProductMapService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "产品信息列表");
      mv.addObject(NormalExcelConstants.CLASS, MProductMap.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("产品信息列表数据", "导出人:Jeecg", "导出信息"));
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
              List<MProductMap> listMProductMaps = ExcelImportUtil.importExcel(file.getInputStream(), MProductMap.class, params);
              mProductMapService.saveBatch(listMProductMaps);
              return Result.ok("文件导入成功！数据行数:" + listMProductMaps.size());
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
