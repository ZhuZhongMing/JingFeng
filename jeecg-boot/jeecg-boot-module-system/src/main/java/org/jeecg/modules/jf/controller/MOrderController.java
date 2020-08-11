package org.jeecg.modules.jf.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.MOrderlist;
import org.jeecg.modules.jf.entity.MOrder;
import org.jeecg.modules.jf.vo.MOrderPage;
import org.jeecg.modules.jf.service.IMOrderService;
import org.jeecg.modules.jf.service.IMOrderlistService;
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
 * @Description: 销售订单
 * @Author: jeecg-boot
 * @Date:   2019-10-21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/system/mOrder")
@Slf4j
public class MOrderController {
	@Autowired
	private IMOrderService mOrderService;
	@Autowired
	private IMOrderlistService mOrderlistService;


	/**
	 * 根据订单号查询订单明细
	 * @param orderid 订单编号
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/queryOrderListByMainId")
	public Result<List<MOrderlist>> queryOrderListByMainId(
														@RequestParam(name="orderid") String orderid,
														HttpServletRequest req) {
		Result<List<MOrderlist>> result = new Result<List<MOrderlist>>();
		QueryWrapper<MOrderlist> queryWrapper = new QueryWrapper<MOrderlist>();

		/*try {
			if (mWareHouse.getDeptid() != null && mWareHouse.getDeptid() !="") {
				queryWrapper.eq("deptid",mWareHouse.getDeptid());
			}
		} catch (Exception e) {
			log.info("全查询车间信息");
		} finally {
			List<MWorkShop> pageList = mWorkShopService.list(queryWrapper);
			result.setSuccess(true);
			result.setResult(pageList);
			return result;
		}*/

