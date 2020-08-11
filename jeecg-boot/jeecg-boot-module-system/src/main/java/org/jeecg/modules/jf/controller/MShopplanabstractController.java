package org.jeecg.modules.jf.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.jf.entity.MOrderlist;
import org.jeecg.modules.jf.service.IMOrderService;
import org.jeecg.modules.jf.service.IMOrderlistService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.MShopplan;
import org.jeecg.modules.jf.entity.MShopplanabstract;
import org.jeecg.modules.jf.vo.MShopplanabstractPage;
import org.jeecg.modules.jf.service.IMShopplanabstractService;
import org.jeecg.modules.jf.service.IMShopplanService;
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
 * @Description: 车间计划摘要
 * @Author: jeecg-boot
 * @Date:   2019-11-19
 * @Version: V1.0
 */
@RestController
@RequestMapping("/system/mShopplanabstract")
@Slf4j
public class MShopplanabstractController {
	@Autowired
	private IMShopplanabstractService mShopplanabstractService;
	@Autowired
	private IMShopplanService mShopplanService;
	@Autowired
	private IMOrderlistService mOrderlistService;
	@Autowired
	private IMOrderService mOrderService;


	 /**
	  * 全查询计划
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/getAll")
	 public Result<List<MShopplanabstract>> getAll(HttpServletRequest req) {
		 Result<List<MShopplanabstract>> result = new Result<List<MShopplanabstract>>();
		 QueryWrapper<MShopplanabstract> queryWapper = new QueryWrapper<MShopplanabstract>();
		 queryWapper.eq("del_flag",0);
		 queryWapper.orderByDesc("create_time");//根据创建时间倒序排序
		 List<MShopplanabstract> pageList = mShopplanabstractService.list(queryWapper);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }




	 /**
	  * 根据车间计划编号分页列表查询车间计划明细
	  * @param shopplanid 计划编号
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/queryPageShopplanList")
	 public Result<IPage<MShopplan>> queryPageShopplanList(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														 @RequestParam(name="shopplanid") String shopplanid,
														 HttpServletRequest req) {
		 Result<IPage<MShopplan>> result = new Result<IPage<MShopplan>>();
		 Page<MShopplan> page = new Page<MShopplan>(pageNo, pageSize);
		 IPage<MShopplan> pageList = mShopplanService.queryPageShopplanList(page,shopplanid);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }
	
	/**
	  * 分页列表查询
	 * @param mShopplanabstract
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<MShopplanabstract>> queryPageList(MShopplanabstract mShopplanabstract,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MShopplanabstract>> result = new Result<IPage<MShopplanabstract>>();
		QueryWrapper<MShopplanabstract> queryWrapper = QueryGenerator.initQueryWrapper(mShopplanabstract, req.getParameterMap());
		Page<MShopplanabstract> page = new Page<MShopplanabstract>(pageNo, pageSize);
		IPage<MShopplanabstract> pageList = mShopplanabstractService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	 /**
	  *   添加或修改明细
	  * @param
	  * @return
	  */
	 @PostMapping(value = "/saveOrUpdate")
	 public Result<MShopplan> addList(@RequestBody MShopplan plan) {
		 Result<MShopplan> result = new Result<MShopplan>();
		 MOrderlist orderlist = new MOrderlist();

		 try {

		 	/*明细编号不为空时，明细表可分配数量*/
			 if (null != plan.getOrderid()) {
				 //log.info("修改订单明细。。"+plan.getOrderid());
				 orderlist = mOrderlistService.getById(plan.getOrderid());

				 if (orderlist.getAllotnumber() < plan.getNumber()) {
					 /*可分配数量少于计划数量*/
					 orderlist.setAllotnumber(0);//订单剩下的全部分配给该计划
					 orderlist.setAllotednumber((orderlist.getAllotednumber()+orderlist.getAllotnumber()));//修改已分配数量
				 } else {
					 /*可分配数量多于计划数量*/
					 orderlist.setAllotnumber((orderlist.getAllotnumber() - plan.getNumber()));
					 orderlist.setAllotednumber((orderlist.getAllotednumber()+plan.getNumber()));//修改已分配数量
				 }
				 mOrderlistService.updateById(orderlist);

				 /*log.info("修改："+plan);
				 if (plan.getId() != null) {
					 *//*修改*//*
					 MShopplan m = mShopplanService.getById(plan.getId());
					 plan.setNumber(plan.getNumber() + m.getNumber());//修改将数量累加上
					 log.info("修改。。"+m);
				 }*/

				 /*Integer sum = mOrderlistService.sumAllotByid(plan.getOrderid());
				 if (sum <= 0) {
				 	MOrder order = new MOrder();
				 	order.setId(plan.getOrderid());
				 	order.setOrderstate("1");
					 mOrderService.updateById(order);
				 }*/
			 }

		 } catch (NullPointerException e) {
			 log.info("添加或修改明细发生异常");
		 } finally {
			 mShopplanService.saveOrUpdate(plan);
			 result.success("操作成功！");
			 return result;
		 }
	 }
	
	/**
	  *   添加
	 * @param mShopplanabstractPage
	 * @return
	 */
	@PostMapping(value = "/add")
	//@RequestMapping(value="/add", method=RequestMethod.POST)
	public Result<MShopplanabstract> add(@RequestBody MShopplanabstractPage mShopplanabstractPage) {
		Result<MShopplanabstract> result = new Result<MShopplanabstract>();
		//log.info("参数："+mShopplanabstractPage);
		try {
			MShopplanabstract mShopplanabstract = new MShopplanabstract();
			BeanUtils.copyProperties(mShopplanabstractPage, mShopplanabstract);
			mShopplanabstractService.saveMain(mShopplanabstract, mShopplanabstractPage.getMShopplanList());
			MShopplanabstract m = mShopplanabstractService.findMaxID();
			result.setResult(m);
			//result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}


	
	/**
	  *  编辑
	 * @param mShopplanabstractPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<MShopplanabstract> edit(@RequestBody MShopplanabstractPage mShopplanabstractPage) {
		Result<MShopplanabstract> result = new Result<MShopplanabstract>();
		MShopplanabstract mShopplanabstract = new MShopplanabstract();
		BeanUtils.copyProperties(mShopplanabstractPage, mShopplanabstract);
		MShopplanabstract mShopplanabstractEntity = mShopplanabstractService.getById(mShopplanabstract.getId());
		if(mShopplanabstractEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = mShopplanabstractService.updateById(mShopplanabstract);
			mShopplanabstractService.updateMain(mShopplanabstract, mShopplanabstractPage.getMShopplanList());
			result.setResult(mShopplanabstract);
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
			mShopplanabstractService.delMain(id);
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
	public Result<MShopplanabstract> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MShopplanabstract> result = new Result<MShopplanabstract>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.mShopplanabstractService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public Result<MShopplanabstract> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MShopplanabstract> result = new Result<MShopplanabstract>();
		MShopplanabstract mShopplanabstract = mShopplanabstractService.getById(id);
		if(mShopplanabstract==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(mShopplanabstract);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryMShopplanByMainId")
	public Result<List<MShopplan>> queryMShopplanListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<MShopplan>> result = new Result<List<MShopplan>>();
		List<MShopplan> mShopplanList = mShopplanService.selectByMainId(id);
		result.setResult(mShopplanList);
		result.setSuccess(true);
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, MShopplanabstract mShopplanabstract) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<MShopplanabstract> queryWrapper = QueryGenerator.initQueryWrapper(mShopplanabstract, request.getParameterMap());
      List<MShopplanabstract> queryList = mShopplanabstractService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<MShopplanabstract> mShopplanabstractList = new ArrayList<MShopplanabstract>();
      if(oConvertUtils.isEmpty(selections)) {
    	  mShopplanabstractList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  mShopplanabstractList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<MShopplanabstractPage> pageList = new ArrayList<MShopplanabstractPage>();
      for (MShopplanabstract main : mShopplanabstractList) {
          MShopplanabstractPage vo = new MShopplanabstractPage();
          BeanUtils.copyProperties(main, vo);
          List<MShopplan> mShopplanList = mShopplanService.selectByMainId(main.getId());
          vo.setMShopplanList(mShopplanList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "车间计划摘要列表");
      mv.addObject(NormalExcelConstants.CLASS, MShopplanabstractPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("车间计划摘要列表数据", "导出人:Jeecg", "导出信息"));
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
              List<MShopplanabstractPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MShopplanabstractPage.class, params);
              for (MShopplanabstractPage page : list) {
                  MShopplanabstract po = new MShopplanabstract();
                  BeanUtils.copyProperties(page, po);
                  mShopplanabstractService.saveMain(po, page.getMShopplanList());
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