		List<MOrderlist> pageList = mOrderlistService.queryOrderListByMainId(orderid);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}



	/**
	 * 根据订单号分页列表查询订单明细
	 * @param orderid 订单编号
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/queryPageOrderList")
	public Result<IPage<MOrderlist>> queryPageOrderList(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												@RequestParam(name="orderid") String orderid,
											   HttpServletRequest req) {
		Result<IPage<MOrderlist>> result = new Result<IPage<MOrderlist>>();
		Page<MOrderlist> page = new Page<MOrderlist>(pageNo, pageSize);
		IPage<MOrderlist> pageList = mOrderlistService.queryPageOrderList(page,orderid);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}


	/**
	 * 全查询
	 * @param mOrder
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/getAll")
	public Result<List<MOrder>> getAll(MOrder mOrder,
											   HttpServletRequest req) {
		Result<List<MOrder>> result = new Result<List<MOrder>>();
		QueryWrapper<MOrder> queryWrapper = QueryGenerator.initQueryWrapper(mOrder, req.getParameterMap());
		queryWrapper.eq("orderstate",0);
		List<MOrder> pageList = mOrderService.list( queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}


	/*订单总数*/
	@GetMapping(value = "/getSumMaterial")
	public Result<Integer> getSumMaterial(HttpServletRequest req) {
		Result<Integer> result = new Result<Integer>();

		Integer sum = mOrderlistService.getSumMaterial();
		result.setSuccess(true);
		result.setResult(sum);
		return result;
	}

	/*今日订单总数*/
	@GetMapping(value = "/getTodaySumMaterial")
	public Result<Integer> getTodaySumMaterial(HttpServletRequest req) {
		Result<Integer> result = new Result<Integer>();

		SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
		Integer sum = mOrderlistService.getSumMaterial(f.format(new Date()));
		result.setSuccess(true);
		result.setResult(sum);
		return result;
	}

	/**
	 * 分页列表查询
	 * @param mOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<MOrder>> queryPageList(MOrder mOrder,
											   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											   HttpServletRequest req) {
		Result<IPage<MOrder>> result = new Result<IPage<MOrder>>();
		QueryWrapper<MOrder> queryWrapper = QueryGenerator.initQueryWrapper(mOrder, req.getParameterMap());
		Page<MOrder> page = new Page<MOrder>(pageNo, pageSize);
		IPage<MOrder> pageList = mOrderService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 *   添加
	 * @param mOrderPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<MOrder> add(@RequestBody MOrderPage mOrderPage) {
		Result<MOrder> result = new Result<MOrder>();
		try {
			MOrder mOrder = new MOrder();
			BeanUtils.copyProperties(mOrderPage, mOrder);
			//log.info("提交参数："+mOrderPage);
			//log.info("提交参数111："+mOrderlistList);
			mOrderService.saveMain(mOrder, mOrderPage.getMOrderlistList());
			MOrder m = mOrderService.findMaxID();
			result.setResult(m);
			//result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	/**
	 *   添加或修改明细
	 * @param
	 * @return
	 */
	@PostMapping(value = "/saveOrUpdate")
	public Result<MOrder> addList(@RequestBody MOrderlist mOrderlistList) {
		Result<MOrder> result = new Result<MOrder>();
		try {
			if (mOrderlistList.getOrdernum() == null) {
				mOrderlistList.setAllotnumber(0);
			} else {
				mOrderlistList.setAllotnumber(mOrderlistList.getOrdernum());
			}
		} catch (NullPointerException e) {
			log.info("添加明细发生空指针异常");
		} finally {
			mOrderlistService.saveOrUpdate(mOrderlistList);
			result.success("操作成功！");
			return result;
		}
	}



	/**
	 *  编辑
	 * @param mOrderPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<MOrder> edit(@RequestBody MOrderPage mOrderPage) {
		Result<MOrder> result = new Result<MOrder>();
		MOrder mOrder = new MOrder();
		BeanUtils.copyProperties(mOrderPage, mOrder);
		//log.info("提交参数："+mOrderPage);
		//log.info("提交参数111："+mOrderlistList);
		MOrder mOrderEntity = mOrderService.getById(mOrder.getId());
		if(mOrderEntity==null) {
			//result.error500("未找到对应实体");
			//添加
			mOrderService.saveMain(mOrder, mOrderPage.getMOrderlistList());
			MOrder m = mOrderService.findMaxID();
			result.setResult(m);
		}else {
			//修改
			boolean ok = mOrderService.updateById(mOrder);
			mOrderService.updateMain(mOrder, mOrderPage.getMOrderlistList());
			result.setResult(mOrder);
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
			mOrderService.delMain(id);
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
	public Result<MOrder> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MOrder> result = new Result<MOrder>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.mOrderService.delBatchMain(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}


    /**
     * 通过id查询订单，修改订单分配状态
     * @param orderid
     * @return
     */
    @GetMapping(value = "/updateById")
    public Result<MOrder> updateById(@RequestParam(name="orderid",required=true) String orderid) {
        /*Result<MOrder> result = new Result<MOrder>();
        MOrder mOrder = mOrderService.getOneById(id);
        if(mOrder==null) {
            result.error500("未找到对应实体");
        }else {
            result.setResult(mOrder);
            result.setSuccess(true);
        }*/
        Result<MOrder> result = new Result<MOrder>();
        Integer sum = mOrderlistService.sumAllotByid(orderid);
        //log.info("订单编号:"+orderid +"   "+sum);
        if (sum <= 0 || sum == null) {
            MOrder order = new MOrder();
            order.setId(orderid);
            order.setOrderstate("1");
            mOrderService.updateById(order);
            result.setResult(order);
        } else {
            MOrder order = new MOrder();
            order.setId(orderid);
            order.setOrderstate("0");
            mOrderService.updateById(order);
            result.setResult(order);
        }
        return result;
    }


	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<MOrder> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MOrder> result = new Result<MOrder>();
		MOrder mOrder = mOrderService.getOneById(id);
		/*MOrder mOrder = mOrderService.getById(id);*/
		if(mOrder==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(mOrder);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryMOrderlistByMainId")
	public Result<List<MOrderlist>> queryMOrderlistListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<MOrderlist>> result = new Result<List<MOrderlist>>();
		List<MOrderlist> mOrderlistList = mOrderlistService.selectByMainId(id);
		result.setResult(mOrderlistList);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, MOrder mOrder) {
		// Step.1 组装查询条件查询数据
		QueryWrapper<MOrder> queryWrapper = QueryGenerator.initQueryWrapper(mOrder, request.getParameterMap());
		List<MOrder> queryList = mOrderService.list(queryWrapper);
		// 过滤选中数据
		String selections = request.getParameter("selections");
		List<MOrder> mOrderList = new ArrayList<MOrder>();
		if(oConvertUtils.isEmpty(selections)) {
			mOrderList = queryList;
		}else {
			List<String> selectionList = Arrays.asList(selections.split(","));
			mOrderList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		}
		// Step.2 组装pageList
		List<MOrderPage> pageList = new ArrayList<MOrderPage>();
		for (MOrder main : mOrderList) {
			MOrderPage vo = new MOrderPage();
			BeanUtils.copyProperties(main, vo);
			List<MOrderlist> mOrderlistList = mOrderlistService.selectByMainId(main.getId());
			vo.setMOrderlistList(mOrderlistList);
			pageList.add(vo);
		}
		// Step.3 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "销售订单列表");
		mv.addObject(NormalExcelConstants.CLASS, MOrderPage.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("销售订单列表数据", "导出人:Jeecg", "导出信息"));
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
				List<MOrderPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MOrderPage.class, params);
				for (MOrderPage page : list) {
					MOrder po = new MOrder();
					BeanUtils.copyProperties(page, po);
					mOrderService.saveMain(po, page.getMOrderlistList());
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
